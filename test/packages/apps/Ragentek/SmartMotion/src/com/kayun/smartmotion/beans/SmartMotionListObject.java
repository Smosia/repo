package com.kayun.smartmotion.beans;

public class SmartMotionListObject {

    private String title;
    private String summary;

    public SmartMotionListObject(String title, String summary) {
        super();
        this.title = title;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
