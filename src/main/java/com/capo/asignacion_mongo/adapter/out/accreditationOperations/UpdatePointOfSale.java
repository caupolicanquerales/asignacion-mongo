package com.capo.asignacion_mongo.adapter.out.accreditationOperations;

import com.capo.adapter.kafkaEvents.RedisUpdatePointOfSaleEvent;
import com.capo.asignacion_mongo.adapter.out.model.PointOfSalesToMongoModel;

import reactor.core.publisher.Flux;

public interface UpdatePointOfSale {
	Flux<PointOfSalesToMongoModel> updatePointOfSale(RedisUpdatePointOfSaleEvent updatePointOfSale);
}
