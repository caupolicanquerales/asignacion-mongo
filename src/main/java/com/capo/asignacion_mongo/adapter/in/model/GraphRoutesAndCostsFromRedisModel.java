package com.capo.asignacion_mongo.adapter.in.model;

import java.util.Map;

public class GraphRoutesAndCostsFromRedisModel {
	
	private Map<String,Map<Integer,String>> result;

	public Map<String, Map<Integer, String>> getResult() {
		return result;
	}

	public void setResult(Map<String, Map<Integer, String>> result) {
		this.result = result;
	}
}
