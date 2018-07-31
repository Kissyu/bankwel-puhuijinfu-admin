package com.bankwel.phjf_admin.common.model.core;

import java.io.FileInputStream;

/**
 * Created by admin on 2018/1/3.
 */
public class FileInfo {

    private String extension = "";
    private String busPath = "";
    private String base64 = "";
    private String fileName = "";
    private String isDatePath = "";
    private String fileStr = "";
    private byte[] data = null;
//    @JSONField(serialize=false)
    private FileInputStream fileInput = null;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getBusPath() {
        return busPath;
    }

    public void setBusPath(String busPath) {
        this.busPath = busPath;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getIsDatePath() {
        return isDatePath;
    }

    public void setIsDatePath(String isDatePath) {
        this.isDatePath = isDatePath;
    }

    public String getFileStr() {
        return fileStr;
    }

    public void setFileStr(String fileStr) {
        this.fileStr = fileStr;
    }

    public FileInputStream getFileInput() {
        return fileInput;
    }

    public void setFileInput(FileInputStream fileInput) {
        this.fileInput = fileInput;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
