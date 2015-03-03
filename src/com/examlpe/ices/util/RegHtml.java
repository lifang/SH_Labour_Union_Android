package com.examlpe.ices.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegHtml {
	
	public static List<String> getImg(String s)     
	{     
	   String regex;     
	   List<String> list = new ArrayList<String>();     
	   regex = "src=\"(.*?)\"";     
	   Pattern pa = Pattern.compile(regex, Pattern.DOTALL);     
	   Matcher ma = pa.matcher(s);     
	   while (ma.find())     
	   {  
	    list.add(ma.group());     
	   }     
	   return list;     
	}     
	 public static String replaceUrl(String s){
		String str=s.replaceAll("src=\"/admin/webfiles/uploadfile", "style=\"width:100%;height:auto\"  src=\"http://www.sh12351.org/admin/webfiles/uploadfile");	 
		  return str;          
	  } 
	 //http://pic.sosucai.com/b/2009_09/b_34597_AI_20090912165656.jpg
		 public static String replaceUrl1(String s){
			String str="<img src='"+s+"'/>"; 
			  return str;
		  } 
		public static String replaceUrl2(String s){
			String str=s.replace("<TABLE style=\"MARGIN: auto auto auto 4.65pt; WIDTH: 509.75pt", "<TABLE style=\"MARGIN: auto auto auto 4.65pt; WIDTH:30pt"); 
			  return str;
		  } 
}
