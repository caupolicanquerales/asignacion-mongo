package com.capo.asignacion_mongo.adapter.out.accreditationOperations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capo.asignacion_mongo.adapter.in.model.CostsAndRoutesFromResultModel;
import com.capo.asignacion_mongo.adapter.mappers.MapperMongoEvent;
import com.capo.asignacion_mongo.adapter.out.persistence.AccreditationMongoRepository;
import com.capo.asignacion_mongo.adapter.out.persistence.PointOfSaleMongoRepository;
import com.capo.asignacion_mongo.application.domain.service.AccreditationImpl;
import com.capo.asignacion_mongo.application.port.out.Accreditation;

@Service
public class CreatingAccreditationImpl implements CreatingAccreditation{
	
	private final PointOfSaleMongoRepository pointOfSaleMongoRepository;
	private final AccreditationMongoRepository accreditationMongoRepository;
	private Accreditation accreditation;
	private InsertInfomation insertInfomation;
	private static final Logger log = LoggerFactory.getLogger(CreatingAccreditationImpl.class);
	
	public CreatingAccreditationImpl(PointOfSaleMongoRepository pointOfSaleMongoRepository,
			AccreditationMongoRepository accreditationMongoRepository) {
		this.pointOfSaleMongoRepository= pointOfSaleMongoRepository;
		this.accreditationMongoRepository= accreditationMongoRepository;
		accreditation= new AccreditationImpl();
		insertInfomation= new AccreditationInformationImpl(); 
	}
	
	@Override
	public void creatingAccreditation(CostsAndRoutesFromResultModel costsAndRoutesFromResult){
		pointOfSaleMongoRepository.findAll()
				.doOnNext(point-> log.info("get result from Mongo point of sale {}", point))
				.filter(point-> point.getPoint().getId().equals(costsAndRoutesFromResult.getTravelfrom()))
				.map(point-> insertInfomation.insertInformation(costsAndRoutesFromResult, point))
				.map(obj-> obj.getAccreditationInformation())
				.map(info->accreditation.getAccreditationInformation(info))
				.map(obj-> obj.createAccreditation())
				.doOnNext(model-> log.info("passing for the whole process {}", model))
				.map(model-> MapperMongoEvent.mapperAccreditationMongoModel(model))
				.doOnNext(model-> accreditationMongoRepository.save(model).subscribe())
				.subscribe();
	}
}
