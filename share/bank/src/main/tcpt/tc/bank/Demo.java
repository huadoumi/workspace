package tc.bank;

import java.io.ByteArrayOutputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

public class Demo {

	public static final String XPATH_SOAP_GW_HEAD = "/soapenv:Envelope/soapenv:Header/gateway:HeadType";
	public static final String SOAP_ENVELOPE_TAG = "soapenv:envelope";
	public static final String SOAP_HEADER_TAG = "soapenv:Header";
	public static final String SOAP_BODY_TAG = "soapenv:Body";
	public static final String SOAP_HEAD_TAG = "HeadType";
	public static final String XMLNS_SOAPENV = "soapenv";
	public static final String XMLNS_SOAPENV_VALUE = "http://schemas.xmlsoap.org/soap/envelope/";
	public static final String XMLNS_V1 = "v1";
	public static final String XMLNS_V1_VALUE = "http://esb.srcb.com/InsuranceParaService/v1";
	public static final String XMLNS_ESB = "esb";
	public static final String XMLNS_ESB_VALUE = "http://esb.srcb.com";
	public static final String XMLNS_V11 = "v11";
	public static final String XMLNS_V11_VALUE = "http://esb.srcb.com/InsuranceParaService/schema/v1";

	public static final String CHARSET_ENCODING = "UTF-8";
	public static final String RAW_SOAP_IN_CONTEXT = "RAW_SOAP_IN_CONTEXT";
	public static final String SOAPENV_PREFIX = "soapenv";
	public static final String DEL_SOAPENV_PREFIX = "SOAP-ENV";

	public static void main(String[] args) throws Exception {
		String msg = buildSoapXml();
		System.out.println(msg);
	}

	public static String getXmlHeader(String encoding) {
		return String.format("<?xml version=\"1.0\" encoding=\"%s\" ?>", encoding);
	}

	public static String buildSoapXml() throws Exception {
		return getXmlHeader("UTF-8") + buildSoapMessage();
	}

	public static String buildSoapMessage() throws Exception {

		MessageFactory msgFactory = MessageFactory.newInstance();
		SOAPMessage msg = msgFactory.createMessage();
		msg.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
		SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
		// 命名空间
		env.addNamespaceDeclaration(XMLNS_SOAPENV, XMLNS_SOAPENV_VALUE);
		env.addNamespaceDeclaration(XMLNS_V1, XMLNS_V1_VALUE);
		env.addNamespaceDeclaration(XMLNS_ESB, XMLNS_ESB_VALUE);
		env.addNamespaceDeclaration(XMLNS_V11, XMLNS_V11_VALUE);
		env.removeNamespaceDeclaration(DEL_SOAPENV_PREFIX);
		env.setPrefix(SOAPENV_PREFIX);

		// //报文头
		SOAPHeader soapHeader = msg.getSOAPHeader();
		soapHeader.setPrefix(SOAPENV_PREFIX);
//		SOAPElement gwHeader = soapHeader.addChildElement(SOAP_HEAD_TAG, XMLNS_V1);

		SOAPBody gwBody = msg.getSOAPBody();
		gwBody.setPrefix(SOAPENV_PREFIX);
		

		// response
		SOAPElement responseMsg = gwBody.addChildElement("logonCusRiskReportResponse", XMLNS_V1);
		SOAPElement replyEle = responseMsg.addChildElement("replyInformation", XMLNS_ESB);
		SOAPElement typeEle = replyEle.addChildElement("responseType", XMLNS_ESB);
		SOAPElement codelyEle = replyEle.addChildElement("responseCode", XMLNS_ESB);
		SOAPElement responseEle = replyEle.addChildElement("responseMessage", XMLNS_ESB);

		msg.saveChanges();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			msg.writeTo(out);
		} finally {
			out.close();
		}

		return new String(out.toByteArray());
	}

}
