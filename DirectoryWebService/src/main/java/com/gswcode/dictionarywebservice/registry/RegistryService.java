/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gswcode.dictionarywebservice.registry;

import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RegistryService {
    
    private final static Logger LOGGER = Logger.getLogger(RegistryService.class);
    
    private final List<RegistryHeader> list = new LinkedList<>();
    
    public void add(HttpServletRequest request, long dictionaryId, long itemId) {
        LOGGER.debug("Creating registry for dictionary: " + dictionaryId + ", item: " + itemId);
        LOGGER.debug("Request object: " + request);
        list.add(0, new RegistryHeader(request, dictionaryId, itemId));        
    }
    
    public List<RegistryHeader> getRegistry() {
        return list;
    }
    
}
