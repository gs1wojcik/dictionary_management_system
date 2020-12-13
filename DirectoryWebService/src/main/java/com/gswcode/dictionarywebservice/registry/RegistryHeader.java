/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gswcode.dictionarywebservice.registry;

import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletRequest;

public final class RegistryHeader {
    
    private final static AtomicInteger index = new AtomicInteger(0);
    
    private int id;
    private String ipAddr;
    private long dictionaryId;
    private long itemId;
    private String usedEndpoint;
    private ZonedDateTime zonedDateTime;

    {
        this.zonedDateTime = ZonedDateTime.now();
        this.id = index.incrementAndGet();
    }
    
    public RegistryHeader() {
    }

    public RegistryHeader(HttpServletRequest request, long dictionaryId, long itemId) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");  
        if (ipAddress == null) {  
            ipAddress = request.getRemoteAddr();  
        }
        this.ipAddr = ipAddress;
        this.dictionaryId = dictionaryId;
        this.itemId = itemId;
        this.usedEndpoint = request.getServletPath();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getUsedEndpoint() {
        return usedEndpoint;
    }

    public void setUsedEndpoint(String usedEndpoint) {
        this.usedEndpoint = usedEndpoint;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.id;
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
        final RegistryHeader other = (RegistryHeader) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "RegistryHeader{" + "id=" + id + ", ipAddr=" + ipAddr + ", dictionaryId=" + dictionaryId + ", itemId=" + itemId + ", usedEndpoint=" + usedEndpoint + ", zonedDateTime=" + zonedDateTime + '}';
    }
    
}
