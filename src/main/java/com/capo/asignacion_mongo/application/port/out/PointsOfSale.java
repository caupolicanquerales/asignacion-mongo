package com.capo.asignacion_mongo.application.port.out;


import com.capo.asignacion_mongo.application.domain.model.PointsOfSaleModel;

import reactor.core.publisher.Mono;

public interface PointsOfSale {
	Mono<PointsOfSaleModel> getPointsOfSale();
}
