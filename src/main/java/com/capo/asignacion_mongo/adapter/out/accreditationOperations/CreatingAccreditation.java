package com.capo.asignacion_mongo.adapter.out.accreditationOperations;

import com.capo.asignacion_mongo.adapter.in.model.CostsAndRoutesFromResultModel;
import com.capo.asignacion_mongo.adapter.out.model.PointOfSalesToMongoModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreatingAccreditation {
	void creatingAccreditation(CostsAndRoutesFromResultModel costsAndRoutesFromResult);
}
