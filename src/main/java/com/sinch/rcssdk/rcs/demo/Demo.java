package com.sinch.rcssdk.rcs.demo;

import ch.qos.logback.core.util.FileSize;
import com.sinch.rcssdk.rcs.chatflow.ChatBot;
import com.sinch.rcssdk.rcs.chatflow.RCSConfigureType;
import com.sinch.rcssdk.rcs.exceptions.FileSizeExceedLimitException;
import com.sinch.rcssdk.rcs.message.messagetype.AgentMessage;

import java.io.IOException;

public class Demo {


    public static void main(String[] args) {


        String MEN_VIDEO_SELECTED = "https://s3.amazonaws.com/sketchers-chatbot/Video/Skechers+RCS+Chatbot+Video+Revisions+Assets+4.19.19/Mens+Tony+Romo+for+Skechers+Slip-ons.mp4";
        String MEN_THUMBNAIL_SELECTED = "https://s3.amazonaws.com/sketchers-chatbot/Video/Skechers+RCS+Chatbot+Video+Revisions+Assets+4.19.19/Video+Revisions+Thumbnails/man.png";
        ChatBot chatBot = new ChatBot(new YourBotConfiguration(RCSConfigureType.api));
        chatBot.setSupplier(AgentMessage.Supplier.MAAP_SAMSUNG);
        try {
            chatBot.sendVideo("14047691562", MEN_VIDEO_SELECTED, "fadsa.mp4", MEN_THUMBNAIL_SELECTED, "image.png");
        }catch (FileSizeExceedLimitException e)
        {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
