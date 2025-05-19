package com.capo.asignacion_mongo.adapter.out.destinationOperations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capo.adapter.kafkaEvents.RedisRemoveDestinationEvent;
import com.capo.asignacion_mongo.adapter.out.model.DestinationToMongoModel;
import com.capo.asignacion_mongo.adapter.out.persistence.DestinationMongoRepository;

import reactor.core.publisher.Flux;

@Service
public class RemoveDestinationSaleImpl implements RemoveDestination{
	
	private final DestinationMongoRepository destinationMongoRepository;
	private static final Logger log = LoggerFactory.getLogger(RemoveDestinationSaleImpl.class);
	
	public RemoveDestinationSaleImpl(DestinationMongoRepository destinationMongoRepository) {
		this.destinationMongoRepository= destinationMongoRepository;
	}
	
	@Override
	public Flux<DestinationToMongoModel> removeDestination(RedisRemoveDestinationEvent removeDestination) {
		return destinationMongoRepository.findAll()
		.filter(destination-> conditionForDestination(destination,removeDestination))
		.doOnNext(point-> destinationMongoRepository.deleteById(point.getId()).subscribe())
		.doOnNext(point-> log.info("remove Destination of Sale {}", point));
	}
	
	private Boolean conditionForDestination(DestinationToMongoModel destination, RedisRemoveDestinationEvent event) {
		return destination.getDestination().getStartVertex().equals(event.getStartVertex())
				&& destination.getDestination().getEndVertex().equals(event.getEndVertex());
	}
}
