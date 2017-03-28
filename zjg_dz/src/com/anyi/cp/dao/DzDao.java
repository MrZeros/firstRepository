package com.anyi.cp.dao;

import java.util.List;

import com.anyi.cp.entity.DZInfo;

public interface DzDao {
	public boolean insert(List dz);
	public boolean delete(String fileName);
}
