/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gswcode.dictionary.service.config;

public class Property {
    
    private static String BASE_URL;

    public static String BASE_URL() {
        if (BASE_URL == null) {
            System.out.println("Setting http base url...");
            BASE_URL = System.getenv("app.base_url");
            if (BASE_URL == null)
                BASE_URL = System.getProperty("app.base_url");
            System.out.println("Base url is: " + BASE_URL);
        }
        return BASE_URL;
    }
    
}
