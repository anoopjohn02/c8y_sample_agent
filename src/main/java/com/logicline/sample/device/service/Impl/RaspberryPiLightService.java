package com.logicline.sample.device.service.Impl;

import com.logicline.sample.agent.drivers.LightDriver;
import com.logicline.sample.device.service.LightService;
import com.pi4j.io.gpio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RaspberryPiLightService implements LightService {

    private static final Logger logger = LoggerFactory.getLogger(RaspberryPiLightService.class);

    private GpioPinDigitalOutput pin;

    @Override
    public void init(){
        logger.info("RaspberryPiLight Operation - init ");
        GpioController gpio = GpioFactory.getInstance();
        pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "MyLED", PinState.HIGH);
        //pin.low();
    }

    @Override
    public void switchOn() {
        logger.info("RaspberryPiLight Operation - On ");
        pin.high();
    }

    @Override
    public void switchOff() {
        logger.info("RaspberryPiLight Operation - Off ");
        pin.low();
    }

    @Override
    public void toggle() {
        logger.info("RaspberryPiLight Operation - toggle ");
        pin.toggle();
    }
}
