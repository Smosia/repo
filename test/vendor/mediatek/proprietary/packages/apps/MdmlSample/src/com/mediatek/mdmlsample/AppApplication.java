package com.mediatek.mdmlsample;

import android.app.Application;
import android.util.Log;

import com.mediatek.mdml.MonitorTrapReceiver;
import com.mediatek.mdml.PlainDataDecoder;

/**
 * Created by MTK03685 on 2015/9/14.
 */
public class AppApplication extends Application {
    private static final String TAG = "AppApplication";

    // MDML connection objects (object retain in here)
    private MonitorTrapReceiver m_trapReceiver;
    private CommandProxy m_cmdProxy;
    private ConnectionState m_connectionState;
    // Trap decoder objects
    private PlainDataDecoder m_plainDataDecoder;

    public AppApplication(){
        Log.d(TAG, "AppApplication() constructor.");
        m_connectionState = new ConnectionState();
        m_cmdProxy = new CommandProxy();
        m_cmdProxy.SetConnectionState(m_connectionState);
        m_trapReceiver = new MonitorTrapReceiver(m_connectionState.m_serverName);
        try {
            m_plainDataDecoder = new PlainDataDecoder(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // getter
    CommandProxy GetCommandProxyObject(){
        return m_cmdProxy;
    }
    MonitorTrapReceiver GetTrapReceiverObject(){
        return m_trapReceiver;
    }
    ConnectionState GetConnectionState(){
        return m_connectionState;
    }
    PlainDataDecoder GetPlainDataDecoder(){ return m_plainDataDecoder;}
}
