package com.zine.zinemob.ad;

import com.zine.zinemob.text.xml.XmlAttributes;
import com.zine.zinemob.text.xml.XmlResource;

/**
 * Response of the request for the web API of Inner-Active. It contains the XML
 * response and the HTTP response code.
 */
public class InnerActiveResponse extends XmlResource {
	
	private int httpResponseCode;
	private boolean ok = false;
	private String clientId = "";
	private Ad ad = new Ad();

	public XmlResource readXmlTag(String name, String text, XmlAttributes attributes) {
		if (name.equals("tns:Response")) {
			this.ok = attributes.get("Error", "").equals("OK");
			return this;
		} else if (name.equals("tns:Ad")) {
			return this.ad;
		} else if (name.equals("tns:Client")) {
			clientId = attributes.get("Id", "");
			return null;
		} else {
			return null;
		}
	}
	
	/**
	 * Returns if the response returns "ok". If true, the http response code is always 200.
	 */
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * Returns the Ad object.
	 */
	public Ad getAd() {
		return ad;
	}

	public void setAd(Ad ad) {
		this.ad = ad;
	}

	/**
	 * Returns the client ID, that an be used in next requesters.
	 */
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * Returns the http response code of the connection.
	 */
	public int getHttpResponseCode() {
		return httpResponseCode;
	}

	public void setHttpResponseCode(int httpResponseCode) {
		this.httpResponseCode = httpResponseCode;
	}
	
	/**
	 * Ad data.
	 */
	public class Ad extends XmlResource {
		private String text = "";
		private String url = "";
		private String image = "";

		public XmlResource readXmlTag(String name, String text, XmlAttributes attributes) {
			if (name.equals("tns:Text")) {
				this.text = text;
			} else if (name.equals("tns:URL")) {
				this.url = text;
			} else if (name.equals("tns:Image")) {
				this.image = text;
			}
			return null;
		}

		/**
		 * Returns the text of the ad.
		 */
		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		/**
		 * Returns the URL of the ad, that should be opened when the user clicks
		 * in the ad.
		 */
		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		/**
		 * Returns the URL to download the image of the ad.
		 */
		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}
	}
}
