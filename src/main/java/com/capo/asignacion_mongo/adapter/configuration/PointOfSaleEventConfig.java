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
import com.capo.adapter.kafkaEvents.RedisPointOfSaleEvent;
import com.capo.asignacion_mongo.adapter.out.model.PointFromRedisModel;
import com.capo.asignacion_mongo.adapter.out.model.PointOfSalesToMongoModel;
import com.capo.asignacion_mongo.adapter.out.persistence.PointOfSaleMongoRepository;
import com.capo.asignacion_mongo.adapter.utils.MessageConverter;

import reactor.core.publisher.Flux;

@Configuration
public class PointOfSaleEventConfig {
	
	private static final Logger log = LoggerFactory.getLogger(PointOfSaleEventConfig.class);
	private final PointOfSaleMongoRepository pointOfSaleMongo;
	
	public PointOfSaleEventConfig(PointOfSaleMongoRepository pointOfSaleMongo) {
		this.pointOfSaleMongo= pointOfSaleMongo;
	}
	
	@Bean
	public Function<Flux<Message<RedisPointOfSaleEvent>>, Flux<Message<MongoResultEvent>>> processor(){
		return flux-> flux.map(MessageConverter::toRecord)
				.doOnNext(r -> log.info("get event from Redis Point of Sale {}", r.message()))
				//.doOnNext(r -> r.acknowledgement().acknowledge())
				.map(r-> mapToPointFromRedisModel(r.message()))
				.map(this::getPointOfSalesInMongo)
				.map(point->pointOfSaleMongo.save(point))
				.concatMap(result->result)
				.map(this::mapMongoResultEvent)
				.map(result->toMessageDestination(result));
	}
	
	private PointFromRedisModel mapToPointFromRedisModel(RedisPointOfSaleEvent point) {
		PointFromRedisModel pointFromRedis = new PointFromRedisModel();
		pointFromRedis.setId(point.getId());
		pointFromRedis.setLocation(point.getLocation());
		return pointFromRedis;
	}
	
	private PointOfSalesToMongoModel getPointOfSalesInMongo(PointFromRedisModel point) {
		PointOfSalesToMongoModel pointsInMongo= new PointOfSalesToMongoModel();
		pointsInMongo.setPoint(point);
		return pointsInMongo;
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
