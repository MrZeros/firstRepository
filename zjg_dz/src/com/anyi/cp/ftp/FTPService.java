package com.anyi.cp.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.anyi.cp.dom4j.ParseFile;
import com.anyi.cp.entity.DZInfo;



public class FTPService {
//	private static Logger logger = Logger.getLogger(FTPService.class.getName());
	//ftp基本配置数据
	private static String ftpip="";
	private static int ftpport=0;
	private static String ftpuser = "";
	private static String ftppassword = "";
	// 上传配置
	private static int issendopen = 0;
	//工行远端目录
	private static String ftpghremotepath="";
	//农行远端目录
	private static String ftpnhremotepath="";
	private static String prefix="";
	private static String suffix="";
//	static{
//		Properties p = new Properties();
//		try{
//				
//			p.load(new FileInputStream(new File("src/Ftp.properties")));
////			p.load(FTPService.class.getClassLoader().getResourceAsStream("Ftp.properties"));
//			ftpip = p.getProperty("ftpip");
//			ftpport = Integer.parseInt(p.getProperty("ftpport"));
//			ftpuser = p.getProperty("ftpuser");
//			ftppassword = p.getProperty("ftppassword");
//			ftpghremotepath=p.getProperty("ftpghremotepath");
//			ftpnhremotepath=p.getProperty("ftpnhremotepath");
//			prefix=p.getProperty("prefix");
//			suffix=p.getProperty("suffix");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	public FTPService(){
		loadProp();
	}
	private  void loadProp(){
		Properties p = new Properties();
		try{
			p.load(this.getClass().getClassLoader().getResourceAsStream("Ftp.properties"));
			ftpip = p.getProperty("ftpip");
			ftpport = Integer.parseInt(p.getProperty("ftpport"));
			ftpuser = p.getProperty("ftpuser");
			ftppassword = p.getProperty("ftppassword");
			ftpghremotepath=p.getProperty("ftpghremotepath");
			ftpnhremotepath=p.getProperty("ftpnhremotepath");
			prefix=p.getProperty("prefix");
			suffix=p.getProperty("suffix");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public List parseNHFile(){
		System.out.println("开始获取文件。。。。。");
		FTPTask task=new FTPTask();
		File remoteFile=task.getFileFromFTP(ftpip, ftpport, ftpuser, ftppassword, ftpnhremotepath, prefix, suffix);
		System.out.println("获取文件完毕。。。。");
		List list=parseFile(remoteFile);
		System.out.println(list==null);
		if(list==null||list.size()==0)return null;
		System.out.println("解析文件完毕。。。。。");
		List dzinfo=new ArrayList();
		for (int i=0;i<list.size();i++) {
			String[] data=(String[]) list.get(i);
			String bankCode=data[2];
			String date=data[1];
			double money=Double.parseDouble(data[6]);
			String zy=data[7];
			String account=data[10];
			String accountName=data[12];
			String []fileName=remoteFile.getName().split("\\.");
			StringBuffer sb=new StringBuffer();
			sb.append(fileName[0]);
			sb.append("nh");
			DZInfo dz=new DZInfo(bankCode, date, money, account, accountName, sb.toString(), zy);
			dzinfo.add(dz);
		}
		System.out.println("获取dzInfo结束.....");
		return dzinfo;
	}
//	public List parseGHFile(){
//		FTPTask task=new FTPTask();
//		File remoteFile=task.getFileFromFTP(ftpip, ftpport, ftpuser, ftppassword, ftpghremotepath, prefix, suffix);
//		List list=parseFile(remoteFile);
//		if(list==null)return null;
//		List dzinfo=new ArrayList();
//		for (int i=0;i<list.size();i++) {
//			String[] data=(String[]) list.get(i);
//			String bankCode=data[7];
//			String date=data[2];
//			double money=Double.parseDouble(data[4]);
//			String zy=data[9];
//			String account=data[1].startsWith("\\d")?data[1]:data[1].substring(1);
//			String accountName=data[6];
//			String []fileName=remoteFile.getName().split("\\.");
//			DZInfo dz=new DZInfo(bankCode, date, money, account, accountName, fileName[0]+"gh", zy);
//			dzinfo.add(dz);
//		}
//		return dzinfo;
//	}
	private List parseFile(File remoteFile){
		return ParseFile.parseTxtFile(remoteFile);
	}
	public static void main(String[] args){
		FTPService service=new FTPService();
		List data=service.parseNHFile();
		System.out.println(data);
	}
}
