package com.logicline.sample.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class SystemUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemUtils.class);

    @Autowired
    private PropUtils propUtils;

    public String getSystemSerialNumber() {
        List<String> output = getLinuxCommandOutput("sudo dmidecode -s system-serial-number");
        if (output.isEmpty() || output.get(0).length() < 2
                || (output.get(0).contains(" ") && !output.get(0).startsWith("VMware"))) {
            String serialNumber = propUtils.getProperty(C8yProperties.agent_serial_number).trim();
            LOGGER.error("Could not read serial number from system. Standard serial number: {} is used.",
                    serialNumber);
            return serialNumber;
        } else {
            String serialNumber = output.get(0).replaceAll(" ", "_");
            propUtils.savePropertyToFile(C8yProperties.agent_serial_number, serialNumber);
            return serialNumber;
        }
    }

    private List<String> getLinuxCommandOutput(String linuxCommand) {
        List<String> output = new ArrayList<>();

        final String osName = System.getProperty("os.name");

        if (osName != null && osName.trim().contains("indows")) {
            LOGGER.warn("Can not run linux command {} on windows.", linuxCommand);
            return output;
        }

        try {
            // execute command and wait for finish
            Process proc = Runtime.getRuntime().exec(linuxCommand);

            // add all lines to output
            BufferedReader bReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = bReader.readLine()) != null) {
                output.add(line);
            }
        } catch (IOException e) {
            LOGGER.error("Error executing command: " + linuxCommand, e);
        }

        return output;
    }
}
