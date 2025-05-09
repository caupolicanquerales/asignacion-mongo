package com.capo.asignacion_mongo.adapter.out.emitEvents;

import java.time.Duration;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

@Service
public class EmitEventImpl implements EmitEvent{
	
	@Override
    public <T> void emitEvent(Sinks.Many<T> sink, T event) {
        sink.emitNext(
                event,
                Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(1))
        );
    }
}
