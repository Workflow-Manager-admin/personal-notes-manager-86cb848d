package com.example.androidfrontend.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Note model class to represent a note in the app.
 */
public class Note implements Serializable {
    private String id;
    private String title;
    private String content;
    private long timestamp;

    // PUBLIC_INTERFACE
    public Note(String title, String content) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    // PUBLIC_INTERFACE
    public String getId() { return id; }

    // PUBLIC_INTERFACE
    public String getTitle() { return title; }

    // PUBLIC_INTERFACE
    public String getContent() { return content; }

    // PUBLIC_INTERFACE
    public long getTimestamp() { return timestamp; }

    // PUBLIC_INTERFACE
    public void setTitle(String title) {
        this.title = title;
        this.timestamp = System.currentTimeMillis();
    }

    // PUBLIC_INTERFACE
    public void setContent(String content) {
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }
}
