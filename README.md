# java-SinchRCS-Library

# Motivation 

This Java Sinch RCS library is a tool where you can directly interact with Sinch RCS API. It is built on top of core RCS API and enables developers the ability to understand core features of rich messaging that normal SMS is not capable of. We hope with this library , you can find the easy ways to use our service. 

---

# Link
Please visit our RCS Page for further information [Sinch-RCS](https://www.sinch.com/docs/rcs/index.html)

---

# Usage
In order to use this library, you will first need to set up your RCS bot environment. You will need to obtain Agent ID and Token ID from Sinch, Inc, so please visit [Sinch](https://www.sinch.com/) and we can help you set up the bot. We will also ask for your [callback URL](https://www.sinch.com/docs/rcs/http-rest.html#callback-request) 
<br />
Otherwise, feel free to use our sample bot which is already integrating in the library. 

---
## Configuration 
You will need to tell our library which bot you are using including the Agent ID and Token ID. <br />
Let's create a configuration class which is called YourBotConfiguration and extends it from ChatAgentConfiguration
```javascript
public class YourBotConfiguration extends AgentConfiguration {

    public YourBotConfiguration(RCSConfigureType type) {
        super(type);
        this.TOKEN = "YOUR TOKEN";
        this.AGENT_ID = "YOUR ID";
    }
}
```
<br/>
Next step is to create an instance from ChatBot class. <br/>

```javascript
      ChatBot chatBot = new ChatBot(new YourBotConfiguration(RCSConfigureType.api));
      // Optional - Setting up your destinated supplier. 
      chatBot.setSupplier(AgentMessage.Supplier.MAAP_SAMSUNG);
```

That is it for the configuration. Now let's jump to how we can use this ChatBot instance to send RCS features message. 
# Core Features 
- [ Text Message ](#textMessage)
- [ Image Message ](#imageMessage)
- [ Video Message ](#videoMessage)
- [ Standalone Rich Card Message](#standaloneMessage)
- [ Carousels Message](#carouselsMessage)
- [ Suggestion Chip Sets](#suggestionChipSets)
- [ Agent Events](#agentEvents)
- [ Revoke Messages](#revokemessage)
- [SMS FallBack](#fallbackSMS)
---

<a name="textMessage"></a>
## Text Message

Sending text message is simple. You will just need to have a phone number and the text you want to send 

```javascript
  chatBot.sendTextMessage("+14047691562", "Hello from Sinch");
```

![Alt Text](https://media.giphy.com/media/d9ZkSp2F6fwf8CWm05/giphy.gif)



<a name="imageMessage"></a>
## Image Message

Sending image message requires an URI to host the file and it has mime type of png or jpeg

```javascript
        try {
            chatBot.sendImage( "+14047691562", "https://s3.amazonaws.com/sketchers-chatbot/Revision_1/Kid/Kids+Boys'+Sport/Boys'+Kinectors+Thermovolt.jpg");
        }catch (FileSizeExceedLimitException e){
            // File size limit the recommendation
            System.out.println(e.getMessage());
        }catch (IOException e){
            // Unable to reach the url host
            System.out.println(e.getMessage());
        }
```

![Alt Text](https://media.giphy.com/media/QtkGNdfLXs6z7kHZ0A/giphy.gif)

<a name="videoMessage"></a>
## Video Message

```javascript

        String MEN_VIDEO_SELECTED = "https://s3.amazonaws.com/sketchers-chatbot/Video/Skechers+RCS+Chatbot+Video+Revisions+Assets+4.19.19/Mens+Tony+Romo+for+Skechers+Slip-ons.mp4";
        String MEN_THUMBNAIL_SELECTED = "https://s3.amazonaws.com/sketchers-chatbot/Video/Skechers+RCS+Chatbot+Video+Revisions+Assets+4.19.19/Video+Revisions+Thumbnails/man.png";
        chatBot.setSupplier(AgentMessage.Supplier.MAAP_SAMSUNG);
        try {
            chatBot.sendVideo("14047691562", MEN_VIDEO_SELECTED, "fadsa.mp4", MEN_THUMBNAIL_SELECTED, "image.png");
        }catch (FileSizeExceedLimitException e)
        {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        
```
<a name="standaloneMessage"></a>
## Standalone Message

Standalone Rich Card has the following properties

- File info 1: This file info indicates the type of the file that you wish to send out. 
- File info 2: This file info is the thumbnail for the Rich Card 
- Title: Title of the rich card. There are at most 200 characters 
- Description: Description of Rich Card. There are at most 2000 characters
- Suggestions Chip Sets: Optional - You can specify some buttons attach on the Rich Card
- Orientation Type: There are HORIZONTAL , and VERTICAL orientation type of the Rich Card. 
- Thumbnail Alignment: There are LEFT, and RIGHT alignment of the Rich Card for the HORIZONTAL Orientation
- Vertical Rich Card Height: There are SHORT, MEDIUM , TALL for the VERTICAL Orientation Type

Depend on the use cases, you can play around different type of Orientation and Thumbnail Alignment to fit your goal. However, the native software in the device will not scale the file properly for different type of orientation and thumbnail alignment. You should consider to design specific file for specific Rich Card. 

```javascript
            String video = "https://s3.amazonaws.com/sketchers-chatbot/Video/Mark+Nason+Dress+Knit+Commercial.mp4";
            String pig = "https://s3.amazonaws.com/sketchers-chatbot/Video/Picture1.png";
            
            // Setting the Rich Card Content object -
            RichCardContent richCardContent = new RichCardContent();
            // Video File  
            FileInfo videoFile  = new FileInfo(FileInfo.Mime_type.VIDEO_MP4, "video.mp4" , video);
            // Thumbnail Image
            FileInfo videoImage = new FileInfo(FileInfo.Mime_type.IMAGE_PNG, "pig.png",pig);
            // Set the Media type for the Rich Card along with Height Type
            richCardContent.setMedia(new RichCardMedia(videoFile, videoImage, HeightType.SHORT));
            
            richCardContent.setTitle("Sinch hello");
            richCardContent.setDescription("Hello From Sinch");

            try {
                chatBot.sendRichCard("+14047691562", richCardContent, OrientationType.VERTICAL, ThumbnailAlignmentType.LEFT);
            }catch (Exception e){
                // Catching the Exception here 
                System.out.print(e.getMessage()); 
            }
```

![Alt Text](https://media.giphy.com/media/IgXqPKkqvVdUvmiClu/giphy.gif)

We can have at most 4 suggestion chip sets in rich card. Let's see how it is done

```javascript
            String video = "https://s3.amazonaws.com/sketchers-chatbot/Video/Mark+Nason+Dress+Knit+Commercial.mp4";
            String pig = "https://s3.amazonaws.com/sketchers-chatbot/Video/Picture1.png";


            RichCardContent richCardContent = new RichCardContent();
            FileInfo videoFile  = new FileInfo(FileInfo.Mime_type.VIDEO_MP4, "video.mp4" , video);
            FileInfo videoImage = new FileInfo(FileInfo.Mime_type.IMAGE_PNG, "pig.png",pig);
            richCardContent.setMedia(new RichCardMedia(videoFile, videoImage, HeightType.SHORT));
            richCardContent.setTitle("Sinch hello");
            richCardContent.setDescription("Hello From Sinch");

            // List of suggestions reply
            List<Suggestion> suggestions = new ArrayList<>();

            // Suggested Reply
            suggestions.add(new SuggestedReply("Hello 1", new PostBack("asdasdadasda1221313"))) ;
            suggestions.add(new SuggestedReply("Hello 4", new PostBack("asdasdadasda1221313"))) ;
        
            // Setting the suggestions in the rich card
            richCardContent.setSuggestions(suggestions); 
            
            // Suggested Actions
            SuggestedAction callUsAction = new SuggestedAction("Call Us Now", null);
            // Set the Dial Phone Number
            DialPhoneNumber dialPhoneNumber = new DialPhoneNumber("+14047691562");
            callUsAction.setAction(dialPhoneNumber);

            richCardContent.addSuggestion(callUsAction);
            
            try {
                chatBot.sendRichCard("+14042329644", richCardContent, OrientationType.VERTICAL, ThumbnailAlignmentType.LEFT);
            }catch (Exception e){
                System.out.println();
            }
     
```

![Alt Text](https://media.giphy.com/media/YT8NO6g0egXTKd2hNr/giphy.gif)

<a name="carouselsMessage"></a>
## Carousel Message
    
Carousel Message is the list of standalone rich cards. Notice that Carousel has two main properties as follow
- Width: The Width of the cards in the carousel 
- Rich Card Contents: The list of the standalone rich card. There must be at least 2 and at most 10 rich cards in the carousel.  

Also be notice that, in order to maintain Rendering Optimization, we would need you as a developer respect to limited resources natively in the device. 
In another words, if you have really long title and description inside one of the rich cards, you should place that card to the first of carousel. 

```javascript

            String video = "https://s3.amazonaws.com/sketchers-chatbot/Video/Mark+Nason+Dress+Knit+Commercial.mp4";
            String pig = "https://s3.amazonaws.com/sketchers-chatbot/Video/Picture1.png";


            RichCardContent richCardContent = new RichCardContent();
            FileInfo videoFile  = new FileInfo(FileInfo.Mime_type.VIDEO_MP4, "video.mp4" , video);
            FileInfo videoImage = new FileInfo(FileInfo.Mime_type.IMAGE_PNG, "pig.png",pig);
            richCardContent.setMedia(new RichCardMedia(videoFile, videoImage, HeightType.SHORT));
            richCardContent.setTitle("Sinch hello");
            richCardContent.setDescription("Hello From Sinch");

            // List of suggestions reply
            List<Suggestion> suggestions = new ArrayList<>();

            // Suggested Reply
            suggestions.add(new SuggestedReply("Hello 1", new PostBack("asdasdadasda1221313"))) ;
            suggestions.add(new SuggestedReply("Hello 4", new PostBack("asdasdadasda1221313"))) ;

            richCardContent.setSuggestions(suggestions);

            List<RichCardContent> richCardContents = new ArrayList<>();
            richCardContents.add(richCardContent);
            richCardContents.add(richCardContent);
            richCardContents.add(richCardContent);
            richCardContents.add(richCardContent);
            richCardContents.add(richCardContent);

            chatBot.setRichCardContents(richCardContents);
            chatBot.setWidthType(WidthType.MEDIUM);
            try {
                chatBot.sendCarousel("+14042329644");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
```

![Alt Text](https://media.giphy.com/media/ThpWyUbFQf6GwlCMf9/giphy.gif)


<a name="suggestionChipSets"></a>
## Suggestion Chip Sets
Suggestion chip sets enable the ability for end-user interact with the chat bot, and you can have up to 11 units chip attach to your message. Message must not be null.
<br/>
Suggestion chip sets contain display text and post-back data <br/>
- Display Text: Text that is shown in the suggested reply and sent back to the agent when the user taps it. Maximum 25 characters.
- Post-back data: Optional data that will be sent back to the agent when the user taps the reply. Usually, the post-back data will be sent to the callback url of the Agent.
                
### Suggested Reply 
Suggested Reply is used in case where the bot is trying to ask for the custom response from the customer. 
<br/>
Suggested Reply has the type of reply, display text, and post-back data. <br/>
In this library, we use Pair<String, String> with the key is display text and value is post_back data.

```javascript
            ChatBot chatBot = new ChatBot(new YourBotConfiguration(RCSConfigureType.api));
            chatBot.setSupplier(AgentMessage.Supplier.MAAP_SAMSUNG);
            
            // Making a list of suggested reply 
            List<Pair<String, String>> suggestedReply = new ArrayList<>();
            
            // Declaring a key as display text, value as post-back data
            suggestedReply.add(new Pair<>("Hi Sich", "jgkaioejn21kl222131223kakdk223123213"));
            
            try{
                chatBot.setSuggestedReply(suggestedReply);
                chatBot.sendTextMessage("+14047691562", "Hello from Sinch");
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
            
                   
            // Clear all the suggestions if you happen do not need it anymore for further usage
            chatBot.clearSuggestionsChip();
```
IO Exception will be thrown if the number of chip sets exceed 11 units or the length of display text exceed 25 characters. 

![Alt Text](https://media.giphy.com/media/VJNxCksYQmtUBRqox6/giphy.gif)


### Suggested Actions

Suggested Actions has the type of action, display text , post back and also it enables one of the following feature in the native device:

- [DialPhoneNumber](#dialPhoneNumber)
- [ShowLocation](#showLocation)
- [RequestLocationPush](#requestLocationPush)
- [OpenURL](#openURL)
- [CreateCalendarEvent](#createCalendarEvent)

<a name="dialPhoneNumber"></a>
#### Dial Phone Number 
- Phone number: Regex pattern ^(?:00|+|)[1-9][0-9]{8,16}$,  E.164 format 
```javascript
            // List of actions
            List<SuggestedAction>  actions = new ArrayList<>();
            
            // Set display text as well as post-back data. 
            // Depend on use cases, but null post-back would make a better sense when we are trying to call
            SuggestedAction callUsAction = new SuggestedAction("Call Us Now", null);
            
            // Set the Dial Phone Number
            DialPhoneNumber dialPhoneNumber = new DialPhoneNumber("+14047691562");
            callUsAction.setAction(dialPhoneNumber);
            
            // Add the dial phone action to the list 
            actions.add(callUsAction);
            
            try {
                chatBot.setSuggestedActions(actions);
                chatBot.sendTextMessage("+14047691562", "Please call Sinch");
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
            
            // Clear all the suggestions if you happen do not need it anymore for further usage
            chatBot.clearSuggestionsChip();
```

![Alt Text](https://media.giphy.com/media/kBxHRWVUQ8H1tUZVcO/giphy.gif)

<a name="showLocation"></a>
#### Show Location
    
Show location instance contains the following attributes <br />
- Latitude : It ranges between [-90, 90]
- Longitude : It ranges between [-180, 180]
- Label : Optional label to be shown on the map at the given lat/long. Max length is 1000 characters

```javascript
            // List of actions
            List<SuggestedAction>  actions = new ArrayList<>();

            // Set display text as well as post-back data.
            // Depend on use cases, but null post-back would make a better sense when we are trying to visit the location
            SuggestedAction showLocationAction = new SuggestedAction("Visit Sinch", null);

            // Set up the URL instance
            ShowLocation showLocation = new ShowLocation(45.4f, 88.9f, "Sinch's HeadQuarter");
            showLocationAction.setAction(showLocation);

            // Add the action to the list.
            actions.add(showLocationAction);

            try{
                chatBot.setSuggestedActions(actions);
                chatBot.sendTextMessage("+14047691562", "Do you want to see what Sinch look like?");
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
            
            // Clear all the suggestions if you happen do not need it anymore for further usage
            chatBot.clearSuggestionsChip();
```

![Alt Text](https://media.giphy.com/media/eKPPa5P5OpB41pWkfJ/giphy.gif)

<a name="requestLocationPush"></a>
#### Request Location Push

With Request location push, we are asking users to share their location. Once, they share the location, you can see their latitude and longitude in the callback url in which Sinch has forwarded it.

```javascript
            // List of actions
            List<SuggestedAction>  actions = new ArrayList<>();

            // Set display text as well as post-back data.
            SuggestedAction requestLocationPushAction = new SuggestedAction("Show my location", null);

            RequestLocationPush requestLocationPush = new RequestLocationPush();
            requestLocationPushAction.setAction(requestLocationPush);

            actions.add(requestLocationPushAction);

            try{
                chatBot.setSuggestedActions(actions);
                chatBot.sendTextMessage("+14047691562", "Do you want to share your location?");
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
```

![Alt Text](https://media.giphy.com/media/hRyRSYmvZJDVC2ZeVk/giphy.gif)


<a name="openURL"></a>
#### Open URL

```javascript
            // List of actions
            List<SuggestedAction>  actions = new ArrayList<>();

            // Set display text as well as post-back data.
            // Depend on use cases, but null post-back would make a better sense when we are trying to visit the URL 
            SuggestedAction openUrlAction = new SuggestedAction("Visit Sinch", null);
            
            // Set up the URL instance
            OpenUrl openUrl = new OpenUrl("https://www.sinch.com/docs/rcs/http-rest.html#openurl");
            openUrlAction.setAction(openUrl);
            
            // Add the action to the list. 
            actions.add(openUrlAction);

            try{
                chatBot.setSuggestedActions(actions);
                chatBot.sendTextMessage("+14047691562", "Do you want to see what Sinch look like?");
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
```
![Alt Text](https://media.giphy.com/media/QX29XzKC1QXVMq3TRh/giphy.gif)

<a name="createCalendarEvent"></a>
#### Create Calendar Event 
Create calendar event consists of 
- Start_time and End_time: A timestamp in RFC3339 UTC “Zulu” format. Example: "2014-10-02T15:01:23.045123456Z"
- Title: Event title. It ranges from 1 to 1024 characters
- Description: Event description. Tt ranges from 1 to 1024 characters

```javascript
            // List of actions
            List<SuggestedAction>  actions = new ArrayList<>();

            // Set display text as well as post-back data.
            SuggestedAction createCalendarAction = new SuggestedAction("Save your calendar", null);

            CreateCalendarEvent createCalendarEvent = new CreateCalendarEvent();
            
            // Choosing Date Format 
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
            dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
            
            // Setting attributes for calendar
            createCalendarEvent.setDescription("Your package from Coffee");
            createCalendarEvent.setTitle("Calendar");
            createCalendarEvent.setStart_time(dateFormat.format(date));
            createCalendarEvent.setEnd_time(dateFormat.format(date));
            
            // Setting action and add to the list
            createCalendarAction.setAction(createCalendarEvent);
            actions.add(createCalendarAction);

            try{
                chatBot.setSuggestedActions(actions);
                chatBot.sendTextMessage("+14047691562", "Do you want to share your location?");
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
```
![Alt Text](https://media.giphy.com/media/JoOyx4tUMAWhLqeSWf/giphy.gif)

---


<a name="agentEvents"></a>
## Agent Events 

Agent Event has the following feature 

- [Agent Composing Event](#agentComposing)
- [Agent Read Event](#agentRead)

<a name="agentComposing"></a>
### Agent Composing Event

Agent composing will enable the ability for the bot to have the composing indication , and so increasing user-experience

- Phone Number : Telling the bot which phone number it is sending the composing event to. Must not be null
```javascript
            chatBot.agentComposing("+14047691562");
```

![Alt Text](https://media.giphy.com/media/hRyRSYmvZJDVC2ZeVk/giphy.gif)


<a name="agentRead"></a>
### Agent Read Event  

Agent Read Event will enable the bot to show the Read indication of the message that end-user is sending to.

- Message ID: Must not null. Tell the bot which message it needs to mark as "Read"
- From: Must not null. Tell the bot which phone number it needs to send to

We obtain the Message ID via the call-back URL because everything interaction between end-user and the bot will go through the call-back URL. 

```javascript
  chatBot.agentReadEvent("MESSAGEID","+14047691562" );
```

![Alt Text](https://media.giphy.com/media/hRyRSYmvZJDVC2ZeVk/giphy.gif)


<a name="fallbackSMS"></a>
## SMS Fallback

<a name="revokemessage"></a>
## Revoke Message

Sometimes we send the messages to the handset with no status response. In this case, the Agent does not know whether the messages have been received to the user handset. Typically, each supplier will have different type of engine to 
revoke the message after X amount of times. However, we have a control of our own to revoke the sent messages after X amount of times. 
<br />
Here is how it is done

- Time out: Specify number of seconds for the message expire. 
- Revoke: Boolean - Should the message be revoked when the timeout happens. If not, the message might still be delivered to the handset after the configured timeout. However, no further status updates will be sent by the system.	

```javascript
    chatBot.setExpireInfo(1000, true);
    chatBot.sendTextMessage("+14047691562", "Hello from Sinch"); 
```

---

# Contributors 
