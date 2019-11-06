package com.logicline.sample.device.service;

public interface SwitchService {
    public void init();

    public void switchOn(String switchId);

    public void switchOff(String switchId);
}
