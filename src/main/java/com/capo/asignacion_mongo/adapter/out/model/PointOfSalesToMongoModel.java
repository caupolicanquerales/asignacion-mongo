package com.capo.asignacion_mongo.adapter.out.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="points")
public class PointOfSalesToMongoModel {
	
	@Id
	private String id;
	private PointFromRedisModel  point;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PointFromRedisModel getPoint() {
		return point;
	}
	public void setPoint(PointFromRedisModel point) {
		this.point = point;
	}
}
