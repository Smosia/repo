package com.mediatek.voiceextension;
public class VoiceCommandManager
{
VoiceCommandManager() { throw new RuntimeException("Stub!"); }
public static  com.mediatek.voiceextension.VoiceCommandManager getInstance() { throw new RuntimeException("Stub!"); }
public  int createCommandSet(java.lang.String name) { throw new RuntimeException("Stub!"); }
public  int isCommandSetCreated(java.lang.String name) { throw new RuntimeException("Stub!"); }
public  int deleteCommandSet(java.lang.String name) { throw new RuntimeException("Stub!"); }
public  java.lang.String getCommandSetSelected() { throw new RuntimeException("Stub!"); }
public  int selectCurrentCommandSet(java.lang.String name, com.mediatek.voiceextension.VoiceCommandListener listener) { throw new RuntimeException("Stub!"); }
public  void setCommands(java.lang.String[] commands) throws java.lang.IllegalAccessException { throw new RuntimeException("Stub!"); }
public  void setCommands(java.io.File file) throws java.io.FileNotFoundException, java.lang.IllegalAccessException { throw new RuntimeException("Stub!"); }
public  void setCommands(android.content.Context context, java.lang.String assetsFilePath) throws java.io.IOException, java.lang.IllegalAccessException { throw new RuntimeException("Stub!"); }
public  void setCommands(android.content.Context context, int resid) throws android.content.res.Resources.NotFoundException, java.lang.IllegalAccessException { throw new RuntimeException("Stub!"); }
public  void startRecognition() throws java.lang.IllegalAccessException { throw new RuntimeException("Stub!"); }
public  void stopRecognition() throws java.lang.IllegalAccessException { throw new RuntimeException("Stub!"); }
public  void pauseRecognition() throws java.lang.IllegalAccessException { throw new RuntimeException("Stub!"); }
public  void resumeRecognition() throws java.lang.IllegalAccessException { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getCommands() throws java.lang.IllegalAccessException { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getCommandSets() { throw new RuntimeException("Stub!"); }
}
