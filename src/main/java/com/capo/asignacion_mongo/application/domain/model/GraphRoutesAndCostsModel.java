package com.capo.asignacion_mongo.application.domain.model;

import java.util.Map;

public class GraphRoutesAndCostsModel {
	
	private Map<String,Map<Integer,String>> result;

	public Map<String, Map<Integer, String>> getResult() {
		return result;
	}

	public void setResult(Map<String, Map<Integer, String>> result) {
		this.result = result;
	}
}
