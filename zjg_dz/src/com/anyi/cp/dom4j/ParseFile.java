package com.anyi.cp.dom4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ParseFile {

	public static List parseTxtFile(File file){
		System.out.println("׼�������ļ�����������");
		List list=new ArrayList();
		if(file==null){
			return null;
		}
		System.out.println("��ʼ����");
		BufferedReader sc=null;
		try {
//			FileInputStream fis=new FileInputStream(file);
			sc=new BufferedReader(new FileReader(file));
			sc.readLine();
			String data=null;
			System.out.println("�����ļ��С�������");
			while((data=sc.readLine())!=null&&!"".equals(data.trim())){
				String[] datas=data.split("\\s*&\\s*");
				list.add(datas);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				sc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(file.exists()){
				file.delete();
			}
		}
		return list;
	}
}
