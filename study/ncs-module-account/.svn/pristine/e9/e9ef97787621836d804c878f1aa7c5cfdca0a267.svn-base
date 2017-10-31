package cn.ncss.module.account.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.oltu.oauth2.common.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 收发送邮件
 * @author kyrin
 *
 */
public final class EmailUtil {
	private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);
	//电子邮箱正则表达式
	public static final String REGEXP_EMAIL = "(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)?";
	//手机号码正则表达式
	public static final String REGEXP_MOBILEPHONE = "1[0-9]{10}";

	private static final String DEFAULT_SENDE_USERNAME_CLOUD = "postmaster@ncss-sender.sendcloud.org";
	private static final String DEFAULT_SENDER_PASSWORD_CLOUD = "IrwMqWZprwhfNOdi";
	//String url = "https://sendcloud.sohu.com/webapi/mail.send.xml";
	private static final String DEFAULT_SEND_EMAIL_URL = "http://sendcloud.sohu.com/webapi/mail.send.json";

	public static boolean sendCloudMail(String fromMail, String fromName, String replyMail, String replyName,
			String toMail, String toName, String title, String content) {
		return sendMail(fromMail, fromName, replyMail, replyName, toMail, toName, title, content,
				DEFAULT_SENDE_USERNAME_CLOUD, DEFAULT_SENDER_PASSWORD_CLOUD);
	}

	public static boolean sendMail(String fromMail, String fromName, String replyMail, String replyName, String toMail,
			String toName, String title, String content, String sendusername, String sendpassword) {
		try {
			fromMail = fromMail.trim();
			replyMail = replyMail.trim();
			toMail = toMail.trim();
			toMail = toMail.replaceAll(" ", "");

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("api_user", sendusername)); //使用api_user和api_key进行验证
			params.add(new BasicNameValuePair("api_key", sendpassword));
			params.add(new BasicNameValuePair("from", fromMail)); // 发信人地址，用正确邮件地址替代
			params.add(new BasicNameValuePair("fromname", fromMail));//发信人名称
			params.add(new BasicNameValuePair("to", toMail)); // 收件人地址，用正确邮件地址替代，多个地址用';'分隔
			params.add(new BasicNameValuePair("replyto", replyMail));//回复人邮箱地址
			params.add(new BasicNameValuePair("subject", title));
			params.add(new BasicNameValuePair("html", content));

			String result = RestUtils.post(DEFAULT_SEND_EMAIL_URL, params);
			logger.info(result);
			Map<String, Object> obj = JSONUtils.parseJSON(result);
			if (obj.get("message") != null && obj.get("message").equals("success")) {
				return true;
			}
			throw new Exception("邮箱发送失败");
		} catch (Exception ex) {
			logger.error("sendMail Exception", ex);
			return false;
		}
	}
}
