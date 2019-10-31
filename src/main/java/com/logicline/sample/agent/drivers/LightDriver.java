package com.logicline.sample.agent.drivers;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.sdk.client.Platform;
import com.logicline.sample.device.service.Impl.RaspberryPiLightService;
import com.logicline.sample.device.service.LightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class LightDriver implements Driver, OperationExecutor {
    private static final Logger logger = LoggerFactory.getLogger(LightDriver.class);

    private Platform platform;

    @Autowired
    private GId gid;

    @Override
    public void initialize(Platform platform) throws Exception {
        this.platform = platform;
    }

    @Override
    public void start() {

    }

    @Override
    public void execute(Object obj) throws Exception {
        HashMap light = (HashMap) obj;
        logger.info("LIGHT Operation successfully called {} ", light.get("on"));
        LightService lightService = getLightService();
        if((boolean)light.get("on")){
            lightService.switchOn();
        } else {
            lightService.switchOff();
        }
    }

    @Override
    public ExecuterType getType() {
        return ExecuterType.LIGHT;
    }

    private LightService getLightService(){
        LightService lightService = new RaspberryPiLightService();
        lightService.init();
        return lightService;
    }
}
