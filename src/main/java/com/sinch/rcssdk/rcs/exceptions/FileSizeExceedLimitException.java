package com.sinch.rcssdk.rcs.exceptions;

import com.sinch.rcssdk.rcs.message.messagetype.AgentMessage;

public class FileSizeExceedLimitException extends Exception {
    private AgentMessage.Supplier supplier;
    private long size;

    public FileSizeExceedLimitException(AgentMessage.Supplier supplier, long size) {
        this.supplier = supplier;
        this.size = size;
    }

    @Override
    public String getMessage() {
        return this.toString();
    }

    @Override
    public String toString() {
        switch (supplier) {
            case GOOGLE:
                return "Size " + size + " exceed recommendation limit. Image is 2MB, and Video is 10MB ";
            case MAAP_SAMSUNG:
                return "Size " + size + " exceed recommendation limit. The file should not exceed 100MB ";
            default:
                return "Size " + size + " exceed recommendation limit";
        }
    }
}
