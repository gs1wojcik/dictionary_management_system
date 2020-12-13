/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gswcode.dictionarywebservice.fileprocessor;

import java.util.Objects;

public final class StatusUpdating {
    
    private final String type;
    private final long typeId;
    private final int isActive;

    public StatusUpdating(String type, long typeId, int isActive) {
        this.type = type;
        this.typeId = typeId;
        this.isActive = isActive;
    }

    public String getType() {
        return type;
    }

    public long getTypeId() {
        return typeId;
    }

    public int isIsActive() {
        return isActive;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + (int) (this.typeId ^ (this.typeId >>> 32));
        hash = 29 * hash + this.isActive;
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
        final StatusUpdating other = (StatusUpdating) obj;
        if (this.typeId != other.typeId) {
            return false;
        }
        if (this.isActive != other.isActive) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "StatusUpdating{" + "type=" + type + ", typeId=" + typeId + ", isActive=" + isActive + '}';
    }
    
}
