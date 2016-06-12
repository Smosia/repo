package com.rlk.powersavemanagement;



import com.rlk.powersavepreference.CustomSwitchPreference;

import android.app.ActionBar;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.provider.Settings.Global;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RadioButton;
import android.os.PowerManager;

@SuppressWarnings("unused")
public class PowerSaveMainActivity extends PreferenceActivity  {
   private static final String TAG = "PowerSave/MainActivity";
   private static final String SAVE_MODE_KEY = "save_mode";
   private static final String SCREEN_POWER_CATEGORY = "screen_power_category";
   private static final String POWER_SAVING_KEY = "power saving";
   private static final String ULTRA_POWER_KEY = "ultra power";
   private PreferenceScreen preferenceScreen;
   private CustomSwitchPreference powerSavingPref;
   private CustomSwitchPreference ultraPowerPref;
   private TextView mStandbyDay;
   private TextView mStandbyHour;
   private TextView mStandbyMinute;
   private boolean mIsPowerSaveOn;
   private boolean mIsUltraPowerOn;
   private boolean mIsScreenColorModeOn;
   private static final int ENABLE_SWITCH = 201;
   private static final int ENABLE_SWITCH_TIME = 1500;
   private boolean isSwitchEnd = true;
   private boolean isPowerSaveEnable = true;
   private ImageView mBatteryRemainderIv;
   private Bitmap mBitmap;
   //A by shubin 
   private RadioButton bt_normal;
   private RadioButton bt_save;
   private RadioButton bt_extreme;
   private PowerManager mPowerManager;
   private final Handler mHandlers = new Handler();
   private final SettingsObserver mSettingsObserver = new SettingsObserver(mHandlers);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
	
