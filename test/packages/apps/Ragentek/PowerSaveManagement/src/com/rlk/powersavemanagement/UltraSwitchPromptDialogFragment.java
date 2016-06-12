package com.rlk.powersavemanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class UltraSwitchPromptDialogFragment extends DialogFragment{
   private static final String TAG = "PowerSave/UltraSwitchPromptDialogFragment";
   private OnSwitchDialogClickListener mListener = null;
   private Dialog mDialog = null;
	
   public UltraSwitchPromptDialogFragment(){};
   public static UltraSwitchPromptDialogFragment newInstance(){
	   return new UltraSwitchPromptDialogFragment();
   }
   
   public interface OnSwitchDialogClickListener{
	   void onSwitchDialogClick(boolean isOn);
   }
   
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
	         mListener = (OnSwitchDialogClickListener) activity;
	     } catch (ClassCastException e) {
	         e.printStackTrace();
	     }
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    super.onCreateDialog(savedInstanceState);
		Context context = getActivity();
		View view = View.inflate(context, R.layout.ultra_switch_dialog, null);
		mDialog = new AlertDialog.Builder(context)
		.setTitle(R.string.ultra_power_saving)
		.setView(view)
		.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (null != mListener) {
                    mListener.onSwitchDialogClick(true);
                }
				dismissAllowingStateLoss();
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (null != mListener) {
                    mListener.onSwitchDialogClick(false);
                }
				dismissAllowingStateLoss();
			}
		}).create();
		return mDialog;
		
	}
      
}
