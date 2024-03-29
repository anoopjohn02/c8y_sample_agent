package com.logicline.sample.device.service.raspberry;

import com.logicline.sample.device.service.LightService;
import com.pi4j.io.gpio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RaspberryPiLightService implements LightService {

    private static final Logger logger = LoggerFactory.getLogger(RaspberryPiLightService.class);

    private GpioPinDigitalOutput output;

    @Override
    public void init(){
        logger.info("RaspberryPiLight Operation - init ");
        GpioController gpio = GpioFactory.getInstance();
        output = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "MyLED", PinState.LOW);
    }

    @Override
    public void switchOn() {
        logger.info("RaspberryPiLight Operation - On ");
        output.high();
    }

    @Override
    public void switchOff() {
        logger.info("RaspberryPiLight Operation - Off ");
        output.low();
    }

    @Override
    public void toggle() {
        logger.info("RaspberryPiLight Operation - toggle ");
        output.toggle();
    }
}