		ActionBar actionbar = getActionBar();
	    if(actionbar!=null){
	    	actionbar.setHomeButtonEnabled(true);
			actionbar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
					| ActionBar.DISPLAY_SHOW_TITLE);
			actionbar.setDisplayHomeAsUpEnabled(true);
	    }
       // addPreferencesFromResource(R.xml.save_mode_main);
        setContentView(R.layout.main);
        mStandbyHour = (TextView) findViewById(R.id.standby_hour);
        mStandbyMinute = (TextView) findViewById(R.id.standby_minute);
        mStandbyDay = (TextView) findViewById(R.id.standby_day);
        mBatteryRemainderIv =(ImageView) findViewById(R.id.battery_remainder_iv);
        
        //A by shubin {
        bt_normal = (RadioButton)findViewById(R.id.radiobt_one);
        bt_save = (RadioButton)findViewById(R.id.radiobt_two);
        bt_extreme = (RadioButton)findViewById(R.id.radiobt_three);
        mPowerManager = (PowerManager) getApplication().getSystemService(Context.POWER_SERVICE);
        
        
        bt_normal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				bt_save.setChecked(false);
				bt_extreme.setChecked(false);
				mPowerManager.setPowerSaveMode(false);
				Log.i("shubin", "bt_nomal is click");
				
			}
		});
        
        bt_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				bt_normal.setChecked(false);
				bt_extreme.setChecked(false);
				mHandlers.postDelayed(mStartMode, 500);
				Log.i("shubin", "bt_save is click");
			}
		});
        
        bt_extreme.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("shubin", "bt_extreme is click");
				bt_normal.setChecked(false);
				bt_save.setChecked(false);
				mPowerManager.setPowerSaveMode(false);
				startActivity(new Intent(getApplication(), UltraSwitchPromptActivity.class));
				
			}
		});
        //}
        //preferenceScreen = (PreferenceScreen)findPreference(SAVE_MODE_KEY);
        //powerSavingPref = (CustomSwitchPreference) findPreference(POWER_SAVING_KEY);
       // ultraPowerPref = (CustomSwitchPreference) findPreference(ULTRA_POWER_KEY);
       // if (powerSavingPref != null) {
        //	powerSavingPref.setOnPreferenceChangeListener(this);
        //}
       // ultraPowerPref.setOnPreferenceChangeListener(this);    
	}
	//A by shubin {
	 private final Runnable mStartMode = new Runnable() {
	        @Override
	        public void run() {
	            AsyncTask.execute(new Runnable() {
	                @Override
	                public void run() {
	                   // if (DEBUG) Log.d(TAG, "Starting low power mode from settings");
	                	trySetPowerSaveMode(true);
	                	Log.i("shubin", "setPowersave");
	                }
	            });
	        }
	    };
	    private void trySetPowerSaveMode(boolean mode) {
	        if (!mPowerManager.setPowerSaveMode(mode)) {
	            //if (DEBUG) Log.d(TAG, "Setting mode failed, fallback to current value");
	            mHandlers.post(mUpdateSwitch);
	        }
	    }
	    private final Runnable mUpdateSwitch = new Runnable() {
	        @Override
	        public void run() {
	            updateSwitch();
	        	
	        }
	    };
	    private void updateSwitch(){
	    	mIsUltraPowerOn = PowerSaveUtils.isUltraPowerOn(this);
			//A by shubin {
			final boolean mode = mPowerManager.isPowerSaveMode();
	        
	        if(mode){
	        	bt_save.setChecked(true);
	        	bt_normal.setChecked(false);
	        	bt_extreme.setChecked(false);
	        	
	        	Log.i("shubin", "save is checked");
	        }else if(mIsUltraPowerOn){
	        	bt_extreme.setChecked(true);
	        	bt_normal.setChecked(false);
	        	bt_save.setChecked(false);
	        	Log.i("shubin", "extreme is checked");
	        }else{
	        	bt_normal.setChecked(true);
	        	bt_extreme.setChecked(false);
	        	bt_save.setChecked(false);
	        	Log.i("shubin", "normal is checked");
	        }
	        //end
	    	
	    }
	    private final class SettingsObserver extends ContentObserver {
	        private final Uri LOW_POWER_MODE_TRIGGER_LEVEL_URI
	                = Global.getUriFor(Global.LOW_POWER_MODE_TRIGGER_LEVEL);

	        public SettingsObserver(Handler handler) {
	            super(handler);
	        }

	        @Override
	        public void onChange(boolean selfChange, Uri uri) {
	            if (LOW_POWER_MODE_TRIGGER_LEVEL_URI.equals(uri)) {
	               // mTriggerPref.update(mContext);
	            }
	        }

	        public void setListening(boolean listening) {
	            final ContentResolver cr = getContentResolver();
	            if (listening) {
	                cr.registerContentObserver(LOW_POWER_MODE_TRIGGER_LEVEL_URI, false, this);
	            } else {
	                cr.unregisterContentObserver(this);
	            }
	        }
	    }
	    //end
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "onResume");
		mSettingsObserver.setListening(true);
		
		registerReceiver(mBatteryInfoReceiver,
                new IntentFilter(PowerSaveUtils.ACTION_AVAILABLE_TIME));	
		mIsPowerSaveOn = PowerSaveUtils.isPowerSaveOn(this);
		mIsUltraPowerOn = PowerSaveUtils.isUltraPowerOn(this);
		//A by shubin 
		updateSwitch();
		//end
		Log.d(TAG, "onResume mIsPowerSaveOn : "+mIsPowerSaveOn+", mIsUltraPowerOn : "+mIsUltraPowerOn);
		if (powerSavingPref != null) {
			powerSavingPref.setChecked(mIsPowerSaveOn);
		}
		//ultraPowerPref.setChecked(mIsUltraPowerOn);
		getContentResolver().registerContentObserver(
				Settings.Global.getUriFor(Settings.Global.POWERSAVE_ON), true,
				mPowerSaveObserver);
		IntentFilter intetnFilter = new IntentFilter();
		intetnFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		intetnFilter.addAction(WifiManager.WIFI_AP_STATE_CHANGED_ACTION);
		intetnFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		registerReceiver(mReceiver, intetnFilter);
		isPowerSaveEnable = PowerSaveUtils.isPowerSaveEnable(getApplicationContext());
		if (powerSavingPref != null) {
			powerSavingPref.setEnabled(isSwitchEnd&&isPowerSaveEnable);
		}
	}
	private ContentObserver mPowerSaveObserver = new ContentObserver(
			new Handler()) {
		@Override
		public void onChange(boolean selfChange) {
			mIsPowerSaveOn = PowerSaveUtils.isPowerSaveOn(getApplicationContext());
			Log.d(TAG, "Main Observer mIsPowerSaveOn : "+mIsPowerSaveOn);
			if (powerSavingPref != null) {
				powerSavingPref.setChecked(mIsPowerSaveOn);
			}
			isSwitchEnd = false;
			if (powerSavingPref != null) {
				powerSavingPref.setEnabled(isSwitchEnd&&isPowerSaveEnable);
			}
			mHandler.removeMessages(ENABLE_SWITCH);
			mHandler.sendEmptyMessageDelayed(ENABLE_SWITCH, ENABLE_SWITCH_TIME);
		}
	};

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d(TAG, "onPause()");
		mSettingsObserver.setListening(true);
		unregisterReceiver(mBatteryInfoReceiver);
		getContentResolver().unregisterContentObserver(mPowerSaveObserver);
		unregisterReceiver(mReceiver);
		mHandler.removeMessages(ENABLE_SWITCH);
		isSwitchEnd = true;		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();			
		
		Log.d(TAG, "onDestroy");
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case android.R.id.home:
			finish();
			break;
		default:
		 break;
		}
		return super.onOptionsItemSelected(item);
	}
	 private BroadcastReceiver mBatteryInfoReceiver = new BroadcastReceiver() {

	        @Override
	        public void onReceive(Context context, Intent intent) {
	            String action = intent.getAction();
	            if (PowerSaveUtils.ACTION_AVAILABLE_TIME.equals(action)) {
	                Log.i(TAG, "ACTION_AVAILABLE_TIME");
	                int[] times = intent.getIntArrayExtra("available_time");
	                double battery = intent.getDoubleExtra("batteryLevel", 1);
	                mStandbyHour.setText(times[0]+"");
	                mStandbyMinute.setText(times[1]+"");
	                float angle =  (float) ((1-battery) * 360);
	                Drawable drawable = context.getResources().getDrawable(R.drawable.battery_remainder);	                
	        		BitmapDrawable bd = (BitmapDrawable)drawable;
	        		//mBitmap = bd.getBitmap();
	        		//mBitmap = getArcBitmap(PowerSaveMainActivity.this,mBitmap,angle);
	        		Bitmap bitmap = getArcBitmap(PowerSaveMainActivity.this,bd.getBitmap(),angle);
	        		if(bitmap!=null&&!bitmap.isRecycled()){
	        			mBatteryRemainderIv.setImageBitmap(bitmap);
	        		}
	                                
	            }
	        }
	    };
	    Bitmap outBitmap;
	    public Bitmap getArcBitmap(Context context,
				Bitmap sourceBitmap,float angle) {
			Log.d(TAG, "getArcBitmap angle:"+angle);
			recycleBitmap(outBitmap);
			outBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(),
					sourceBitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(outBitmap);
			final int color = 0xff6B23BE;
			final Paint paint = new Paint();
			final Rect ovalRect = new Rect(0, 0, sourceBitmap.getWidth(),
					sourceBitmap.getHeight());
			final RectF rectF = new RectF(ovalRect);
			canvas.drawColor(0x00000000);
			PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
			canvas.setDrawFilter(pfd);
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(color);
			canvas.drawArc(rectF, -90, angle, true, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
			if(sourceBitmap!=null&&!sourceBitmap.isRecycled()){
				canvas.drawBitmap(sourceBitmap, ovalRect, ovalRect, paint);
				return outBitmap;
			}else{
				return null;
			}		
			
		}	
	     void recycleBitmap(Bitmap bitmap) {
			// for example BookmarkThumbnailWidgetService.java
			if (bitmap != null&&!bitmap.isRecycled()){
				bitmap.recycle();
				Log.d(TAG, "recycleBitmap isRecycled:"+bitmap.isRecycled());
			}
		}
	 /**
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		if(POWER_SAVING_KEY.equals(preference.getKey())){
			startActivity(new Intent(this,PowerSavingModeActivity.class));
		}
		else if(ULTRA_POWER_KEY.equals(preference.getKey())){
//			startActivity(new Intent(this,UltraSwitchActivity.class));
		    startActivity(new Intent(this,UltraSwitchPromptActivity.class));
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		Log.d(TAG, "onPreferenceChange");
		if (POWER_SAVING_KEY.equals(preference.getKey())) {
			Log.d(TAG, "onPreferenceChange->newValue:"+newValue+",mIsPowerSaveOn:"+mIsPowerSaveOn);
			if (mIsPowerSaveOn != (Boolean) newValue)
			{
				mIsPowerSaveOn = (Boolean) newValue;
				PowerSaveUtils.setPowerSaveState(getApplicationContext(), mIsPowerSaveOn,false);

				PowerSaveUtils.cancleNotification(this);
			}
		}

		else if(ULTRA_POWER_KEY.equals(preference.getKey())){
			mIsUltraPowerOn = (Boolean) newValue;
			startActivity(new Intent(this,UltraSwitchPromptActivity.class));
		}
		return true;
	}
*/
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			//WifiManager.WIFI_STATE_CHANGED_ACTION
			//WifiManager.WIFI_AP_STATE_CHANGED_ACTION
			//BluetoothAdapter.ACTION_STATE_CHANGED
			Log.d(TAG, "action="+action);
			isPowerSaveEnable = PowerSaveUtils.isPowerSaveEnable(getApplicationContext());
			if (powerSavingPref != null) {
				powerSavingPref.setEnabled(isSwitchEnd&&isPowerSaveEnable);
			}
		}		
	};   
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Log.d(TAG, "mHandler ");
			switch (msg.what) {
			case ENABLE_SWITCH:
				isSwitchEnd = true;
				isPowerSaveEnable = PowerSaveUtils.isPowerSaveEnable(/*PowerSaveMainActivity.this*/getApplicationContext());
				if (powerSavingPref != null) {
					powerSavingPref.setEnabled(isSwitchEnd&&isPowerSaveEnable);
				}
				break;
			default:
				break;
			}
		}

	};
}
