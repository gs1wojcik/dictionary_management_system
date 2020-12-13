/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gswcode.dictionarywebservice.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class InitConfig {
    
    private final static Logger LOGGER = Logger.getLogger(InitConfig.class);
    
    public static void initExternalConfig() {
        Properties properties = new Properties();
        File configFile = new File(Path.getApplicationPath() + "/dws-config/dws-config.properties");
        try {
            FileReader fileReader = new FileReader(configFile);
            properties.load(fileReader);
            System.setProperty("dws.app.db_connection_string", properties.getProperty("dws.app.db_connection_string"));
            System.setProperty("dws.app.db_user", properties.getProperty("dws.app.db_user"));
            System.setProperty("dws.app.db_password", properties.getProperty("dws.app.db_password"));
            LOGGER.info("DB properties set successfully");
        } catch (IOException | NullPointerException ioe) {
            LOGGER.warn("File /dws-config/dws-config.properties can not be processed: " + ioe.getMessage());
            System.exit(0);
        }
        
        File brigdeFolder = new File(Path.getApplicationPath() + "/file-processing");
        if (!brigdeFolder.exists()) {
            LOGGER.warn("Dir /file-processing/ does not exist! Batch update from files off");
        } else {
            LOGGER.info("Dir /file-processing/ exists! Batch update from files on");
        }
        
    }
    
    
}
