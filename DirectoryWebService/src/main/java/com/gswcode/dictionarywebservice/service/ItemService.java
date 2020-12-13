/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gswcode.dictionarywebservice.service;

import com.gswcode.dictionarywebservice.domain.DictConf;
import com.gswcode.dictionarywebservice.domain.DictItem;
import com.gswcode.dictionarywebservice.dto.ServiceStatusDto;
import com.gswcode.dictionarywebservice.repository.DictConfRepository;
import com.gswcode.dictionarywebservice.repository.DictItemRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemService {

    private final static Logger LOGGER = Logger.getLogger(ItemService.class);

    @Autowired
    private DictConfRepository dictionaryRepo;

    @Autowired
    private DictItemRepository itemRepo;

    public List<DictItem> getItemsByDictionaryId(long id) {
        LOGGER.debug("Checking dictionary (" + id + ")...");
        Optional<DictConf> opt = dictionaryRepo.findById(id);
        List<Long> ids = new ArrayList<>();
        if (opt.isPresent()) {
            DictConf dictionary = opt.get();
            ids.add(id);
            completeDictIdRecursively(ids, dictionary);
            return itemRepo.getItemsByDictionariesId(ids);
        }
        return new ArrayList<>();
    }

    private void completeDictIdRecursively(List<Long> ids, DictConf dictConf) {
        LOGGER.debug("Checking sub dictionaries...");
        dictConf.getDictConfList().forEach(subDict -> {
            LOGGER.debug("Checking sub dictionary (" + subDict.getId() + ") items...");
            if (subDict.getIsActive()) {
                ids.add(subDict.getId());
                if (!subDict.getDictConfList().isEmpty()) {
                    completeDictIdRecursively(ids, subDict);
                }
            }
        });
    }

    public ServiceStatusDto addItem(DictItem item) {
        LOGGER.debug("Adding new item...");
        ServiceStatusDto status = new ServiceStatusDto();
        Optional<DictItem> opt = itemRepo.findByUniqueIndex(item.getIdDictConf().getId(), item.getTermName());
        if (opt.isPresent()) {
            LOGGER.debug("Error, this item exists: " + item.getIdDictConf().getId() + ", " + item.getTermName());
            status.setSuccess(false);
            status.setMessage("Item already exists!");
            return status;
        }
        item.setTermActive(true);
        itemRepo.save(item);
        if (item.getId() != 0) {
            status.setSuccess(true);
            status.setMessage("Item has been added");
        } else {
            status.setSuccess(false);
            status.setMessage("Item cannot be saved, try later");
        }
        return status;
    }

    public ServiceStatusDto updateItem(DictItem item) {
        LOGGER.debug("Updating item...");
        Optional<DictItem> opt = itemRepo.findById(item.getId());
        ServiceStatusDto status = new ServiceStatusDto();
        if (opt.isPresent()) {
            DictItem dictItem = opt.get();
            dictItem.setTermName(item.getTermName());
            dictItem.setTermDescription(item.getTermDescription());
            if (!item.equals(item.getAliasId()))
                dictItem.setAliasId(item.getAliasId());
            dictItem.setIdDictConf(item.getIdDictConf());
            itemRepo.save(dictItem);
            status.setSuccess(true);
            status.setMessage("Item has been updated");           
            if (item.equals(item.getAliasId()))
                status.setMessage("Item updated partially, it is not allowed to set master item id the same as current item id");                   
        } else {
            status.setSuccess(false);
            status.setMessage("Item cannot be updated, try later");
        }
        return status;
    }

    public ServiceStatusDto deactivate(long id) {
        LOGGER.debug("Deactivating item: " + id);
        ServiceStatusDto status = new ServiceStatusDto();
        Optional<DictItem> optDict = itemRepo.findById(id);
        if (optDict.isPresent()) {
            DictItem domain = optDict.get();
            if (domain.getTermActive()) {
                domain.setTermActive(false);
                itemRepo.save(domain);
                status.setMessage("Item deactivated: " + domain.getTermName());
                status.setSuccess(true);
            } else {
                status.setMessage("Item " + domain.getTermName() + " is already archived!");
                status.setSuccess(false);
            }
        } else {
            status.setMessage("Item not found");
            status.setSuccess(false);
        }
        return status;
    }

    public ServiceStatusDto activate(long id) {
        LOGGER.debug("Activating item: " + id);
        ServiceStatusDto status = new ServiceStatusDto();
        Optional<DictItem> optDict = itemRepo.findById(id);
        if (optDict.isPresent()) {
            DictItem domain = optDict.get();
            DictConf dictConf = dictionaryRepo.getOne(domain.getIdDictConf().getId());
            if (dictConf != null && !dictConf.getIsActive()) {
                status.setMessage("You can not activate the item when dictionary is deactivated");
                status.setSuccess(false);
                return status;
            }
            if (!domain.getTermActive()) {
                domain.setTermActive(true);
                itemRepo.save(domain);
                status.setMessage("Item restored: " + domain.getTermName());
                status.setSuccess(true);
            } else {
                status.setMessage("Item " + domain.getTermName() + " is already active!");
                status.setSuccess(false);
            }
        } else {
            status.setMessage("Item not found");
            status.setSuccess(false);
        }
        return status;
    }

    public ServiceStatusDto delete(long id) {
        LOGGER.debug("Deleting item: " + id);
        itemRepo.deleteById(id);
        ServiceStatusDto status = new ServiceStatusDto();
        status.setMessage("Item has been deleted");
        status.setSuccess(true);
        return status;
    }

    public DictItem getItemById(long id) {
        LOGGER.debug("Looking for item: " + id);
        Optional<DictItem> opt = itemRepo.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            return new DictItem();
        }
    }

}
