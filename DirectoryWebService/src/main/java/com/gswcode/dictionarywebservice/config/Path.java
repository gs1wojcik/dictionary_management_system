/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gswcode.dictionarywebservice.config;

import com.gswcode.dictionarywebservice.DictionaryWebServiceApplication;
import org.springframework.boot.system.ApplicationHome;

public class Path {

    public static String getApplicationPath() {
        ApplicationHome home = new ApplicationHome(DictionaryWebServiceApplication.class);
        return home.getDir().getAbsolutePath();
//        return "src/main/resources";
    }

    public static String getUpdateFileDir() {
        return getApplicationPath() + "/file-processing";
    }
    
    public static String getArchiveDir() {
        return getUpdateFileDir() + "/archive";
    }
    
}
