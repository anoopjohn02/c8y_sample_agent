package com.logicline.sample.agent.service;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.rest.representation.measurement.MeasurementRepresentation;
import com.logicline.sample.agent.exceptions.AgentRuntimeException;
import com.logicline.sample.commons.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service("deviceService")
public class DeviceService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);

    @Autowired
    private GId gid;

    @Autowired
    @Qualifier("paltformService")
    private PaltformService paltformService;

    public void sendNewMeasurement() throws AgentRuntimeException {
        logger.info("Sending new measurement");
        Optional<ManagedObjectRepresentation> mo = paltformService.getManagedObject(gid);
        if(mo.isPresent()){
            MeasurementRepresentation measurementRepresentation = new MeasurementRepresentation();
            measurementRepresentation.setSource(mo.get());
            measurementRepresentation.setTime(new Date());
            measurementRepresentation.setType("c8y_SampleMeasurement");
            measurementRepresentation.set(Util.getRandomMeasurement());

            Optional<MeasurementRepresentation> resultingMeasurementRepresentation = paltformService.createMeasurement(measurementRepresentation);
            if(resultingMeasurementRepresentation.isPresent()){
                logger.info("New measurement has been sent");
            } else {
                logger.error("Failed to send measurement");
            }
        } else {
            logger.error("Failed to send measurement : Managed Objects not found");
        }
    }
}
