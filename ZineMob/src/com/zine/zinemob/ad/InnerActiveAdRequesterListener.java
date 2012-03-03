package com.zine.zinemob.ad;

/**
 * Listener used with InnerActiveAdRequester.
 */
public interface InnerActiveAdRequesterListener {
	
	/**
	 * Called when the advertisement is successfully downloaded.
	 * @param ad the downloaded advertisement
	 * @param response the original response of the connection
	 */
	public void onAdDownloadSuccess(Ad ad, InnerActiveResponse response);
	
	/**
	 * Called when the downloaded fails because an exceptionw was throwed.
	 * @param ex the exception
	 */
	public void onAdDownloadFail(Exception ex);
	
	/**
	 * Called when the download fails.
	 * @param response the unsuccessfull response
	 */
	public void onAdDownloadFail(InnerActiveResponse response);
}
