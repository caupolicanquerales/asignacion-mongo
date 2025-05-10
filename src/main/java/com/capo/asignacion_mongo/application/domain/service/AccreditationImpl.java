package com.capo.asignacion_mongo.application.domain.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.capo.asignacion_mongo.application.domain.model.AccreditationInformationModel;
import com.capo.asignacion_mongo.application.domain.model.AccreditationTicketModel;
import com.capo.asignacion_mongo.application.domain.model.PointModel;
import com.capo.asignacion_mongo.application.port.out.Accreditation;

public class AccreditationImpl implements Accreditation {
	
	private AccreditationInformationModel accreditationInformation;
	private static final String WEIGHT= "weight";
	private static final String ROUTE= "route";
	
	@Override
	public AccreditationImpl getAccreditationInformation(AccreditationInformationModel accreditationInformation) {
		this.accreditationInformation= accreditationInformation;
		return this;
	}
	
	@Override
	public AccreditationTicketModel createAccreditation() {
		Map<Integer,String> mapWeight= accreditationInformation.getResult().get(WEIGHT);
		Map<Integer,String> mapRoute= accreditationInformation.getResult().get(ROUTE);
		PointModel point= accreditationInformation.getPoint();
		String cost = mapWeight.get(Integer.parseInt(accreditationInformation.getTravelTo()));
		String route = mapRoute.get(Integer.parseInt(accreditationInformation.getTravelTo()));
		return createAccreditationTicket(point,cost,route);
	}
	
	private AccreditationTicketModel createAccreditationTicket(PointModel point, String cost, String route) {
		AccreditationTicketModel accreditationTicket= new AccreditationTicketModel();
		accreditationTicket.setCost(cost);
		accreditationTicket.setRoute(route);
		accreditationTicket.setDateOfReception(setCurrentDate());
		accreditationTicket.setIdPontOfSale(point.getId());
		accreditationTicket.setNamePontOfSale(point.getLocation());
		return accreditationTicket;
	}
	
	private String setCurrentDate() {
		SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy"); 
		return ft.format(new Date());
	}
}
