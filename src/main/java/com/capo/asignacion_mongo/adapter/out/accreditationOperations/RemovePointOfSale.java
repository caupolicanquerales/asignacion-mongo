package com.capo.asignacion_mongo.adapter.out.accreditationOperations;

import com.capo.adapter.kafkaEvents.RedisRemovePointOfSaleEvent;
import com.capo.asignacion_mongo.adapter.out.model.PointOfSalesToMongoModel;

import reactor.core.publisher.Flux;

public interface RemovePointOfSale {
	Flux<PointOfSalesToMongoModel> removePointOfSale(RedisRemovePointOfSaleEvent removePointOfSale);
}
