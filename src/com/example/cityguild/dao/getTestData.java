package com.example.cityguild.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.cityguild.entity.MerchantsEntity;

public class getTestData {
	List<MerchantsEntity> myList = new ArrayList<MerchantsEntity>();

	MerchantsEntity me = new MerchantsEntity();
	public List<MerchantsEntity> getData(){
		for(int i=0;i<=10;i++){
		i++;
		
		}
		return myList;
	}

}
