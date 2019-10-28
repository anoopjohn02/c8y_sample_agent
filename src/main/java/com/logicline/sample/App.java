package com.logicline.sample;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cumulocity.sdk.client.Platform;

import java.nio.file.Paths;

@SpringBootApplication
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private static final String SYSTEM_PROP_CONFIG_DIR = "SYSTEM_PROP_CONFIG_DIR";
    private static final String SYSTEM_PROP_LOG_DIR = "SYSTEM_PROP_LOG_DIR";
    private static final String CURRENT_USER_DIR = Paths.get("").toAbsolutePath().toString();

    public static String getConfigDir() {
        return System.getProperty(SYSTEM_PROP_CONFIG_DIR);
    }

    @Autowired
    @Qualifier("deviceService")
    private DeviceService deviceService;

    public static String getLogDir() {
        return System.getProperty(SYSTEM_PROP_LOG_DIR);
    }

    public static void main(String[] args) throws InterruptedException {
        if (args != null && args.length > 0 && StringUtils.isNotBlank(args[0])) {
            System.setProperty(SYSTEM_PROP_CONFIG_DIR, args[0]);
        } else {
            LOGGER.warn("First argument was null, using {} as config dir.", CURRENT_USER_DIR);
            System.setProperty(SYSTEM_PROP_CONFIG_DIR, CURRENT_USER_DIR);
        }

        if (args != null && args.length > 1 && StringUtils.isNotBlank(args[1])) {
            System.setProperty(SYSTEM_PROP_LOG_DIR, args[0]);
        } else {
            LOGGER.warn("First argument was null, using {} as log dir.", CURRENT_USER_DIR + "/log");
            System.setProperty(SYSTEM_PROP_LOG_DIR, CURRENT_USER_DIR + "/log");
        }

        SpringApplication.run(App.class, args);
    }

}
