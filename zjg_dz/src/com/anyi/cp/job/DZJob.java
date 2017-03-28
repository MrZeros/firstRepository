package com.anyi.cp.job;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;


import com.anyi.cp.entity.DZInfo;
import com.anyi.cp.ftp.FTPService;
import com.anyi.cp.util.DBTools;

public class DZJob {
//	private static Logger logger = Logger.getLogger(DZJob.class);
	private DBTools dbtools=new DBTools();
	private FTPService service=new FTPService();
	public DZJob(){
		
	}
	public void taskJob(){
		try{
			System.out.println("启动DZJOB定时任务。。。。。");
			System.out.println(nhTaskJob());
			System.out.println("本次TASKJOB定时任务结束。。。。。");
		}catch(Exception e){
//			logger.error("上传定时任务出错,原因是："+e.getMessage());
		}
	}
	private String nhTaskJob(){
		System.out.println("进入.......");
		List dzInfo=service.parseNHFile();
		if(dzInfo==null)return "获取空文件";
		System.out.println(dzInfo);
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		StringBuffer sb=new StringBuffer();
		sb.append(format.format(date));
		sb.append("nh");
		String fileName=sb.toString();
		if(dbtools.delete(fileName)){
			dbtools.insertDzInfo(dzInfo);
		}
		return "工作完毕";
	}
	public static void main(String[] args) {
		
	}
}
