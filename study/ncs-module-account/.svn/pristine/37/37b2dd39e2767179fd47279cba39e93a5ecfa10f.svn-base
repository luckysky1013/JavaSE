package cn.ncss.module.account.utils;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http 请求工具类
 * @author kyrin
 *
 */
public final class RestUtils {

	private static Logger logger = LoggerFactory.getLogger(RestUtils.class);

	private static CloseableHttpClient client = HttpClients.createDefault();

	private static final String QP_SEP_A = "?";
	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	public static String get(String url, List<NameValuePair> parameters) throws ClientProtocolException, IOException {
		url = buildURL(url, parameters);
		HttpGet get = new HttpGet(url);
		return exec(get);
	}

	public static String post(String url, List<NameValuePair> parameters) throws ClientProtocolException, IOException {
		url = buildURL(url, parameters);
		HttpPost post = new HttpPost(url);
		return exec(post);
	}

	public static String put(String url, List<NameValuePair> parameters) throws ClientProtocolException, IOException {
		url = buildURL(url, parameters);
		HttpPut put = new HttpPut(url);
		return exec(put);
	}

	public static String delete(String url, List<NameValuePair> parameters) throws ClientProtocolException, IOException {
		url = buildURL(url, parameters);
		HttpDelete del = new HttpDelete(url);
		return exec(del);
	}

	/**
	 * 如果HTTP status code 
	 * @param requestBase
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static String exec(HttpRequestBase requestBase) throws ClientProtocolException, IOException {
		CloseableHttpResponse response = client.execute(requestBase);
		String result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
		int status_code = response.getStatusLine().getStatusCode();

		switch (status_code) {
		case 400:
			logger.error("400 Bad request [{} - {} - {}] :{}", requestBase.getMethod(), requestBase.getURI(), result);
			break;
		case 401:
			logger.error("401 Request Unauthorized [{} - {}] : {}", requestBase.getMethod(), requestBase.getURI(),
					result);
			break;
		case 402:
			logger.error("402 Rquest Payment Required [{} - {}] : {}", requestBase.getMethod(), requestBase.getURI(),
					result);
			break;
		case 403:
			logger.error("403 Request Forbidden [{} - {}] : {}", requestBase.getMethod(), requestBase.getURI(), result);
			break;
		case 500:
			logger.error("500 Internal Server Error [{} - {}] : {}", requestBase.getMethod(), requestBase.getURI(),
					result);
			break;
		}
		return result;
	}

	private static String buildURL(String url, List<NameValuePair> parameters) {
		if (parameters != null && !parameters.isEmpty()) {
			StringBuffer str = new StringBuffer();
			str.append(url);
			str.append(QP_SEP_A);
			str.append(URLEncodedUtils.format(parameters, DEFAULT_CHARSET));
			url = str.toString();
		}
		return url;
	}
	
	public static NameValuePair buildNameValuePair(String name,String value){
		return new BasicNameValuePair(name, value);
	}

}
