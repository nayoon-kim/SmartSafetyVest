package com.hanium_ict.smartvest.utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
 

public class JsonUtil {
	
	public static ObjectMapper mapper = new ObjectMapper();
	
	public static String Map2Json(Map<String, Object> map) {
		String json = "";
		try {
			json = mapper.writeValueAsString(map);
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	public static Map<String, String> Json2Map(String json) {
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			map = mapper.readValue(json, Map.class);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}

}
