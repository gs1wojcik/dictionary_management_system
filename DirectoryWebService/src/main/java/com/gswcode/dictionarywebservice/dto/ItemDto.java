package com.gswcode.dictionarywebservice.dto;

import java.util.Objects;

public class ItemDto {
    
    private Long id;
    private Long dictionaryId;
    private String termName;
    private String termDescription;
    private boolean isActive;
    private Long masterItemId;

    public ItemDto(Long id, Long dictionaryId, String termName, String termDescription, boolean isActive, Long masterItemId) {
        this.id = id;
        this.dictionaryId = dictionaryId;
        this.termName = termName;
        this.termDescription = termDescription;
        this.isActive = isActive;
        this.masterItemId = masterItemId;
    }

    public ItemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
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

    public Long getMasterItemId() {
        return masterItemId;
    }

    public void setMasterItemId(Long masterItemId) {
        this.masterItemId = masterItemId;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemDto other = (ItemDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ItemDto{" + "id=" + id + ", dictionaryId=" + dictionaryId + ", termName=" + termName + ", termDescription=" + termDescription + ", isActive=" + isActive + ", masterItemId=" + masterItemId + '}';
    }

}
