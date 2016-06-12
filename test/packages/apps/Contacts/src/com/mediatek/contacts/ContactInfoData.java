package com.mediatek.contacts;

import java.util.ArrayList;
import android.graphics.Bitmap;
import java.sql.Struct;

public class ContactInfoData {
	String data1;
	int data2;
	String data3;
	String data4;
	String data5;
	String data6;
	String data7;
	String data8;
	String data9;
	byte[] p = null;
	int photoFileId;
	
	
	public void setData(int no, String data) {
		switch(no) {
		case 1: 
			this.data1 = data;
			break;
		case 3:
			this.data3 = data;
			break;
		case 4:
			this.data4 = data;
			break;
		case 5:
			this.data5 = data;
			break;
		case 6:
			this.data6 = data;
			break;
		case 7:
			this.data7 = data;
			break;
		case 8:
			this.data8 = data;
			break;
		case 9:
			this.data9 = data;
			break;
		}
	}
	
	public String getData(int no) {
		switch(no) {
		case 1:
			return data1;
		case 3:
			return data3;
		case 4:
			return data4;
		case 5:
			return data5;
		case 6:
			return data6;
		case 7:
			return data7;
		case 8:
			return data8;
		case 9:
			return data9;
		}
		return null;
	}
	
	
	public void setData2(int data2){
		this.data2 = data2;
	}
	
	public int getData2(){
		return data2;
	}
	
	public void setPhoto(byte[] p) {
		this.p = p;
	}
	public byte[] getPhoto(){
		return p;
	}
	
	public void setPhotoFileId(int id) {
		photoFileId = id;
	}
	
	public int getPhotoFileId() {
		return photoFileId;
	}
	
	public String toString(){
		return "data1:" + this.data1 + '\n' +
		       "data2:" + this.data2 + '\n' +
		       "data3:" + this.data3 + '\n' +
		       "data4:" + this.data4 + '\n' +
		       "data5:" + this.data5 + '\n' +
		       "data6:" + this.data6 + '\n' +
		       "data7:" + this.data7 + '\n' +
		       "data8:" + this.data8 + '\n' +
		       "data9:" + this.data9 + '\n';
	}
	
}