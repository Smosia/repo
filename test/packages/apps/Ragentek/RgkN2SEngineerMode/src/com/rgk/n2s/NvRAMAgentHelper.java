package com.rgk.n2s;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import android.os.ServiceManager;

import android.os.IBinder;
import android.util.Log;

public class NvRAMAgentHelper {

    private static final String TAG = "NvRAMAgentHelper";
    /**
     * This value related to
     * mediatek\custom\(project)\cgen\cfgfileinc\CFG_PRODUCT_INFO_File
     * .h---PRODUCT_INFO->FactoryData3(first byte)
     */
    public static final int PSENSOR_INDEX = 808;
    public static final int PSENSOR_INDEX_GAIN = 809;

    private static NvRAMAgent getNvRAMAgent() {
        NvRAMAgent agent = null;
        try {
            IBinder binder = ServiceManager.getService("NvRAMAgent");
            agent = NvRAMAgent.Stub.asInterface(binder);
            if (agent == null) {
                Log.e(TAG, "getNvRAMAgent null!");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return agent;
    }

    public static int readNVData(int index) {
        int data = -1;
        NvRAMAgent agent = getNvRAMAgent();
        byte[] buff = null;
        try {
            buff = agent.readFile(Util.AP_CFG_REEB_PRODUCT_INFO_LID);
            if (buff != null) {
                data = buff[index]& 0xff;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.i(TAG, "readNVData: data = " + data);
        return data;
    }

    // Write data to nvram
    public static int writeNVData(int index, int value) {
        NvRAMAgent agent = getNvRAMAgent();
        byte[] buff = null;
        try {
            buff = agent.readFile(Util.AP_CFG_REEB_PRODUCT_INFO_LID);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        buff[index] = (byte) value;
        int result = 0;
        try {
            result = agent
                    .writeFile(Util.AP_CFG_REEB_PRODUCT_INFO_LID, buff);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "writeNVData: result=" + result);
        return result;
    }
    
}
