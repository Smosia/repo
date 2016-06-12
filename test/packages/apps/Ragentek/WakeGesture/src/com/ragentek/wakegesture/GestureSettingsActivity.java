package com.ragentek.wakegesture;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.ragentek.wakegesture.adapter.GestureListAdapter;
import com.ragentek.wakegesture.bean.KeyGestureFunction;
import com.ragentek.wakegesture.constant.KEY_EVENT_WAKE;
import com.ragentek.wakegesture.sqlite.SqliteManager;

import android.R.integer;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.os.ITpGesture;
import android.os.ServiceManager;
public class GestureSettingsActivity extends BaseActivity {
    
	private  ListView   listView_gesture;
	private  GestureListAdapter  gestureListAdapter;
	private  SqliteManager   sqliteManager;
	private  ArrayList<KeyGestureFunction>  keyFunctions;
	private boolean mLastEnable;
	private  boolean  isFirstChange=true;
	private  File  awakeFile=new  File("sys/class/syna/gesenable");
	private  Switch  switch_Gesture;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initControl();
	}
	
    private void  initControl(){
		//setTitleText(R.string.gesture_wake_configuration);
		//getToggleButton().setOnCheckedChangeListener(
				//gesture_checkedChangeListener);
		mLastEnable = (Settings.Global.getInt(getContentResolver(),
				Settings.Global.BLACK_GESTURE_WAKE, 0) == 1);
		setControlsVisible(mLastEnable);		
		//mLastEnable=getTpGestureEnable();
		//getToggleButton().setChecked(mLastEnable);
		//switch_Gesture=(Switch)getLayoutInflater()
                //.inflate(com.mediatek.internal.R.layout.imageswitch_layout,null);
		switch_Gesture=new  Switch(GestureSettingsActivity.this);		
		switch_Gesture.setPaddingRelative(0, 0, 18, 0);
		switch_Gesture.setChecked(mLastEnable);
		switch_Gesture.setOnCheckedChangeListener(
				gesture_checkedChangeListener);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(switch_Gesture, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER_VERTICAL | Gravity.END));
		sqliteManager = new SqliteManager(this);
		keyFunctions = sqliteManager.queryAll();
		listView_gesture = (ListView) findViewById(R.id.ll_listview);
		gestureListAdapter = new GestureListAdapter(
				GestureSettingsActivity.this,keyFunctions);
    	listView_gesture.setAdapter(gestureListAdapter);
    	listView_gesture.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(arg2>4){
					KeyGestureFunction keyFunction = gestureListAdapter
							.getKeyGestureFunctions().get(arg2 - 5);
					Intent intent = new Intent(GestureSettingsActivity.this,
							AppSelectActivity.class);
					intent.putExtra("keyCode", keyFunction.getKey());
					intent.putExtra("keyDescription",
							keyFunction.getKey_description());
					startActivity(intent);
				  }
			}
		});
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		   if (item.getItemId() == android.R.id.home) {
			   super.onBackPressed();
			   return true;
		   }
		   return super.onOptionsItemSelected(item);
	   }
    
    private OnCheckedChangeListener  gesture_checkedChangeListener=new  OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton arg0, final boolean arg1) {
			// TODO Auto-generated method stub
			    
				if (mLastEnable != arg1) {
					Builder builder = new AlertDialog.Builder(
							GestureSettingsActivity.this);
					builder.setOnDismissListener(new OnDismissListener() {

						@Override
						public void onDismiss(DialogInterface dialog) {
							// TODO Auto-generated method stub
							switch_Gesture.setChecked(mLastEnable);
						}
					});
					builder.setTitle(R.string.gesture_wake_configuration).setIcon(R.drawable.ic_settings_gesture_wake);
					if (arg1) {
						builder.setMessage(R.string.title_open_gesture_wake)
								.setPositiveButton(R.string.ok,
										new OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub
												Settings.Global
														.putInt(getContentResolver(),
																Settings.Global.BLACK_GESTURE_WAKE,
																1);
												setControlsVisible(true);
												setTpGesture(true);
												mLastEnable = arg1;
											}
										})
								.setNegativeButton(R.string.cancel,
										new OnClickListener() {

											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {
												// TODO Auto-generated method
												// stub

											}
										});
						builder.create().show();

					} else {

						Settings.Global.putInt(getContentResolver(),
								Settings.Global.BLACK_GESTURE_WAKE, 0);
								setControlsVisible(false);
						setTpGesture(false);
						mLastEnable = arg1;

					}

				}
			
		}
	};
    
	private  void  setTpGesture(boolean enable){
		try {
			ITpGesture tp = ITpGesture.Stub.asInterface(ServiceManager.getService("tpgesture"));
			tp.setTpGestureEnabled(enable);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(null);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		keyFunctions = sqliteManager.queryAll();
		gestureListAdapter.setKeyGestureFunctions(keyFunctions);
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public View getBelowView() {
		// TODO Auto-generated method stub
		View  view=mLayoutInflater.inflate(R.layout.ll_listview, null);
		return view;
	}
	
	private  void setControlsVisible(boolean  isChecked){
		getTextViewTips().setVisibility(isChecked ? View.GONE:View.VISIBLE);
		getLlAdd().setVisibility(isChecked ? View.VISIBLE:View.GONE);
	}

	
}
