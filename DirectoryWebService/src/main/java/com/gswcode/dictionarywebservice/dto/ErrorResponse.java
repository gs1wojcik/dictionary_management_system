/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gswcode.dictionarywebservice.dto;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    
    private String error;
    private int code;
    private String message;

    public ErrorResponse(String error, int code, String message) {
        this.error = error;
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.error = httpStatus.getReasonPhrase();
        this.code = httpStatus.value();
        this.message = message;
    }

    public ErrorResponse() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
