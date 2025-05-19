package com.capo.asignacion_mongo.adapter.out.pointOfSaleOperations;

import com.capo.adapter.kafkaEvents.MongoResultPointsOfSaleEvent;

import reactor.core.publisher.Flux;

public interface GetPointsOfSale {
	Flux<MongoResultPointsOfSaleEvent> getPointOfSale();
}
