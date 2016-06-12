package com.mediatek.contacts;

import java.util.ArrayList;
import android.graphics.Bitmap;

public class ContactInfoDataSet {
	protected ArrayList<ContactInfoData> numList;
	protected ArrayList<ContactInfoData> emailList;
	protected ArrayList<ContactInfoData> addressList;
	
	protected ContactInfoData im;
	protected ContactInfoData nick;
	//protected ArrayList<ContactInfoData> identityList;
	protected ContactInfoData web;
	protected ContactInfoData note;
	
	protected ContactInfoData photo;
	
	int rawContactId;
	
	public ContactInfoData getPhoto(){
		return photo;
	}
	
	public void setPhoto(ContactInfoData photo) {
		this.photo = photo;
	}
	
	//number
	public ArrayList<ContactInfoData> getNumlist() {
		return numList;
	}

	public void setNumlist(ArrayList<ContactInfoData> numList) {
		this.numList = numList;
	}

	//email
	public ArrayList<ContactInfoData> getEmaillist() {
		return emailList;
	}

	public void setEmaillist(ArrayList<ContactInfoData> emailList) {
		this.emailList = emailList;
	}
	
	//address
	public ArrayList<ContactInfoData> getAddresslist() {
		return addressList;
	}

	public void setAddresslist(ArrayList<ContactInfoData> addressList) {
		this.addressList = addressList;
	}
	
	//im
	public ContactInfoData getIm() {
		return im;
	}
	
	public void setIm(ContactInfoData im) {
		this.im = im;
	}
	
	//nickname
	public ContactInfoData getNick() {
		return nick;
	}
	
	public void setNick(ContactInfoData nick) {
		this.nick = nick;
	}
	
	//website
	public ContactInfoData getWeb() {
		return web;
	}
	
	public void setWeb(ContactInfoData web) {
		this.web = web;
	}
	
	//note
	public ContactInfoData getNote() {
		return note;
	}
	
	public void setNote(ContactInfoData note) {
		this.note = note;
	}
	
	public void setRawContactId(int rawContactId) {
		this.rawContactId = rawContactId;
	}
	
	public int getRawContactId() {
		return rawContactId;
	}
	
	public String toString() {
		return "numList:" + this.numList.toString() + '\n' +
			   "emailList:" + this.emailList.toString() + '\n' +
			   "addressList:" + this.addressList.toString() + '\n' +
			   "im:" + this.im.toString() + '\n' +
			   "note:" + this.note.toString() + '\n' + 
			   "nick:" + this.nick.toString() + '\n' +
			   "web:" + this.web.toString() + '\n' +
			   "photo:" + this.photo.toString();
	}
	
}