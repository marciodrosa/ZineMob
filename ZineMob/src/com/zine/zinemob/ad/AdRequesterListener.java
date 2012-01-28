package com.zine.zinemob.ad;

public interface AdRequesterListener {
	public void onAdDownloadSuccess(Ad ad);
	public void onAdDownloadFail();
}
