package com.sinch.rcssdk.rcs.message.component.richcard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sinch.rcssdk.rcs.util.Util;

import java.io.IOException;

public class FileInfo {
    @JsonIgnore
    private Mime_type mime;

    private String mime_type;
    private long file_size;
    private String file_name;
    private String file_uri;

    public FileInfo() {

    }

    public FileInfo(Mime_type mime_type, String file_name, String file_uri) {
        try {
            this.file_size = Util.getFileSize(file_uri);
            this.file_name = file_name;
            this.file_uri = file_uri;
            this.mime = mime_type;
            this.mime_type = mime_type.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public FileInfo(Mime_type mime_type, long file_size, String file_name, String file_uri) {
        this.file_size = file_size;
        this.file_name = file_name;
        this.file_uri = file_uri;
        this.mime = mime_type;
        this.mime_type = mime_type.toString();
    }

    public Mime_type getMime() {
        return mime;
    }

    public void setMime(Mime_type mime_type) {
        this.mime = mime_type;
        this.mime_type = mime_type.toString();
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public long getFile_size() {
        return file_size;
    }

    public void setFile_size(long file_size) {
        this.file_size = file_size;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_uri() {
        return file_uri;
    }

    public void setFile_uri(String file_uri) {
        this.file_uri = file_uri;
    }

    public enum Mime_type {
        VIDEO_MP4("video/mp4"), IMAGE_PNG("image/png"), IMAGE_JPEG("image/jpeg");
        private String supply;

        Mime_type(String supply) {
            this.supply = supply;
        }

        @Override
        public String toString() {
            return String.valueOf(supply);
        }
    }
}
