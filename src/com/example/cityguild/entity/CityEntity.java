package com.example.cityguild.entity;

import java.util.List;

public class CityEntity {
	private String city_name;
	private String city_area_id;
	private String id;
	private String area_id;
	private String area_sub_id;
	private String area_name;
	List<CityEntity> childrens;
	private boolean islight=false;
	public boolean isIslight() {
		return islight;
	}

	public void setIslight(boolean islight) {
		this.islight = islight;
	}

	public List<CityEntity> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<CityEntity> childrens) {
		this.childrens = childrens;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getCity_area_id() {
		return city_area_id;
	}

	public void setCity_area_id(String city_area_id) {
		this.city_area_id = city_area_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getArea_sub_id() {
		return area_sub_id;
	}

	public void setArea_sub_id(String area_sub_id) {
		this.area_sub_id = area_sub_id;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
}
