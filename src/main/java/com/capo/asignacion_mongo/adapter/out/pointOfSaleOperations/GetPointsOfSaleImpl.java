package com.capo.asignacion_mongo.adapter.out.pointOfSaleOperations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capo.adapter.kafkaEvents.MongoResultPointsOfSaleEvent;
import com.capo.asignacion_mongo.adapter.mappers.MapperPointOfSale;
import com.capo.asignacion_mongo.adapter.out.model.PointOfSalesToMongoModel;
import com.capo.asignacion_mongo.adapter.out.persistence.PointOfSaleMongoRepository;

import reactor.core.publisher.Flux;

@Service
public class GetPointsOfSaleImpl implements GetPointsOfSale {
	
	private final PointOfSaleMongoRepository pointOfSaleMongoRepository;
	private static final Logger log = LoggerFactory.getLogger(GetPointsOfSaleImpl.class);
	
	public GetPointsOfSaleImpl(PointOfSaleMongoRepository pointOfSaleMongoRepository) {
		this.pointOfSaleMongoRepository= pointOfSaleMongoRepository;
	}
	
	@Override
	public Flux<MongoResultPointsOfSaleEvent> getPointOfSale() {
		return pointOfSaleMongoRepository.findAll()
			.switchIfEmpty(Flux.defer(()-> Flux.just(new PointOfSalesToMongoModel())))
			.map(point-> MapperPointOfSale.mapperResultPointsOfSaleEvent(point))
			.doOnNext(point-> log.info("sending location Point of sale to preload {}", point));
	}
	
}
