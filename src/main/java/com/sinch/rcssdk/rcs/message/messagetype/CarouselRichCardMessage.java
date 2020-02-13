package com.sinch.rcssdk.rcs.message.messagetype;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sinch.rcssdk.rcs.message.component.richcard.RichCardContent;
import com.sinch.rcssdk.rcs.message.enums.MessageType;
import com.sinch.rcssdk.rcs.message.enums.WidthType;

import java.util.List;
@JsonIgnoreProperties({"type"})
public class CarouselRichCardMessage extends Message {

    private WidthType width;
    private List<RichCardContent> contents;

    public CarouselRichCardMessage() {
        super(MessageType.carousel_rich_card);

    }

    public WidthType getWidth() {
        return width;
    }

    public void setWidth(WidthType width) {
        this.width = width;
    }

    public List<RichCardContent> getContents() {
        return contents;
    }

    public void setContents(List<RichCardContent> contents) {
        this.contents = contents;
    }

    @Override
    public void generateFallback() {
        // TODO Auto-generated method stub

    }

}
