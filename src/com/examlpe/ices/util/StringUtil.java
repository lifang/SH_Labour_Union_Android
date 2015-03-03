package com.examlpe.ices.util;

public class StringUtil {
	public static String replace(String str){
		str=str.replace("行业类别+", "").replace("首选工作区域+", "").replace("次选工作区域+", "");
		if(str.length()!=0){
			String c=str.substring(str.length()-1,str.length() );
			if(c.equals("+")){
				return str.substring(0, str.length()-1);
			}else{
				return str;
			}
		
		}else{
			return str;
		}
	}
	public static String change(int second){  
        int h = 0;  
        int d = 0;  
        int s = 0;  
        int temp = second%3600;  
             if(second>3600){  
               h= second/3600;  
                    if(temp!=0){  
               if(temp>60){  
               d = temp/60;  
            if(temp%60!=0){  
               s = temp%60;  
            }  
            }else{  
               s = temp;  
            }  
           }  
          }else{  
              d = second/60;  
           if(second%60!=0){  
              s = second%60;  
           }  
          }  
             if(h==0)
            	 return d+"分钟";
         return h+"小时时"+d+"分钟";  
       }  
}
