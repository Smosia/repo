package com.rgk.factory;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Window;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.view.Display;

public class ItemTestActivity extends Activity implements OnItemClickListener{
	private static String TAG = "ItemTestActivity";
	WindowManager wm;
	Display display;
	public static int mWidth;
	public static int mHeight;
	GridView gridView;
	GridAdapter gridAdapter;
	
	private CitService mCitService = null;
	private ServiceConnection mCitBackService = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			Log.e(TAG, "onServiceConnected");
			mCitService = ((CitService.LocalBinder) service).getService();
			if (mCitService == null) {
				Log.e(TAG, "build failed");
			} else {
				sendBroadcast(new Intent("start_in_factory_mode"));
				Log.e(TAG, "sendBroadcast");
			}
		}

		public void onServiceDisconnected(ComponentName className) {
			Log.e(TAG, "onServiceDisconnected");
			mCitService = null;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
	       // M: huangkunming 2014.12.20 @{
	       // since window flags full, change FLAG_HOMEKEY_DISPATCHED to privateFlags
	    	//getWindow().addFlags(WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED);
	    	getWindow().getAttributes().privateFlags |= WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED;
		// @}
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_item_test);
		LinearLayout mLayout = (LinearLayout) findViewById(R.id.item_test_layout);
	    mLayout.setSystemUiVisibility(0x00002000);
	    
		gridView = (GridView)findViewById(R.id.gridview);
		gridAdapter = new GridAdapter(this);
		gridView.setAdapter(gridAdapter);
		gridView.setOnItemClickListener(this);
		wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		display = (Display) wm.getDefaultDisplay();
		mWidth = display.getWidth();
		mHeight = display.getHeight();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Config.ItemOnClick);
		//registerReceiver(mBroadcastReceiver, filter);

		// startService(new Intent(this,CitService.class));
		bindService(new Intent(this, CitService.class), mCitBackService,
				Context.BIND_AUTO_CREATE);
		Log.e(TAG, "build success");

	}
	
	@Override
	protected void onResume() {
		gridView.invalidateViews();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		unbindService(mCitBackService);
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i(TAG,"onItemClick:pos="+position+" class="+ Util.singleTestClass.get(position));
		startActivity(new Intent(this,Util.singleTestClass.get(position)));
	}
}