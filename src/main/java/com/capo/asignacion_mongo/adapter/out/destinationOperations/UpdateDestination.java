package com.capo.asignacion_mongo.adapter.out.destinationOperations;

import com.capo.adapter.kafkaEvents.RedisUpdateDestinationEvent;
import com.capo.asignacion_mongo.adapter.out.model.DestinationToMongoModel;

import reactor.core.publisher.Flux;

public interface UpdateDestination {
	Flux<DestinationToMongoModel> updateCostDestination(RedisUpdateDestinationEvent updateDestination);
}
