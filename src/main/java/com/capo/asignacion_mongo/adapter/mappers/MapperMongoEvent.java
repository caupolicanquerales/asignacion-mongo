package com.capo.asignacion_mongo.adapter.mappers;

import com.capo.adapter.kafkaEvents.CostsAndRoutesFromEvent;
import com.capo.adapter.kafkaEvents.CostsAndRoutesFromResultEvent;
import com.capo.asignacion_mongo.adapter.in.model.AccreditationModel;
import com.capo.asignacion_mongo.adapter.in.model.CostsAndRoutesFromResultModel;

public class MapperMongoEvent {
	
	public static CostsAndRoutesFromEvent mapperCostsAndRoutesFromEvent(AccreditationModel accreditationModel) {
		CostsAndRoutesFromEvent event = new CostsAndRoutesFromEvent();
		event.setTravelfrom(accreditationModel.getTravelfrom());
		event.setTravelTo(accreditationModel.getTravelTo());
		return event;
	}
	
	public static CostsAndRoutesFromResultModel mapperCostsAndRoutesFromModel(CostsAndRoutesFromResultEvent costsAndRoutesFromResult) {
		CostsAndRoutesFromResultModel model = new CostsAndRoutesFromResultModel();
		model.setTravelfrom(costsAndRoutesFromResult.getTravelfrom());
		model.setTravelTo(costsAndRoutesFromResult.getTravelTo());
		model.setResult(costsAndRoutesFromResult.getResult());
		return model;
	}
}
