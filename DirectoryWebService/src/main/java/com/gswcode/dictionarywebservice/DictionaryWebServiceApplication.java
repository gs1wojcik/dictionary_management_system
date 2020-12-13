package com.gswcode.dictionarywebservice;

import com.gswcode.dictionarywebservice.config.InitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DictionaryWebServiceApplication {

    public static void main(String[] args) {
        InitConfig.initExternalConfig();
        SpringApplication.run(DictionaryWebServiceApplication.class, args);
    }

}
