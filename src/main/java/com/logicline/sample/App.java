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
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(App.class, args);
    }

}
