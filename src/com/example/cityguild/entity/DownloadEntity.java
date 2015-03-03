package com.example.cityguild.entity;

public class DownloadEntity {

	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogoFilePath() {
		return logoFilePath;
	}
	public void setLogoFilePath(String logoFilePath) {
		this.logoFilePath = logoFilePath;
	}
	public String getAppSize() {
		return appSize;
	}
	public void setAppSize(String appSize) {
		this.appSize = appSize;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAndroidDownloadPath() {
		return androidDownloadPath;
	}
	public void setAndroidDownloadPath(String androidDownloadPath) {
		this.androidDownloadPath = androidDownloadPath;
	}
	private String logoFilePath;
	private String appSize;
	private String score;
	private String description;
	private String androidDownloadPath;
	private String name;
	private String name1;
	private boolean isshow=false;
	public boolean isIsshow() {
		return isshow;
	}
	public void setIsshow(boolean isshow) {
		this.isshow = isshow;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
