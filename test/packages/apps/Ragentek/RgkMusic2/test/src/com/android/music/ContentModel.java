package com.android.music;

public class ContentModel {

	//private int imageView;
	private int text;
	
	/*public ContentModel(int imageView, int text) {
		super();
		this.imageView = imageView;
		this.text = text;
	}*/
	public ContentModel(int text) {
		super();
		//this.imageView = imageView;
		this.text = text;
	}

	/*public int getImageView() {
		return imageView;
	}

	public void setImageView(int imageView) {
		this.imageView = imageView;
	}*/

	public int getText() {
		return text;
	}

	public void setText(int text) {
		this.text = text;
	}

}

