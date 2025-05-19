package com.capo.adapter.kafkaEvents;

public class MongoResultPointsOfSaleEvent {
	
	private String id;
	private String idLocation;
	private String location;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdLocation() {
		return idLocation;
	}
	public void setIdLocation(String idLocation) {
		this.idLocation = idLocation;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
