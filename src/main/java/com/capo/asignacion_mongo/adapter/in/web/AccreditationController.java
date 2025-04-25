package com.capo.asignacion_mongo.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capo.asignacion_mongo.adapter.in.model.AccreditationRequest;
import com.capo.asignacion_mongo.application.port.in.Accreditation;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("accreditation")
public class AccreditationController {
	
	private Accreditation accreditation;
	
	
	@PostMapping("/create")
	public Mono<ResponseEntity<String>> createAccreditation(@RequestBody AccreditationRequest request){
		//return accreditation.createAccreditation(request)
		//		.map(ResponseEntity.ok()::body)
		//		.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
		return null;
	}
}
