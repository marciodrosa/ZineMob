//package com.zine.zinemob.oauth;
//
//import com.zine.zinemob.text.Pair;
//import com.zine.zinemob.text.TextUtils;
//import java.util.Vector;
//
///**
// * Generates a OAuth header to authenticate http connections (can be use on Twitter API,
// * for example). This header is time stamped, so it must be used right after it is
// * created.
// */
//public class OAuthHeader {
//	
//	private String header;
//	
//	/**
//	 * @param httpMethod the http method, like GET or POST
//	 * @param url the URL, without parameters, should not be URL encoded
//	 * @param parameters the URL parameters, should not be URL encoded, can be null
//	 * @param post the post parameters, should not be URL encoded, can be null
//	 * @param consumerKey the consumer key
//	 * @param nonce the nonce (current device key)
//	 * @param token the token
//	 */
//	public OAuthHeader(String httpMethod, String url, Pair[] parameters, Pair[] post, String consumerKey, String nonce, String token, String consumerSecret, String tokenSecret) {
//		Pair consumerKeyPair = new Pair("oauth_consumer_key", "");
//		Pair noncePair = new Pair("oauth_nonce", nonce);
//		Pair signatureMethodPair = new Pair("oauth_signature_method", "HMAC-SHA1");
//		Pair timeStampPair = new Pair("oauth_timestamp", String.valueOf(System.currentTimeMillis()/1000));
//		Pair tokenPair = new Pair("oauth_token", "");
//		Pair oauthVersionPair = new Pair("oauth_version", "1.0");
//		
//		String signature = createSignature(httpMethod, url, parameters, post, new Pair[] {consumerKeyPair, noncePair, signatureMethodPair, timeStampPair, tokenPair, oauthVersionPair}, consumerSecret, tokenSecret);
//		Pair signaturePair = new Pair("oauth_signature", signature);
//		
//		StringBuffer buffer = new StringBuffer();
//		buffer.append("OAuth ");
//		buffer.append(consumerKeyPair.toString()).append(", ");
//		buffer.append(noncePair.toString()).append(", ");
//		buffer.append(signaturePair.toString()).append(", ");
//		buffer.append(signatureMethodPair.toString()).append(", ");
//		buffer.append(timeStampPair.toString()).append(", ");
//		buffer.append(tokenPair.toString()).append(", ");
//		buffer.append(oauthVersionPair.toString());
//		header = buffer.toString();
//	}
//	
//	private String createSignature(String httpMethod, String url, Pair[] parameters, Pair[] post, Pair[] oAuthHeader, String consumerSecret, String tokenSecret) {
//		StringBuffer buffer = new StringBuffer();
//		buffer.append(httpMethod.toUpperCase());
//		buffer.append('&');
//		buffer.append(TextUtils.urlEncode(url));
//		buffer.append('&');
//		buffer.append(TextUtils.urlEncode(createSignatureParameterString(parameters, post, oAuthHeader)));
//		String signatureBase = buffer.toString();
//		String signingKey = TextUtils.urlEncode(consumerSecret) + '&' + TextUtils.urlEncode(tokenSecret);
//		return hashSignature(signatureBase, signingKey);
//	}
//	
//	private String hashSignature(String signatureBase, String signingKey) {
//		// TODO:
//		return "";
//		/*
//		 * function hmac (key, message)
//		 *  if (length(key) > blocksize) then
//				key = hash(key) // keys longer than blocksize are shortened
//			end if
//			if (length(key) < blocksize) then
//				key = key ? [0x00 * (blocksize - length(key))] // keys shorter than blocksize are zero-padded ('?' is concatenation) 
//			end if
//
//			o_key_pad = [0x5c * blocksize] ? key // Where blocksize is that of the underlying hash function
//			i_key_pad = [0x36 * blocksize] ? key // Where ? is exclusive or (XOR)
//
//			return hash(o_key_pad ? hash(i_key_pad ? message)) // Where '?' is concatenation
//		 */
//	}
//	
//	private String createSignatureParameterString(Pair[] parameters, Pair[] post, Pair[] oAuthHeader) {
//		StringBuffer buffer = new StringBuffer();
//		
//		Vector pairs = new Vector();
//		for (int i=0; i<parameters.length; i++) {
//			addPairToVector(parameters[i], pairs);
//		}
//		for (int i=0; i<oAuthHeader.length; i++) {
//			addPairToVector(oAuthHeader[i], pairs);
//		}
//		for (int i=0; i<post.length; i++) {
//			addPairToVector(post[i], pairs);
//		}
//		
//		for (int i=0; i<pairs.size(); i++) {
//			Pair pair = (Pair) pairs.elementAt(i);
//			buffer.append(pair.toString());
//			if (i < pairs.size() - 1) {
//				buffer.append('&');
//			}
//		}
//		
//		return buffer.toString();
//	}
//	
//	private void addPairToVector(Pair pair, Vector pairs) {
//		pair = pair.clone();
//		pair.setKey(TextUtils.urlEncode(pair.getKey()));
//		pair.setValue(TextUtils.urlEncode(pair.getValue()));
//		for (int i=0; i<pairs.size(); i++) {
//			Pair current = (Pair) pairs.elementAt(i);
//			if (pair.getKey().compareTo(current.getKey()) == 0 && pair.getValue().compareTo(current.getValue()) < 0) {
//				pairs.insertElementAt(pair, i);
//				break;
//			} else if (pair.getKey().compareTo(current.getKey()) < 0) {
//				pairs.insertElementAt(pair, i);
//				break;
//			} else if (i == pairs.size() - 1) {
//				pairs.addElement(pair);
//				break;
//			}
//		}
//	}
//	
//	/**
//	 * Returns the String representation of this header.
//	 */
//	public String toString() {
//		return header;
//	}
//}
