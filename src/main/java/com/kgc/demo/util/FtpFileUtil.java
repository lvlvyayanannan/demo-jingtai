package com.kgc.demo.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName FtpFileUtil
 * @Description TODO
 * @Author yaozhaobao
 * @Date 2019/6/15 18:42
 * @Version 1.0
 **/
public class FtpFileUtil {
    //ftp服务器ip地址
    private static final String FTP_ADDRESS = "106.52.87.226";
    //端口号
    private static final int FTP_PORT = 21;
    //用户名
    private static final String FTP_USERNAME = "jt84";
    //密码
    private static final String FTP_PASSWORD = "jt84";
    //图片路径
    private static final String FTP_BASEPATH = "/home/jt84/img";


    //跟服务器建立连接,然后将文件文件上传服务器方法
    public  static boolean uploadFile(String originFileName, InputStream input){
        //服务器建立连接请求
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        try {
            int reply;   //状态码
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();  //服务器给的响应回复
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            //服务器建立连接请求并且连接成功,传输文件
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(FTP_BASEPATH );
            ftp.changeWorkingDirectory(FTP_BASEPATH );
            ftp.storeFile(originFileName,input);
            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }



}
