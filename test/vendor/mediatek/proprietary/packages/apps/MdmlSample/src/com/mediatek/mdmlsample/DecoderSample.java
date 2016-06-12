package com.mediatek.mdmlsample;

import android.os.Environment;

import com.mediatek.mdml.Msg;
import com.mediatek.mdml.PlainDataDecoder;

public class DecoderSample {
    public void Run() {
        String sdcardPath = Environment.getExternalStorageDirectory().getPath();
        String infoDatPath = sdcardPath + "/Download/info.dat";
        String outputTxtPath = sdcardPath + "/Download/output.txt";
        OutDbgStr ods = new OutDbgStr(outputTxtPath);
        String s = "";
        try {
            PlainDataDecoder decoder = new PlainDataDecoder(infoDatPath);

            OutDbgStr.writeLine("test buffer: 0 1 2 ... 97 98 99");
            otaInfo_test(decoder);
            msgInfo_test(decoder);
            volteInfo_test(decoder);
            globalId_test(decoder);
        } catch (Exception e) {
            s += e.toString();
        }

        /*TextView v = (TextView) findViewById(R.id.textViewItem);
        v.setText(s);*/
        ods.close();
    }

    private void otaInfo_test(PlainDataDecoder decoder) {
        OutDbgStr.writeLine("============================================================");
        String[] msgList = decoder.otaInfo_getMsgList();
        OutDbgStr.writeLine("Decoder.otaInfo_getMsgList()");
        OutDbgStr.writeLine("------------------------------------------------------------");
        if (msgList == null) {
            OutDbgStr.writeLine("null");
            return;
        }

        for (String i : msgList) {
            OutDbgStr.writeLine(i);
        }

        otaInfo_testMsg(decoder, "SIBE_PEER_MSG_RRC_SI", 225);
        otaInfo_testMsg(decoder, "RR_NW_TO_MS_SI_MSG", 226);
        otaInfo_testMsg(decoder, "ERRC_SYS_SI_SIB_PEER", 227);
    }

    private void otaInfo_testMsg(PlainDataDecoder decoder, String msgName, int simIdx) {
        byte[] testBuf = getTestbuf();
        OutDbgStr.writeLine("------------------------------------------------------------");
        OutDbgStr.writeLine("test msg: " + msgName + ", sim index: " + Integer.toString(simIdx));
        int msgID = decoder.otaInfo_getMsgID(msgName);
        OutDbgStr.writeLine("Decoder.otaInfo_getMsgID(" + msgName + ") = " + Integer.toString(msgID));
        OutDbgStr.writeLine("Decoder.otaInfo_getMsgName(" + Integer.toString(msgID) + ") = " + decoder.otaInfo_getMsgName(msgID));
        writeBuf(testBuf, 0, 4, msgID);
        writeBuf(testBuf, 6, 2, simIdx);
        Msg msg = decoder.otaInfo_getMsg(testBuf, 0);
        OutDbgStr.writeLine("Decoder.otaInfo_getMsg() --> Msg");
        msgApiTest(msg);
    }

    private void msgInfo_test(PlainDataDecoder decoder) {
        OutDbgStr.writeLine("============================================================");
        String[] msgList = decoder.msgInfo_getMsgList();
        OutDbgStr.writeLine("Decoder.msgInfo_getMsgList()");
        OutDbgStr.writeLine("------------------------------------------------------------");
        if (msgList == null) {
            OutDbgStr.writeLine("null");
            return;
        }

        for (String i : msgList) {
            OutDbgStr.writeLine(i);
        }

        msgInfo_testMsg(decoder, "MSG_ID_EM_ERRC_MOB_MEAS_INTERRAT_UTRAN_INFO_IND", 225);
        msgInfo_testMsg(decoder, "MSG_ID_EM_MEME_DCH_LTE_CELL_INFO_IND", 226);
        msgInfo_testMsg(decoder, "MSG_ID_EM_MEME_DCH_UMTS_CELL_INFO_IND", 227);
    }

    private void msgInfo_testMsg(PlainDataDecoder decoder, String msgName, int simIdx) {
        byte[] testBuf = getTestbuf();
        OutDbgStr.writeLine("------------------------------------------------------------");
        OutDbgStr.writeLine("test msg: " + msgName + ", sim index: " + Integer.toString(simIdx));
        int msgID = decoder.msgInfo_getMsgID(msgName);
        OutDbgStr.writeLine("Decoder.msgInfo_getMsgID(" + msgName + ") = " + Integer.toString(msgID));
        OutDbgStr.writeLine("Decoder.msgInfo_getMsgName(" + Integer.toString(msgID) + ") = " + decoder.msgInfo_getMsgName(msgID));
        writeBuf(testBuf, 6, 2, msgID);
        writeBuf(testBuf, 0, 2, simIdx);
        Msg msg = decoder.msgInfo_getMsg(testBuf, 0);
        OutDbgStr.writeLine("Decoder.msgInfo_getMsg() --> Msg");
        msgApiTest(msg);
    }

