package com.capo.asignacion_mongo.adapter.in.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capo.asignacion_mongo.adapter.in.model.AccreditationModel;
import com.capo.asignacion_mongo.adapter.out.accreditationOperations.AskingCostsAndRoutes;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("accreditation")
public class AccreditationController {
	
	@Autowired
	private AskingCostsAndRoutes askingCostsAndRoutes;
	
	
	@PostMapping("/create")
	public Mono<ResponseEntity<String>> createAccreditation(@RequestBody AccreditationModel request){
		return askingCostsAndRoutes.askingCostsAndRoutesFrom(request)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
}
