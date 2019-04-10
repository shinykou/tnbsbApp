package com.gxn.diamond.tools.helper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {
	
	public static Integer parseToInteger(String number){
		try {
			if(number==null){
				return null;
			}
			return Integer.valueOf(number);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String toJson(Object obj){
		ObjectMapper mapper=  new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "null";
		}
	}

	

}
