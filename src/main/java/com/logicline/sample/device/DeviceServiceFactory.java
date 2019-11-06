package com.logicline.sample.device;

import com.logicline.sample.device.service.raspberry.RaspberryPiLightService;
import com.logicline.sample.device.service.LightService;

public class DeviceServiceFactory {

    private static LightService lightService;
    public static LightService getLightService(){
        if(lightService == null){
            lightService = new RaspberryPiLightService();
            lightService.init();
        }
        return lightService;
    }
}
