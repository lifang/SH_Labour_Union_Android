package com.example.cityguild.entity;

public class MerchantsEntity {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getAbout_detail() {
		return about_detail;
	}
	public void setAbout_detail(String about_detail) {
		this.about_detail = about_detail;
	}
	public Boolean getIsshow() {
		return isshow;
	}
	public void setIsshow(Boolean isshow) {
		this.isshow = isshow;
	}
	private String id;
	
	private String name;
	private String tel;
	private String addr;
	private String about;
	private String about_detail;
	private String logo;
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	private Boolean isshow=false;
	private String total;
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	private String offset;
	public String getOffset() {
		return offset;
	}
	public void setOffset(String offset) {
		this.offset = offset;
	}
	
	



}
