package com.zine.zinemob.ad;

import com.zine.zinemob.text.xml.XmlParser;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;

/**
 * Requester for advertisements to the Inner-Active web API.
 */
public class InnerActiveAdRequester {
	
	public static final String DIST_CHANNEL_OVI_STORE = "519";
	public static final String APP_ID_TEST = "IA_GameTest";
	
	public void downloadAdAsync(final String appId, final String clientId, final String distChannel, final AdRequesterListener listener) {
		new Thread() {
			public void run() {
				Ad ad = downloadAd(appId, clientId, distChannel);
				if (ad == null) {
					listener.onAdDownloadFail();
				} else {
					listener.onAdDownloadSuccess(ad);
				}
			}
		}.start();
	}
	
	public Ad downloadAd(String appId, String clientId, String distChannel) {
		InnerActiveXmlResponse response = connectToInnerActiveApi(appId, clientId, distChannel);
		if (response.isOk()) {
			Ad ad = new Ad();
			ad.setText(response.getAd().getText());
			ad.setUrl(response.getAd().getUrl());
			ad.setImage(downloadImage(response.getAd().getImage()));
			return ad;
		} else {
			return null;
		}
	}
	
	public InnerActiveXmlResponse connectToInnerActiveApi(String appId, String clientId, String distChannel) {
		HttpConnection connection = null;
		InnerActiveXmlResponse response;
		try {
			String imei = System.getProperty("com.nokia.mid.imei");
			Form fakeForm = new Form("");
			
			StringBuffer url = new StringBuffer();
			url.append("http://m2m1.inner-active.com/simpleM2M/clientRequestAd?");
			url.append("aid=").append(appId);
			url.append("&po=").append(distChannel);
			url.append("&v=Sm2m-1.5.3");
			if (clientId != null) {
				url.append("&cid=").append(clientId);
			}
			if (imei != null) {
				url.append("&hid=").append(imei);
			}
			if (fakeForm.getWidth() > 0 && fakeForm.getHeight() > 0) {
				url.append("&w=").append(fakeForm.getWidth());
				url.append("&h=").append(fakeForm.getHeight());
			}
					
			connection = (HttpConnection) Connector.open(url.toString());
			connection.setRequestMethod(HttpConnection.GET);
			connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			
			response = new InnerActiveXmlResponse();
			if (connection.getResponseCode() == HttpConnection.HTTP_OK) {
				new XmlParser().parseInputStream(connection.openInputStream(), response);
			}
			
		} catch (Exception ex) {
			System.out.println("Error downloading ad: " + ex.toString());
			ex.printStackTrace();
			response = new InnerActiveXmlResponse();
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {
			}
		}
		return response;
	}
	
	private Image downloadImage(String url) {
		HttpConnection connection = null;
		InputStream inputStream = null;
		Image image = null;
		if (url.length() > 0) {
			try {
				connection = (HttpConnection) Connector.open(url);
				connection.setRequestMethod(HttpConnection.GET);
				connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
				if (connection.getResponseCode() == HttpConnection.HTTP_OK) {
					inputStream = connection.openInputStream();
					image = Image.createImage(inputStream);
				} else {
					throw new Exception("Can't download ad image: connection returned " + connection.getResponseCode());
				}
			} catch (Exception ex) {
				image = null;
			} finally {
				try {
					inputStream.close();
				} catch (Exception ex) {
				}
				try {
					connection.close();
				} catch (Exception ex) {
				}
			}
		}
		return image;
	}
}
