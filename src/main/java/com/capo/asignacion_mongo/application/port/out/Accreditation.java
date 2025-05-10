package com.capo.asignacion_mongo.application.port.out;

import com.capo.asignacion_mongo.application.domain.model.AccreditationInformationModel;
import com.capo.asignacion_mongo.application.domain.model.AccreditationTicketModel;
import com.capo.asignacion_mongo.application.domain.service.AccreditationImpl;

public interface Accreditation {
	AccreditationTicketModel createAccreditation();
	AccreditationImpl getAccreditationInformation(AccreditationInformationModel AccreditationInformation);
}
