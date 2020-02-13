package com.sinch.rcssdk.rcs.message.messagetype;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sinch.rcssdk.rcs.message.Utils.UtilsToString;
import com.sinch.rcssdk.rcs.message.enums.MessageType;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextMessage.class, name = "text"),
        @JsonSubTypes.Type(value = StandaloneRichCardMessage.class, name = "standalone_rich_card"),
        @JsonSubTypes.Type(value = FileMessage.class, name = "file"),
        @JsonSubTypes.Type(value = CarouselRichCardMessage.class, name = "carousel_rich_card")
})
public abstract class Message {
    private MessageType type;

    public Message() {
        type = null;
    }

    public Message(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return this.type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public abstract void generateFallback();

    @Override
    public String toString() {
//        try {
//            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        } catch (IOException c) {
//            c.printStackTrace();
//        }
//        return null;
        return UtilsToString.convertString(this);
    }
}
