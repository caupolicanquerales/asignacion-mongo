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
import com.capo.adapter.kafkaEvents.RedisRemovePointOfSaleEvent;
import com.capo.asignacion_mongo.adapter.out.model.PointOfSalesToMongoModel;
import com.capo.asignacion_mongo.adapter.out.pointOfSaleOperations.RemovePointOfSale;
import com.capo.asignacion_mongo.adapter.utils.MessageConverter;

import reactor.core.publisher.Flux;

@Configuration
public class RemovePointOfSaleEventConfig {
	
	private static final Logger log = LoggerFactory.getLogger(RemovePointOfSaleEventConfig.class);
	private final RemovePointOfSale removePointOfSale;
	
	public RemovePointOfSaleEventConfig(RemovePointOfSale removePointOfSale) {
		this.removePointOfSale= removePointOfSale;
	}
	
	@Bean
	public Function<Flux<Message<RedisRemovePointOfSaleEvent>>, Flux<Message<MongoResultEvent>>> processorRemovePointOfSale(){
		return flux-> flux.map(MessageConverter::toRecord)
				.doOnNext(r -> log.info("get event from Redis to Remove Point of Sale {}", r.message()))
				//.doOnNext(r -> r.acknowledgement().acknowledge())
				.map(r-> removePointOfSale.removePointOfSale(r.message()))
				.concatMap(result->result)
				.map(this::mapMongoResultEvent)
				.map(result->toMessageDestination(result));
	}
	
	private MongoResultEvent mapMongoResultEvent(PointOfSalesToMongoModel redisPoint) {
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
