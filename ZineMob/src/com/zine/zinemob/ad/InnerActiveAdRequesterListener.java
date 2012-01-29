package com.zine.zinemob.ad;

public interface InnerActiveAdRequesterListener {
	public void onAdDownloadSuccess(Ad ad, String clientId);
	public void onAdDownloadFail();
}
