package com.capo.asignacion_mongo.application.domain.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import com.capo.asignacion_mongo.application.domain.model.AccreditationModel;
import com.capo.asignacion_mongo.application.domain.model.AccreditationTicketModel;
import com.capo.asignacion_mongo.application.domain.model.EstimationFromVertexModel;
import com.capo.asignacion_mongo.application.domain.model.PointModel;
import com.capo.asignacion_mongo.application.port.in.Accreditation;
import com.capo.asignacion_mongo.application.port.out.AccreditationTicket;
import com.capo.asignacion_mongo.application.port.out.CostAndRoute;
import com.capo.asignacion_mongo.application.port.out.PointsOfSale;

import reactor.core.publisher.Mono;

public class AccreditationImpl implements Accreditation {
	
	private CostAndRoute costAndRoute;
	
	private PointsOfSale pointsOfSale;
	
	private AccreditationTicket accreditationTicket;
	
	public AccreditationImpl(CostAndRoute costAndRoute,PointsOfSale pointsOfSale,
			AccreditationTicket accreditationTicket) {
		this.costAndRoute=costAndRoute;
		this.pointsOfSale=pointsOfSale;
		this.accreditationTicket=accreditationTicket;
	}
	
	@Override
	public Mono<String> createAccreditation(AccreditationModel accreditationModel) {
		EstimationFromVertexModel estimationFromVertex = getEstimationFromVertexModel(accreditationModel); 
		return costAndRoute.estimationOfCosts(estimationFromVertex)
				.map(result->result.getResult())
				.map(mapResult->{
					Map<Integer,String> mapWeight= mapResult.get("weight");
					Map<Integer,String> mapRoute= mapResult.get("route");
					Integer travelTo = Integer.valueOf(accreditationModel.getTravelTo());
					if(mapWeight.get(travelTo)!=null){
						return getPointsOfSale(accreditationModel.getTravelfrom(), mapWeight.get(travelTo), mapRoute.get(travelTo));
					}
					return Mono.just("ERROR");
				}).flatMap(res->res);
	}
	
	private EstimationFromVertexModel getEstimationFromVertexModel(AccreditationModel accreditationModel) {
		EstimationFromVertexModel estimationFromVertex= new EstimationFromVertexModel();
		estimationFromVertex.setVertex(accreditationModel.getTravelfrom());
		return estimationFromVertex;
	}
	
	private Mono<String> getPointsOfSale(String travelFrom, String cost, String route) {
		return pointsOfSale.getPointsOfSale()
		.map(points-> points.getPoints().stream().filter(point->point.getLocation().equals(travelFrom))
				.collect(Collectors.toList()).get(0)
		).map(point->getAccreditationMongo(point, cost, route))
		.map(accreditationTicket::save)
		.flatMap(response->{
			return response.map(item->item.getId());
		}).map(result->{
			return "OK";
		});
	}
	
	private AccreditationTicketModel getAccreditationMongo(PointModel point, String cost, String route) {
		AccreditationTicketModel accreditationTicket= new AccreditationTicketModel();
		accreditationTicket.setCost(cost);
		accreditationTicket.setRoute(route);
		accreditationTicket.setDateOfReception(setCurrentDate());
		accreditationTicket.setIdPontOfSale(point.getLocation());
		accreditationTicket.setNamePontOfSale(point.getId());
		return accreditationTicket;
	}
	
	private String setCurrentDate() {
		SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy"); 
		return ft.format(new Date());
	}
}
