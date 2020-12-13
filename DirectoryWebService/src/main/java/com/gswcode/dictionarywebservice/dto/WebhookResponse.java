/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gswcode.dictionarywebservice.dto;

public class WebhookResponse {
    
    private long filesToProcess;

    public WebhookResponse() {
    }

    public WebhookResponse(long filesToProcess) {
        this.filesToProcess = filesToProcess;
    }

    public long getFilesToProcess() {
        return filesToProcess;
    }

    public void setFilesToProcess(long filesToProcess) {
        this.filesToProcess = filesToProcess;
    }

    @Override
    public String toString() {
        return "WebhookResponse{" + "filesToProcess=" + filesToProcess + '}';
    }
    
}
