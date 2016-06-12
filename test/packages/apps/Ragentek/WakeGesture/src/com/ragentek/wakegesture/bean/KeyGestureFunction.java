package com.ragentek.wakegesture.bean;

import java.io.Serializable;

import android.R.integer;

public class KeyGestureFunction implements Serializable{
       
	private int  key;
	private String  key_description;
	private int  action_type;
	private String  information_one;
	private String  information_second;
	private String  information_three;
	
	
	public KeyGestureFunction(int key, String key_description, int action_type,
			String information_one, String information_second,
			String information_three) {
		super();
		this.key = key;
		this.key_description = key_description;
		this.action_type = action_type;
		this.information_one = information_one;
		this.information_second = information_second;
		this.information_three = information_three;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public int getAction_type() {
		return action_type;
	}
	public void setAction_type(int action_type) {
		this.action_type = action_type;
	}
	public String getInformation_one() {
		return information_one;
	}
	public void setInformation_one(String information_one) {
		this.information_one = information_one;
	}
	public String getInformation_second() {
		return information_second;
	}
	public void setInformation_second(String information_second) {
		this.information_second = information_second;
	}
	public String getKey_description() {
		return key_description;
	}
	public void setKey_description(String key_description) {
		this.key_description = key_description;
	}
	public String getInformation_three() {
		return information_three;
	}
	public void setInformation_three(String information_three) {
		this.information_three = information_three;
	}
	
	
}
