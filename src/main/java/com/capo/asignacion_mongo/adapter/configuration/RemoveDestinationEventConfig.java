package com.capo.asignacion_mongo.adapter.configuration;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.capo.adapter.kafkaEvents.MongoResultEvent;
import com.capo.adapter.kafkaEvents.RedisRemoveDestinationEvent;
import com.capo.asignacion_mongo.adapter.out.accreditationOperations.RemoveDestination;
import com.capo.asignacion_mongo.adapter.out.model.DestinationToMongoModel;
import com.capo.asignacion_mongo.adapter.utils.MessageConverter;

import reactor.core.publisher.Flux;

@Configuration
public class RemoveDestinationEventConfig {
	
	private static final Logger log = LoggerFactory.getLogger(RemoveDestinationEventConfig.class);
	private final RemoveDestination removeDestination;
	
	public RemoveDestinationEventConfig(RemoveDestination removeDestination) {
		this.removeDestination= removeDestination;
	}
	
	@Bean
	public Function<Flux<Message<RedisRemoveDestinationEvent>>, Flux<Message<MongoResultEvent>>> processorRemoveDestination(){
		return flux-> flux.map(MessageConverter::toRecord)
				.doOnNext(r -> log.info("get event from Redis to remove Destination {}", r.message()))
				//.doOnNext(r -> r.acknowledgement().acknowledge())
				.map(r-> removeDestination.removeDestination(r.message()))
				.concatMap(result->result)
				.map(this::mapMongoResultEvent)
				.map(result->toMessageDestination(result));
	}
	
	private MongoResultEvent mapMongoResultEvent(DestinationToMongoModel redisPoint) {
		MongoResultEvent resultEvent= new MongoResultEvent();
		resultEvent.setId(redisPoint.getId());
		resultEvent.setStatus("PROCESSED");
		return resultEvent;
	}
	
	private Message<MongoResultEvent> toMessageDestination(MongoResultEvent event) {
		return MessageBuilder.withPayload(event)
                             .setHeader(KafkaHeaders.KEY, "10")
                             .build();
    }
}
