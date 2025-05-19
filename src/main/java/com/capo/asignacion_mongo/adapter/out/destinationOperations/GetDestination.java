package com.capo.asignacion_mongo.adapter.out.destinationOperations;

import com.capo.adapter.kafkaEvents.MongoResultDestinationEvent;

import reactor.core.publisher.Flux;

public interface GetDestination {
	Flux<MongoResultDestinationEvent> getDestination();
}
