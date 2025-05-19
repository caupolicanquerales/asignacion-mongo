package com.capo.asignacion_mongo.adapter.out.accreditationOperations;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capo.adapter.kafkaEvents.CostsAndRoutesFromEvent;
import com.capo.asignacion_mongo.adapter.in.model.AccreditationModel;
import com.capo.asignacion_mongo.adapter.mappers.MapperAccreditation;
import com.capo.asignacion_mongo.adapter.out.emitEvents.EmitingEvent;

import reactor.core.publisher.Mono;

@Service
public class AskingCostsAndRoutesFromImpl implements AskingCostsAndRoutes{
	
	private final EmitingEvent<CostsAndRoutesFromEvent> emitEvent;
	
	private static final Logger log = LoggerFactory.getLogger(AskingCostsAndRoutesFromImpl.class);
	
	public AskingCostsAndRoutesFromImpl(EmitingEvent<CostsAndRoutesFromEvent> emitEvent) {
		this.emitEvent= emitEvent;
	}
	
	@Override
	public Mono<String> askingCostsAndRoutesFrom(AccreditationModel accreditationModel){
		return Mono.just(accreditationModel)
		.map(model-> MapperAccreditation.mapperCostsAndRoutesFromEvent(model))
		.doOnNext(r -> log.info("asking costs and routes from in Mongo {}", Objects.nonNull(r)))
		.doOnNext(event->{
			if(Objects.nonNull(event)) {
				emitEvent.emit(event);
			}
		}).map(event-> {
			return "OK";
		});
	}
}
