/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gswcode.dictionary.service.client.mapper;

import com.gswcode.dictionary.service.client.dto.DictionaryDto;
import com.gswcode.dictionary.service.model.Dictionary;
import com.gswcode.dictionary.service.model.DictionaryKey;
import com.gswcode.dictionary.service.model.Status;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DictionaryMapper implements Mapper<Dictionary, DictionaryDto> {

    public final static Map<Long, DictionaryKey> NAMES_MAP = new HashMap<>();
    
    @Override
    public List<Dictionary> mapToModelList(List<DictionaryDto> dtos) {
        List<Dictionary> modelList = new ArrayList<>();
        NAMES_MAP.clear();
        NAMES_MAP.put(0l, new DictionaryKey(0, ""));
        dtos.forEach(dto -> NAMES_MAP.put(dto.getId(), new DictionaryKey(dto.getId(), dto.getName())));
        dtos.forEach(dto -> {
            String status = dto.isIsActive() ? Status.ACTIVE : Status.ARCHIVED;
            String masterDictionaryName = NAMES_MAP.get(dto.getMasterDictionaryId()).getName();
            modelList.add(new Dictionary(dto.getId(),
                    dto.getName(),
                    dto.getDescription(),
                    dto.getAuthor(),
                    LocalDateTime.parse(dto.getCreationAt()).format(FORMAT_TO_MODEL),
                    status,
                    dto.getMasterDictionaryId(),
                    masterDictionaryName,
                    dto.getTotalItems()));
        });
        return modelList;
    }

    @Override
    public Dictionary mapToModel(DictionaryDto dto) {
        String status = dto.isIsActive() ? Status.ACTIVE : Status.ARCHIVED;
        String masterDictionaryName = NAMES_MAP.get(dto.getMasterDictionaryId()).getName();
        return new Dictionary(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getAuthor(),
                LocalDateTime.parse(dto.getCreationAt()).format(FORMAT_TO_MODEL),
                status,
                dto.getMasterDictionaryId(),
                masterDictionaryName,
                dto.getTotalItems());
    }

    @Override
    public DictionaryDto mapToDto(Dictionary model) {
        boolean status = model.getStatus().equalsIgnoreCase(Status.ACTIVE);
        return new DictionaryDto(
                model.getMasterDictionaryId(),
                model.getName(),
                model.getDescription(),
                model.getAuthor(),
                Timestamp.valueOf(model.getCreatedAt()).toLocalDateTime().format(FORMAT_TO_DTO),
                status,
                model.getItems());
    }

}
