package com.capo.asignacion_mongo.adapter.out.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="costs")
public class DestinationToMongoModel {
	
	@Id
	private String id;
	private DestinationModel destination;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public DestinationModel getDestination() {
		return destination;
	}
	public void setDestination(DestinationModel destination) {
		this.destination = destination;
	}
	
}
