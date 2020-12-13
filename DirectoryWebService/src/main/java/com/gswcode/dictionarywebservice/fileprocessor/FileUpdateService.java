/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gswcode.dictionarywebservice.fileprocessor;

import com.google.gson.Gson;
import com.gswcode.dictionarywebservice.config.Path;
import com.gswcode.dictionarywebservice.service.DictionaryService;
import com.gswcode.dictionarywebservice.service.ItemService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FileUpdateService {

    private final static Logger LOGGER = Logger.getLogger(FileUpdateService.class);
    private final String fileDir = Path.getUpdateFileDir();
    private final String archiveDir = Path.getArchiveDir();
    private boolean terminateProcessing;
    private TerminationStatus terminationStatus;
    private boolean processing;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DictionaryService dictService;

    @Autowired
    private ItemService itemService;

    public long runUpdate(final String webhookUrl) {
        terminateProcessing = false;
        terminationStatus = null;
        File file = new File(fileDir);
        List<File> files;
        try {
            files = Files.list(file.toPath())
                    .filter(t -> t.getFileName().toString().endsWith(".csv"))
                    .map(t -> t.toFile())
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return 0;
        }

        if (files == null) {
            return 0;
        }
        int filesSize = files.size();

        LOGGER.info("Files to process: " + filesSize);
        if (filesSize > 0) {

            CompletableFuture.runAsync(() -> {
                processing = true;
                int filesProcessed = 0;
                int updatedDictionaries = 0;
                int updatedItems = 0;
                int lastLineNo = 0;
                int filesCounter = 0;

                for (File t : files) {
                    if (terminateProcessing) {
                        break;
                    }
                    filesCounter++;
                    LOGGER.info("Processing file: " + t.getName());
                    boolean errorOccured = false;
                    List<StatusUpdating> list = new ArrayList<>();
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(t), StandardCharsets.UTF_8))) {

                        String line;
                        boolean firstSkip = false;
                        while ((line = br.readLine()) != null) {
                            if (!firstSkip) {
                                firstSkip = true;
                                continue;
                            }
                            String[] array = line.split(";");
                            if (!array[0].equals("dict") && !array[0].equals("item")) {
                                errorOccured = true;
                                break;
                            }

                            StatusUpdating newStatus = new StatusUpdating(array[0], Long.valueOf(array[1]), Integer.valueOf(array[2]));
                            list.add(newStatus);
                        }
                        br.close();
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                        errorOccured = true;
                    }

                    if (errorOccured) {
                        LOGGER.warn("File " + t + " is incorrect! Go to next file...");
                        continue;
                    }
                    for (StatusUpdating status : list) {
                        checkTerminationStatus(t.getName(), list.size(), lastLineNo, filesSize - filesCounter);
                        if (terminateProcessing) {
                            break;
                        }
                        switch (status.getType()) {
                            case ("dict"):
                                if (status.isIsActive() == 1) {
                                    dictService.activate(status.getTypeId());
                                } else {
                                    dictService.deactivate(status.getTypeId());
                                }
                                updatedDictionaries++;
                                break;
                            case ("item"):
                                if (status.isIsActive() == 1) {
                                    itemService.activate(status.getTypeId());
                                } else {
                                    itemService.deactivate(status.getTypeId());
                                }
                                updatedItems++;
                                break;
                        }
                        lastLineNo++;
                    }

                    if (!terminateProcessing) {
                        LOGGER.info("Moving file " + t.getName() + " to archive...");
                        try {
                            Files.move(t.toPath(), new File(archiveDir + "/" + t.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                            list.clear();
                        } catch (IOException ex) {
                            LOGGER.error(ex.getMessage(), ex);
                        }
                    }
                    filesProcessed++;
                }
                if (!terminateProcessing) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("totalFiles", filesSize);
                    params.put("filesProcessed", filesProcessed);
                    params.put("status", "completed");
                    params.put("updatedDictionaries", updatedDictionaries);
                    params.put("updatedItems", updatedItems);
                    params.put("totalLines", lastLineNo);
                    LOGGER.info("Sending info to webhook...");
                    callWebhook(webhookUrl, params);
                    processing = false;
                } else {
                    LOGGER.warn("The process has been terminated: " + terminationStatus);
                }
            });
        }

        return filesSize;
    }

    public void callWebhook(String webhookUrl, Map<String, Object> params) {
        Gson gson = new Gson();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        HttpEntity<String> requestAdd = new HttpEntity<>(gson.toJson(params), headers);
        restTemplate.exchange(webhookUrl, HttpMethod.POST, requestAdd, Object.class);
    }

    private void checkTerminationStatus(String currFileName, int linesQty, int linesProcessed, int filesLeft) {
        if (terminateProcessing) {
            terminationStatus = new TerminationStatus(currFileName, linesQty, linesProcessed, linesQty - linesQty, filesLeft);
        }
    }

    public TerminationStatus stopProcessing() {
        if (processing) {
            terminateProcessing = true;
            while (terminationStatus == null) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
            }
            processing = false;
            return terminationStatus;
        } else {
            return new TerminationStatus("", 0, 0, 0, 0);
        }
    }

    public boolean isProcessing() {
        return processing;
    }

}
