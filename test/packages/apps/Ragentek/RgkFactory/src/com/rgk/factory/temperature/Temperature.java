
//file create by xuemingwei 20150919

package com.rgk.factory.temperature;

import com.rgk.factory.Config;
import com.rgk.factory.R;
import com.rgk.factory.ControlCenter.AutoTestHandle;
import com.rgk.factory.ControlCenter.ResultHandle;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Temperature extends Activity implements View.OnClickListener {
    
	public static String TAG = "Temperature";
	private TextView mBattery_temp;
	private int mtemp;
	private boolean hadSendBroadcast = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
       // M: huangkunming 2014.12.20 @{
       // since window flags full, change FLAG_HOMEKEY_DISPATCHED to privateFlags
    	//getWindow().addFlags(WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED);
    	getWindow().getAttributes().privateFlags |= WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED;
	// @}
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setTitle(R.string.temperature);
        setContentView(R.layout.temperature);
        RelativeLayout mLayout = (RelativeLayout) findViewById(R.id.mTemperature_layout);
        mLayout.setSystemUiVisibility(0x00002000);
        mBattery_temp = (TextView) findViewById(R.id.temperature_info_temp);
        
        findViewById(R.id.temperature_pass).setOnClickListener(this);
        findViewById(R.id.temperature_fail).setOnClickListener(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mBroadcastReceiver, filter);
        
    }
    
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
    	public void onReceive(Context context, Intent intent){
    		Log.e(TAG,"action="+intent.getAction());
            if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
            	String temp = tenthsToFixedString(intent.getIntExtra("temperature", 0));
            	
            	Log.e(TAG,"temp="+temp);
				
				mBattery_temp.setText(getResources().getString(R.string.temp)+temp+"*C");
            }
    	}

    };

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
			case R.id.temperature_pass:
				SendBroadcast(Config.PASS);
				break;
			case R.id.temperature_fail:
				SendBroadcast(Config.FAIL);
				break;
		}
		
	}
	
	public void SendBroadcast(String result){
		if (!hadSendBroadcast) {
			hadSendBroadcast = true;
			ResultHandle.SaveResultToSystem(result, TAG);
			AutoTestHandle.autoTest(this, TAG);
			//sendBroadcast(new Intent(Config.ACTION_START_AUTO_TEST).putExtra("test_item", TAG));
			finish();
		}	
	}
	
    private final String tenthsToFixedString(int x) {
        int tens = x / 10;
        Log.e(TAG,"X="+x);
        return Integer.toString(tens) + "." + (x - 10 * tens);
    }
	
    @Override
    protected void onPause() {
        super.onPause();
    }
    
    @Override
    public void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}