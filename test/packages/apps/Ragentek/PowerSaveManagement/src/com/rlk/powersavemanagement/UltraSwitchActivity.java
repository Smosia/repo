package com.rlk.powersavemanagement;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class UltraSwitchActivity extends Activity implements OnClickListener,UltraSwitchPromptDialogFragment.OnSwitchDialogClickListener{
    private static final String TAG = "PowerSave/UltraSwitchActivity";
    private Button mOpenBtn;
    private FragmentManager mFragmentManager = null;
    private static final String PROMPT_ULTRA_SWITCH = "PromptUltraSwitch";
    private TextView screenText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionbar = getActionBar();
        if(actionbar!=null){
        	actionbar.setHomeButtonEnabled(true);
    		/*actionbar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
    				| ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE
    				| ActionBar.DISPLAY_USE_LOGO);*/
        	actionbar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
    		actionbar.setDisplayHomeAsUpEnabled(true);
        }
        mFragmentManager = getFragmentManager();
        setContentView(R.layout.ultra_power_setting);
        mOpenBtn = (Button) findViewById(R.id.ultra_open_btn);
        mOpenBtn.setOnClickListener(this);
        screenText = (TextView)findViewById(R.id.ultra_power_instructions_screen_id);
	    if(PowerSaveUtils.isSupportScreenMode(this)){
	    	screenText.setVisibility(View.VISIBLE);	
	    }
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// android.R.id.home;
		switch(item.getItemId()){
		case android.R.id.home:
			finish();
			break;
		default:
		 break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		dismissPromptDialog();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//showPromptDialog();
		startActivity(new Intent(this,UltraSwitchPromptActivity.class));
	}
	private void showPromptDialog(){
		UltraSwitchPromptDialogFragment newFragment = UltraSwitchPromptDialogFragment.newInstance();
        newFragment.show(mFragmentManager, PROMPT_ULTRA_SWITCH);
        mFragmentManager.executePendingTransactions();
    }
    private void dismissPromptDialog() {
    	UltraSwitchPromptDialogFragment newFragment = (UltraSwitchPromptDialogFragment) mFragmentManager
				.findFragmentByTag(PROMPT_ULTRA_SWITCH);
		if (null != newFragment) {
			newFragment.dismissAllowingStateLoss();
		}
	}
	@Override
	public void onSwitchDialogClick(boolean isOn) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onSwitchDialogClick");
		PowerSaveUtils.setUltraPowerState(UltraSwitchActivity.this,isOn);
	}
     
}
