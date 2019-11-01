package com.logicline.sample.device.service.Impl;

import com.logicline.sample.device.service.LightService;
import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RaspberryPiLightService implements LightService {

    private static final Logger logger = LoggerFactory.getLogger(RaspberryPiLightService.class);

    private GpioPinDigitalOutput output;

    @Override
    public void init(){
        logger.info("RaspberryPiLight Operation - init ");
        GpioController gpio = GpioFactory.getInstance();
        Pin pin = CommandArgumentParser.getPin( RaspiPin.class, RaspiPin.GPIO_22, null);
        //output = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, "MyLED", PinState.HIGH);
        output = gpio.provisionDigitalOutputPin(pin, "My Output", PinState.HIGH);
        //output.setShutdownOptions(true, PinState.LOW);

        output.low();
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
