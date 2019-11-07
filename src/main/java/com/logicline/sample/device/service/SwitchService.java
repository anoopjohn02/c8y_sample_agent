package com.logicline.sample.device.service;

import com.logicline.sample.agent.exceptions.AgentRuntimeException;

public interface SwitchService {
    public void init();

    public void switchOn(String switchId) throws AgentRuntimeException;

    public void switchOff(String switchId) throws AgentRuntimeException;
}
