package com.uni.desk.ssh2;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SCPInputStream;

import java.io.IOException;

public class SshHandler {

    public Connection logInLinux(ServerInfo info){
        Connection conn = new Connection(info.getIp(),info.getPort());
        try {
            //连接远程服务器
            conn.connect();
            //使用用户名和密码登录
            conn.authenticateWithPassword(info.getUsername(),info.getPassword());
        } catch (
                IOException e) {
            System.err.printf("用户%s密码%s登录服务器%s失败！", info.getUsername(),info.getPassword(),info.getIp());
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 下载服务器文件到本地目录
     * @param fileName 服务器文件
     * @param localPath 本地目录
     */
    public void copyFile(Connection conn, String fileName,String localPath){
        SCPClient sc = new SCPClient(conn);
        try {
            SCPInputStream scpInputStream = sc.get(fileName);
//            sc.put()
//            sc.get(fileName, localPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
