
package com.mediatek.security.ragentek;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.mediatek.common.mom.IMobileManager;
import com.mediatek.common.mom.ReceiverRecord;
import com.mediatek.security.R;
import com.mediatek.security.service.PermControlUtils;

import java.util.ArrayList;
import java.util.List;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class AutoStartCheckActivity extends Activity {

    private static final String TAG = "AutoStartCheckActivity";
	private Intent i = null;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
		i = getIntent();
		
        AlertDialog.Builder builder = new AlertDialog.Builder(this); 
        builder.setMessage("You can disable it's auto start feature! " + i.getStringExtra("packageName")) 
       .setCancelable(false) 
       .setPositiveButton("Yes", new DialogInterface.OnClickListener() { 
           public void onClick(DialogInterface dialog, int id) { 
		        IMobileManager mMoMService = (IMobileManager) getSystemService(Context.MOBILE_SERVICE);
				mMoMService.setBootReceiverEnabledSetting(i.getStringExtra("packageName"), false);
                finish(); 
           } 
       }) 
       .setNegativeButton("No", new DialogInterface.OnClickListener() { 
           public void onClick(DialogInterface dialog, int id) { 
                dialog.cancel(); 
           } 
       }); 
	   AlertDialog alert = builder.create();
	   alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
	   alert.show();
    }
}
