package com.capo.asignacion_mongo.adapter.mappers;

import com.capo.adapter.kafkaEvents.CostsAndRoutesFromEvent;
import com.capo.asignacion_mongo.adapter.in.model.AccreditationModel;

public class MapperMongoEvent {
	
	public static CostsAndRoutesFromEvent mapperCostsAndRoutesFromEvent(AccreditationModel accreditationModel) {
		CostsAndRoutesFromEvent event = new CostsAndRoutesFromEvent();
		event.setTravelfrom(accreditationModel.getTravelfrom());
		event.setTravelTo(accreditationModel.getTravelTo());
		return event;
	}
}
