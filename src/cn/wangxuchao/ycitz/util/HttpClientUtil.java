package cn.wangxuchao.ycitz.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	/**
	 * 处理Get请求
	 *
	 * @param url
	 *            请求链接
	 * @return responseBody 网页源代码
	 * @throws Exception
	 */
	public static String httpGet(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);

			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(10000).setConnectTimeout(10000).build();
			httpget.setConfig(requestConfig);
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						return "error:code:0x000" + status;
					}
				}
			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			return responseBody;
		} catch (Exception e) {
			return "error:连接超时";
		} finally {
			httpclient.close();
		}
	}

	/**
	 * 处理 Post 请求
	 *
	 * @param url
	 *            请求链接
	 * @param map
	 *            post 的参数
	 * @return responseBody 网页源代码
	 * @throws Exception
	 */
	public static String httpPost(String url, Map<String, String> map)
			throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			// 获取参数
			for (Map.Entry<String, String> entry : map.entrySet()) {
				formparams.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
					Consts.UTF_8);
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(entity);
			
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(10000).setConnectTimeout(10000).build();
			httppost.setConfig(requestConfig);

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response)
						throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity)
								: null;
					} else {
						return "error:code:0x000" + status;
					}
				}
			};
			String responseBody = httpclient.execute(httppost, responseHandler);
			return responseBody;
		} finally {
			httpclient.close();
		}
	}

}
