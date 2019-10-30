package com.logicline.sample;

import c8y.Firmware;
import c8y.IsDevice;
import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.rest.representation.measurement.MeasurementRepresentation;
import com.cumulocity.sdk.client.Platform;
import com.logicline.sample.agent.config.C8yConfiguration;
import com.logicline.sample.agent.exceptions.AgentRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service("deviceService")
public class DeviceService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);

    @Autowired
    private Platform platform;

    public void sendNewMeasurement() throws AgentRuntimeException {
        logger.info("Sending new measurement");

        ManagedObjectRepresentation mo = platform.getInventoryApi().get(new GId("14835439"));

        MeasurementRepresentation measurementRepresentation = new MeasurementRepresentation();
        measurementRepresentation.setSource(mo);
        measurementRepresentation.setTime(new Date());
        measurementRepresentation.setType("c8y_SampleMeasurement");

        Measurement measurement = new Measurement();
        measurement.setUnit("S");
        measurement.setValue(Math.random());
        measurementRepresentation.set(measurement);

        MeasurementRepresentation resultingMeasurementRepresentation = platform.getMeasurementApi().create(measurementRepresentation);
        if(resultingMeasurementRepresentation == null){
            logger.error("Failed to send measurement");

        } else {
            logger.info("New measurement has been sent");
        }
    }
}
