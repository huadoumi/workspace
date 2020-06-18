package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;

/**
 * HTTP客户端
 * 
 * @date 2015-07-27 11:6:33
 */
@ComponentGroup(level = "平台", groupName = "渠道通讯类组件")
public class P_HttpClient {

	private static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * @param url
	 *            入参|请求URL,例如：http://127.0.0.1:8080/xx|{@link java.lang.String}
	 * @param header
	 *            入参|HTTP协议头部携带信息字典,可为null|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param requestBody
	 *            入参|请求报文体|{@link java.lang.String}
	 * @param connectTimeout
	 *            入参|连接超时时间(毫秒),小于等于0时默认为5000毫秒|int
	 * @param socketTimeout
	 *            入参|响应超时时间（毫秒）,小于等于0时默认为5000毫秒|int
	 * @param encoding
	 *            入参|编码,默认使用UTF-8|{@link java.lang.String}
	 * @param responseBody
	 *            出参|响应报文体|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "url", comment = "请求URL,例如：\"http://127.0.0.1:8080/xx\"", type = java.lang.String.class),
			@Param(name = "header", comment = "HTTP协议头部携带信息字典,可为null", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "requestBody", comment = "请求报文体", type = java.lang.String.class),
			@Param(name = "connectTimeout", comment = "连接超时时间(毫秒),小于等于0时默认为5000毫秒", type = int.class),
			@Param(name = "socketTimeout", comment = "响应超时时间(毫秒),小于等于0时默认为5000毫秒", type = int.class),
			@Param(name = "encoding", comment = "编码，默认为UTF-8", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "responseBody", comment = "响应报文体", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "发送HTTP POST请求", style = "判断型", type = "同步组件", comment="根据HTTP动作解释，POST动作用于请求服务器新建某个资源，通常用于发送表单", date = "Mon Jul 27 12:39:11 CST 2015")
	public static TCResult doPost(String url, JavaDict header,
			String requestBody, int connectTimeout, int socketTimeout,
			String encoding) {
		if (url == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参请求URL不能为空");
		}
		if (requestBody == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参HTTP协议实体信息不能为空");
		}
		if (encoding == null) {
			encoding = DEFAULT_CHARSET;
		}
		CloseableHttpClient client = HttpClientBuilder.create().build();
		int connectTime = connectTimeout > 0 ? connectTimeout : 5000;
		int socketTime = socketTimeout > 0 ? socketTimeout : 5000;
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(connectTime).setSocketTimeout(socketTime)
				.build();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new ByteArrayEntity(requestBody
					.getBytes(encoding)));
			if (header != null) {
				Iterator<Entry<Object, Object>> headerIterator = header
						.entrySet().iterator();
				while (headerIterator.hasNext()) {
					Map.Entry<Object, Object> entry = headerIterator.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					httpPost.setHeader(key, value);
				}
			}
			httpPost.setConfig(requestConfig);
			String responseBody = client.execute(httpPost,
					new BodyHandler(encoding));
			return TCResult.newSuccessResult(responseBody);
		} catch (ClientProtocolException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		} catch (IOException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					return TCResult.newFailureResult(ErrorCode.HANDLING, e);
				}
			}
		}
	}
	
	/**
	 * @param url
	 *            入参|请求URL,例如：http://127.0.0.1:8080/xx|{@link java.lang.String}
	 * @param header
	 *            入参|HTTP协议头部携带信息字典,可为null|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param requestBody
	 *            入参|请求报文体|{@link java.lang.String}
	 * @param connectTimeout
	 *            入参|连接超时时间(毫秒),小于等于0时默认为5000毫秒|int
	 * @param socketTimeout
	 *            入参|响应超时时间（毫秒）,小于等于0时默认为5000毫秒|int
	 * @param encoding
	 *            入参|编码,默认使用UTF-8|{@link java.lang.String}
	 * @param responseBody
	 *            出参|响应报文体|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "url", comment = "请求URL,例如：\"http://127.0.0.1:8080/xx\"", type = java.lang.String.class),
			@Param(name = "header", comment = "HTTP协议头部携带信息字典,可为null", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "requestBody", comment = "请求报文体", type = java.lang.String.class),
			@Param(name = "connectTimeout", comment = "连接超时时间(毫秒),小于等于0时默认为5000毫秒", type = int.class),
			@Param(name = "socketTimeout", comment = "响应超时时间(毫秒),小于等于0时默认为5000毫秒", type = int.class),
			@Param(name = "encoding", comment = "编码，默认为UTF-8", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "responseBody", comment = "响应报文体", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "发送HTTP PUT请求", style = "判断型", type = "同步组件", comment="根据HTTP动作解释，PUT动作用于请求服务器全量更新某个资源", date = "Mon Jul 27 12:39:11 CST 2015")
	public static TCResult doPut(String url, JavaDict header,
			String requestBody, int connectTimeout, int socketTimeout,
			String encoding) {
		if (url == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参请求URL不能为空");
		}
		if (requestBody == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参HTTP协议实体信息不能为空");
		}
		if (encoding == null) {
			encoding = DEFAULT_CHARSET;
		}
		CloseableHttpClient client = HttpClientBuilder.create().build();
		int connectTime = connectTimeout > 0 ? connectTimeout : 5000;
		int socketTime = socketTimeout > 0 ? socketTimeout : 5000;
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(connectTime).setSocketTimeout(socketTime)
				.build();
		try {
			HttpPut httpPut = new HttpPut(url);
			httpPut.setEntity(new ByteArrayEntity(requestBody
					.getBytes(encoding)));
			if (header != null) {
				Iterator<Entry<Object, Object>> headerIterator = header
						.entrySet().iterator();
				while (headerIterator.hasNext()) {
					Map.Entry<Object, Object> entry = headerIterator.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					httpPut.setHeader(key, value);
				}
			}
			httpPut.setConfig(requestConfig);
			String responseBody = client.execute(httpPut,
					new BodyHandler(encoding));
			return TCResult.newSuccessResult(responseBody);
		} catch (ClientProtocolException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		} catch (IOException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					return TCResult.newFailureResult(ErrorCode.HANDLING, e);
				}
			}
		}
	}

	/**
	 * @param url
	 *            入参|请求URL,例如：http://127.0.0.1:8080/xx|{@link java.lang.String}
	 * @param header
	 *            入参|HTTP协议头部携带信息字典,可为null|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param params
	 *            入参|请求参数信息|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param connectTimeout
	 *            入参|连接超时时间(毫秒),小于等于0时默认为5000毫秒|int
	 * @param socketTimeout
	 *            入参|响应超时时间(毫秒),小于等于0时默认为5000毫秒|int
	 * @param encoding
	 *            入参|编码,默认使用UTF-8|{@link java.lang.String}
	 * @param responseBody
	 *            出参|响应报文体|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "url", comment = "请求URL,例如：\"http://127.0.0.1:8080/xx\"", type = java.lang.String.class),
			@Param(name = "header", comment = "HTTP协议头部携带信息字典,可为null", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "params", comment = "请求参数信息", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "connectTimeout", comment = "连接超时时间(毫秒),小于等于0时默认为5000毫秒", type = int.class),
			@Param(name = "socketTimeout", comment = "响应超时时间(毫秒),小于等于0时默认为5000毫秒", type = java.lang.String.class),
			@Param(name = "encoding", comment = "编码，默认为UTF-8", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "responseBody", comment = "响应报文体", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "发送HTTP GET请求", style = "判断型", type = "同步组件", comment="根据HTTP动作解释，GET动作用于请求服务器发送某个资源，应该是幂等的", date = "Mon Jul 27 13:20:46 CST 2015")
	public static TCResult doGet(String url, JavaDict header, JavaDict params,
			int connectTimeout, int socketTimeout, String encoding) {
		if (url == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参请求URL不能为空");
		}
		if (encoding == null) {
			encoding = DEFAULT_CHARSET;
		}
		CloseableHttpClient client = HttpClientBuilder.create().build();
		int connectTime = connectTimeout > 0 ? connectTimeout : 5000;
		int socketTime = socketTimeout > 0 ? socketTimeout : 5000;
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(connectTime).setSocketTimeout(socketTime)
				.build();
		String paramStr = "";
		try {
			if (params != null) {
				Iterator<Entry<Object, Object>> paramsIterator = params
						.entrySet().iterator();
				while (paramsIterator.hasNext()) {
					Map.Entry<Object, Object> entry = paramsIterator.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					paramStr += "&" + key + "="
							+ URLEncoder.encode(value, encoding);
				}
			}
		} catch (UnsupportedEncodingException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		if (!paramStr.equals("")) {
			paramStr = paramStr.replaceFirst("&", "?");
			url += paramStr;
		}
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.setConfig(requestConfig);
			if (header != null) {
				Iterator<Entry<Object, Object>> headIterator = header
						.entrySet().iterator();
				while (headIterator.hasNext()) {
					Map.Entry<Object, Object> entry = headIterator.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					httpget.setHeader(key, value);
				}
			}
			String responseBody = client
					.execute(httpget, new BodyHandler(encoding));
			return TCResult.newSuccessResult(responseBody);
		} catch (ClientProtocolException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		} catch (IOException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					return TCResult.newFailureResult(ErrorCode.HANDLING, e);
				}
			}
		}
	}

	/**
	 * @param url
	 *            入参|请求URL,例如：http://127.0.0.1:8080/xx|{@link java.lang.String}
	 * @param header
	 *            入参|HTTP协议头部携带信息字典,可为null|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param params
	 *            入参|请求参数信息|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param connectTimeout
	 *            入参|连接超时时间(毫秒),小于等于0时默认为5000毫秒|int
	 * @param socketTimeout
	 *            入参|响应超时时间(毫秒),小于等于0时默认为5000毫秒|int
	 * @param encoding
	 *            入参|编码,默认使用UTF-8|{@link java.lang.String}
	 * @param status
	 *            出参|响应码|{@link java.lang.Integer}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "url", comment = "请求URL,例如：\"http://127.0.0.1:8080/xx\"", type = java.lang.String.class),
			@Param(name = "header", comment = "HTTP协议头部携带信息字典,可为null", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "params", comment = "请求参数信息", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "connectTimeout", comment = "连接超时时间(毫秒),小于等于0时默认为5000毫秒", type = int.class),
			@Param(name = "socketTimeout", comment = "响应超时时间(毫秒),小于等于0时默认为5000毫秒", type = java.lang.String.class),
			@Param(name = "encoding", comment = "编码，默认为UTF-8", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "status", comment = "响应码", type = java.lang.Integer.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "发送HTTP HEAD请求", style = "判断型", type = "同步组件", comment="根据HTTP动作解释，Head动作在响应中只返回首部不会返回实体的主体部分，通常用于检查资源的情况", date = "Mon Jul 27 13:20:46 CST 2015")
	public static TCResult doHead(String url, JavaDict header, JavaDict params,
			int connectTimeout, int socketTimeout, String encoding) {
		if (url == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参请求URL不能为空");
		}
		if (encoding == null) {
			encoding = DEFAULT_CHARSET;
		}
		CloseableHttpClient client = HttpClientBuilder.create().build();
		int connectTime = connectTimeout > 0 ? connectTimeout : 5000;
		int socketTime = socketTimeout > 0 ? socketTimeout : 5000;
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(connectTime).setSocketTimeout(socketTime)
				.build();
		String paramStr = "";
		try {
			if (params != null) {
				Iterator<Entry<Object, Object>> paramsIterator = params
						.entrySet().iterator();
				while (paramsIterator.hasNext()) {
					Map.Entry<Object, Object> entry = paramsIterator.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					paramStr += "&" + key + "="
							+ URLEncoder.encode(value, encoding);
				}
			}
		} catch (UnsupportedEncodingException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		if (!paramStr.equals("")) {
			paramStr = paramStr.replaceFirst("&", "?");
			url += paramStr;
		}
		try {
			HttpHead httphead = new HttpHead(url);
			httphead.setConfig(requestConfig);
			if (header != null) {
				Iterator<Entry<Object, Object>> headIterator = header
						.entrySet().iterator();
				while (headIterator.hasNext()) {
					Map.Entry<Object, Object> entry = headIterator.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					httphead.setHeader(key, value);
				}
			}
			Integer status = client.execute(httphead, new StatusHandler());
			return TCResult.newSuccessResult(status);
		} catch (ClientProtocolException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		} catch (IOException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					return TCResult.newFailureResult(ErrorCode.HANDLING, e);
				}
			}
		}
	}
	
	/**
	 * @param url
	 *            入参|请求URL,例如：http://127.0.0.1:8080/xx|{@link java.lang.String}
	 * @param header
	 *            入参|HTTP协议头部携带信息字典,可为null|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param params
	 *            入参|请求参数信息|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param connectTimeout
	 *            入参|连接超时时间(毫秒),小于等于0时默认为5000毫秒|int
	 * @param socketTimeout
	 *            入参|响应超时时间(毫秒),小于等于0时默认为5000毫秒|int
	 * @param encoding
	 *            入参|编码,默认使用UTF-8|{@link java.lang.String}
	 * @param responseBody
	 *            出参|响应报文体|{@link java.lang.String.class}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "url", comment = "请求URL,例如：\"http://127.0.0.1:8080/xx\"", type = java.lang.String.class),
			@Param(name = "header", comment = "HTTP协议头部携带信息字典,可为null", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "params", comment = "请求参数信息", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "connectTimeout", comment = "连接超时时间(毫秒),小于等于0时默认为5000毫秒", type = int.class),
			@Param(name = "socketTimeout", comment = "响应超时时间(毫秒),小于等于0时默认为5000毫秒", type = java.lang.String.class),
			@Param(name = "encoding", comment = "编码，默认为UTF-8", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "responseBody", comment = "响应报文体", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "发送HTTP DELETE请求", style = "判断型", type = "同步组件", comment="根据HTTP动作解释，Delete动作用于请求服务器删除资源", date = "Mon Jul 27 13:20:46 CST 2015")
	public static TCResult doDelete(String url, JavaDict header, JavaDict params,
			int connectTimeout, int socketTimeout, String encoding) {
		if (url == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "入参请求URL不能为空");
		}
		if (encoding == null) {
			encoding = DEFAULT_CHARSET;
		}
		CloseableHttpClient client = HttpClientBuilder.create().build();
		int connectTime = connectTimeout > 0 ? connectTimeout : 5000;
		int socketTime = socketTimeout > 0 ? socketTimeout : 5000;
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(connectTime).setSocketTimeout(socketTime)
				.build();
		String paramStr = "";
		try {
			if (params != null) {
				Iterator<Entry<Object, Object>> paramsIterator = params
						.entrySet().iterator();
				while (paramsIterator.hasNext()) {
					Map.Entry<Object, Object> entry = paramsIterator.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					paramStr += "&" + key + "="
							+ URLEncoder.encode(value, encoding);
				}
			}
		} catch (UnsupportedEncodingException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		if (!paramStr.equals("")) {
			paramStr = paramStr.replaceFirst("&", "?");
			url += paramStr;
		}
		try {
			HttpDelete httpDelete = new HttpDelete(url);
			httpDelete.setConfig(requestConfig);
			if (header != null) {
				Iterator<Entry<Object, Object>> headIterator = header
						.entrySet().iterator();
				while (headIterator.hasNext()) {
					Map.Entry<Object, Object> entry = headIterator.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					httpDelete.setHeader(key, value);
				}
			}
			String responseBody = client
					.execute(httpDelete, new BodyHandler(encoding));
			return TCResult.newSuccessResult(responseBody);
		} catch (ClientProtocolException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		} catch (IOException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					return TCResult.newFailureResult(ErrorCode.HANDLING, e);
				}
			}
		}
	}

	static class BodyHandler implements ResponseHandler<String> {

		private String encoding;

		public BodyHandler(String encoding) {
			this.encoding = encoding;
		}

		@Override
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			int status = response.getStatusLine().getStatusCode();
			if (status == 200) {
				HttpEntity entity = response.getEntity();
				if (entity == null) {
					return new String("Response message is empty");
				}
				InputStream inputStream = entity.getContent();
				byte[] data = inputStream2Byte(inputStream);
				return new String(data, encoding);
			} else {
				throw new ClientProtocolException(
						"Unexpected response status: " + status);
			}
		}

		private static byte[] inputStream2Byte(InputStream in)
				throws IOException {
			byte[] tmp = new byte[1024];
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int bytesRead = -1;
			while ((bytesRead = in.read(tmp)) != -1) {
				out.write(tmp, 0, bytesRead);
			}
			return out.toByteArray();
		}

	}

	static class StatusHandler implements ResponseHandler<Integer> {

		public StatusHandler() {
		}

		@Override
		public Integer handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			return response.getStatusLine().getStatusCode();

		}
	}
}
