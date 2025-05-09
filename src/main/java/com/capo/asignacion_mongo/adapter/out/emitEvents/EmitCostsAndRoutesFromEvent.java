package com.capo.asignacion_mongo.adapter.out.emitEvents;

import org.springframework.beans.factory.annotation.Autowired;

import com.capo.adapter.kafkaEvents.CostsAndRoutesFromEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class EmitCostsAndRoutesFromEvent implements EmitingEvent<CostsAndRoutesFromEvent>,EventInUse<CostsAndRoutesFromEvent>{
	
	@Autowired
	private EmitEvent emitEvent;
	
	private final Sinks.Many<CostsAndRoutesFromEvent> sink;
	private final Flux<CostsAndRoutesFromEvent> flux;
	
	public EmitCostsAndRoutesFromEvent(Sinks.Many<CostsAndRoutesFromEvent> sink,Flux<CostsAndRoutesFromEvent> flux) {
		this.sink=sink;
		this.flux=flux;
	}
	
	@Override
    public Flux<CostsAndRoutesFromEvent> publish() {
        return this.flux;
    }
	
	@Override
	public void emit(CostsAndRoutesFromEvent event) {
		emitEvent.emitEvent(sink, event);
	}
}
