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
import com.capo.adapter.kafkaEvents.RedisDestinationEvent;
import com.capo.asignacion_mongo.adapter.out.model.DestinationModel;
import com.capo.asignacion_mongo.adapter.out.model.DestinationToMongoModel;
import com.capo.asignacion_mongo.adapter.out.persistence.DestinationMongoRepository;
import com.capo.asignacion_mongo.adapter.utils.MessageConverter;

import reactor.core.publisher.Flux;

@Configuration
public class DestinationEventConfig {
	
	private static final Logger log = LoggerFactory.getLogger(DestinationEventConfig.class);
	private final DestinationMongoRepository destinationMongo;
	
	public DestinationEventConfig(DestinationMongoRepository destinationMongo) {
		this.destinationMongo= destinationMongo;
	}
	
	@Bean
	public Function<Flux<Message<RedisDestinationEvent>>, Flux<Message<MongoResultEvent>>> processorDestination(){
		return flux-> flux.map(MessageConverter::toRecord)
				.doOnNext(r -> log.info("get event from Redis Destination {}", r.message()))
				//.doOnNext(r -> r.acknowledgement().acknowledge())
				.map(r-> mapToPointFromRedisModel(r.message()))
				.map(this::getPointOfSalesInMongo)
				.map(point->destinationMongo.save(point))
				.concatMap(result->result)
				.map(this::mapMongoResultEvent)
				.map(result->toMessageDestination(result));
	}
	
	private DestinationModel mapToPointFromRedisModel(RedisDestinationEvent destination) {
		DestinationModel destinationModel = new DestinationModel();
		destinationModel.setCost(destination.getCost());
		destinationModel.setEndVertex(destination.getEndVertex());
		destinationModel.setStartVertex(destination.getStartVertex());
		return destinationModel;
	}
	
	private DestinationToMongoModel getPointOfSalesInMongo(DestinationModel destination) {
		DestinationToMongoModel destinationInMongo= new DestinationToMongoModel();
		destinationInMongo.setDestination(destination);
		return destinationInMongo;
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
