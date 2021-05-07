package com.uni.desk.ssh2;

import com.trilead.ssh2.Connection;
import com.uni.desk.base.CommonBusinessException;
import com.uni.desk.base.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class  SshServer {
    @Resource
    private ServerInfo serverInfo;

    /**
     * 从配置文件{@link ServerInfo}获取配置信息
     * @return
     */
    public  Connection getConn() {
        log.info("服务器配置信息：{}", Optional.ofNullable(serverInfo.toString()).orElse("空"));
        Connection conn = new Connection(serverInfo.getIp(), serverInfo.getPort());
        if(conn.isAuthenticationComplete()) {
            return conn;
        }
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(serverInfo.getUsername(), serverInfo.getPassword());
            if(!isAuthenticated) {
                throw new ConnectException("authentication failed！");
            }
            return conn;
        } catch (Exception e) {
            log.error("Connect error：", e);
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 读取远程文件的文件流
     * @param path
     * @return
     */
    public InputStream readFile(String path){
        InputStream inputStream = null;
        try {
            inputStream = SshHandler.readFile(getConn(), path, serverInfo.getIp());
        } catch (IOException e) {
            log.error("读取远程文件流IO异常");
            e.printStackTrace();
        } catch (InterruptedException e) {
            log.error("读取远程文件流被中断");
            e.printStackTrace();
        }
        return inputStream;

    }

    /**
     * 获取远程指定目录下的带指定后缀所有文件
     * @param dir
     * @param suffix
     * @return
     * @throws IOException
     */
    public  Set<String> getFileAbsolutePaths(String dir, String suffix) throws IOException {
        return getFileAbsolutePaths(dir,"",suffix);
    }
    public  Set<String> getFileAbsolutePaths(String dir, String prefix,String suffix) {
        return SshHandler.getFileAbsolutePaths(dir,getConn(),prefix,suffix);
    }

}
