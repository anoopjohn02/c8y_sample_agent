package com.logicline.sample;

import c8y.Firmware;
import c8y.IsDevice;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.sdk.client.Platform;
import com.logicline.sample.agent.exceptions.AgentRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("deviceService")
public class DeviceService {
    @Autowired
    private Platform platform;
    
    public void createSampleDevice() throws AgentRuntimeException {


    }
}
