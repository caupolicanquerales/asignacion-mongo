package com.capo.asignacion_mongo.adapter.configuration;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import com.capo.adapter.kafkaEvents.CostsAndRoutesFromResultEvent;
import com.capo.asignacion_mongo.adapter.mappers.MapperMongoEvent;
import com.capo.asignacion_mongo.adapter.utils.MessageConverter;
import com.capo.asignacion_mongo.adapter.out.accreditationOperations.CreatingAccreditation;

import reactor.core.publisher.Flux;


@Configuration
public class ConsumingRedisEventConfig {
	
	private final CreatingAccreditation creatingAccreditation;
	private static final Logger log = LoggerFactory.getLogger(ConsumingRedisEventConfig.class);
    
	public ConsumingRedisEventConfig(CreatingAccreditation creatingAccreditation) {
		this.creatingAccreditation= creatingAccreditation;
	}
	
	@Bean
    public Consumer<Flux<Message<CostsAndRoutesFromResultEvent>>> processorResultCostsAndRoutes() {
        return flux -> flux.map(MessageConverter::toRecord)
                           .doOnNext(r -> log.info("get event from Redis for resul Cost and Route {}", r.message()))
                           .map(r-> MapperMongoEvent.mapperCostsAndRoutesFromModel(r.message()))
                           .doOnNext(model-> creatingAccreditation.creatingAccreditation(model))
                           .subscribe();
                           //.concatMap(r -> this.eventProcessor.process(r.message())
                          //                                    .doOnSuccess(e -> r.acknowledgement().acknowledge())
                          // )
                           //.doOnNext(r -> r.acknowledgement().acknowledge())
                           //.map(r-> mapDestination(r.message()))
                           //.map(this::toMessageDestination);
    }
	
}
