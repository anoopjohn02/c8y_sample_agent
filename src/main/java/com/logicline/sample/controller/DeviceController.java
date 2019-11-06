package com.logicline.sample.controller;

import com.logicline.sample.agent.service.DeviceService;
import com.logicline.sample.agent.exceptions.AgentRuntimeException;
import com.logicline.sample.device.DeviceServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Controller
@RestController
@RequestMapping("/v1")
public class DeviceController {

    @Autowired
    @Qualifier("deviceService")
    private DeviceService deviceService;

    @GetMapping(value = "/sendnewmeasurement")
    @ResponseStatus(HttpStatus.OK)
    public String createDevice(Locale locale, Model model) throws AgentRuntimeException {
        deviceService.sendNewMeasurement();
        return "SUCCESS";
    }

    @GetMapping(value = "/switchon/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String switchon(Locale locale, Model model, @PathVariable(value = "id") String id) throws AgentRuntimeException {
        DeviceServiceFactory.getSwitchService().switchOn(id);
        return "SUCCESS";
    }

    @GetMapping(value = "/switchoff/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String switchoff(Locale locale, Model model, @PathVariable(value = "id") String id) throws AgentRuntimeException {
        DeviceServiceFactory.getSwitchService().switchOff(id);
        return "SUCCESS";
    }
}
