package com.capo.asignacion_mongo.adapter.configuration;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.capo.asignacion_mongo.adapter.in.events.RedisDestinationEvent;
import com.capo.asignacion_mongo.adapter.in.events.RedisPointOfSaleEvent;
import com.capo.asignacion_mongo.adapter.out.events.ToRedisResultEvent;
import com.capo.asignacion_mongo.adapter.utils.MessageConverter;

import reactor.core.publisher.Flux;


@Configuration
public class RedisEventProcessorConfig {
	
	private static final Logger log = LoggerFactory.getLogger(RedisEventProcessorConfig.class);
    
	
	@Bean
    public Function<Flux<Message<RedisDestinationEvent>>, Flux<Message<ToRedisResultEvent>>> processorDestination() {
        return flux -> flux.map(MessageConverter::toRecord)
                           .doOnNext(r -> log.info("get event from Redis Destination {}", r.message()))
                           //.concatMap(r -> this.eventProcessor.process(r.message())
                          //                                    .doOnSuccess(e -> r.acknowledgement().acknowledge())
                          // )
                           .doOnNext(r -> r.acknowledgement().acknowledge())
                           .map(r-> mapDestination(r.message()))
                           .map(this::toMessageDestination);
    }
	
	@Bean
    public Function<Flux<Message<RedisPointOfSaleEvent>>, Flux<Message<ToRedisResultEvent>>> processorPointOfSale() {
        return flux -> flux.map(MessageConverter::toRecord)
                           .doOnNext(r -> log.info("get event from Redis Point of Sale {}", r.message()))
                           //.concatMap(r -> this.eventProcessor.process(r.message())
                          //                                    .doOnSuccess(e -> r.acknowledgement().acknowledge())
                          // )
                           .doOnNext(r -> r.acknowledgement().acknowledge())
                           .map(r-> mapPoints(r.message()))
                           .map(this::toMessageDestination);
    }
	
	
	private ToRedisResultEvent mapDestination(RedisDestinationEvent redisDestination) {
		ToRedisResultEvent toRedisResultEvent= new ToRedisResultEvent();
		toRedisResultEvent.setId("Destination");
		return toRedisResultEvent;
	}
	
	private ToRedisResultEvent mapPoints(RedisPointOfSaleEvent redisDestination) {
		ToRedisResultEvent toRedisResultEvent= new ToRedisResultEvent();
		toRedisResultEvent.setId("Points");
		return toRedisResultEvent;
	}
	
	private Message<ToRedisResultEvent> toMessageDestination(ToRedisResultEvent event) {
		return MessageBuilder.withPayload(event)
                             .setHeader(KafkaHeaders.KEY, event.getId().toString())
                             .build();
    }

}
