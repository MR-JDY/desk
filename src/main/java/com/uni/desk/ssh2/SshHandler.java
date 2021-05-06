package com.uni.desk.ssh2;


import com.trilead.ssh2.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ConnectException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

@Slf4j
public class SshHandler {

    public static Connection logInLinux(ServerInfo info){
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
    /*public void copyFile(Connection conn, String fileName,String localPath){
        SCPClient sc = new SCPClient(conn);
        try {
            SCPInputStream scpInputStream = sc.get(fileName);
//            sc.put()
//            sc.get(fileName, localPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 读取远端文件流
     * @param filePath
     * @return
     * @throws IOException
     */
    public static InputStream readFile(Connection conn, String filePath, String ip) throws IOException, InterruptedException {
        Session session = getSession(conn);
        //获取文件大小
        session.execCommand("du -b ".concat(filePath));
        InputStream sizeIn = new StreamGobbler(session.getStdout());
        //将字节流向字符流的转换。
        InputStreamReader isr = new InputStreamReader(sizeIn);//读取
        //创建字符流缓冲区
        BufferedReader bufr = new BufferedReader(isr);//缓冲
        String line;
        int fileSize = 0;
        while((line = bufr.readLine())!=null){
            String[] fileAttr = line.split("\t");
            fileSize = Integer.parseInt(fileAttr[0]);
        }
        isr.close();
        session.close();
        log.info("          【" + ip + "】【文件大小】" + fileSize);


        session = getSession(conn);
        session.execCommand("cat ".concat(filePath));
        //休眠2秒再获取返回信息，防止网络传输过程中延迟造成读取文件大小为0字节
        //Thread.sleep(2000);
        InputStream is = new StreamGobbler(session.getStdout());
        session.waitForCondition(ChannelCondition.EXIT_STATUS, 1000);
        //获取指令是否成功执行:0－成功,非0－失败.
        //int ret = session.getExitStatus();
        //log.info("          【" + ip + "】【命令执行结果】" + (0 == ret ? "---成功---" : "====失败===="));
        log.info("          【" + ip + "】【获取到当前文件流为】" + is.available());
        int i = 0;
        while (fileSize != is.available()) {
            i++;
            Thread.sleep(1000);
            log.info("          【" + ip + "】【第" + i + "次获取到当前文件流为】" + is.available());
        }
        session.close();
        return is;
    }

    /**
     * 获取conn
     * @param ip
     * @param port
     * @param user
     * @param pwd
     * @return
     */
    public static Connection getConn(String ip, int port, String user, String pwd) {
        Connection conn = new Connection(ip, port);
        if(conn.isAuthenticationComplete()) {
            return conn;
        }
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(user, pwd);
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
     * 获取session
     * @param conn
     * @return
     * @throws IOException
     */
    public static Session getSession(Connection conn) throws IOException{
        Session session = conn.openSession();
        return session;
    }

    /**
     * 获取SFTPv3Client
     * @param conn
     * @return
     * @throws IOException
     */
    public static SFTPv3Client getClient(Connection conn) throws IOException {
        SFTPv3Client client = new SFTPv3Client(conn);
        return client;
    }

    /**
     * 获取指定目录下所有文件的绝对路径
     * @param dir 指定目录名称
     * @param suffix 需要获取的文件的后缀名
     * @return
     */
    public static Set<String> getFileAbsolutePaths(String dir,Connection conn,String suffix,String prefix) throws IOException {
        //处理directory后面的"/"号
        if(!dir.endsWith("/")){
            dir += "/";
        }
        String directory =dir;
        SFTPv3Client client = getClient(conn);
        Vector ls = client.ls(directory);
        Set collect = (Set) ls.stream().filter(obj-> {
            SFTPv3DirectoryEntry obj1 = (SFTPv3DirectoryEntry) obj;
            return obj1.filename.endsWith(suffix) && obj1.filename.startsWith(prefix);
        }).map(entry -> {
            SFTPv3DirectoryEntry entry1 = (SFTPv3DirectoryEntry) entry;
            return directory + entry1.filename;
        }).collect(Collectors.toSet());
        return collect;
    }

    /**
     * 从路径中提取文件名
     * @param filePath
     * @return
     */
    public static String extractFileName(String filePath){
        File file = new File(filePath);
        String name = file.getName();
        String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
        return fileName;
    }
}
