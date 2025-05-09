package com.capo.adapter.kafkaEvents;

import java.util.Map;

public class CostsAndRoutesFromResultEvent {
	
	private String travelfrom;
	private String travelTo;
	private Map<String,Map<Integer,String>> result;

	public String getTravelfrom() {
		return travelfrom;
	}

	public void setTravelfrom(String travelfrom) {
		this.travelfrom = travelfrom;
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
