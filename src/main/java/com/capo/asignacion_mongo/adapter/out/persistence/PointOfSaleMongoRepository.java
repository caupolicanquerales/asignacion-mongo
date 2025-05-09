package com.capo.asignacion_mongo.adapter.out.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.capo.asignacion_mongo.adapter.out.model.PointOfSalesToMongoModel;

import reactor.core.publisher.Mono;

@Repository
public interface PointOfSaleMongoRepository extends ReactiveMongoRepository<PointOfSalesToMongoModel, String> {
	Mono<PointOfSalesToMongoModel> findByPointId(String id);
}
