package com.capo.asignacion_mongo.adapter.out.destinationOperations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capo.adapter.kafkaEvents.MongoResultDestinationEvent;
import com.capo.asignacion_mongo.adapter.mappers.MapperDestination;
import com.capo.asignacion_mongo.adapter.out.model.DestinationToMongoModel;
import com.capo.asignacion_mongo.adapter.out.persistence.DestinationMongoRepository;

import reactor.core.publisher.Flux;

@Service
public class GetDestinationImpl implements GetDestination {
	
	private final DestinationMongoRepository destinationMongoRepository;
	private static final Logger log = LoggerFactory.getLogger(GetDestinationImpl.class);
	
	public GetDestinationImpl(DestinationMongoRepository destinationMongoRepository) {
		this.destinationMongoRepository= destinationMongoRepository;
	}
	
	@Override
	public Flux<MongoResultDestinationEvent> getDestination() {
		return destinationMongoRepository.findAll()
			.switchIfEmpty(Flux.defer(()-> Flux.just(new DestinationToMongoModel())))
			.map(model->MapperDestination.mapperResultDestinationEvent(model))
			.doOnNext(point-> log.info("sending destination to preload destination {}", point));
	}
	
}
