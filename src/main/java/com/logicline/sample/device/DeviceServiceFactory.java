package com.logicline.sample.device;

import com.logicline.sample.device.service.SwitchService;
import com.logicline.sample.device.service.raspberry.RaspberryPiLightService;
import com.logicline.sample.device.service.LightService;
import com.logicline.sample.device.service.xbee.XbeeSwitchService;

public class DeviceServiceFactory {

    private static LightService lightService;
    private static SwitchService switchService;

    public static LightService getLightService(){
        if(lightService == null){
            lightService = new RaspberryPiLightService();
            lightService.init();
        }
        return lightService;
    }


    public static SwitchService getSwitchService(){
        if(switchService == null){
            switchService = new XbeeSwitchService();
            switchService.init();
        }
        return switchService;
    }
}
