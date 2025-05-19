package com.capo.asignacion_mongo.adapter.configuration;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.capo.adapter.kafkaEvents.GetInformationDestinationEvent;
import com.capo.adapter.kafkaEvents.MongoResultDestinationEvent;
import com.capo.asignacion_mongo.adapter.out.destinationOperations.GetDestination;
import com.capo.asignacion_mongo.adapter.utils.MessageConverter;

import reactor.core.publisher.Flux;

@Configuration
public class GetDestinationEventConfig {
	
	private static final Logger log = LoggerFactory.getLogger(GetDestinationEventConfig.class);
	private final GetDestination getDestination;
	
	public GetDestinationEventConfig(GetDestination getDestination) {
		this.getDestination= getDestination;
	}
	
	@Bean
	public Function<Flux<Message<GetInformationDestinationEvent>>, Flux<Message<MongoResultDestinationEvent>>> processorGetDestination(){
		return flux-> flux.map(MessageConverter::toRecord)
				.doOnNext(r -> log.info("get event from Redis to get all destinations {}", r.message()))
				//.doOnNext(r -> r.acknowledgement().acknowledge())
				.map(r-> getDestination.getDestination())
				.concatMap(result->result)
				.map(result->toMessageDestination(result));
	}
	
	
	private Message<MongoResultDestinationEvent> toMessageDestination(MongoResultDestinationEvent event) {
		return MessageBuilder.withPayload(event)
                             .setHeader(KafkaHeaders.KEY, "18")
                             .build();
    }
}
