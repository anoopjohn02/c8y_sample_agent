package com.logicline.sample.device.service.Impl;

import com.logicline.sample.device.service.LightService;
import com.pi4j.io.gpio.*;

public class RaspberryPiLightService implements LightService {

    private GpioPinDigitalOutput pin;

    @Override
    public void init(){
        GpioController gpio = GpioFactory.getInstance();
        pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);
    }

    @Override
    public void switchOn() {
        pin.high();
    }

    @Override
    public void switchOff() {
        pin.low();
    }

    @Override
    public void toggle() {
        pin.toggle();
    }
}
