package com.anyi.cp.util;

import java.util.List;

import com.anyi.cp.dao.DzDao;
import com.anyi.cp.dao.impl.DzDaoImpl;
import com.anyi.cp.entity.DZInfo;
import com.anyi.cp.ftp.FTPService;

public class DBTools {
	private DzDao dao=new DzDaoImpl();
	public boolean insertDzInfo(List dz){
		return dao.insert(dz);
	}
	public boolean delete(String fileName){
		return dao.delete(fileName);
	}
	
}
