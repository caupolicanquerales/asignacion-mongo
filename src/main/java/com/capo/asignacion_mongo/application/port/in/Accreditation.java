package com.capo.asignacion_mongo.application.port.in;

import com.capo.asignacion_mongo.application.domain.model.AccreditationModel;

import reactor.core.publisher.Mono;

public interface Accreditation {
	Mono<String> createAccreditation(AccreditationModel accreditationModel);
}
