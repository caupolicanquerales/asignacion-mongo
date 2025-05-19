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
import com.capo.adapter.kafkaEvents.RedisUpdatePointOfSaleEvent;
import com.capo.asignacion_mongo.adapter.out.model.PointOfSalesToMongoModel;
import com.capo.asignacion_mongo.adapter.out.pointOfSaleOperations.UpdatePointOfSale;
import com.capo.asignacion_mongo.adapter.utils.MessageConverter;

import reactor.core.publisher.Flux;

@Configuration
public class UpdatePointOfSaleEventConfig {
	
	private static final Logger log = LoggerFactory.getLogger(UpdatePointOfSaleEventConfig.class);
	private final UpdatePointOfSale updatePointOfSale;
	
	public UpdatePointOfSaleEventConfig(UpdatePointOfSale updatePointOfSale) {
		this.updatePointOfSale= updatePointOfSale;
	}
	
	@Bean
	public Function<Flux<Message<RedisUpdatePointOfSaleEvent>>, Flux<Message<MongoResultEvent>>> processorUpdatePointOfSale(){
		return flux-> flux.map(MessageConverter::toRecord)
				.doOnNext(r -> log.info("get event from Redis to update Point of Sale {}", r.message()))
				//.doOnNext(r -> r.acknowledgement().acknowledge())
				.map(r-> updatePointOfSale.updatePointOfSale(r.message()))
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
