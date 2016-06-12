package com.mediatek.contacts;

import java.util.ArrayList;

public class MergerData {
	protected ArrayList<ContactData> mList;
	protected ContactData data;

	public ArrayList<ContactData> getList() {
		return mList;
	}

	public void setList(ArrayList<ContactData> mList) {
		this.mList = mList;
	}

	public ContactData getData() {
		return data;
	}

	public void setData(ContactData data) {
		this.data = data;
	}

}