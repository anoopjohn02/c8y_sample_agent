package com.logicline.sample.controller;

import com.logicline.sample.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

@Controller
public class DeviceController {

    @Autowired
    @Qualifier("deviceService")
    private DeviceService deviceService;

    @RequestMapping(value = "/createdevice", method = RequestMethod.GET)
    public String createDevice(Locale locale, Model model) {

        return "SUCCESS";
    }
}
