package com.logicline.sample.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class SystemUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemUtils.class);
    /**
     * Reads system-serial-number from device and writes it to the properties file.
     *
     * @return the serial number. Null, if the serial number could not be read from the system.
     */
    public String getSystemSerialNumber() {
        List<String> output = getLinuxCommandOutput("sudo dmidecode -s system-serial-number");

        // The serial number of the KM test environment looks like this:
        // VMware-56 4d 25 56 14 04 03 10-00 44 c7 d0 c5 26 89 a4
        if (output.isEmpty() || output.get(0).length() < 2
                || (output.get(0).contains(" ") && !output.get(0).startsWith("VMware"))) {
            String serialNumber = propUtils.getProperty(ESettings.agent_serial_number).trim();
            LOGGER.error("Could not read serial number from system. Standard serial number: {} is used.",
                    serialNumber);
            return serialNumber;
        } else {
            String serialNumber = output.get(0).replaceAll(" ", "_");
            propUtils.savePropertyToFile(ESettings.agent_serial_number, serialNumber);
            return serialNumber;
        }
    }
    /**
     * Executes a linux command and returns the output as list of strings.
     *
     * @param linuxCommand
     * @return
     */
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
