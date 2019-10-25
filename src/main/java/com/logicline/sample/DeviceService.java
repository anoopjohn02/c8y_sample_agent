package com.logicline.sample;

import c8y.Firmware;
import c8y.IsDevice;
import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.rest.representation.operation.OperationRepresentation;
import com.cumulocity.sdk.client.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("deviceService")
public class DeviceService {
    @Autowired
    private static Platform platform;

    private static final String BOX_NAME_PREFIX = "ed_";

    public static final String TYPE = "km_edgeDevice";

    @Value("${app.version}")
    private String version;

    public void createSampleDevice(){

        ManagedObjectRepresentation managedObjectRepresentation = new ManagedObjectRepresentation();
        managedObjectRepresentation.setType(TYPE);
        managedObjectRepresentation.setName(BOX_NAME_PREFIX + systemUtils.getSystemSerialNumber());
        managedObjectRepresentation.set(new com.cumulocity.model.Agent());
        managedObjectRepresentation.set(new IsDevice());

        final Firmware firmware = new Firmware();
        firmware.setName("KMAgent");
        firmware.setVersion(version);
        managedObjectRepresentation.set(firmware, "c8y_Firmware");
        platform.getInventoryApi().create(managedObjectRepresentation);
    }
}
