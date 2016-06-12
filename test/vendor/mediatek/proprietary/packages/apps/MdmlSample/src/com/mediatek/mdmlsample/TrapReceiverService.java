package com.mediatek.mdmlsample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.mediatek.mdml.MonitorTrapReceiver;
import com.mediatek.mdml.Msg;
import com.mediatek.mdml.PlainDataDecoder;
import com.mediatek.mdml.TRAP_TYPE;
import com.mediatek.mdml.TrapHandlerInterface;

import java.lang.ref.WeakReference;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TrapReceiverService extends Service implements TrapHandlerInterface {
    private static final String TAG = "TrapReceiverService";
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    // app trapReceiver reference
    MonitorTrapReceiver m_trapReceiver;
    // Trap decoder objects
    private PlainDataDecoder m_plainDataDecoder;
    // Output file related objects
    String outputFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/MDML_output.txt";
    OutDbgStr outputFile;
    int trapCount = 0;


    @Override
    public void onCreate(){
        Log.d(TAG, "onCreate()");
        // Get MDML MonitorTrapReceiver objects and connection state from app object
        AppApplication app = (AppApplication) getApplicationContext();
        m_trapReceiver = app.GetTrapReceiverObject();
        // Get trap decoder object from app object
        m_plainDataDecoder = app.GetPlainDataDecoder();
        // setup trap handler to trap receiver
        m_trapReceiver.SetTrapHandler(this);
        // create output file (use OutDbgStr)
        outputFile = new OutDbgStr(outputFilePath);
    }

    @Override
    public int onStartCommand(Intent intenr, int flags, int startID){
        Toast.makeText(this, "TrapReceiverService Start", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return mBinder;
    }

    @Override
    public void onDestroy(){
        Toast.makeText(this, "TrapReceiverService Done", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy()");
        // close output file (use OutDbgStr)
        outputFile.close();
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        TrapReceiverService getService() {
            // Return this instance of LocalService so clients can call public methods
            return TrapReceiverService.this;
        }
    }

    // Public methods for clients
    WeakReference<NewTrapListener> m_trapListeners;
    public void registerLogUpdater(NewTrapListener listener){
        Log.d(TAG, "registerLogUpdater()");
        m_trapListeners = new WeakReference<NewTrapListener>(listener);
    }

    public void ProcessTrap(long timeInMillisecond, TRAP_TYPE type, int len, byte[] data, int offset) {
        Log.d(TAG, "frame is incoming: timestamp = [" + timeInMillisecond + "], type = [" + type.GetValue() + "], len = [" + len + "]");
        // Dump trap number
        trapCount++;
        OutDbgStr.writeLine("#*" + trapCount + "*#  ");
        // format time string and dump to file
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ");
        //String timeString = df.format(new Timestamp(timeInMillisecond));
        OutDbgStr.writeLine(String.valueOf(timeInMillisecond));
        // Message object
        Msg msg = null;

        // Decode trap by trap type
        switch (type) {
            case TRAP_TYPE_OTA: {
                int traceID = (int) Utils.GetIntFromByte(data, offset, 4);
                Log.d(TAG, "[OTA]traceID: " + traceID);
                OutDbgStr.writeLine("[OTA]traceID: " + traceID);
                msg = m_plainDataDecoder.otaInfo_getMsg(data, offset);
                DumpOTAMessageFields(msg);
                break;
            }
            case TRAP_TYPE_EM: {
                int msgID = (int) Utils.GetIntFromByte(data, offset + 6, 2);
                Log.d(TAG, "[EM] " + m_plainDataDecoder.msgInfo_getMsgName(msgID) + " " + msgID);
                OutDbgStr.writeLine("[EM] " + m_plainDataDecoder.msgInfo_getMsgName(msgID) + " " + msgID);
                msg = m_plainDataDecoder.msgInfo_getMsg(data, offset);
                DumpGeneralMessageFields(msg);
                break;
            }
            case TRAP_TYPE_VOLTE: {
                int msgID = (int) Utils.GetIntFromByte(data, offset + 6, 2);
                Log.d(TAG, "[VoLTE] " + m_plainDataDecoder.volteInfo_getMsgName(msgID) + " " + msgID);
                OutDbgStr.writeLine("[VoLTE] " + m_plainDataDecoder.volteInfo_getMsgName(msgID) + " " + msgID);
                msg = m_plainDataDecoder.volteInfo_getMsg(data, offset, msgID);
                DumpGeneralMessageFields(msg);
                break;
            }
            case TRAP_TYPE_DISCARDINFO:
                OutDbgStr.writeLine("[Discard ... ]");
                break;
            default:
                Log.e(TAG, "[Unknown Type, Error ...]");
                OutDbgStr.writeLine("[Unknown Type, Error ...]");
                break;
        }

        // End token for one trap
        OutDbgStr.writeLine("*#*#");

        Log.d(TAG, "Trigger Activity listener !");
        if (m_trapListeners != null && m_trapListeners.get() != null) {
            m_trapListeners.get().NewTrapArrival();
        }
        Log.d(TAG, "frame is incoming... done!");
    }

    private void DumpGeneralMessageFields(Msg msg){
        if (msg != null) {
            Log.d(TAG, "Print General fields !");
            String[] fieldList = msg.getFieldList();
            if (fieldList != null) {
                for (String fieldName : fieldList) {
                    OutDbgStr.write("     " + fieldName + " : ");
                    byte[] fieldValueBytes = msg.getFieldValue(fieldName);
                    if (fieldValueBytes == null) {
                        OutDbgStr.writeLine("null");
                    } else {
                        long fieldValue = Utils.GetIntFromByte(fieldValueBytes, 0, fieldValueBytes.length);
                        OutDbgStr.write(" " + fieldValue);
                        /*for (byte val : fieldValueBytes) {
                            OutDbgStr.write(" ( " + Byte.toString(val) + " ) ");
                        }*/
                        OutDbgStr.newLine();
                    }
                }
            }
        }
    }

    private void DumpOTAMessageFields(Msg msg) {
        if (msg != null) {
            Log.d(TAG, "Print OTA fields !");
            // get OTA global ID
            byte[] otaOffset = msg.getOtaMsgFieldValue();
            String otaGlobalID = msg.getOtaMsgGlobalID(otaOffset);
            if (otaGlobalID != null) {
                OutDbgStr.writeLine("     Global ID : " + otaGlobalID);
            }
            // get OTA raw data
            int raw_offset = msg.getOtaMsgAirMsgRawDataBufOffset(otaOffset);
            int raw_length = msg.getOtaMsgAirMsgRawDataBufLength(otaOffset);
            Log.d(TAG, "raw length = " + raw_length + ",  otaOffset.length - raw_offset - 2 = " + (otaOffset.length - raw_offset - 2));
            if(raw_length > 0){
                OutDbgStr.write("     OTA raw data : ");
                for (int i=0; i<raw_length; i++) {
                    OutDbgStr.write( String.format("0x%02x", otaOffset[raw_offset+i]) + "(" + Byte.toString(otaOffset[raw_offset+i]) + ") ");
                }
                OutDbgStr.newLine();
            }
        }
    }
}
