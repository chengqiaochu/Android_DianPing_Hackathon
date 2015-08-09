package com.miao.movieshow.util;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;

public class UtilNet {

	public static String sendPost(String url, Map<String, String> params)
			throws ConnectException, ConnectTimeoutException, ParseException,
			IOException {

		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
		HttpConnectionParams.setSoTimeout(httpParams, 15000);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpPost httpPost = new HttpPost(url);
		if (params != null && !params.isEmpty()) {
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> param : params.entrySet()) {
				parameters.add(new BasicNameValuePair(param.getKey(), param
						.getValue()));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,
					"UTF-8");
			httpPost.setEntity(entity);
		}
		HttpResponse response = httpClient.execute(httpPost);
//		if (response.getStatusLine().getStatusCode() != 200) {
//			throw new RuntimeException("响应码不是200"
//					+ response.getStatusLine().getStatusCode());
//		}
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity, "UTF-8");
	}

	public static String sendGet(String url) throws ConnectException,
			ConnectTimeoutException, ParseException, IOException {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 8000);
		HttpConnectionParams.setSoTimeout(httpParams, 8000);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("响应码不是200"
					+ response.getStatusLine().getStatusCode());
		}
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity, "UTF-8");
	}

	/**
	 * 判断手机是否联网
	 * 
	 * @return
	 */
	public static boolean isNetwork(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		}
		if (connectivityManager.getActiveNetworkInfo() == null) {
			return false;
		}
		return connectivityManager.getActiveNetworkInfo().isAvailable();
	}
}
