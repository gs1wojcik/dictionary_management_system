/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gswcode.dictionary.service.service;

import com.gswcode.dictionary.service.client.RestClient;
import com.gswcode.dictionary.service.client.dto.DictionaryDto;
import com.gswcode.dictionary.service.client.mapper.DictionaryMapper;
import com.gswcode.dictionary.service.model.Dictionary;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class DictionaryService {

    private final static Logger LOGGER = Logger.getLogger("DictionaryService");
    private boolean serverAvailable = true;

    @Autowired
    private RestClient restClient;

    @Autowired
    private DictionaryMapper mapper;

    public List<Dictionary> getDictionaries() {
        List<DictionaryDto> dtos;
        try {
            dtos = Arrays.asList(restClient.getDictionaries());
            serverAvailable = true;
        } catch (ResourceAccessException cex) {
            LOGGER.warning(cex.getMessage());
            dtos = new ArrayList<>();
            serverAvailable = false;
        }
        return mapper.mapToModelList(dtos);
    }

    public String saveDictionary(Dictionary model) {
        if (model.getId() > 0) { //update
            DictionaryDto dto = restClient.getDictionaryById(model.getId());
            dto.setName(model.getName());
            dto.setDescription(model.getDescription());
            dto.setAuthor(model.getAuthor());
            dto.setMasterDictionaryId(model.getMasterDictionaryId());
            return restClient.updateDictionary(dto).getMessage();
        } else { //insert
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            model.setCreatedAt(LocalDateTime.now().format(formatter));
            DictionaryDto dto = mapper.mapToDto(model);
            return restClient.addDictionary(dto).getMessage();
        }
    }

    public Dictionary getDictionaryById(long id) {
        return mapper.mapToModel(restClient.getDictionaryById(id));
    }

    public String deactivateDictionary(long id) {
        return restClient.deactivateDictionary(id).getMessage();
    }

    public String activateDictionary(long id) {
        return restClient.activateDictionary(id).getMessage();
    }

    public String deleteDictionary(long id) {
        return restClient.deleteDictionary(id).getMessage();
    }

    public boolean isServerAvailable() {
        return serverAvailable;
    }

}
