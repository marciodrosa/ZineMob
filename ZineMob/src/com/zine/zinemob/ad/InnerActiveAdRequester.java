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
	
	/**
	 * Do a request to the Inner-Active web API. This method blocks until it gets
	 * the response.
	 * @param appId the app ID
	 * @param clientId the client Id, can be null
	 * @param distChannel the dist channel
	 * @return the response
	 */
	public InnerActiveXmlResponse requestAd(String appId, String clientId, String distChannel) {
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
	
	/**
	 * Do a request to the Inner-Active web API in a parallel thread.
	 * @param appId the app ID
	 * @param clientId the client Id, can be null
	 * @param distChannel the dist channel
	 * @param listener the listener to callback
	 */
	public void requestAdAsync(final String appId, final String clientId, final String distChannel, final InnerActiveAdRequesterListener listener) {
		new Thread() {
			public void run() {
				InnerActiveXmlResponse response = requestAd(appId, clientId, distChannel);
				Ad ad = parseInnerActiveXmlResponse(response);
				if (ad == null) {
					listener.onAdDownloadFail();
				} else {
					listener.onAdDownloadSuccess(ad, response.getClientId());
				}
			}
		}.start();
	}
	
	/**
	 * Parses the XML response into an Ad object. This method can connect to internet
	 * to download the Ad image.
	 * @param xmlResponse the response
	 * @return an Ad object or null if the response is not ok
	 */
	public Ad parseInnerActiveXmlResponse(InnerActiveXmlResponse xmlResponse) {
		if (xmlResponse.isOk()) {
			Ad ad = new Ad();
			ad.setText(xmlResponse.getAd().getText());
			ad.setUrl(xmlResponse.getAd().getUrl());
			ad.setImage(downloadImage(xmlResponse.getAd().getImage()));
			return ad;
		} else {
			return null;
		}
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
					image = null;
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
