package com.zine.zinemob.ad;

import javax.microedition.lcdui.Image;

/**
 * Advertisement object.
 */
public class Ad {
	private String text = "";
	private String url = "";
	private Image image = null;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
