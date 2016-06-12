package com.mediatek.voiceextension;
public abstract class VoiceCommandListener
{
public  VoiceCommandListener() { throw new RuntimeException("Stub!"); }
public abstract  void onCommandRecognized(int retCode, int commandId, java.lang.String commandStr);
public abstract  void onError(int retCode);
public abstract  void onPauseRecognition(int retCode);
public abstract  void onResumeRecognition(int retCode);
public abstract  void onSetCommands(int retCode);
public abstract  void onStartRecognition(int retCode);
public abstract  void onStopRecognition(int retCode);
}
