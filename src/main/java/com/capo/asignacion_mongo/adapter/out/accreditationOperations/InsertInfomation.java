package com.capo.asignacion_mongo.adapter.out.accreditationOperations;

import com.capo.asignacion_mongo.adapter.in.model.CostsAndRoutesFromResultModel;
import com.capo.asignacion_mongo.adapter.out.model.PointOfSalesToMongoModel;

public interface InsertInfomation {
	AccreditationInformationImpl insertInformation(CostsAndRoutesFromResultModel costsAndRoutesFromResult,
			PointOfSalesToMongoModel pointOfSalesToMongoModel);
}
