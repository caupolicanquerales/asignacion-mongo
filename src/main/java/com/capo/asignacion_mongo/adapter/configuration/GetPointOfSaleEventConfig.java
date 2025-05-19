package com.capo.asignacion_mongo.adapter.configuration;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.capo.adapter.kafkaEvents.GetPointsOfSaleEvent;
import com.capo.adapter.kafkaEvents.MongoResultPointsOfSaleEvent;
import com.capo.asignacion_mongo.adapter.out.pointOfSaleOperations.GetPointsOfSale;
import com.capo.asignacion_mongo.adapter.utils.MessageConverter;

import reactor.core.publisher.Flux;

@Configuration
public class GetPointOfSaleEventConfig {
	
	private static final Logger log = LoggerFactory.getLogger(GetPointOfSaleEventConfig.class);
	private final GetPointsOfSale getPointsOfSale;
	
	public GetPointOfSaleEventConfig(GetPointsOfSale getPointsOfSale) {
		this.getPointsOfSale= getPointsOfSale;
	}
	
	@Bean
	public Function<Flux<Message<GetPointsOfSaleEvent>>, Flux<Message<MongoResultPointsOfSaleEvent>>> processorGetPointsOfSale(){
		return flux-> flux.map(MessageConverter::toRecord)
				.doOnNext(r -> log.info("get event from Redis to get all Points of Sale {}", r.message()))
				//.doOnNext(r -> r.acknowledgement().acknowledge())
				.map(r-> getPointsOfSale.getPointOfSale())
				.concatMap(result->result)
				.map(result->toMessageDestination(result));
	}
	
	
	private Message<MongoResultPointsOfSaleEvent> toMessageDestination(MongoResultPointsOfSaleEvent event) {
		return MessageBuilder.withPayload(event)
                             .setHeader(KafkaHeaders.KEY, "17")
                             .build();
    }
}
