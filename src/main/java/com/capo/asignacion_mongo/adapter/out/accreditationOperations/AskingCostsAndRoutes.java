package com.capo.asignacion_mongo.adapter.out.accreditationOperations;

import com.capo.asignacion_mongo.adapter.in.model.AccreditationModel;

import reactor.core.publisher.Mono;

public interface AskingCostsAndRoutes {
	Mono<String> askingCostsAndRoutesFrom(AccreditationModel accreditationModel);
}
