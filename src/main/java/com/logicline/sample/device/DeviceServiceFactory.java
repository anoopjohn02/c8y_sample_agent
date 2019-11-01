package com.logicline.sample.device;

import com.logicline.sample.device.service.Impl.RaspberryPiLightService;
import com.logicline.sample.device.service.LightService;

public class DeviceServiceFactory {

    public static LightService getLightService(){
        LightService lightService = new RaspberryPiLightService();
        lightService.init();
        return lightService;
    }
}
