package com.capo.asignacion_mongo.adapter.mappers;

import java.util.Objects;

import com.capo.adapter.kafkaEvents.MongoResultDestinationEvent;
import com.capo.asignacion_mongo.adapter.out.model.DestinationToMongoModel;

public class MapperDestination {
	
	public static MongoResultDestinationEvent mapperResultDestinationEvent(DestinationToMongoModel destinationModel) {
		MongoResultDestinationEvent event = new MongoResultDestinationEvent();;
		if(Objects.nonNull(destinationModel) && Objects.nonNull(destinationModel.getId())) {
			event.setId(destinationModel.getId());
			event.setCost(destinationModel.getDestination().getCost());
			event.setStartVertex(destinationModel.getDestination().getStartVertex());
			event.setEndVertex(destinationModel.getDestination().getEndVertex());
			return event;
		}
		event.setId("EMPTY_RESULT");
		return event;
	}
}
