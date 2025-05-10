package com.capo.asignacion_mongo.adapter.out.accreditationOperations;

import com.capo.asignacion_mongo.adapter.in.model.CostsAndRoutesFromResultModel;
import com.capo.asignacion_mongo.adapter.out.model.PointFromRedisModel;
import com.capo.asignacion_mongo.adapter.out.model.PointOfSalesToMongoModel;
import com.capo.asignacion_mongo.application.domain.model.AccreditationInformationModel;
import com.capo.asignacion_mongo.application.domain.model.PointModel;
import com.capo.asignacion_mongo.application.port.in.AccreditationInformation;

public class AccreditationInformationImpl implements InsertInfomation, AccreditationInformation{
	
	private CostsAndRoutesFromResultModel costsAndRoutesFromResult;
	private PointOfSalesToMongoModel pointOfSalesToMongoModel;
	
	@Override
	public AccreditationInformationImpl insertInformation(CostsAndRoutesFromResultModel costsAndRoutesFromResult,
			PointOfSalesToMongoModel pointOfSalesToMongoModel) {
		this.costsAndRoutesFromResult= costsAndRoutesFromResult;
		this.pointOfSalesToMongoModel= pointOfSalesToMongoModel;
		return this;
	}
	
	@Override
	public AccreditationInformationModel getAccreditationInformation() {
		AccreditationInformationModel accreditationInformation= new AccreditationInformationModel();  
		accreditationInformation.setPoint(getPointModel(pointOfSalesToMongoModel.getPoint()));
		accreditationInformation.setResult(costsAndRoutesFromResult.getResult());
		accreditationInformation.setTravelTo(costsAndRoutesFromResult.getTravelTo());
		return accreditationInformation;
	}
	
	private PointModel getPointModel(PointFromRedisModel pointFromRedisModel) {
		PointModel point= new PointModel();
		point.setId(pointFromRedisModel.getId());
		point.setLocation(pointFromRedisModel.getLocation());
		return point;
	}
}
