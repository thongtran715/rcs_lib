package com.sinch.rcssdk.rcs.chatflow;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public abstract class AgentConfiguration {

    protected String TOKEN;

    protected String AGENT_ID;

    private String CONTENT_HEADER;

    private String AUTH_HEADER;

    private String BASE_API_URL;

    private String RCS_API_URL;

    private String RCS_EVENT_URL;

    private RCSConfigureType type;


    public AgentConfiguration(RCSConfigureType type, String TOKEN, String AGENT_ID) {
        this(type);
        this.TOKEN = TOKEN;
        this.AGENT_ID = AGENT_ID;
    }

    public AgentConfiguration(RCSConfigureType type) {
        this.type = type;
    }

    public void setType(RCSConfigureType type) {
        this.type = type;
    }

    private void setUpBase() {
        this.CONTENT_HEADER = "application/json";
        this.AUTH_HEADER = "Bearer " + this.TOKEN;
        this.BASE_API_URL = "https://api.clxcommunications.com/rcs/v1/" + AGENT_ID;
        this.RCS_API_URL = BASE_API_URL + "/messages";
        this.RCS_EVENT_URL = BASE_API_URL + "/events";
    }

    public HttpResponse post(String payload) {
        setUpBase();
        if (this.type == RCSConfigureType.api) {
            return postRequestApi(payload);
        }
            return postRequestAgent(payload);
    }


    private HttpResponse postRequestApi(String payload) {
        try {
            return postRequest(payload, RCS_API_URL);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    private HttpResponse postRequestAgent(String payload) {
        try {
            return postRequest(payload, RCS_EVENT_URL);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private HttpResponse postRequest(String payload, String url) throws Exception {
      CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost request = new HttpPost(url);
            request.setHeader("Authorization", AUTH_HEADER);
            request.setHeader("Content-Type", CONTENT_HEADER);
            request.setEntity(new StringEntity(payload));
            HttpResponse response =  httpClient.execute(request);
            System.out.println("Response: " + EntityUtils.toString(response.getEntity()));
            System.out.println("Status Code: " + response.getStatusLine().getStatusCode());
            return response;
    }


}