package com.capo.asignacion_mongo.adapter.mappers;

import java.util.Objects;

import com.capo.adapter.kafkaEvents.MongoResultPointsOfSaleEvent;
import com.capo.asignacion_mongo.adapter.out.model.PointOfSalesToMongoModel;

public class MapperPointOfSale {
	
	public static MongoResultPointsOfSaleEvent mapperResultPointsOfSaleEvent(PointOfSalesToMongoModel pointOfSaleModel) {
		MongoResultPointsOfSaleEvent event = new MongoResultPointsOfSaleEvent();;
		if(Objects.nonNull(pointOfSaleModel) && Objects.nonNull(pointOfSaleModel.getId())) {
			event.setId(pointOfSaleModel.getId());
			event.setIdLocation(pointOfSaleModel.getPoint().getId());
			event.setLocation(pointOfSaleModel.getPoint().getLocation());
			return event;
		}
		event.setId("EMPTY_RESULT");
		return event;
	}
}
