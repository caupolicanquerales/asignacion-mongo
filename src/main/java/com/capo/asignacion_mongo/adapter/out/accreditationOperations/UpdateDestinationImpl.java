package com.capo.asignacion_mongo.adapter.out.accreditationOperations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capo.adapter.kafkaEvents.RedisUpdateDestinationEvent;
import com.capo.asignacion_mongo.adapter.out.model.DestinationToMongoModel;
import com.capo.asignacion_mongo.adapter.out.persistence.DestinationMongoRepository;

import reactor.core.publisher.Flux;

@Service
public class UpdateDestinationImpl implements UpdateDestination{
	
	private final DestinationMongoRepository destinationMongoRepository;
	private static final Logger log = LoggerFactory.getLogger(UpdateDestinationImpl.class);
	
	public UpdateDestinationImpl(DestinationMongoRepository destinationMongoRepository) {
		this.destinationMongoRepository= destinationMongoRepository;
	}
	
	@Override
	public Flux<DestinationToMongoModel> updateCostDestination(RedisUpdateDestinationEvent updateDestination) {
		return destinationMongoRepository.findAll()
		.filter(destination-> conditionForDestination(destination,updateDestination))
		.map(model->setCost(model,updateDestination.getCost()))
		.doOnNext(point-> destinationMongoRepository.save(point).subscribe())
		.doOnNext(point-> log.info("updating cost in Destination {}", point));
	}
	
	private Boolean conditionForDestination(DestinationToMongoModel destination, RedisUpdateDestinationEvent event) {
		return destination.getDestination().getStartVertex().equals(event.getStartVertex())
				&& destination.getDestination().getEndVertex().equals(event.getEndVertex());
	}
	
	private DestinationToMongoModel setCost(DestinationToMongoModel destination, String cost) {
		destination.getDestination().setCost(cost);
		return destination;
	}
}
