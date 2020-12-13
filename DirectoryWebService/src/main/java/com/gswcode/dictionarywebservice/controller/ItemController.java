package com.gswcode.dictionarywebservice.controller;

import com.gswcode.dictionarywebservice.dto.ItemDto;
import com.gswcode.dictionarywebservice.dto.ServiceStatusDto;
import com.gswcode.dictionarywebservice.mapper.ItemMapper;
import com.gswcode.dictionarywebservice.registry.RegistryService;
import com.gswcode.dictionarywebservice.service.ItemService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("item")
public class ItemController {

    private final static Logger LOGGER = Logger.getLogger(ItemController.class);

    @Autowired
    private ItemService service;

    @Autowired
    private ItemMapper mapper;
    
    @Autowired
    private RegistryService registryService;

    @GetMapping("/getList")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> getList(@RequestParam("dictionaryId") long id, HttpServletRequest request) {       
        LOGGER.debug("Loading items of diconary id: " + id);
        registryService.add(request, id, 0);
        return mapper.mapToListDto(service.getItemsByDictionaryId(id));
    }
    
    @GetMapping("/getById")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto getById(@RequestParam("id") long id, HttpServletRequest request) {
        registryService.add(request, 0, id);
        LOGGER.debug("Loading item of id: " + id);
        return mapper.mapToDto(service.getItemById(id));
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceStatusDto addItem(@RequestBody ItemDto item, HttpServletRequest request) {
        registryService.add(request, 0, item.getId());
        try {
            LOGGER.debug("Adding item: " + item);
            ServiceStatusDto status = service.addItem(mapper.mapToDomain(item));
            status.setRequestId(ControllerRegistry.getNextRegisterId());           
            return status;
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid request", ex);
        }
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ServiceStatusDto updateItem(@RequestBody ItemDto item, HttpServletRequest request) {
        registryService.add(request, 0, item.getId());
        try {
            LOGGER.debug("Updating item: " + item);
            ServiceStatusDto status = service.updateItem(mapper.mapToDomain(item));
            status.setRequestId(ControllerRegistry.getNextRegisterId());
            return status;
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid request", ex);
        }
    }

    @PutMapping("/deactivate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ServiceStatusDto deactivateItem(@RequestParam("itemId") long itemId, HttpServletRequest request) {
        registryService.add(request, 0, itemId);
        LOGGER.debug("Deactivating item: " + itemId);
        ServiceStatusDto status = service.deactivate(itemId);
        status.setRequestId(ControllerRegistry.getNextRegisterId());       
        return status;
    }
    
    @PutMapping("/activate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ServiceStatusDto activate(@RequestParam("itemId") long itemId, HttpServletRequest request) {
        registryService.add(request, 0, itemId);
        LOGGER.debug("Restoring item from archive: " + itemId);
        ServiceStatusDto status = service.activate(itemId);
        status.setRequestId(ControllerRegistry.getNextRegisterId());
        return status;
    }
    
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ServiceStatusDto deleteItem(@RequestParam("itemId") long itemId, HttpServletRequest request) {
        registryService.add(request, 0, itemId);
        LOGGER.debug("Deleting item: " + itemId);
        ServiceStatusDto status = service.delete(itemId);
        status.setRequestId(ControllerRegistry.getNextRegisterId());
        return status;
    }

}
