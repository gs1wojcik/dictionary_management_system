/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gswcode.dictionarywebservice.fileprocessor;

public class TerminationStatus {
    
    private String teminatedFileName;
    private int totalLines;
    private int linesProcessed;
    private int linesRemaining;
    private int remainingFiles;

    public TerminationStatus() {
    }

    public TerminationStatus(String teminatedFileName, int totalLines, int linesProcessed, int linesRemaining, int remainingFiles) {
        this.teminatedFileName = teminatedFileName;
        this.totalLines = totalLines;
        this.linesProcessed = linesProcessed;
        this.linesRemaining = totalLines-linesProcessed;
        this.remainingFiles = remainingFiles;
    }

    public String getTeminatedFileName() {
        return teminatedFileName;
    }

    public void setTeminatedFileName(String teminatedFileName) {
        this.teminatedFileName = teminatedFileName;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public int getLinesProcessed() {
        return linesProcessed;
    }

    public void setLinesProcessed(int linesProcessed) {
        this.linesProcessed = linesProcessed;
    }

    public int getLinesRemaining() {
        return linesRemaining;
    }

    public void setLinesRemaining(int linesRemaining) {
        this.linesRemaining = linesRemaining;
    }

    public int getRemainingFiles() {
        return remainingFiles;
    }

    public void setRemainingFiles(int remainingFiles) {
        this.remainingFiles = remainingFiles;
    }

    @Override
    public String toString() {
        return "TerminationStatus{" + "teminatedFileName=" + teminatedFileName + ", totalLines=" + totalLines + ", linesProcessed=" + linesProcessed + ", linesRemaining=" + linesRemaining + ", remainingFiles=" + remainingFiles + '}';
    }
    
}
