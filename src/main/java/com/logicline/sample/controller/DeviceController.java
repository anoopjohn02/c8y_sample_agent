package com.logicline.sample.controller;

import com.logicline.sample.DeviceService;
import com.logicline.sample.agent.exceptions.AgentRuntimeException;
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

    @GetMapping(value = "/createdevice")
    @ResponseStatus(HttpStatus.OK)
    public String createDevice(Locale locale, Model model) throws AgentRuntimeException {
        deviceService.createSampleDevice();
        return "SUCCESS";
    }
}
