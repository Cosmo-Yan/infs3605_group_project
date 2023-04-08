package com.example.infs3605_group_project;

public class FeedEventModel {
    String eventType;
    String hostName;
    int image;


    public FeedEventModel(String eventType, String hostName, int image) {
        this.eventType = eventType;
        this.hostName = hostName;
        this.image = image;
    }

    public String getEventType() {
        return eventType;
    }

    public String getHostName() {
        return hostName;
    }

    public int getImage() {
        return image;
    }
}
