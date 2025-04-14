package com.capo.asignacion_mongo.adapter.out.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.capo.asignacion_mongo.adapter.out.model.AccreditationMongoModel;


@Repository
public interface AccreditationMongoRepository extends ReactiveMongoRepository<AccreditationMongoModel, String>{

}
