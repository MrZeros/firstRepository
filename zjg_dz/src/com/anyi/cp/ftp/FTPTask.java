package com.anyi.cp.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FTPTask {
	
//	private static Logger log=Logger.getLogger(FTPTask.class);
	public  File getFileFromFTP(String ftpip, int ftpport,
			String ftpuser, String ftppassword, String ftpfileremotepath,String prefix,String suffix)
			{		
		FTPClient ftpClient=new FTPClient();
		File localFile=null;
		try {
			//连接ftp服务器
			ftpClient.connect(ftpip, ftpport);
			//登陆ftp服务器
			ftpClient.login(ftpuser, ftppassword);
			//验证服务器是否登陆成功
			int replyCode = ftpClient.getReplyCode();
			if(!FTPReply.isPositiveCompletion(replyCode)){
				return null;//登陆失败，返回
			}
			List fileList=new ArrayList();//存放从ftp上获取的文件集合
			ftpClient.enterLocalPassiveMode();// 设置当前数据的链接模式
			
			Date date=new Date();
			SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
			String name=format.format(date);
			StringBuffer sb=new StringBuffer();
			sb.append(prefix);
			sb.append(name);
			sb.append(suffix);
			String fileName=sb.toString();
			sb=new StringBuffer();
			sb.append(ftpfileremotepath);
			sb.append("\\");
			sb.append(fileName);
			FTPFile[] ftpFiles = ftpClient.listFiles(sb.toString());//获取文件集合
			
			int size=(ftpFiles==null)?0:ftpFiles.length;
			if(size==0){
				return null;//没有获取到文件，返回结束
			}
			FileOutputStream out=null;
			boolean retrieveFile = false;
			localFile=new File(fileName);			
			try{				
				out=new FileOutputStream(localFile);
				sb=new StringBuffer();
				sb.append(ftpfileremotepath);
				sb.append("\\");
				sb.append(fileName);
				System.out.println("获取文件中.........");
				retrieveFile = ftpClient.retrieveFile(
						sb.toString(), out);
				out.close();
			}catch (Exception e) {
				System.out.println(retrieveFile);
			}
			if(!retrieveFile){
				return null;
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return localFile;
	}
	public static void main(String[] args){
		FTPTask task=new FTPTask();
		File f=task.getFileFromFTP("192.168.10.130", 21, "ftp_test", "1", "/nh/", "yhzf", ".txt");
		System.out.println(f.exists());
	}
}
