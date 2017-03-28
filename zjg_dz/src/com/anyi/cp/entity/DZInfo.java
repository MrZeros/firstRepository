package com.anyi.cp.entity;

import java.io.Serializable;

public class DZInfo implements Serializable {
	private String bankCode;
	private String date;
	private double money;//交易金额
	private String account;//对方的账号
	private String accountName;//对方户名
	private String fileName;
	private String zy;//摘要
	public DZInfo(){
		
	}
	public DZInfo(String bankCode, String date, double money, String account,
			String accountName, String fileName, String zy) {
		super();
		this.bankCode = bankCode;
		this.date = date;
		this.money = money;
		this.account = account;
		this.accountName = accountName;
		this.fileName = fileName;
		this.zy = zy;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getaccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getZy() {
		return zy;
	}
	public void setZy(String zy) {
		this.zy = zy;
	}
	public String toString() {
		return "DZInfo [bankCode=" + bankCode + ", date=" + date + ", money="
				+ money + ", account=" + account + ", accountName="
				+ accountName + ", fileName=" + fileName + ", zy=" + zy + "]";
	}
}
