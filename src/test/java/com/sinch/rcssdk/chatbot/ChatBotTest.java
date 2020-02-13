package com.sinch.rcssdk.chatbot;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinch.rcssdk.rcs.chatflow.ChatBot;
import com.sinch.rcssdk.rcs.chatflow.RCSConfigureType;
import com.sinch.rcssdk.rcs.exceptions.MissingRichCardContentsException;
import com.sinch.rcssdk.rcs.message.component.action.DialPhoneNumber;
import com.sinch.rcssdk.rcs.message.component.agentevent.AgentEventSup;
import com.sinch.rcssdk.rcs.message.component.agentevent.AgentReadEvent;
import com.sinch.rcssdk.rcs.message.component.postback.PostBack;
import com.sinch.rcssdk.rcs.message.component.richcard.FileInfo;
import com.sinch.rcssdk.rcs.message.component.richcard.RichCardContent;
import com.sinch.rcssdk.rcs.message.component.richcard.RichCardMedia;
import com.sinch.rcssdk.rcs.message.component.suggestions.SuggestedAction;
import com.sinch.rcssdk.rcs.message.component.suggestions.SuggestedReply;
import com.sinch.rcssdk.rcs.message.component.suggestions.Suggestion;
import com.sinch.rcssdk.rcs.message.enums.*;
import com.sinch.rcssdk.rcs.message.messagetype.AgentMessage;
import com.sinch.rcssdk.rcs.message.messagetype.FileMessage;
import com.sinch.rcssdk.rcs.message.messagetype.StandaloneRichCardMessage;
import com.sinch.rcssdk.rcs.message.messagetype.TextMessage;
import com.sinch.rcssdk.rcs.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ChatBotTest {

    private final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private ChatBot chatBot;

    @Before
    public void setUp() throws Exception {
        chatBot = new ChatBot(new AgentConfig(RCSConfigureType.api));
    }

    @Test(expected = MissingRichCardContentsException.class)
    public void testCarouselExceptionMissingWidth() throws Exception {
        chatBot.sendCarousel("+12332112");
        fail();
    }

    @Test(expected = Exception.class)
    public void testCarouselExceptionRichCardsInValid() throws Exception {
        chatBot.setWidthType(WidthType.MEDIUM);
        chatBot.sendCarousel("+12332112");
    }

    @Test
    public void testFileSizeLimit() {

        chatBot.setSupplier(AgentMessage.Supplier.MAAP_SAMSUNG);
        boolean isValid = chatBot.isFileSizeValidHelper(800000000, FileInfo.Mime_type.VIDEO_MP4);
        assertFalse(isValid);
        isValid = chatBot.isFileSizeValidHelper(1000, FileInfo.Mime_type.VIDEO_MP4);
        assertTrue(isValid);
        chatBot.setSupplier(AgentMessage.Supplier.GOOGLE);
        isValid = chatBot.isFileSizeValidHelper(1332321, FileInfo.Mime_type.IMAGE_JPEG);
        assertTrue(isValid);
        isValid = chatBot.isFileSizeValidHelper(1232133123, FileInfo.Mime_type.IMAGE_JPEG);
        assertFalse(isValid);
        isValid = chatBot.isFileSizeValidHelper(123213312, FileInfo.Mime_type.VIDEO_MP4);
        assertFalse(isValid);
        isValid = chatBot.isFileSizeValidHelper(1321, FileInfo.Mime_type.IMAGE_JPEG);
        assertTrue(isValid);
    }

    @Test
    public void testSendTextMessage() throws Exception {
        AgentMessage expectedMessage = getMappedEntityAndAssertGeneralMapping("user_agent_message_text.json", AgentMessage.class);
        AgentMessage agentMessage = chatBot.sendTextMessage("46555123456", "Madam Im Adam");
        assertEquals(expectedMessage.getTo(), agentMessage.getTo());
        assertEquals(expectedMessage.getMessage().getType(), agentMessage.getMessage().getType());
        TextMessage expectedTextMessage = getEntityAsSpecificSubClass(expectedMessage.getMessage(), TextMessage.class);
        TextMessage actualTextMessage = getEntityAsSpecificSubClass(agentMessage.getMessage(), TextMessage.class);
        assertEquals(expectedTextMessage.getText(), actualTextMessage.getText());
    }

    @Test(expected = RuntimeException.class)
    public void testSendImageMessageWithNullSourceImage() throws Exception {
        String image = "https://s3.amazonaws.com/sketchers-chatbot/random/sadasdad";
        AgentMessage agentMessage = chatBot.sendImage("+123213133", image);
    }

    @Test
    public void testSendImageMessage() throws Exception {
        AgentMessage expectedMessage = getMappedEntityAndAssertGeneralMapping("user_agent_message_send_file_message.json", AgentMessage.class);
        String image = "https://s3.amazonaws.com/sketchers-chatbot/Video/Picture1.png";
        AgentMessage actualMessage = chatBot.sendImage("46555123456", image);
        assertEquals(expectedMessage.getTo(), actualMessage.getTo());
        FileMessage expectedFileMessage = (FileMessage) expectedMessage.getMessage();
        FileMessage actualFileMessage = (FileMessage) actualMessage.getMessage();

        assertEquals(expectedFileMessage.getThumbnail().getFile_uri(), actualFileMessage.getThumbnail().getFile_uri());
        assertEquals(expectedFileMessage.getThumbnail().getFile_size(), actualFileMessage.getThumbnail().getFile_size());
        assertEquals(expectedFileMessage.getFile().getFile_uri(), actualFileMessage.getFile().getFile_uri());
        assertEquals(expectedFileMessage.getFile().getFile_size(), actualFileMessage.getFile().getFile_size());
    }

    @Test
    public void testSendTextMessageWithRevoke() throws Exception {
        AgentMessage expectedMessage = getMappedEntityAndAssertGeneralMapping("user_agent_message_text_revoke.json", AgentMessage.class);
        chatBot.setExpireInfo(3, true);
        AgentMessage actualMessage = chatBot.sendTextMessage("46555123456", "This is a time-sensitive message!");
        assertEquals(expectedMessage.getTo(), actualMessage.getTo());
        assertEquals(actualMessage.getExpire().getTimeout(), expectedMessage.getExpire().getTimeout());
        assertEquals(actualMessage.getExpire().isRevoke(), expectedMessage.getExpire().isRevoke());

        TextMessage expectedTextMessage = getEntityAsSpecificSubClass(expectedMessage.getMessage(), TextMessage.class);
        TextMessage actualTextMessage = getEntityAsSpecificSubClass(actualMessage.getMessage(), TextMessage.class);
        assertEquals(expectedTextMessage.getText(), actualTextMessage.getText());
    }


    @Test
    public void testSendTextMessageWithSuggestions() throws Exception {
        AgentMessage expectedMessage = getMappedEntityAndAssertGeneralMapping("user_agent_message_text_suggestions.json", AgentMessage.class);

        this.chatBot.addSuggestedReply(new Pair<>("Like", "feed1169-8500-4b66-a65c-5986b8ae59f7_LIKE"));
        this.chatBot.addSuggestedReply(new Pair<>("Stop please", "feed1169-8500-4b66-a65c-5986b8ae59f7_STOP"));
        SuggestedAction callUsAction = new SuggestedAction("Call us", new PostBack("feed1169-8500-4b66-a65c-5986b8ae59f7_CALL"));
        // Set the Dial Phone Number
        DialPhoneNumber dialPhoneNumber = new DialPhoneNumber("+46555123456");
        callUsAction.setAction(dialPhoneNumber);

        this.chatBot.addSuggestedAction(callUsAction);

        AgentMessage actualMessage = chatBot.sendTextMessage("46555123456", "Test message!");

        assertEquals(expectedMessage.getTo(), actualMessage.getTo());
        TextMessage expectedTextMessage = getEntityAsSpecificSubClass(expectedMessage.getMessage(), TextMessage.class);
        TextMessage actualTextMessage = getEntityAsSpecificSubClass(actualMessage.getMessage(), TextMessage.class);
        assertEquals(expectedTextMessage.getText(), actualTextMessage.getText());

        // Suggestions test
        List<Suggestion> expectedSuggestions = expectedMessage.getSuggestions();
        List<Suggestion> actualSuggestions = actualMessage.getSuggestions();
        assertEquals(expectedSuggestions.size(), actualSuggestions.size());

        int lenghSize = actualSuggestions.size();
        for (int i = 0; i < lenghSize; ++i) {
            if (expectedSuggestions.get(i).getType() == SuggestionType.reply) {
                SuggestedReply e1 = (SuggestedReply) expectedSuggestions.get(i);
                SuggestedReply a1 = (SuggestedReply) actualSuggestions.get(i);
                assertEquals(e1.getDisplay_text(), a1.getDisplay_text());
                assertEquals(e1.getPostback().getData(), a1.getPostback().getData());
            } else {
                SuggestedAction e1 = (SuggestedAction) expectedSuggestions.get(i);
                SuggestedAction a1 = (SuggestedAction) actualSuggestions.get(i);
                assertEquals(e1.getDisplay_text(), a1.getDisplay_text());
                assertEquals(e1.getPostback().getData(), a1.getPostback().getData());
                assertEquals(e1.getAction().getType(), a1.getAction().getType());
                DialPhoneNumber dialPhoneNumber1 = (DialPhoneNumber) e1.getAction();
                DialPhoneNumber dialPhoneNumber2 = (DialPhoneNumber) a1.getAction();
                assertEquals(dialPhoneNumber1.getPhone_number(), dialPhoneNumber2.getPhone_number());
            }
        }
    }


    @Test
    public void testStandAloneMessage() throws  Exception{
        AgentMessage expectedMessage = getMappedEntityAndAssertGeneralMapping("user_agent_message_standalone.json", AgentMessage.class) ;

        String video = "https://s3.amazonaws.com/sketchers-chatbot/Video/Mark+Nason+Dress+Knit+Commercial.mp4";
        String pig = "https://s3.amazonaws.com/sketchers-chatbot/Video/Picture1.png";

        // Setting the Rich Card Content object -
        RichCardContent richCardContent = new RichCardContent();
        // Video File
        FileInfo videoFile  = new FileInfo(FileInfo.Mime_type.VIDEO_MP4, "video.mp4" , video);
        // Thumbnail Image
        FileInfo videoImage = new FileInfo(FileInfo.Mime_type.IMAGE_PNG, "pig.png",pig);
        // Set the Media type for the Rich Card along with Height Type
        richCardContent.setMedia(new RichCardMedia(videoFile, videoImage, HeightType.TALL));

        richCardContent.setTitle("Hello1");
        richCardContent.setDescription("Hello There");
        // List of suggestions reply
        List<Suggestion> suggestions = new ArrayList<>();

        // Suggested Reply
        suggestions.add(new SuggestedReply("Like", new PostBack("feed1169-8500-4b66-a65c-5986b8ae59f7_LIKE"))) ;
        suggestions.add(new SuggestedReply("Stop please", new PostBack("feed1169-8500-4b66-a65c-5986b8ae59f7_STOP"))) ;
        SuggestedAction callUsAction = new SuggestedAction("Call us", new PostBack("feed1169-8500-4b66-a65c-5986b8ae59f7_CALL"));
        // Set the Dial Phone Number
        DialPhoneNumber dialPhoneNumber = new DialPhoneNumber("+46555123456");
        callUsAction.setAction(dialPhoneNumber);
        suggestions.add(callUsAction);
        richCardContent.setSuggestions(suggestions);

        AgentMessage actualMessage = chatBot.sendRichCard("46555123456", richCardContent, OrientationType.VERTICAL, ThumbnailAlignmentType.RIGHT);

        StandaloneRichCardMessage actualStandAlone = (StandaloneRichCardMessage)actualMessage.getMessage();
        StandaloneRichCardMessage expectedStandAlone = (StandaloneRichCardMessage)expectedMessage.getMessage();

        assertEquals(actualMessage.getTo(), expectedMessage.getTo());
        assertEquals(actualStandAlone.getContent().getDescription(), expectedStandAlone.getContent().getDescription());
        assertEquals(actualStandAlone.getContent().getTitle(), expectedStandAlone.getContent().getTitle());
        assertEquals(actualStandAlone.getOrientation(), expectedStandAlone.getOrientation());
        assertEquals(actualStandAlone.getContent().getTitle(), expectedStandAlone.getContent().getTitle());
        assertEquals(actualStandAlone.getThumbnail_alignment(), expectedStandAlone.getThumbnail_alignment());
        assertEquals(actualStandAlone.getContent().getMedia().getHeight(), expectedStandAlone.getContent().getMedia().getHeight());

        // Suggestions
        // Suggestions test
        List<Suggestion> expectedSuggestions = expectedMessage.getSuggestions();
        List<Suggestion> actualSuggestions = actualMessage.getSuggestions();
        assertEquals(expectedSuggestions.size(), actualSuggestions.size());

        int lenghSize = actualSuggestions.size();
        for (int i = 0; i < lenghSize; ++i) {
            if (expectedSuggestions.get(i).getType() == SuggestionType.reply) {
                SuggestedReply e1 = (SuggestedReply) expectedSuggestions.get(i);
                SuggestedReply a1 = (SuggestedReply) actualSuggestions.get(i);
                assertEquals(e1.getDisplay_text(), a1.getDisplay_text());
                assertEquals(e1.getPostback().getData(), a1.getPostback().getData());
            } else {
                SuggestedAction e1 = (SuggestedAction) expectedSuggestions.get(i);
                SuggestedAction a1 = (SuggestedAction) actualSuggestions.get(i);
                assertEquals(e1.getDisplay_text(), a1.getDisplay_text());
                assertEquals(e1.getPostback().getData(), a1.getPostback().getData());
                assertEquals(e1.getAction().getType(), a1.getAction().getType());
                DialPhoneNumber dialPhoneNumber1 = (DialPhoneNumber) e1.getAction();
                DialPhoneNumber dialPhoneNumber2 = (DialPhoneNumber) a1.getAction();
                assertEquals(dialPhoneNumber1.getPhone_number(), dialPhoneNumber2.getPhone_number());
            }
        }
    }


    @Test(expected = IOException.class)
    public void testSuggestionsChipSet() throws IOException {
        String str = "12321321321332132132132132132132132321321321312312321312313131313";
        List<Pair<String, String>> sug = new ArrayList<>();
        sug.add(new Pair<>(str, "something"));
        chatBot.setSuggestedReply(sug);
    }

    @Test
    public void testAgentComposing() throws Exception{
        AgentEventSup expectedEvent = getMappedEntityAndAssertGeneralMapping("agent_event_composing.json", AgentEventSup.class) ;
        AgentEventSup actualEvent = chatBot.agentComposing("46555123456");
        assertEquals(expectedEvent.getTo(), actualEvent.getTo());
        assertEquals(expectedEvent.getEvent().getType(), actualEvent.getEvent().getType());
    }

    @Test
    public void testAgentRead() throws Exception{
        AgentEventSup expectedEvent = getMappedEntityAndAssertGeneralMapping("agent_event_read.json", AgentEventSup.class) ;
        AgentEventSup actualEvent = chatBot.agentReadEvent("Jsiuh76sJKAhdsiufg86823","46555123456");
        assertEquals(expectedEvent.getTo(), actualEvent.getTo());
        assertEquals(expectedEvent.getEvent().getType(), actualEvent.getEvent().getType());
        AgentReadEvent expectRead = (AgentReadEvent)actualEvent.getEvent();
        AgentReadEvent actualRead = (AgentReadEvent)expectedEvent.getEvent();
        assertEquals(expectRead.getMessage_id(), actualRead.getMessage_id());
    }



    private <T> T getMappedEntityAndAssertGeneralMapping(String fileName, Class<T> clazz) throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        JsonNode jsonTree = mapper.readTree(url);
        T obj = mapper.treeToValue(jsonTree, clazz);
        return obj;
    }

    private <T extends Object> T getEntityAsSpecificSubClass(Object generalObject, Class<T> subClass) {
        assertTrue(
                String.format("testing %s is assignable from %s", subClass.getCanonicalName(), generalObject.getClass().getCanonicalName()),
                subClass.isAssignableFrom(generalObject.getClass())
        );
        @SuppressWarnings("unchecked")
        T res = (T) generalObject;
        return res;
    }


}
