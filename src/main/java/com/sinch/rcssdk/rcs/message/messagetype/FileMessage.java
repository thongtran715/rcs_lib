package com.sinch.rcssdk.rcs.message.messagetype;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sinch.rcssdk.rcs.message.component.richcard.FileInfo;
import com.sinch.rcssdk.rcs.message.enums.MessageType;
@JsonIgnoreProperties({"type"})
public class FileMessage extends Message {

    private FileInfo file;
    private FileInfo thumbnail;

    public FileMessage() {
        super(MessageType.file);
    }

    public FileMessage(FileInfo file, FileInfo thumbnail) {
        super(MessageType.file);
        this.file = file;
        this.thumbnail = thumbnail;
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

    @Override
    public void generateFallback() {
        // TODO Auto-generated method stub

    }

}
