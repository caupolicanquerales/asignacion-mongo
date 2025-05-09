package com.capo.asignacion_mongo.adapter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.capo.adapter.kafkaEvents.CostsAndRoutesFromEvent;
import com.capo.asignacion_mongo.adapter.out.emitEvents.EmitCostsAndRoutesFromEvent;

import reactor.core.publisher.Sinks;


@Configuration
public class EmitCostsAndRoutesFromConfig {
	
	@Bean
    public EmitCostsAndRoutesFromEvent emitCostsAndRoutesEventListener(){
        var sink = Sinks.many().unicast().<CostsAndRoutesFromEvent>onBackpressureBuffer();
        var flux = sink.asFlux();
        return new EmitCostsAndRoutesFromEvent(sink, flux);
    }
}
