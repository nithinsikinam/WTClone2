package com.example.wtclone.models;

public class Chat {
    private int id;
    private int image;
    private String name;
    private String sentMessage;
    private String receivedMessage;

    public Chat(int id, int image, String name, String sentMessage,String receivedMessage) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.sentMessage = sentMessage;
        this.receivedMessage = receivedMessage;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getSentMessage() {
        return sentMessage;
    }

    public String getReceivedMessage() {
        return receivedMessage;
    }
}
