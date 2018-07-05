package com.teamthree.conferencescheduler.dto.user;

import java.io.File;
import java.io.Serializable;

public class FileUploadDto implements Serializable {
    private File file;

    public FileUploadDto() {
    }

    public FileUploadDto(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
