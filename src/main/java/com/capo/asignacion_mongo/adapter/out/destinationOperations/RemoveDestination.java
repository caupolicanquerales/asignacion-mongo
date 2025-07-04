package com.capo.asignacion_mongo.adapter.out.destinationOperations;

import com.capo.adapter.kafkaEvents.RedisRemoveDestinationEvent;
import com.capo.asignacion_mongo.adapter.out.model.DestinationToMongoModel;

import reactor.core.publisher.Flux;

public interface RemoveDestination {
	Flux<DestinationToMongoModel> removeDestination(RedisRemoveDestinationEvent removeDestination);
}
