package com.capo.asignacion_mongo.application.port.out;

import com.capo.asignacion_mongo.application.domain.model.AccreditationTicketModel;

import reactor.core.publisher.Mono;

public interface AccreditationTicket {
	Mono<AccreditationTicketModel> save(AccreditationTicketModel accreditationTicket);
}
