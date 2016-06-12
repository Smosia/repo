package com.rgk.n2s;

/**
 * @author kezhu.xiang
 * @date 20160114
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
@SuppressLint("ShowToast") public class MainActivity extends Activity implements View.OnClickListener {
	public static String TAG = "N2S_TEST";
	EditText editText1;
	Button settingButton, button1, button2, button3,button4,button5,button6;
	private AudioManager mAudioManager;
	private Vibrator mVibrator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

	    //getWindow().getAttributes().privateFlags |= WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED;
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		new Util(MainActivity.this).initValue();
		setTitle(R.string.app_name);
		setContentView(R.layout.activity_main);
		LinearLayout mLayout = (LinearLayout) findViewById(R.id.main_layout);
		mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		mVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		editText1 = (EditText)this.findViewById(R.id.editText1);
		settingButton = (Button) this.findViewById(R.id.settingButton);
		button1 = (Button) this.findViewById(R.id.button1);
		button2 = (Button) this.findViewById(R.id.button2);
	    button3 = (Button) this.findViewById(R.id.button3);
	    button4 = (Button) this.findViewById(R.id.button4);
	    button5 = (Button) this.findViewById(R.id.button5);
	    button6 = (Button) this.findViewById(R.id.button6);
	    editText1.setFocusable(true);
	    settingButton.setOnClickListener(this);
	    
	    button1.setOnClickListener(this);
	    button2.setOnClickListener(this);
	    button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        int nv = NvRAMAgentHelper.readNVData(NvRAMAgentHelper.PSENSOR_INDEX);
        Log.d(TAG, "nv = " + nv);
        editText1.setText(String.valueOf(nv));

	}
    @Override
    public void onClick(View v) {
        int id = v.getId();
        String messages;
        String vibrateStrengthTitle = getResources().getString(R.string.vibrateStrengthTitle);
        switch(id){
        case R.id.settingButton:
            final String hzValue = editText1.getText().toString();
            Log.d(TAG, "hzValue = " + hzValue);
            setVibspkzHz(hzValue);
            NvRAMAgentHelper.writeNVData(NvRAMAgentHelper.PSENSOR_INDEX, Integer.parseInt(hzValue));
            break;
        case R.id.button1:
            messages = getResources().getString(R.string.buttonText1);
            NvRAMAgentHelper.writeNVData(NvRAMAgentHelper.PSENSOR_INDEX_GAIN, 3);
            createDialogs(vibrateStrengthTitle,getResources().getString(R.string.vibrate_strength_confirm_text,messages),"3");
            break;
        case R.id.button2:
            messages = getResources().getString(R.string.buttonText2);
            NvRAMAgentHelper.writeNVData(NvRAMAgentHelper.PSENSOR_INDEX_GAIN, 4);
            createDialogs(vibrateStrengthTitle,getResources().getString(R.string.vibrate_strength_confirm_text,messages),"4");

            break;
        case R.id.button3:
            messages = getResources().getString(R.string.buttonText3);
            NvRAMAgentHelper.writeNVData(NvRAMAgentHelper.PSENSOR_INDEX_GAIN, 5);
            createDialogs(vibrateStrengthTitle,getResources().getString(R.string.vibrate_strength_confirm_text,messages),"5");
            break;
        case R.id.button4:
            messages = getResources().getString(R.string.buttonText4);
            NvRAMAgentHelper.writeNVData(NvRAMAgentHelper.PSENSOR_INDEX_GAIN, 6);
            createDialogs(vibrateStrengthTitle,getResources().getString(R.string.vibrate_strength_confirm_text,messages),"6");
            break;
        case R.id.button5:
            messages = getResources().getString(R.string.buttonText5);
            NvRAMAgentHelper.writeNVData(NvRAMAgentHelper.PSENSOR_INDEX_GAIN, 7);
            createDialogs(vibrateStrengthTitle,getResources().getString(R.string.vibrate_strength_confirm_text,messages),"7");
            break;
        case R.id.button6:
            mVibrator.vibrate(1000);
            break;
        
        }
        
    }
    void setVibspkzHz(final String hzValue){
        String setVinspkHzValue =  getResources().getString(R.string.set_vibspk_hz_value,hzValue);
        if(hzValue.isEmpty() || Integer.valueOf(hzValue) < 150 || Integer.valueOf(hzValue) > 200 ){
            new AlertDialog.Builder(this).setMessage(R.string.set_vibspk_hz_error).setTitle(R.string.set_vibspk_hz).setPositiveButton(R.string.confirm, null).show();
        }else{
            new AlertDialog.Builder(this).setMessage(setVinspkHzValue).setTitle(R.string.set_vibspk_hz).setPositiveButton(R.string.confirm, new OnClickListener(){

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    mAudioManager.setParameters("SET_VIBSPK_HZ="+hzValue);
                    Log.d(TAG, "mAudioManager.getParameters = " + mAudioManager.getParameters("SET_VIBSPK_HZ"));
                    String textToast = getResources().getString(R.string.toastText);
                    Toast toast = Toast.makeText(getApplicationContext(),textToast , Toast.LENGTH_SHORT);
                    toast.show();
                }
                
            }).setNegativeButton(R.string.cancel,null).show();
            
            }
    }
    private void createDialogs(String title,String message, final String level){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setPositiveButton(R.string.confirm, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                    mAudioManager.setParameters("SET_VIBSPK_GAIN="+level);
                String textToast = getResources().getString(R.string.toastText);
                Toast toast = Toast.makeText(getApplicationContext(),textToast , Toast.LENGTH_SHORT);
                toast.show();
                dialog.dismiss();
                //finish();
            }});
        builder.setNegativeButton(R.string.cancel, null);
        builder.setMessage(message);
        builder.create().show();
    }
	@Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
	public void onDestroy() {
		super.onDestroy();
	}	
	
}
