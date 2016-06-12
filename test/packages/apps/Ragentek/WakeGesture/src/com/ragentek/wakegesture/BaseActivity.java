package com.ragentek.wakegesture;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public abstract class BaseActivity extends Activity{
	private  ImageView  iv_back;
	private  TextView   title_tv;
	private  ToggleButton  toggleButton_title;
	private  LinearLayout  ll_add;
	private  RelativeLayout  ll_title;
	public   LayoutInflater mLayoutInflater;
	private  TextView    tv_tips;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		iv_back=(ImageView) findViewById(R.id.back_iv_title);
		
		title_tv=(TextView)findViewById(R.id.tv_title);
		toggleButton_title=(ToggleButton) findViewById(R.id.toggle_title);
		tv_tips=(TextView) findViewById(R.id.switch_tips);
		mLayoutInflater=LayoutInflater.from(this);
		ll_add=(LinearLayout) findViewById(R.id.ll_add);
		ll_add.addView(getBelowView());
		ll_title=(RelativeLayout) findViewById(R.id.title_left);
		ll_title.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	protected ImageView getImageViewTitle() {
		return   iv_back;
	}
	protected TextView  getTextViewTitle(){
		return   title_tv;
	}
	protected ToggleButton getToggleButton(){
		return   toggleButton_title;
	}
	
	
	protected TextView  getTextViewTips(){
		return   tv_tips;
	}
	
	protected LinearLayout  getLlAdd(){
		return   ll_add;
	}
	
	protected void  setTitleText(int str_int) {
		title_tv.setText(str_int);
	}
	public abstract  View  getBelowView();

}
