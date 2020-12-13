package com.gswcode.dictionarywebservice.controller;

import com.gswcode.dictionarywebservice.registry.RegistryHeader;
import com.gswcode.dictionarywebservice.registry.RegistryService;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("metadata/registry")
public class ControllerRegistry {
    
    private final static Logger LOGGER = Logger.getLogger(DictionaryController.class);
    private final static AtomicInteger REQUEST_NO = new AtomicInteger(0);
    
    @Autowired
    private RegistryService registyService;
    
    @GetMapping("/show")
    @ResponseStatus(HttpStatus.OK)
    public List<RegistryHeader> show() {
        LOGGER.debug("Preparing registry events...");
        return registyService.getRegistry();
    }
    
    public static long getNextRegisterId() {
        return REQUEST_NO.incrementAndGet();
    }
    
}
