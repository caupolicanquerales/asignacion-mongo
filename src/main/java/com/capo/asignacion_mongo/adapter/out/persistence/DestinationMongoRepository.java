package com.capo.asignacion_mongo.adapter.out.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.capo.asignacion_mongo.adapter.out.model.DestinationModel;
import com.capo.asignacion_mongo.adapter.out.model.DestinationToMongoModel;

import reactor.core.publisher.Mono;

@Repository
public interface DestinationMongoRepository extends ReactiveMongoRepository<DestinationToMongoModel, String>{
	Mono<DestinationToMongoModel> findByDestination(DestinationModel costAndDestination);
	Mono<DestinationToMongoModel> deleteByDestination(DestinationModel costAndDestination);
}
