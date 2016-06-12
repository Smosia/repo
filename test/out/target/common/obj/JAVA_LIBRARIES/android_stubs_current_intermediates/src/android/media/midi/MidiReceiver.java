package android.media.midi;
public abstract class MidiReceiver
{
public  MidiReceiver() { throw new RuntimeException("Stub!"); }
public  MidiReceiver(int maxMessageSize) { throw new RuntimeException("Stub!"); }
public abstract  void onSend(byte[] msg, int offset, int count, long timestamp) throws java.io.IOException;
public  void flush() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void onFlush() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public final  int getMaxMessageSize() { throw new RuntimeException("Stub!"); }
public  void send(byte[] msg, int offset, int count) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void send(byte[] msg, int offset, int count, long timestamp) throws java.io.IOException { throw new RuntimeException("Stub!"); }
}
