package com.capo.asignacion_mongo.adapter.out.pointOfSaleOperations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capo.adapter.kafkaEvents.RedisRemovePointOfSaleEvent;
import com.capo.asignacion_mongo.adapter.out.model.PointOfSalesToMongoModel;
import com.capo.asignacion_mongo.adapter.out.persistence.PointOfSaleMongoRepository;

import reactor.core.publisher.Flux;

@Service
public class RemovePointOfSaleImpl implements RemovePointOfSale{
	
	private final PointOfSaleMongoRepository pointOfSaleMongoRepository;
	private static final Logger log = LoggerFactory.getLogger(RemovePointOfSaleImpl.class);
	
	public RemovePointOfSaleImpl(PointOfSaleMongoRepository pointOfSaleMongoRepository) {
		this.pointOfSaleMongoRepository= pointOfSaleMongoRepository;
	}
	
	@Override
	public Flux<PointOfSalesToMongoModel> removePointOfSale(RedisRemovePointOfSaleEvent removePointOfSale) {
		return pointOfSaleMongoRepository.findAll()
		.filter(point-> point.getPoint().getId().equals(removePointOfSale.getId()))
		.doOnNext(point-> pointOfSaleMongoRepository.deleteById(point.getId()).subscribe())
		.doOnNext(point-> log.info("remove Point of Sale {}", point));
	}
	
}
