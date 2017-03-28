package com.anyi.cp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.anyi.cp.dao.DzDao;
import com.anyi.cp.entity.DZInfo;
import com.anyi.cp.util.DBUtil;

public class DzDaoImpl  implements DzDao{


	public boolean insert(List dz) {
		boolean flag=true;
		System.out.println("开始插入数据。。。。");
		DBUtil db=new DBUtil();
		Connection conn=db.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="insert into BA_CP_VOUCHER_DZ_AUTO_TEMP values(?,to_date(?,'yyyymmdd'),?,?,?,?,?)";
		try {
			conn.setAutoCommit(false);
			ps=conn.prepareStatement(sql);
			for (int i=0;i<dz.size();i++) {
				DZInfo dzInfo=(DZInfo) dz.get(i);
				ps.setString(1, dzInfo.getBankCode());
				ps.setString(2, dzInfo.getDate());
				ps.setDouble(3,dzInfo.getMoney());
				ps.setString(4, dzInfo.getZy());
				ps.setString(5, dzInfo.getAccount());
				ps.setString(6, dzInfo.getaccountName());
				ps.setString(7, dzInfo.getFileName());
				ps.addBatch();
			}
			ps.executeBatch();
			ps.clearBatch();
			conn.commit();
			System.out.println("插入成功");
		} catch (SQLException e) {
			flag=false;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			db.close(conn, rs, ps);
		}
		return flag;
	}


	public boolean delete(String fileName) {
		System.out.println("开始删除数据。。。。");
		boolean flag=true;
		DBUtil db=new DBUtil();
		Connection conn=db.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="delete from BA_CP_VOUCHER_DZ_AUTO_TEMP where file_name like '%' || ? ||'%'";
		String sql2="delete from BA_CP_VOUCHER_DZ_AUTO where file_name like '%' || ? ||'%' ";
		try {
			conn.setAutoCommit(false);
			ps=conn.prepareStatement(sql);
			ps.setString(1, fileName);
			ps.executeUpdate();
			ps=conn.prepareStatement(sql2);
			ps.setString(1, fileName);
			ps.executeUpdate();
		} catch (SQLException e) {
			flag=false;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}finally{
				db.close(conn, rs, ps);
			}
		}
		if(flag){
			System.out.println("删除成功");
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

}
