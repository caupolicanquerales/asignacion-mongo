package com.capo.asignacion_mongo.application.domain.model;

import java.util.List;

public class PointsOfSaleModel {
	private List<PointModel> points;

	public List<PointModel> getPoints() {
		return points;
	}

	public void setPoints(List<PointModel> points) {
		this.points = points;
	}
}
