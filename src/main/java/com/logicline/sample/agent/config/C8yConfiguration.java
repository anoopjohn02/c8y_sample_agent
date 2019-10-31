package com.logicline.sample.agent.config;

import com.cumulocity.model.idtype.GId;
import com.cumulocity.sdk.client.Platform;
import com.logicline.sample.agent.Agent;
import com.logicline.sample.agent.DeviceBootstrapProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class C8yConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(C8yConfiguration.class);

    @Value("${cumulocity.serial}")
    private String serial;

    @Value("${cumulocity.host}")
    private String host;

    @Value("${device.id}")
    private String device_id;

    @Bean
    public Platform platform(Agent agent) {
        return agent.getPlatform();
    }

    @Bean
    public Agent agent() {
        logger.info("Starting agent configuration {} {}.", host, serial);
        return new Agent(serial, host);
    }

    @Bean
    public GId gid() {
        return new GId(device_id);
    }

}
