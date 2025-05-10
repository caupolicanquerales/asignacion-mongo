package com.capo.asignacion_mongo.application.domain.model;

import java.util.Map;

public class AccreditationInformationModel {
	
	private PointModel point;
	private String travelTo;
	private Map<String,Map<Integer,String>> result;
	
	public PointModel getPoint() {
		return point;
	}
	public void setPoint(PointModel point) {
		this.point = point;
	}
	public String getTravelTo() {
		return travelTo;
	}
	public void setTravelTo(String travelTo) {
		this.travelTo = travelTo;
	}
	public Map<String, Map<Integer, String>> getResult() {
		return result;
	}
	public void setResult(Map<String, Map<Integer, String>> result) {
		this.result = result;
	}
	
	
}
