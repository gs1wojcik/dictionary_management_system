/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gswcode.dictionary.service.client.dto;

public class ItemDto {
    
    private long id;
    private long dictionaryId;
    private String termName;
    private String termDescription;
    private boolean isActive;
    private long masterItemId;

    public ItemDto() {
    }

    public ItemDto(long id, long dictionaryId, String term, String description, boolean isActive, long masterItemId) {
        this.id = id;
        this.dictionaryId = dictionaryId;
        this.termName = term;
        this.termDescription = description;
        this.isActive = isActive;
        this.masterItemId = masterItemId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getTermDescription() {
        return termDescription;
    }

    public void setTermDescription(String termDescription) {
        this.termDescription = termDescription;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public long getMasterItemId() {
        return masterItemId;
    }

    public void setMasterItemId(long masterItemId) {
        this.masterItemId = masterItemId;
    }
    
}
