package tc.bank;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;
import tc.bank.utils.ListUtil;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;


import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.AppLogger;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaDict;
import cn.com.agree.afa.svc.javaengine.context.JavaList;

/**
 * XML解析器
 * 
 * @author AlanMa
 * @date 2015-12-30 10:14:50
 */
@ComponentGroup(level = "银行", groupName = "报文解析器")
public class B_XMLParser {

	/**
	 * @category 解析网关报文头
	 * @param recv
	 *            入参|接收报文|{@link byte}
	 * @param encoding
	 *            入参|字符编码|{@link java.lang.String}
	 * @param out
	 *            出参|输出字典|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "recv", comment = "接收报文", type = byte[].class),
			@Param(name = "encoding", comment = "字符编码", type = java.lang.String.class),
			@Param(name = "req", comment = "接收容器", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "解析XML报文到Dict", style = "判断型", type = "同步组件", comment = "解析收到的HTTP请求到javaDict", date = "2017-01-05 07:57:26")
	public static TCResult B_unPackXmltoDict(byte[] recv, String encoding,
			JavaDict req) {
		Document document = null;
		JavaDict javaDictElement = new JavaDict();
		JavaDict javaDictElement1 = new JavaDict();
		try {
			document = DocumentHelper.parseText(new String(recv, encoding));
		} catch (UnsupportedEncodingException e) {
			return TCResult.newFailureResult(ErrorCode.AGR, "编码" + encoding
					+ "转换失败");
		} catch (DocumentException e) {
			return TCResult.newFailureResult(ErrorCode.AGR,"DocumentException");
		}
		Element rootElement = document.getRootElement();
		getChildNodesCount(rootElement, javaDictElement);
		new B_XMLParser().getNodes(rootElement, javaDictElement,
				javaDictElement1);
		req.putAll(javaDictElement1);
		return TCResult.newSuccessResult();
	}
	public static void main(String[] args) throws Exception {
//		byte[] b = (byte[]) ReadFileUtil.ReadFile("D://351100.xml", "UTF-8", "byte[]");
		byte[] b = Demo.buildSoapXml().getBytes("UTF-8");
		JavaDict req = new JavaDict();
		B_XMLParser.B_unPackXmltoDict(b, "UTF-8",req);
		System.out.println(req.toString());
	}
	/**
	 * 递归查询，得到所有节点函数的 统计数量
	 * 
	 * @param elem
	 */
	@SuppressWarnings("unchecked")
	private static void getChildNodesCount(Element elem,
			JavaDict javaDictElement) {
		Iterator<Node> it = elem.nodeIterator();
		while (it.hasNext()) {
			Node node = it.next();
			if (node instanceof Element) {
				Element e1 = (Element) node;
				if (javaDictElement.containsKey(node.getName())) {
					javaDictElement.put(e1.getName(),
							javaDictElement.getIntItem(e1.getName()) + 1);
					getChildNodesCount(e1, javaDictElement);
				} else {
					javaDictElement.put(e1.getName(), 0);
				}
				getChildNodesCount(e1, javaDictElement);
			}
		}
	}
	
	/**
	 * 所有子节点的属性的不同进行加载到javaDict
	 * 
	 * @param node
	 */
	@SuppressWarnings("unchecked")
	private void getNodes(Element node, JavaDict javaDictElement,
			JavaDict javaDictElement1) {
		String nodeName = node.getName();
		String nodeText = node.getTextTrim();
		if (javaDictElement.getIntItem(node.getName()) >= 1) {
			List<Attribute> listArr = node.attributes();
			for (Attribute att : listArr) {
				String value = att.getValue();
				JavaList listTemp = new JavaList();
				if (!javaDictElement1.containsKey(value)) {
					javaDictElement1.put(value, nodeText);
				} else {
					Object keyValue = javaDictElement1.get(value);
					if(keyValue instanceof JavaList){
						listTemp.addAll(javaDictElement1.getListItem(value));
						listTemp.add(nodeText);
						javaDictElement1.put(value, listTemp);
					}else{
						listTemp.add(keyValue);
						listTemp.add(nodeText);
						javaDictElement1.put(value, listTemp);
					}
				}
			}
		} else {
			javaDictElement1.put(nodeName, nodeText);
		}
		List<Element> listElement = node.elements();
		for (Element element : listElement) {
			this.getNodes(element, javaDictElement, javaDictElement1);
		}
	}

	/**
	 * @param origMsg
	 *            入参|需要解析的报文信息（数据字典）|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @param encoding
	 *            入参|字符集编码|{@link java.lang.String}
	 * @param rootName
	 *            入参|根节点名称|{@link java.lang.String}
	 * @param msgXML
	 *            出参|解析后的XML报文|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 *         2 异常<br/>
	 */
	@InParams(param = {
			@Param(name = "origMsg", comment = "需要解析的报文信息（数据字典）", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class),
			@Param(name = "encoding", comment = "字符集编码", type = java.lang.String.class),
			@Param(name = "rootName", comment = "根节点名称", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "msgXML", comment = "解析后的XML报文", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "异常") })
	@Component(label = "解析字典拼装XML", style = "选择型", type = "同步组件", comment = "将数据字典解析成XML", date = "2016-01-05 02:20:31")
	@SuppressWarnings({ "rawtypes" })
	public static TCResult B_ParseToXML(JavaDict origMsg, String encoding,
			String rootName) {
		JavaDict starXmlJD = new JavaDict();
		starXmlJD.put(rootName, origMsg);
		Iterator iter = starXmlJD.entrySet().iterator();
		Entry entry = (Entry) iter.next();
		JavaDict origMap = new JavaDict();
		origMap.putAll((JavaDict) entry.getValue());
		Document doc = DocumentHelper.createDocument();
		Element body = DocumentHelper.createElement((String) entry.getKey());
		doc.add(body);
		convMapToXML(body, origMap);
		String encode = (encoding == null || "".equals(encoding)) ? "UTF-8"
				: encoding;
		doc.setXMLEncoding(encode);
		TCResult tcResult = TCResult.newSuccessResult();
		tcResult.setOutputParams(doc.asXML());
		return tcResult;
	}

