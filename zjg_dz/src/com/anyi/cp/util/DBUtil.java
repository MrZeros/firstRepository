package com.anyi.cp.util;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DBUtil {
	  private static String username="";
		private static String password="";
		private static String url="";
		private static String driver="";
//		private static Logger logger = Logger.getLogger(DBUtil.class);
		private static ThreadLocal stfs = new ThreadLocal();
		public DBUtil(){
			loadProp();
		}
		private void loadProp(){
			Properties p = new Properties();
			try {
//				String filePath=DBUtil.class.getClassLoader().getResource("db.properties").getFile();
				//InputStream inconfigstream=DBUtil.class.getClassLoader().getResourceAsStream("db.properties"); 
				p.load(this.getClass().getClassLoader().getResourceAsStream("db.properties"));
				username = p.getProperty("username");
				password = p.getProperty("password");
				url = p.getProperty("url");
				driver = p.getProperty("driver");
				Class.forName(driver);
			} catch (Exception e) {
//				logger.debug(e);
			}
		}
		/**
		 * 获取链接
		 * @return
		 */
		public  Connection getConnection() {
			
			Connection connection = (Connection)stfs.get();
			if (connection == null) {
				try {
					connection = DriverManager.getConnection(url, username,
							password);
					System.out.println("#####链接数据库成功###");
					stfs.set(connection);
				} catch (SQLException e) {
//					logger.debug(e);
				} 
			}
			return connection;
		}
		/**
		 * 关闭链接
		 * @param conn
		 * @param rs
		 * @param ps
		 */
		public  void close(Connection conn, ResultSet rs,
				PreparedStatement ps) {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
//				logger.debug(e);
			}
		}
//		public static void main(String[] args){
//			Connection conn=getConnection();
//			System.out.println(conn);
//		}
}
