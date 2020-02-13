package com.sinch.rcssdk.rcs.message.component.richcard;

import com.sinch.rcssdk.rcs.message.enums.HeightType;

public class RichCardMedia {
    private FileInfo file;
    private FileInfo thumbnail;
    private HeightType height;

    public RichCardMedia() {

    }

    public RichCardMedia(FileInfo file, FileInfo thumbnail, HeightType height) {
        this.file = file;
        this.thumbnail = thumbnail;
        this.height = height;
    }

    public FileInfo getFile() {
        return file;
    }

    public void setFile(FileInfo file) {
        this.file = file;
    }

    public FileInfo getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(FileInfo thumbnail) {
        this.thumbnail = thumbnail;
    }

    public HeightType getHeight() {
        return height;
    }

    public void setHeight(HeightType height) {
        this.height = height;
    }
}
