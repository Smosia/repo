package com.android.settings.emergencyassistant;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.android.settings.R;
import android.util.Log;
import android.widget.Button;
import android.view.View.OnClickListener;

/**
 * Created by maoxunlei on 13-7-22.
 */
public class EmergencyEditMsgActivity extends Activity implements OnClickListener{
	
	private final static String TAG = "EmergencyEditMsgActivity";
    private ActionBar mActionBar;
    //private View mActionBarView;
    private EditText mEditText;
    private Button mCancelButton;
    private Button mSaveButton;
    public static final String FILE_NAME = "emergency_msg.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_edit_msg);
        mEditText = (EditText) findViewById(R.id.emergency_msg);
        mCancelButton = (Button) findViewById(R.id.cancel_save_msg_button);
        mSaveButton = (Button) findViewById(R.id.save_msg_button);
        mCancelButton.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onResume() {
        super.onResume();

        try {
            String content = readContent(FILE_NAME);
            mEditText.setText(content);

        } catch (Exception e) {
            Log.e(TAG, "EmergencyEditMsgActivity readContent() exception!");
        } finally {
            if (mEditText != null) {
                if (mEditText.getText() != null && mEditText.getText().toString().equals("")) {
                    mEditText.setText(R.string.default_emergency_msg);
                }
            }
        }

        if (mEditText != null) {
            String text = mEditText.getText().toString();
            if (!text.isEmpty()) {
                mEditText.requestFocus();
                mEditText.setSelection(text.length());
            }
        }
    }

    @Override
    public void onClick(View v) {
    	if (v == null) return;
    	switch (v.getId()) {
    	   case R.id.save_msg_button :
    		   Log.d(TAG,"save msg button click");
               Editable editable = mEditText.getText();
               String content = editable != null ? editable.toString() : getString(R.string.default_emergency_msg);
               try {
                   saveContent(content);
               } catch (Exception e) {
                   Log.e(TAG, "EmergencyEditMsgActivity saveContent() exception!");
               }
               finish();
               break;
           case R.id.cancel_save_msg_button:
        	   Log.d(TAG,"cancel save msg button click");
               finish();
               break;
    	}
    }
    
    

    // save content to file 'emergency_msg.txt'
    public void saveContent(String content) throws Exception {
        FileOutputStream outStream = this.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
        outStream.write(content.getBytes());
        outStream.close();
    }

    // read content from file 'emergency_msg.txt'
    public String readContent(String filename) throws Exception {
        FileInputStream inStream = this.openFileInput(filename);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        String content = new String(data);
        inStream.close();
        outStream.close();
        return content;
    }
}
