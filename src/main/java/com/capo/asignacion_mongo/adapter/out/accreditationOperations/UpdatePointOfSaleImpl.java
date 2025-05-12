package com.capo.asignacion_mongo.adapter.out.accreditationOperations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capo.adapter.kafkaEvents.RedisUpdatePointOfSaleEvent;
import com.capo.asignacion_mongo.adapter.out.model.PointOfSalesToMongoModel;
import com.capo.asignacion_mongo.adapter.out.persistence.PointOfSaleMongoRepository;

import reactor.core.publisher.Flux;

@Service
public class UpdatePointOfSaleImpl implements UpdatePointOfSale{
	
	private final PointOfSaleMongoRepository pointOfSaleMongoRepository;
	private static final Logger log = LoggerFactory.getLogger(UpdatePointOfSaleImpl.class);
	
	public UpdatePointOfSaleImpl(PointOfSaleMongoRepository pointOfSaleMongoRepository) {
		this.pointOfSaleMongoRepository= pointOfSaleMongoRepository;
	}
	
	@Override
	public Flux<PointOfSalesToMongoModel> updatePointOfSale(RedisUpdatePointOfSaleEvent updatePointOfSale) {
		return pointOfSaleMongoRepository.findAll()
		.filter(point-> point.getPoint().getId().equals(updatePointOfSale.getId()))
		.map(point-> setLocation(point,updatePointOfSale.getLocation()))
		.doOnNext(point-> pointOfSaleMongoRepository.save(point).subscribe())
		.doOnNext(point-> log.info("updating location Point of Sale {}", point));
	}
	
	private PointOfSalesToMongoModel setLocation(PointOfSalesToMongoModel point, String location) {
		point.getPoint().setLocation(location);
		return point;
	}
}
