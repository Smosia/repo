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

public class PowerSavePromptDialogFragment extends DialogFragment implements OnCheckedChangeListener {
   private static final String TAG = "PowerSave/PowerSaveSettingsDialogFragment";
   private CheckBox mTipCheckBox;
   private boolean mIsNoAsk = false;
   private OnSlectDialogClickListener mListener = null;
   private Dialog mDialog = null;
	
   public PowerSavePromptDialogFragment(){};
   public static PowerSavePromptDialogFragment newInstance(){
	   return new PowerSavePromptDialogFragment();
   }
   
   public interface OnSlectDialogClickListener{
	   void onSelectDialogClick(boolean isChecked);
   }
   
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
	         mListener = (OnSlectDialogClickListener) activity;
	     } catch (ClassCastException e) {
	         e.printStackTrace();
	     }
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    super.onCreateDialog(savedInstanceState);
		Context context = getActivity();
		View view = View.inflate(context, R.layout.power_save_setting_dialog, null);
		mTipCheckBox = (CheckBox) view.findViewById(R.id.settings_tip);
		mTipCheckBox.setOnCheckedChangeListener(this);
		mDialog = new AlertDialog.Builder(context)
		.setTitle(R.string.power_save_title)
		.setView(view)
		.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (null != mListener) {
                    mListener.onSelectDialogClick(!mIsNoAsk);
                }
				dismissAllowingStateLoss();
			}
		})
		.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (null != mListener) {
                    mListener.onSelectDialogClick(true);
                }
                dismissAllowingStateLoss();
			}
		}).create();
		return mDialog;
		
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		mIsNoAsk = isChecked;
	}
      
}