    private void volteInfo_test(PlainDataDecoder decoder) {
        OutDbgStr.writeLine("============================================================");
        String[] msgList = decoder.volteInfo_getMsgList();
        OutDbgStr.writeLine("Decoder.volteInfo_getMsgList()");
        OutDbgStr.writeLine("------------------------------------------------------------");
        if (msgList == null) {
            OutDbgStr.writeLine("null");
            return;
        }

        for (String i : msgList) {
            OutDbgStr.writeLine(i);
        }

        volteInfo_testMsg(decoder, "MSG_ID_DHL_UPCM_IMS_DL_RAW_DATA");
        volteInfo_testMsg(decoder, "MSG_ID_DHL_UPCM_IMS_UL_RAW_DATA");
    }

    private void volteInfo_testMsg(PlainDataDecoder decoder, String msgName) {
        byte[] testBuf = getTestbuf();
        OutDbgStr.writeLine("------------------------------------------------------------");
        OutDbgStr.writeLine("test msg: " + msgName);
        int msgID = decoder.volteInfo_getMsgID(msgName);
        OutDbgStr.writeLine("Decoder.volteInfo_getMsgID(" + msgName + ") = " + Integer.toString(msgID));
        OutDbgStr.writeLine("Decoder.volteInfo_getMsgName(" + Integer.toString(msgID) + ") = " + decoder.volteInfo_getMsgName(msgID));
        Msg msg = decoder.volteInfo_getMsg(testBuf, 0, msgID);
        OutDbgStr.writeLine("Decoder.volteInfo_getMsg() --> Msg");
        msgApiTest(msg);
    }

    private void globalId_test(PlainDataDecoder decoder) {
        OutDbgStr.writeLine("============================================================");
        String[] msgList = decoder.globalId_getList();
        OutDbgStr.writeLine("Decoder.globalId_getList()");
        OutDbgStr.writeLine("------------------------------------------------------------");
        if (msgList == null) {
            OutDbgStr.writeLine("null");
            return;
        }

        for (String i : msgList) {
            OutDbgStr.writeLine(i + "(" + Integer.toString(decoder.globalId_getValue(i)) + ")");
        }
    }

    private void msgApiTest(Msg msg) {
        if (msg == null) {
            OutDbgStr.writeLine("null");
            return;
        }


        OutDbgStr.writeLine("Msg.getSimIdx() == " + msg.getSimIdx());
        OutDbgStr.writeLine("Msg.getMsgName() = " + msg.getMsgName());

        OutDbgStr.write("Msg.getOtaMsgGlobalID() = ");
        byte [] otaOffset = msg.getOtaMsgFieldValue();

        String otaMsgGlobalID = msg.getOtaMsgGlobalID(otaOffset);
        if (otaMsgGlobalID == null) {
            OutDbgStr.writeLine("null");
        } else {
            OutDbgStr.writeLine(otaMsgGlobalID);
        }

        String[] fieldList = msg.getFieldList();
        OutDbgStr.writeLine("----------Msg.getFieldList() : Msg.getFieldValue()----------");
        if (fieldList == null) {
            OutDbgStr.writeLine("null");
        } else {
            for (String fieldName : fieldList) {
                writeField(fieldName, msg.getFieldValue(fieldName));
            }
        }

        OutDbgStr.writeLine("------------------------------------------------------------");
        OutDbgStr.writeLine("Msg.getOtaMsgFieldValue()");
        writeField("OTA_MSG_OFFSET", otaOffset);
    }

    private void writeField(String fieldName, byte[] fieldValue) {
        OutDbgStr.write(fieldName + " :");
        if (fieldValue == null) {
            OutDbgStr.write(" null");
        } else {
            for (byte val : fieldValue) {
                OutDbgStr.write(" " + Byte.toString(val));
            }
        }

        OutDbgStr.newLine();
    }

    private byte[] getTestbuf() {
        byte[] testBuf = new byte[100];
        for (byte i = 0; i < 100; ++i) {
            testBuf[i] = i;
        }

        return testBuf;
    }

    private void writeBuf(byte[] buffer, int offset, int size, int data) {
        if (buffer.length < offset + size) {
            return;
        }

        for (int i = 0; i < size; ++i) {
            buffer[i + offset] = (byte) (0xFF & (data >> (8 * i)));
        }
    }
}
