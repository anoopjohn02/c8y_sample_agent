package com.logicline.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cumulocity.sdk.client.Platform;

@SpringBootApplication
public class App {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(App.class, args);
    }
}
