package com.sftp;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPSClient;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class TestSftp {
	public static void main(String args[]) {
		try {
			Session session  = createSession();
			try {
				upload(session,"target/test.txt","/home/bufanli/test.txt");
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public static Session createSession() throws JSchException {
        Session session= null;
		JSch jSch=new JSch(); //创建JSch对象
        session=jSch.getSession("bufanli","192.168.1.107",22);//根据用户名，主机ip和端口获取一个Session对象
        session.setPassword("bflbfl1980314"); //设置密码
        Properties config=new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);//为Session对象设置properties
        session.setTimeout(5000);//设置超时
        session.connect();//通过Session建立连接
        return session;
    }
    public void download(Session session,String src,String dst) throws JSchException, SftpException{
        //src linux服务器文件地址，dst 本地存放地址
        ChannelSftp channelSftp=(ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        channelSftp.get(src, dst);
        channelSftp.quit();
    }
    public static void upload(Session session,String src,String dst) throws JSchException,SftpException{
        //src 本机文件地址。 dst 远程文件地址
        ChannelSftp channelSftp=(ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        channelSftp.put(src, dst);
        channelSftp.quit();
    }

}
