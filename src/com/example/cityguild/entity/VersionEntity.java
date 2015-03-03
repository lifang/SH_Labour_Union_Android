package com.example.cityguild.entity;

public class VersionEntity {
	//"id":1,"appVersionNumber":"0.01","appVersionUrl":"http://baidu.com/apk_0.01/","appType":1,"
	private int id;
	private String appVersionNumber;
	private String appVersionUrl;
	private int appType;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppVersionNumber() {
		return appVersionNumber;
	}
	public void setAppVersionNumber(String appVersionNumber) {
		this.appVersionNumber = appVersionNumber;
	}
	public String getAppVersionUrl() {
		return appVersionUrl;
	}
	public void setAppVersionUrl(String appVersionUrl) {
		this.appVersionUrl = appVersionUrl;
	}
	public int getAppType() {
		return appType;
	}
	public void setAppType(int appType) {
		this.appType = appType;
	}
	
}
