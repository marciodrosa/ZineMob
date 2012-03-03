package com.zine.zinemob.ad;

import com.zine.zinemob.text.TextUtils;
import com.zine.zinemob.text.xml.XmlParser;
import java.io.IOException;
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
	public InnerActiveResponse requestAd(String appId, String clientId, String distChannel) throws IOException {
		System.out.println("Requesting ad to Inner-Active...");
		HttpConnection connection = null;
		InputStream inputStream = null;
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
			
			InnerActiveResponse response = new InnerActiveResponse();
			response.setHttpResponseCode(connection.getResponseCode());
			
			System.out.println("Request to Inner-Active returns response code " + response.getHttpResponseCode());
			
			if (response.getHttpResponseCode() == HttpConnection.HTTP_OK) {
				inputStream = connection.openInputStream();
				readResponse(inputStream, response);
				
				System.out.println("Request to Inner-Active downloads the response.");
			}
			return response;
		} catch (IOException ex) {
			System.out.println("Request to Inner-Active failed. Exception: " + ex.toString());
			throw ex;
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {
			}
			try {
				inputStream.close();
			} catch (Exception ex) {
			}
		}
	}
	
	private void readResponse(InputStream inputStream, InnerActiveResponse response) {
		String xmlAsString = TextUtils.decodeUrl(TextUtils.readTextFromInputStream(inputStream, "UTF-8"));
		System.out.println("AD response received:\n" + xmlAsString);
		new XmlParser().parseString(xmlAsString, response);
	}
	
	/**
	 * Do a request to the Inner-Active web API. This method blocks until it gets
	 * the response.
	 * @param appId the app ID
	 * @param clientId the client Id, can be null
	 * @param distChannel the dist channel
	 * @param listener the listener to callback
	 */
	public void requestAd(String appId, String clientId, String distChannel, final InnerActiveAdRequesterListener listener) {
		InnerActiveResponse response = null;
		try {
			response = requestAd(appId, clientId, distChannel);
		} catch (Exception ex) {
			listener.onAdDownloadFail(ex);
		}
		if (response != null) {
			if (response.isOk() && response.getHttpResponseCode() == HttpConnection.HTTP_OK) {
				Ad ad = parseInnerActiveXmlResponse(response);
				if (ad != null) {
					listener.onAdDownloadSuccess(ad, response);
				} else {
					listener.onAdDownloadFail(response);
				}
			} else {
				listener.onAdDownloadFail(response);
			}
		}
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
				requestAd(appId, clientId, distChannel, listener);
			}
		}.start();
	}
	
	/**
	 * Parses the XML response into an Ad object. This method can connect to internet
	 * to download the Ad image.
	 * @param xmlResponse the response
	 * @return an Ad object or null if the response is not ok
	 */
	public Ad parseInnerActiveXmlResponse(InnerActiveResponse xmlResponse) {
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
					System.out.println("Fail to download Ad image at " + connection.getURL());
					System.out.println("Response code of Ad image download: " + connection.getResponseCode());
					image = null;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
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
