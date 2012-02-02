package com.zine.zinemob.ad;

import com.zine.zinemob.text.xml.XmlAttributes;
import com.zine.zinemob.text.xml.XmlResource;

/**
 * XML response of the request for the web API of Inner-Active.
 */
public class InnerActiveXmlResponse extends XmlResource {
	
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
	
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public Ad getAd() {
		return ad;
	}

	public void setAd(Ad ad) {
		this.ad = ad;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
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

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}
	}
}