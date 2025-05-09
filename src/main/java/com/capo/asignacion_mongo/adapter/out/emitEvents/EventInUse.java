package com.capo.asignacion_mongo.adapter.out.emitEvents;

import reactor.core.publisher.Flux;

public interface EventInUse<T> {
	Flux<T> publish();
}
