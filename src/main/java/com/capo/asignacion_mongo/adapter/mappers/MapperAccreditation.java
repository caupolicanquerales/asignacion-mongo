package com.capo.asignacion_mongo.adapter.mappers;

import com.capo.adapter.kafkaEvents.CostsAndRoutesFromEvent;
import com.capo.adapter.kafkaEvents.CostsAndRoutesFromResultEvent;
import com.capo.asignacion_mongo.adapter.in.model.AccreditationModel;
import com.capo.asignacion_mongo.adapter.in.model.CostsAndRoutesFromResultModel;
import com.capo.asignacion_mongo.adapter.out.model.AccreditationMongoModel;
import com.capo.asignacion_mongo.application.domain.model.AccreditationTicketModel;

public class MapperAccreditation {
	
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
	
	public static AccreditationMongoModel mapperAccreditationMongoModel(AccreditationTicketModel accreditationTicketModel) {
		AccreditationMongoModel model = new AccreditationMongoModel();
		model.setCost(accreditationTicketModel.getCost());
		model.setDateOfReception(accreditationTicketModel.getDateOfReception());
		model.setIdPontOfSale(accreditationTicketModel.getIdPontOfSale());
		model.setNamePontOfSale(accreditationTicketModel.getNamePontOfSale());
		model.setRoute(accreditationTicketModel.getRoute());
		return model;
	}
	
}
