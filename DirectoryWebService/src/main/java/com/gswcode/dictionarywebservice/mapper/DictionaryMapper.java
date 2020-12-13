package com.gswcode.dictionarywebservice.mapper;

import com.gswcode.dictionarywebservice.domain.DictConf;
import com.gswcode.dictionarywebservice.dto.DictionaryDto;
import com.gswcode.dictionarywebservice.service.ItemService;
import com.gswcode.dictionarywebservice.util.Common;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DictionaryMapper {

    @Autowired
    private ItemService itemService;
    
    private final static Logger LOGGER = Logger.getLogger(DictionaryMapper.class);
    
    public DictConf mapToDomain(DictionaryDto dto) {
        LOGGER.debug("Mapping dictionary dto to domain: " + dto);
        DictConf masterDictConf = null;
        if (dto.getMasterDictionaryId()!= 0) 
            masterDictConf = new DictConf(dto.getMasterDictionaryId());
        DictConf dictConf = new DictConf();
        dictConf.setId(dto.getId());
        dictConf.setDictName(dto.getName());
        dictConf.setDictDescription(dto.getDescription());
        dictConf.setAuthor(dto.getAuthor()); 
        dictConf.setMasterDictId(masterDictConf);
        dictConf.setCreationTime(checkDateTime(dto.getCreationAt()));
        return dictConf;
    }
    
    public List<DictionaryDto> mapToListDto(List<DictConf> list) {
        List<DictionaryDto> dtos = new ArrayList<>();
        list.forEach(dictionary -> {
            dtos.add(mapToDto(dictionary));
        });
        return dtos;
    }

    public DictionaryDto mapToDto(DictConf domain) {
        LOGGER.debug("Mapping dictionary domain to dto: " + domain);
        long masterId = 0;
        if (domain.getMasterDictId() != null)
            masterId = domain.getMasterDictId().getId();   
        int itemsQty = itemService.getItemsByDictionaryId(domain.getId()).size();
        DictionaryDto dto = new DictionaryDto(
                domain.getId(),
                masterId,
                domain.getDictName(),
                domain.getDictDescription(),
                domain.getAuthor(),
                checkDateTime(domain.getCreationTime()),
                domain.getIsActive(),
                itemsQty);     
        LOGGER.debug("Object mapped successfully: " + dto);
        return dto;
    }

    private String checkDateTime(Timestamp timestamp) {      
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime().format(Common.LOCAL_DT_FORMATTER);
    }
    
    private Timestamp checkDateTime(String timestamp) {      
        if (timestamp == null) {
            return null;
        }
        return Timestamp.valueOf(LocalDateTime.parse(timestamp));
    }

}
