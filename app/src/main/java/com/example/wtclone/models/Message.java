package com.example.wtclone.models;

public class Message {
    private String text;
    private boolean isSent;
    private int userId;

    public Message(String text, boolean isSent, int userId) {
        this.text = text;
        this.isSent = isSent;
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public boolean isSent() {
        return isSent;
    }

    public int getUserId() {
        return userId;
    }
}
