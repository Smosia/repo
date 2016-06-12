package com.rgk.phonemanager.view.util;

public class FuncBtnItem {
	private int id;
	private int stringRes;
	private int imgRes;
	
	public FuncBtnItem(int id, int stringRes, int imgRes) {
		super();
		this.id = id;
		this.stringRes = stringRes;
		this.imgRes = imgRes;
	}

	public int getId() {
		return id;
	}

	public int getStringRes() {
		return stringRes;
	}

	public int getImgRes() {
		return imgRes;
	}
	
}
