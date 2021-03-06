package com.example.materialdesign.model;

public class MessageEntity {
    //autogenerated
    private long timestamp;

    private CharSequence sender;
    private CharSequence text;

    public MessageEntity(CharSequence sender, CharSequence text) {
        this.sender = sender;
        this.text = text;
        timestamp = System.currentTimeMillis();
    }

    public CharSequence getText() {
        return text;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public CharSequence getSender() {
        return sender;
    }
}
