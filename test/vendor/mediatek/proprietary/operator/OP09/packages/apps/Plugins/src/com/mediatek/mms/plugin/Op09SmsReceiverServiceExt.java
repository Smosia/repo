package com.mediatek.mms.plugin;

import com.mediatek.common.sms.IConcatenatedSmsFwkExt;
import com.mediatek.mms.callback.ISmsReceiverServiceCallback;
import com.mediatek.mms.ext.DefaultOpSmsReceiverServiceExt;

import android.net.Uri;
import android.provider.Telephony.Sms;
import android.provider.Telephony.Sms.Inbox;
import android.telephony.SmsMessage;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.sqlite.SqliteWrapper;
import android.database.Cursor;
import android.util.Log;

import com.android.internal.telephony.SmsHeader;

public class Op09SmsReceiverServiceExt extends DefaultOpSmsReceiverServiceExt {
    /**
     * Updated segments to dispatch flag type.
     *
     * @internal
     */
    public static final int UPLOAD_FLAG_UPDATE = IConcatenatedSmsFwkExt.UPLOAD_FLAG_UPDATE;

    private static String TAG = "Op09SmsReceiverServiceExt";

    /**
     * Updated segments tag to put on the intent extra value.
     *
     * @internal
     */
    public static final String UPLOAD_FLAG_TAG = IConcatenatedSmsFwkExt.UPLOAD_FLAG_TAG;
    /**
     * New segments to dispatch flag type.
     *
     * @internal
     */
    public static final int UPLOAD_FLAG_NEW = IConcatenatedSmsFwkExt.UPLOAD_FLAG_NEW;
    private ISmsReceiverServiceCallback mCallback;
    private boolean mIsWholeSms;
    public Op09SmsReceiverServiceExt(Context base) {
        super(base);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(Service service, ISmsReceiverServiceCallback callback) {
        mCallback = callback;
    }

    @Override
    public boolean storeMessage(SmsMessage[] msgs, SmsMessage sms, ContentValues values) {
        extractSmsBody(msgs, sms, values);
        return true;
    }

    @Override
    public Uri handleSmsReceived(Context context, SmsMessage[] msgs, Intent intent, int error) {
        Uri messageUri = null;
        /// M: For OP09 Storage Low @{
        if (MessageUtils.isLowMemoryNotifEnable()) {
            MessageUtils.dealCTDeviceLowNotification(context);
        }
        /// M: @}

        /// M: OP09 Feature, receive long SMS. @{
        int uploadFlag = UPLOAD_FLAG_NEW;
        if (MessageUtils.isMissedSmsReceiverEnable()) {
            uploadFlag = intent.getIntExtra(UPLOAD_FLAG_TAG,
                UPLOAD_FLAG_NEW);
            Log.d(TAG, "UPLOAD_FLAG_TAG: " + uploadFlag);
        }
        /// @}

        /// M: For OP09 Feature, receive missing part of concatenated Sms. @{
        if (msgs != null && msgs[0].getMessageClass() != SmsMessage.MessageClass.CLASS_0
            && !msgs[0].isReplace() && MessageUtils.isMissedSmsReceiverEnable()
            && uploadFlag == UPLOAD_FLAG_UPDATE) {
            mIsWholeSms = true;
            messageUri = updateMissedSms(
                context, msgs, error, mCallback);
        /// @}
        }
        return messageUri;
        /// @}
    }

    @Override
    public Intent displayClassZeroMessage(Intent intent) {
        /// M: add for OP09 @{
        if (MessageUtils.isClassZeroModelShowLatestEnable()) {
            return Op09DisplayClassZeroMessageExt.getIntance(getApplicationContext())
                                                 .setLaunchMode(intent);
        }
        /// @}
        return intent;
    }

    public void extractSmsBody(SmsMessage[] msgs, SmsMessage sms, ContentValues values) {
        Log.d("@M_" + TAG, "Op09SmsReceiverExt.extractSmsBody");

        int pduCount = msgs.length;
        boolean hasMissedSegments = checkConcateRef(sms.getUserDataHeader(), pduCount);

        Log.d("@M_" + TAG, "pduCount=" + pduCount);

        if (hasMissedSegments) {
            int totalParts = sms.getUserDataHeader().concatRef.msgCount;
            Log.v("@M_" + TAG, "[fake process missed segment(s) " + pduCount + "/" + totalParts);
            String messageBody = handleMissedParts(msgs);
            if (messageBody != null) {
                values.put(Inbox.BODY, messageBody);
            }
            int referenceId = sms.getUserDataHeader().concatRef.refNumber;
            values.put(Sms.REFERENCE_ID, referenceId);
            values.put(Sms.TOTAL_LENGTH, totalParts);
            values.put(Sms.RECEIVED_LENGTH, pduCount);
        } else {
            if (pduCount == 1) {
                // There is only one part, so grab the body directly.
                values.put(Inbox.BODY, replaceFormFeeds(sms.getDisplayMessageBody()));
            } else {
                // Build up the body from the parts.
                StringBuilder body = new StringBuilder();
                for (int i = 0; i < pduCount; i++) {
                    SmsMessage msg;
                    msg = msgs[i];
                    body.append(msg.getDisplayMessageBody());
                }
                values.put(Inbox.BODY, replaceFormFeeds(body.toString()));
            }
        }
    }

    /**
     * M: check concate ref.
     * @param udh  sms heahder.
     * @param actualPartsNum part's num.
     * @return
     */
    private boolean checkConcateRef(SmsHeader udh, int actualPartsNum) {
        if (udh == null || udh.concatRef == null) {
            Log.d("@M_" + TAG, "[fake not concate message");
            return false;
        } else {
            int totalPartsNum = udh.concatRef.msgCount;
            if (totalPartsNum > actualPartsNum) {
                Log.d("@M_" + TAG, "[fake missed segment(s) "
                        + (totalPartsNum - actualPartsNum));
                return true;
            }
        }

        return false;
    }

    /**
     * M: Some providers send formfeeds in their messages. Convert those formfeeds to newlines.
     * @param s the content.
     * @return the formated string.
     */
    private String replaceFormFeeds(String s) {
        /** M: process null @{ */
        if (s == null) {
            return "";
        }
        /** @} */
        return s.replace('\f', '\n');
    }

    public Uri updateMissedSms(Context context, SmsMessage[] msgs, int error,
            ISmsReceiverServiceCallback callback) {
        Log.d("@M_" + TAG, "MissedSmsReceiverExt.updateMissedSms");

        SmsMessage smsTmp = msgs[0];
        int pduCount = msgs.length;
        int refId = smsTmp.getUserDataHeader().concatRef.refNumber;
        Log.d("@M_" + TAG, "pduCount=" + pduCount + " refId=" + refId);
        Uri missedSmsUri = findMissedSms(context, refId, pduCount);

        if (missedSmsUri != null) {
            Log.d("@M_" + TAG, "Find missed Sms: " + missedSmsUri.toString());
            return handleUpdate(context, msgs, pduCount, missedSmsUri);
        } else { // Original SMS may be deleted.
            return callback.callStoreMessage(context, msgs, error);
        }
    }

    /**
     * M: handle update .
     * @param msgs smses.
     * @param pduCount the pdu count.
     * @param missedSmsUri the uri for missed sms.
     * @return the new sms uri.
     */
    private Uri handleUpdate(Context mContext, SmsMessage[] msgs, int pduCount, Uri missedSmsUri) {
        // Build up the body from the parts.
        StringBuilder body = new StringBuilder();
        if (!mIsWholeSms) {
            body.append(handleMissedParts(msgs));
        } else {
            for (int i = 0; i < pduCount; i++) {
                SmsMessage msg;
                msg = msgs[i];
                body.append(msg.getDisplayMessageBody());
            }
        }

        ContentValues values = new ContentValues(2);
        values.put(Sms.BODY, body.toString());
        values.put(Sms.RECEIVED_LENGTH, pduCount);
        int ret = SqliteWrapper.update(mContext, mContext.getContentResolver(), missedSmsUri,
            values, null, null);
        if (ret == 1) {
            return missedSmsUri;
        } else {
            Log.e("@M_" + TAG, "Update Sms error!");
            return null;
        }
    }

    /**
     * M: find out the sms in database according to the Reference number.
     * @param refId the reference id.
     * @param newPduCount new pdu count.
     * @return the sms uri.
     */
    private Uri findMissedSms(Context mContext, int refId, int newPduCount) {
        int totalCount = 0;

        String where = Sms.REFERENCE_ID + " = ?";
        Cursor cursor = SqliteWrapper.query(mContext, mContext.getContentResolver(),
            Sms.CONTENT_URI, new String[] {Sms._ID, Sms.TOTAL_LENGTH}, where, new String[] {String
                    .valueOf(refId)}, null);

        if (cursor == null || cursor.getCount() != 1) {
            Log.e("@M_" + TAG, "cursor == " + (cursor == null ? "NULL" : cursor.getCount()));
            return null;
        }

        try {
            if (cursor.moveToFirst()) {
                totalCount = cursor.getInt(1);
                Log.d("@M_" + TAG, "totalCount: " + totalCount + " newPduCount: " + newPduCount);

                if (newPduCount > totalCount) {
                    Log.e("@M_" + TAG, "Wrong Pdu Count!");
                    return null;
                }

                if (newPduCount < totalCount) {
                    mIsWholeSms = false;
                    Log.d("@M_" + TAG, "Not whole SMS! totalCount should be: " + totalCount);
                }

                int id = cursor.getInt(0);
                return Uri.parse(Sms.CONTENT_URI + "/" + id);
            }
        } finally {
            cursor.close();
        }

        return null;
    }

    /**
     * M: handle Miessed Pasts for cascaed sms.
     * @param parts the smsMessage parts.
     * @return the message content string.
     */
    private String handleMissedParts(SmsMessage[] parts) {
        if (parts == null || parts.length <= 0) {
            Log.e("@M_" + TAG, "[fake invalid message array");
            return null;
        }

        int totalPartsNum = parts[0].getUserDataHeader().concatRef.msgCount;

        String[] fakeContents = new String[totalPartsNum];
        for (SmsMessage msg : parts) {
            int seq = msg.getUserDataHeader().concatRef.seqNumber;
            Log.d("@M_" + TAG, "[fake add segment " + seq);
            fakeContents[seq - 1] = msg.getDisplayMessageBody();
        }
        for (int i = 0; i < fakeContents.length; ++i) {
            if (fakeContents[i] == null) {
                Log.d("@M_" + TAG, "[fake replace segment " + (i + 1));
                fakeContents[i] = "(...)";
            }
        }

        StringBuilder body = new StringBuilder();
        for (String s : fakeContents) {
            body.append(s);
        }
        return body.toString();
    }

}















