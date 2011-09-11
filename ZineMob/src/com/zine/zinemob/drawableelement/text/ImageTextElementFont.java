package com.zine.zinemob.drawableelement.text;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Image;

public class ImageTextElementFont {
	
	private Image image;
	private int[] metrics;
	
	public ImageTextElementFont(Image image, int[] metrics) {
		this.image = image;
		this.metrics = metrics;
	}
	
	public ImageTextElementFont(String imageResourceName, String metricsResourceName) {
		
		InputStream metricsInputStream = null;
		
		try {
			this.metrics = new int[256];
			metricsInputStream = getClass().getResourceAsStream(metricsResourceName);
			if (metricsInputStream == null) {
				System.out.println ("Font metrics " + metricsResourceName + " not found.");
				return;
			}
			for (int i=0; i<metrics.length; i++) {
				metrics[i] = metricsInputStream.read();
			}
		} catch (IOException e) {
			System.out.println("Error while reading font metrics " + metricsResourceName + ": " + e.toString());
			e.printStackTrace();
		} finally {
			try {
				metricsInputStream.close();
			} catch (Exception ex) {
			}
		}

		try {
			image = Image.createImage(imageResourceName);
		} catch (IOException e) {
			System.out.println("Error while reading font image " + imageResourceName + ": " + e.toString());
			e.printStackTrace();
		}
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int[] getMetrics() {
		return metrics;
	}

	public void setMetrics(int[] metrics) {
		this.metrics = metrics;
	}
	
}
