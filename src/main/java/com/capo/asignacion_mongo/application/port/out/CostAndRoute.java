package com.capo.asignacion_mongo.application.port.out;

import com.capo.asignacion_mongo.application.domain.model.EstimationFromVertexModel;
import com.capo.asignacion_mongo.application.domain.model.GraphRoutesAndCostsModel;

import reactor.core.publisher.Mono;


public interface CostAndRoute {
	Mono<GraphRoutesAndCostsModel> estimationOfCosts(EstimationFromVertexModel vertex);
}
