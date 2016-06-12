package com.kayun.smartmotion.beans;

public class SmartMotionDialogInfo {

    private int mDialogTitle;
    private int mDialogMessage;
    private int mDialogDrawable;

    public SmartMotionDialogInfo(int mDialogTitle, int mDialogMessage, int mDialogDrawable) {
        super();
        this.mDialogTitle = mDialogTitle;
        this.mDialogMessage = mDialogMessage;
        this.mDialogDrawable = mDialogDrawable;
    }

    public int getmDialogTitle() {
        return mDialogTitle;
    }

    public void setmDialogTitle(int mDialogTitle) {
        this.mDialogTitle = mDialogTitle;
    }

    public int getmDialogMessage() {
        return mDialogMessage;
    }

    public void setmDialogMessage(int mDialogMessage) {
        this.mDialogMessage = mDialogMessage;
    }

    public int getmDialogDrawable() {
        return mDialogDrawable;
    }

    public void setmDialogDrawable(int mDialogDrawable) {
        this.mDialogDrawable = mDialogDrawable;
    }

}
