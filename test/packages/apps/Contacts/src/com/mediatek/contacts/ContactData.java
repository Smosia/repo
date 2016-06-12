package com.mediatek.contacts;

import java.util.ArrayList;


public class ContactData {
	protected ArrayList<String> phoneNum;
	protected ArrayList<String> email;
	protected String displayName;
	protected long contactId;
	protected String lookupKey;
	protected int hasPhoneNum;
	protected String accountName;
	protected int starred;
	protected int photoFileId;
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getAccountName() {
		return accountName;
	}

	public ArrayList<String> getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(ArrayList<String> phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	public ArrayList<String> getEmail() {
		return email;
	}
	
	public void setEmail(ArrayList<String> email) {
		this.email = email;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public long getContactId() {
		return contactId;
	}

	public void setConatctId(long contactId) {
		this.contactId = contactId;
	}

	public String getLookupKey() {
		return lookupKey;
	}
	
	public void setLookupKey(String lookupKey) {
		this.lookupKey = lookupKey;
	}
	
	public int getHasPhoneNum() {
		return hasPhoneNum;
	}
	
	public void setHasPhoneNum(int hasPhoneNum) {
		this.hasPhoneNum = hasPhoneNum;
	}
	
	public int getStarred() {
		return starred;
	}
	
	public void setStarred(int starred) {
		this.starred = starred;
	}

}