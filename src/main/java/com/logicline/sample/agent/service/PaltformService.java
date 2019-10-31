package com.logicline.sample.agent.service;


import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.rest.representation.measurement.MeasurementRepresentation;
import com.cumulocity.sdk.client.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("paltformService")
public class PaltformService {

    @Autowired
    private Platform platform;

    public Optional<MeasurementRepresentation> createMeasurement(MeasurementRepresentation measurementRepresentation) {
        return Optional.of(platform.getMeasurementApi().create(measurementRepresentation));
    }

    public Optional<ManagedObjectRepresentation> getManagedObject(GId gid){
        return Optional.of(platform.getInventoryApi().get(gid));
    }
}