	/**
	 * @param origMsg
	 *            入参|未解析的XML报文内容|{@link java.lang.String}
	 * @param msgJD
	 *            出参|解析后的数据字典|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaDict}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 *         2 异常<br/>
	 */
	@InParams(param = { @Param(name = "origMsg", comment = "未解析的XML报文内容", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "msgJD", comment = "解析后的数据字典", type = cn.com.agree.afa.svc.javaengine.context.JavaDict.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功"), @Return(id = "2", desp = "异常") })
	@Component(label = "解析XML拼装字典", style = "选择型", type = "同步组件", comment = "将XML解析成数据字典", date = "2015-12-30 05:01:03")
	public static TCResult B_ParseToJD(String origMsg) {
		String msg = origMsg.substring(origMsg.indexOf("<?"), origMsg.length());
		String orgEncoding = msg.substring(msg.indexOf("encoding=\"")
				+ "encoding=\"".length(), msg.indexOf("\"?>"));
		JavaDict chileRetJD = null;
		Element element = null;
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(new ByteArrayInputStream(msg
					.getBytes(orgEncoding.trim())));
		} catch (UnsupportedEncodingException e) {
			AppLogger.error(e);
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		} catch (DocumentException e) {
			AppLogger.error(e);
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		element = document.getRootElement();
		chileRetJD = parseElement(element);
		TCResult tcResult = TCResult.newSuccessResult();
		tcResult.setOutputParams(chileRetJD);
		return tcResult;
	}

	/**
	 * 解析元素
	 * 
	 * @param element
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static JavaDict parseElement(Element element) {
		JavaDict retJD = new JavaDict();
		boolean isPutFlag = true;
		for (Iterator i = element.elementIterator(); i.hasNext();) {
			Element subElem = (Element) i.next();
			Iterator j = subElem.elementIterator();
			if (j.hasNext()) {
				isPutFlag = false;
				if (retJD.get(subElem.getName()) != null) {
					JavaList contents = new JavaList();
					if (retJD.get(subElem.getName()) instanceof String) {
						String content = new String((String) retJD.get(subElem
								.getName()));
						contents.add(content);
					}
					if (retJD.get(subElem.getName()) instanceof JavaList) {
						JavaList content = new JavaList();
						content.addAll((JavaList) retJD.get(subElem.getName()));
						contents.addAll(content);
					}
					if (retJD.get(subElem.getName()) instanceof JavaDict) {
						JavaDict content = new JavaDict();
						content.putAll((JavaDict) retJD.get(subElem.getName()));
						contents.add(content);
					}
					retJD.remove(subElem.getName());
					contents.add(parseElement(subElem));
					retJD.put(subElem.getName(), contents);
				} else {
					retJD.put(subElem.getName(), parseElement(subElem));
				}

			}
			if (isPutFlag) {
				retJD.put(subElem.getName(), subElem.getText());
			}
		}

		return retJD;
	}

	/**
	 * 将Map转为XML
	 * 
	 * @param eleBody
	 * @param origMap
	 */
	private static void convMapToXML(Element eleBody, JavaDict origMap) {
		Iterator<Object> it = origMap.keySet().iterator();
		boolean isAddEle = true;
		while (it.hasNext()) {
			String key = (String) it.next();
			Object elemValue = origMap.get(key);
			Element element = null;
			if (!(elemValue instanceof JavaList)) {
				element = DocumentHelper.createElement(key);
			}
			if (elemValue instanceof String) {
				element.setText((String) elemValue);
			} else if (elemValue instanceof JavaDict) {
				convMapToXML(element, (JavaDict) elemValue);
			} else if (elemValue instanceof JavaList) {
				JavaList retList = (JavaList) elemValue;
				/**
				 * 如果retList的每个元素为list,直接将retList.toString()
				 */
				if (ListUtil.isNotEmpty(retList)
						&& (retList.get(0)) instanceof JavaList) {
					element = DocumentHelper.createElement(key);
					element.setText(retList.toString());
				} else {
					/**
					 * 如果retList的每个元素为map,继续进行递归调用
					 */
					if (ListUtil.isNotEmpty(retList)
							&& (retList.get(0)) instanceof JavaDict) {
						for (int i = 0; i < retList.size(); i++) {
							JavaDict contentMap = new JavaDict();
							contentMap.put(key, (JavaDict) retList.get(i));
							/** 继续递归调用 */
							convMapToXML(eleBody, contentMap);
							if (i != retList.size() - 1) {
								element = DocumentHelper.createElement(key);
							} else {
								isAddEle = false;
							}

						}
					}
				}
			} else {
				element.setText(String.valueOf(elemValue));
			}
			if (isAddEle)
				eleBody.add(element);
			isAddEle = true;
		}
	}

}
