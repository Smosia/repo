package com.android.ota;

import java.io.File;
import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.os.SystemProperties;
import android.util.Log;
import android.view.Window;
//20130321 add MTK OTA upgrade liangyulong create
public class MtkUpgradeRestart extends Activity {

    private static final String TAG = "OTA/MtkUpgradeRestart";

    @Override
	protected void onCreate(Bundle arg0) {		
		super.onCreate(arg0);
// add BUG_ID:JELY-803 20130606 yulong.liang start
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
// add BUG_ID:JELY-803 20130606 yulong.liang end

		//modify bug_id:none zengchuiguo 20140226 (start)
		//String filepath=this.getIntent().getStringExtra("filePath");
		String filepath = this.getIntent().getStringExtra("filePath");
                Intent intent = new Intent();
                intent.setAction("com.android.suc.startupdate");
                intent.putExtra("filePath",filepath);
                sendBroadcast(intent);
    }
}
