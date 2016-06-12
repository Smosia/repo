package android.media.midi;
public abstract class MidiSender
{
public  MidiSender() { throw new RuntimeException("Stub!"); }
public  void connect(android.media.midi.MidiReceiver receiver) { throw new RuntimeException("Stub!"); }
public  void disconnect(android.media.midi.MidiReceiver receiver) { throw new RuntimeException("Stub!"); }
public abstract  void onConnect(android.media.midi.MidiReceiver receiver);
public abstract  void onDisconnect(android.media.midi.MidiReceiver receiver);
}
