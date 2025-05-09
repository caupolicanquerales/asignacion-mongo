package com.capo.asignacion_mongo.adapter.out.accreditationOperations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capo.asignacion_mongo.adapter.in.model.CostsAndRoutesFromResultModel;
import com.capo.asignacion_mongo.adapter.out.model.PointOfSalesToMongoModel;
import com.capo.asignacion_mongo.adapter.out.persistence.PointOfSaleMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreatingAccreditationImpl implements CreatingAccreditation{
	
	private final PointOfSaleMongoRepository pointOfSaleMongoRepository;
	private static final Logger log = LoggerFactory.getLogger(CreatingAccreditationImpl.class);
	
	public CreatingAccreditationImpl(PointOfSaleMongoRepository pointOfSaleMongoRepository) {
		this.pointOfSaleMongoRepository= pointOfSaleMongoRepository;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Flux<PointOfSalesToMongoModel> creatingAccreditation(CostsAndRoutesFromResultModel costsAndRoutesFromResult){
		return (Flux<PointOfSalesToMongoModel>) pointOfSaleMongoRepository.findAll()
				.doOnNext(res-> log.info("get result from Mongo point of sale {}", res))
				.subscribe();
	}
}
