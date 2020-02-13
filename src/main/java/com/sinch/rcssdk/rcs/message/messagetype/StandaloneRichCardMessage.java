package com.sinch.rcssdk.rcs.message.messagetype;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sinch.rcssdk.rcs.message.component.richcard.RichCardContent;
import com.sinch.rcssdk.rcs.message.enums.MessageType;
import com.sinch.rcssdk.rcs.message.enums.OrientationType;
import com.sinch.rcssdk.rcs.message.enums.ThumbnailAlignmentType;
@JsonIgnoreProperties({"type"})
public class StandaloneRichCardMessage extends Message {

    private OrientationType orientation;
    private ThumbnailAlignmentType thumbnail_alignment;
    private RichCardContent content;

    public StandaloneRichCardMessage() {
        super(MessageType.standalone_rich_card);
    }

    public StandaloneRichCardMessage(OrientationType orientation, ThumbnailAlignmentType thumbnail_alignment,
                                     RichCardContent content) {
        super(MessageType.standalone_rich_card);
        this.orientation = orientation;
        this.thumbnail_alignment = thumbnail_alignment;
        this.content = content;
    }

    public OrientationType getOrientation() {
        return orientation;
    }

    public void setOrientation(OrientationType orientation) {
        this.orientation = orientation;
    }

    public ThumbnailAlignmentType getThumbnail_alignment() {
        return thumbnail_alignment;
    }

    public void setThumbnail_alignment(ThumbnailAlignmentType thumbnail_alignment) {
        this.thumbnail_alignment = thumbnail_alignment;
    }

    public RichCardContent getContent() {
        return content;
    }

    public void setContent(RichCardContent content) {
        this.content = content;
    }

    @Override
    public void generateFallback() {
        // TODO Auto-generated method stub

    }

}
