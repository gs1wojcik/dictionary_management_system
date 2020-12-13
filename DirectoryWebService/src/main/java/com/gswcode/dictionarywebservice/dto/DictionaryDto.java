package com.gswcode.dictionarywebservice.dto;

public class DictionaryDto {

    private long id;
    private long masterDictionaryId;   
    private String name;
    private String description;
    private String author;
    private String creationAt;
    private boolean isActive;
    private int totalItems;

    public DictionaryDto() {
    }

    public DictionaryDto(long id, long masterDictionaryId, String name, String description, String author, String creationAt, boolean isActive, int totalItems) {
        this.id = id;
        this.masterDictionaryId = masterDictionaryId;
        this.name = name;
        this.description = description;
        this.author = author;
        this.creationAt = creationAt;
        this.isActive = isActive;
        this.totalItems = totalItems;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMasterDictionaryId() {
        return masterDictionaryId;
    }

    public void setMasterDictionaryId(long masterDictionaryId) {
        this.masterDictionaryId = masterDictionaryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desciption) {
        this.description = desciption;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreationAt() {
        return creationAt;
    }

    public void setCreationAt(String creationAt) {
        this.creationAt = creationAt;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final DictionaryDto other = (DictionaryDto) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DictionaryDto{" + "id=" + id + ", masterDictionaryId=" + masterDictionaryId + ", name=" + name + ", description=" + description + ", author=" + author + ", creationAt=" + creationAt + ", isActive=" + isActive + ", totalItems=" + totalItems + '}';
    }

}
