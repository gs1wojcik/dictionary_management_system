/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gswcode.dictionarywebservice.dto;

public class WebhookRequest {
    
    private String webhookUrl;

    public WebhookRequest() {
    }

    public WebhookRequest(String webhookUrl) {
        this.webhookUrl = webhookUrl;

    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    @Override
    public String toString() {
        return "WebhookRequestDto{" + "webhookUrl=" + webhookUrl + '}';
    }
    
}
