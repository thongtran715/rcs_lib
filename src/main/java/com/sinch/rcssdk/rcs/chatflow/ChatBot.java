package com.sinch.rcssdk.rcs.chatflow;

import com.sinch.rcssdk.rcs.exceptions.CarouselsSizeException;
import com.sinch.rcssdk.rcs.exceptions.FileSizeExceedLimitException;
import com.sinch.rcssdk.rcs.exceptions.MissingRichCardContentsException;
import com.sinch.rcssdk.rcs.exceptions.MissingWidthTypeException;
import com.sinch.rcssdk.rcs.message.component.action.*;
import com.sinch.rcssdk.rcs.message.component.agentevent.AgentComposingEvent;
import com.sinch.rcssdk.rcs.message.component.agentevent.AgentEventSup;
import com.sinch.rcssdk.rcs.message.component.agentevent.AgentReadEvent;
import com.sinch.rcssdk.rcs.message.component.postback.PostBack;
import com.sinch.rcssdk.rcs.message.component.richcard.ExpireInfo;
import com.sinch.rcssdk.rcs.message.component.richcard.FileInfo;
import com.sinch.rcssdk.rcs.message.component.richcard.RichCardContent;
import com.sinch.rcssdk.rcs.message.component.richcard.RichCardMedia;
import com.sinch.rcssdk.rcs.message.component.suggestions.SuggestedAction;
import com.sinch.rcssdk.rcs.message.component.suggestions.SuggestedReply;
import com.sinch.rcssdk.rcs.message.component.suggestions.Suggestion;
import com.sinch.rcssdk.rcs.message.enums.HeightType;
import com.sinch.rcssdk.rcs.message.enums.OrientationType;
import com.sinch.rcssdk.rcs.message.enums.ThumbnailAlignmentType;
import com.sinch.rcssdk.rcs.message.enums.WidthType;
import com.sinch.rcssdk.rcs.message.messagetype.*;
import com.sinch.rcssdk.rcs.util.Pair;
import com.sinch.rcssdk.rcs.util.Util;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ChatBot {

    private TextMessage textMessage;

    private CarouselRichCardMessage carouselRichCardMessage;

    private StandaloneRichCardMessage standaloneRichCardMessage;

    private FileMessage fileMessage;

    private AgentMessage agentMessage;

    private AgentConfiguration agentConfiguration;

    private List<Suggestion> suggestions;

    private AgentMessage.Supplier supplier;

    private List<RichCardContent> richCardContents;

    private WidthType widthType;

    private ExpireInfo expireInfo;

    public ChatBot(AgentConfiguration agentConfiguration) {
        textMessage = new TextMessage();
        carouselRichCardMessage = new CarouselRichCardMessage();
        standaloneRichCardMessage = new StandaloneRichCardMessage();
        agentMessage = new AgentMessage();
        this.agentConfiguration = agentConfiguration;
        fileMessage = new FileMessage();
        suggestions = new ArrayList<>();
    }

    /**
     * Make http request to Rcs API Gateway
     * @param agentMessage AgentMessage object
     */

    private HttpResponse sendPayLoad(AgentMessage agentMessage) {
        agentMessage.setMessage_id(UUID.randomUUID().toString());
        HttpResponse httpResponse = agentConfiguration.post(agentMessage.toString());
        System.out.println(agentMessage.toString());
        return httpResponse;
    }

    /**
     * Setting the Agent Message Object
     * @param phone       MSIDN number
     * @param message     Message object
     * @param suggestions List of suggestions that can go with particular message. Including Suggested Reply and Actions
     */
    private void setAgentMessage(String phone, Message message, List<Suggestion> suggestions) {
        agentMessage.setTo(phone);
        agentMessage.setMessage(message);
        agentMessage.setSuggestions(suggestions);
        agentMessage.setExpire(expireInfo);
    }


    /**
     * @param phone       MSIDN Number
     * @param message     Message Object
     * @param suggestions List of suggestions that can go with particular message. Including Suggested Reply and Actions
     * @param supplier    OPTIONAL - User defines which RCS Platform to go to
     */
    private void setAgentMessage(String phone, Message message, List<Suggestion> suggestions, AgentMessage.Supplier supplier) {
        this.setAgentMessage(phone, message, suggestions);
        this.agentMessage.setSupplier(supplier);
    }

    /**
     * @param suggestions List of suggestions that can go with particular message.  Suggested Reply in this case
     * @param actions     List of suggestions that can go with particular message. Suggested Actions
     * @return list of suggestions
     */
    public List<Suggestion> setSuggestions(List<Pair<String, String>> suggestions, List<SuggestedAction> actions) throws IOException {
        if (suggestions != null) {
            if (suggestions.size() > 11) {
                throw new IOException("Exceed the size of suggestion chip sets. It has to be 11 units");
            }

            for (Pair<String, String> pair : suggestions) {
                int title = pair.getKey().length();
                if (title > 25) {
                    throw new IOException("Display text exceed maximum length. It has to be 25 characters");
                }
                this.suggestions.add(new SuggestedReply(pair.getKey(), new PostBack(pair.getValue())));
            }

        }
        if (actions != null) {

            for (SuggestedAction action : actions) {
                if (action.getDisplay_text().length() > 25) {
                    throw new IOException("Display text exceed maximum length. It has to be 25 characters");
                }
                checkSuggestedActionsValid(action);
                this.suggestions.add(action);
            }

        }
        return this.suggestions;
    }

    /**
     * This function will take a single suggested reply and call the set suggestions to add up to the list
     *
     * @param suggestedReply a suggested reply
     * @return List of suggested replies
     * @throws IOException
     */
    public List<Suggestion>  addSuggestedReply(Pair<String, String> suggestedReply) throws IOException{
        return this.setSuggestions(Arrays.asList(suggestedReply), null);
    }

    /**
     *  Clear the suggesiton chip sets.
     */
    public void clearSuggestionsChip() {
        if (this.suggestions != null)
            this.suggestions.clear();
    }

    /**
     *
     * @param suggestedAction
     * @return
     * @throws IOException
     */
    public List<Suggestion> addSuggestedAction(SuggestedAction suggestedAction) throws IOException{
        return this.setSuggestions(null, Arrays.asList(suggestedAction));
    }

    /**
     * @param sugg Suggested Reply actions
     * @return set of suggestions
     */
    public List<Suggestion> setSuggestedReply(List<Pair<String, String>> sugg) throws IOException {
        return this.setSuggestions(sugg, null);
    }

    /**
     * @param actions Suggested Actions
     * @return set of suggestions
     */
    public List<Suggestion> setSuggestedActions(List<SuggestedAction> actions) throws IOException {
        return this.setSuggestions(null, actions);
    }


    /**
     * Default Width Type will be MEDIUM
     *
     * @param phoneNumber      MSIDN Number
     * @param richCardContents List of rich cards that can go with the carousel model
     * @return set of rich card contents
     */
    public List<RichCardContent> sendCarousel(String phoneNumber, List<RichCardContent> richCardContents) throws CarouselsSizeException {
        int size = richCardContents.size();
        if (size < 2 || size > 10) {
            throw new CarouselsSizeException(size);
        }
        carouselRichCardMessage.setContents(richCardContents);
        carouselRichCardMessage.setWidth(WidthType.MEDIUM);
        setAgentMessage(phoneNumber, carouselRichCardMessage, this.suggestions, this.supplier);
        sendPayLoad(agentMessage);
        return richCardContents;
    }

    /**
     * Custom Width Type settings
     *
     * @param phoneNumber      MSIDN Number
     * @param richCardContents List of rich cards that can go with the carousel model
     * @param widthType        With Type for each of the Rich Card. Including SMALL or MEDIUM
     * @return set of rich card contents
     */
    public List<RichCardContent> sendCarousel(String phoneNumber, List<RichCardContent> richCardContents, WidthType widthType) {
        carouselRichCardMessage.setContents(richCardContents);
        carouselRichCardMessage.setWidth(widthType);
        setAgentMessage(phoneNumber, carouselRichCardMessage, this.suggestions, this.supplier);
        sendPayLoad(agentMessage);
        return richCardContents;
    }

    /**
     * @param phoneNumber phone number to send to
     * @return set of rich card contents
     * @throws MissingRichCardContentsException It will throw exception if this can't find the objects of rich card
     * @throws MissingWidthTypeException        It will throw exception if this can't find the Width Type identity
     */
    public List<RichCardContent> sendCarousel(String phoneNumber) throws Exception {
        if (this.richCardContents == null) {
            throw new MissingRichCardContentsException();
        }

        if (this.widthType == null) {
            throw new MissingWidthTypeException();
        }

        if (!isRichCardsValid(richCardContents)) {
            throw new Exception("At least one of the rich card in cards is invalid");
        }

        this.carouselRichCardMessage.setContents(this.richCardContents);
        this.carouselRichCardMessage.setWidth(this.widthType);
        this.setAgentMessage(phoneNumber, carouselRichCardMessage, this.suggestions, this.supplier);
        sendPayLoad(agentMessage);
        return this.richCardContents;
    }

    /**
     * @param phoneNumber     MSIDN Number
     * @param richCardContent Rich Card Content Object
     * @param orientationType The Orientation Type of the Rich Card including Horizontal and Vertical
     * @return rich card content object
     */
    public AgentMessage sendRichCard(String phoneNumber, RichCardContent richCardContent, OrientationType orientationType, ThumbnailAlignmentType thumbnailAlignmentType) throws Exception {
        if (!isValidRichCardContent(richCardContent)) {
            throw new Exception("Rich Card invalid");
        }
        standaloneRichCardMessage.setContent(richCardContent);
        standaloneRichCardMessage.setOrientation(orientationType);
        standaloneRichCardMessage.setThumbnail_alignment(thumbnailAlignmentType);
        setAgentMessage(phoneNumber, standaloneRichCardMessage, this.suggestions, this.supplier);
        sendPayLoad(agentMessage);
        return this.agentMessage;
    }


    /**
     * @param phoneNumber MSIDN Number
     * @param text        Plain Text Message
     * @return text message object
     */
    public AgentMessage sendTextMessage(String phoneNumber, String text) {
        this.textMessage.setText(text);
        setAgentMessage(phoneNumber, this.textMessage, this.suggestions, this.supplier);
        sendPayLoad(agentMessage);
        return this.agentMessage;
    }

    /**
     * @param imageUrl    Image URL that ends with PNG, JPEG, ...
     * @param phoneNumber MSIDN Number
     * @return File message object
     */
    public AgentMessage sendImage(String phoneNumber, String imageUrl) throws IOException, FileSizeExceedLimitException {

        // Check if the size is valid.
        long size = Util.getFileSize(imageUrl);
        if (!isFileSizeValidHelper(size, FileInfo.Mime_type.IMAGE_JPEG)) {
            throw new FileSizeExceedLimitException(supplier, size);
        }

        FileInfo fileInfo = new FileInfo(FileInfo.Mime_type.IMAGE_PNG, size, "picture.png", imageUrl);
        this.fileMessage.setThumbnail(fileInfo);
        this.fileMessage.setFile(fileInfo);
        setAgentMessage(phoneNumber, this.fileMessage, this.suggestions, this.supplier);
        sendPayLoad(agentMessage);
        return this.agentMessage;
    }

    /**
     * Send video using Rich Card with file only. There is no title, description and suggestions. Only the file name
     * @param phoneNumber MSIDN to send
     * @param urlVideo Video url file
     * @param videoName the name of the video
     * @param videoThumbnail Thumbnail for the video
     * @param thumbnailName Thumbnail image name
     * @return Rich Card Content
     * @throws IOException
     * @throws FileSizeExceedLimitException
     */
    public RichCardContent sendVideo(String phoneNumber, String urlVideo, String videoName, String videoThumbnail, String thumbnailName) throws IOException, FileSizeExceedLimitException {
        // Check if the size is valid.
        long thumbnailSize = Util.getFileSize(videoThumbnail);
        if (!isFileSizeValidHelper(thumbnailSize, FileInfo.Mime_type.IMAGE_JPEG)) {
            throw new FileSizeExceedLimitException(supplier, thumbnailSize);
        }
        long videoSize = Util.getFileSize(urlVideo);
        if (!isFileSizeValidHelper(videoSize, FileInfo.Mime_type.VIDEO_MP4)) {
            throw new FileSizeExceedLimitException(supplier, videoSize);
        }

        RichCardContent richCardContent = new RichCardContent();
        RichCardMedia media = new RichCardMedia();
        media.setHeight(HeightType.MEDIUM);
        media.setFile(new FileInfo(FileInfo.Mime_type.VIDEO_MP4, videoSize, videoName, urlVideo));
        media.setThumbnail(new FileInfo(FileInfo.Mime_type.IMAGE_PNG, thumbnailSize, thumbnailName, videoThumbnail));
        richCardContent.setMedia(media);
        standaloneRichCardMessage.setContent(richCardContent);
        standaloneRichCardMessage.setOrientation(OrientationType.VERTICAL);
        standaloneRichCardMessage.setThumbnail_alignment(ThumbnailAlignmentType.LEFT);
        setAgentMessage(phoneNumber, this.standaloneRichCardMessage, null, this.supplier);
        sendPayLoad(agentMessage);
        return richCardContent;
    }

    /**
     * This function will make the status of the message to be read
     *
     * @param uuid message ID
     * @param from MSIDN number
     */
    public AgentEventSup agentReadEvent(String uuid, String from) {
        AgentReadEvent agentReadEvent = new AgentReadEvent();
        AgentEventSup agentEventSup = new AgentEventSup();
        this.agentConfiguration.setType(RCSConfigureType.event);
        agentReadEvent.setMessage_id(uuid);
        agentEventSup.setEvent_id(UUID.randomUUID().toString());
        agentEventSup.setTo(from);
        agentEventSup.setEvent(agentReadEvent);
        String payloadRead = agentEventSup.toString();
        this.agentConfiguration.post(payloadRead);
        return agentEventSup;
    }

    /**
     * This will enable the user composing
     *
     * @param phoneNumber phone number to send the request to
     */
    public AgentEventSup agentComposing(String phoneNumber) {
        AgentEventSup agentEventSup = new AgentEventSup();
        this.agentConfiguration.setType(RCSConfigureType.event);
        agentEventSup.setEvent(new AgentComposingEvent());
        agentEventSup.setEvent_id(UUID.randomUUID().toString());
        agentEventSup.setTo(phoneNumber);
        this.agentConfiguration.post(agentEventSup.toString());
        return agentEventSup;
    }


    /**
     * GETTER
     */
    public TextMessage getTextMessage() {
        return textMessage;
    }

    /**
     * SETTER
     */
    public void setTextMessage(TextMessage textMessage) {
        this.textMessage = textMessage;
    }

    /**
     * @param timeout number of time to revoke the message in hours
     * @param revoke  boolean is revoking
     */
    public void setExpireInfo(int timeout, boolean revoke) {
        if (expireInfo == null) {
            expireInfo = new ExpireInfo();
        }
        expireInfo.setRevoke(revoke);
        expireInfo.setTimeout(timeout);
    }

    public void setExpireInfo(ExpireInfo expireInfo) {
        this.expireInfo = expireInfo;
    }

    public CarouselRichCardMessage getCarouselRichCardMessage() {
        return carouselRichCardMessage;
    }

    public void setCarouselRichCardMessage(CarouselRichCardMessage carouselRichCardMessage) {
        this.carouselRichCardMessage = carouselRichCardMessage;
    }

    public StandaloneRichCardMessage getStandaloneRichCardMessage() {
        return standaloneRichCardMessage;
    }

    public void setStandaloneRichCardMessage(StandaloneRichCardMessage standaloneRichCardMessage) {
        this.standaloneRichCardMessage = standaloneRichCardMessage;
    }

    public FileMessage getFileMessage() {
        return fileMessage;
    }

    public void setFileMessage(FileMessage fileMessage) {
        this.fileMessage = fileMessage;
    }

    public AgentMessage getAgentMessage() {
        return agentMessage;
    }

    public void setAgentMessage(AgentMessage agentMessage) {
        this.agentMessage = agentMessage;
    }

    public AgentConfiguration getAgentConfiguration() {
        return agentConfiguration;
    }

    public void setAgentConfiguration(AgentConfiguration agentConfiguration) {
        this.agentConfiguration = agentConfiguration;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public AgentMessage.Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(AgentMessage.Supplier supplier) {
        this.supplier = supplier;
    }

    public List<RichCardContent> getRichCardContents() {
        return richCardContents;
    }

    public void setRichCardContents(List<RichCardContent> richCardContents) {

        this.richCardContents = richCardContents;
    }

    public WidthType getWidthType() {
        return widthType;
    }

    public void setWidthType(WidthType widthType) {
        this.widthType = widthType;
    }

    /**
     * @param richCardContents set of rich card contents to be validated
     * @return boolean
     */
    private boolean isRichCardsValid(List<RichCardContent> richCardContents) {
        int size = richCardContents.size();
        if (size < 2 || size > 10) {
            return false;
        }
        for (RichCardContent richCardContent : richCardContents) {
            try {
                if (!isValidRichCardContent(richCardContent)) {
                    return false;
                }
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                return false;
            }
        }
        return true;
    }

    /**
     * @param richCardContent a rich card content to be validated
     * @return boolean
     */
    public boolean isRichCardTitleAndDescriptionValid(RichCardContent richCardContent) {
        String title = richCardContent.getTitle();
        String description = richCardContent.getDescription();
        return title.length() < 200 && description.length() < 2000;
    }

    /**
     * @param size   Long type determines how big the size is
     * @param m_type Type of File size ( Video or Image)
     * @return boolean
     */
    public boolean isFileSizeValidHelper(long size, FileInfo.Mime_type m_type) {
        if (supplier == null) {
            return !(size > 1.6e+7);
        }
        switch (supplier) {
            case MAAP_SAMSUNG:
                if (size > 8e+8) {
                    return false;
                }
            case GOOGLE:
                if (m_type == FileInfo.Mime_type.IMAGE_JPEG || m_type == FileInfo.Mime_type.IMAGE_PNG) {
                    if (size > 1.6e+7) {
                        return false;
                    }
                } else if (m_type == FileInfo.Mime_type.VIDEO_MP4) {
                    if (size > 8e+7) {
                        return false;
                    }
                }
            default:
                return !(size > 1.6e+7);
        }
    }

    /**
     * @param richCardContent a rich card content to be validated
     * @return boolean
     * @throws IOException throws the IO Exception if the file is not able to reach the source host
     */
    public boolean isValidRichCardContent(RichCardContent richCardContent) throws IOException {
        long size_1 = Util.getFileSize(richCardContent.getMedia().getFile().getFile_uri());
        long size_2 = Util.getFileSize(richCardContent.getMedia().getThumbnail().getFile_uri());
        return isFileSizeValidHelper(size_1, richCardContent.getMedia().getFile().getMime()) && isRichCardTitleAndDescriptionValid(richCardContent) && isFileSizeValidHelper(size_2, richCardContent.getMedia().getThumbnail().getMime());
    }

    /**
     * This function to check if all of the Suggested Actions are valid
     *
     * @param suggestedAction a suggested Action type
     * @throws IOException throws the IO Exception if the type of input is incorrect
     */
    private void checkSuggestedActionsValid(SuggestedAction suggestedAction) throws IOException {
        Action actionType = suggestedAction.getAction();
        if (actionType == null) {
            throw new IOException("Null Pointer Exception for Action Type. Please set action type to SuggestedAction object");
        }
        switch (actionType.getType()) {
            case open_url:
                OpenUrl openUrl = (OpenUrl) suggestedAction.getAction();
                if (openUrl.getUrl() == null) {
                    throw new IOException("URL must not be null");
                }
                break;
            case show_location:
                ShowLocation showLocation = (ShowLocation) suggestedAction.getAction();
                if (showLocation.getLatitude() < -90f || showLocation.getLatitude() > 90f || showLocation.getLongitude() < -180f || showLocation.getLongitude() > 180f) {
                    throw new IOException("Latitude must be from -90 to 90, and Longitude must be from -180 to 180");
                }
                if (showLocation.getLabel() != null && showLocation.getLabel().length() > 1000) {
                    throw new IOException("Label of show location action must not exceed 1000 characters");
                }
                break;
            case dial_phone_number:
                DialPhoneNumber dialPhoneNumber = (DialPhoneNumber) suggestedAction.getAction();
                if (dialPhoneNumber.getPhone_number() == null) {
                    throw new IOException("Phone number must not be null");
                }
                break;
            case create_calendar_event:
                CreateCalendarEvent calendarEvent = (CreateCalendarEvent) suggestedAction.getAction();
                if (calendarEvent.getTitle().isEmpty() || calendarEvent.getTitle().length() > 1024 || calendarEvent.getTitle().length() < 1) {
                    throw new IOException("Calendar Title is required. The length is from 1 to 1024 characters");
                }
                if (calendarEvent.getDescription().isEmpty() || calendarEvent.getDescription().length()> 1024 || calendarEvent.getDescription().length()< 1){
                    throw new IOException("Calendar description is required. The length is from 1 to 1024 characters");
                }
                break;

            default:
                break;
        }
    }


    private List<Suggestion> cloneSuggestions(){
        List<Suggestion> newSugg = new ArrayList<>();
        for (Suggestion suggestion : this.suggestions) {
            newSugg.add(suggestion);
        }
        return newSugg;
    }


    public static void main(String[] args) {
        int x = 4;
        int y = x;
        x = 3;
        System.out.println(y);
    }
}
