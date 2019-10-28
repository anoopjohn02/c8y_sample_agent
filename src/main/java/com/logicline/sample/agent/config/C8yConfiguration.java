package com.logicline.sample.agent.config;

import com.cumulocity.sdk.client.Platform;
import com.logicline.sample.agent.Agent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class C8yConfiguration {
    @Value("${cumulocity.serial}")
    private String serial;

    @Value("${cumulocity.host}")
    private String host;

    @Bean
    public Platform platform(Agent agent) {
        return agent.getPlatform();
    }

    @Bean
    public Agent agent() {
        return new Agent(serial, host);
    }
}
