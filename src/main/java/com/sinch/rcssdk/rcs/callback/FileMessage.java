package com.sinch.rcssdk.rcs.callback;

import com.sinch.rcssdk.rcs.callback.enums.CallbackMessageType;
import com.sinch.rcssdk.rcs.message.component.richcard.FileInfo;

public class FileMessage extends CallbackMessage {

    private FileInfo file;
    private FileInfo thumbnail;

    public FileMessage() {
        super(CallbackMessageType.file);
    }

    public FileMessage(FileInfo file, FileInfo thumbnail) {
        super(CallbackMessageType.file);
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

}
