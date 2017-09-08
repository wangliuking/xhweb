package com.chinamobile.lscservice;

import org.apache.axiom.om.OMAbstractFactory; 
import org.apache.axiom.om.impl.builder.StAXBuilder; 
import org.apache.axiom.om.util.StAXUtils; 
import org.apache.axiom.soap.SOAP12Constants; 
import org.apache.axiom.soap.SOAPEnvelope; 
import org.apache.axiom.soap.SOAPFactory; 
import org.apache.axiom.soap.impl.builder.StAXSOAPModelBuilder;  
import org.apache.commons.io.FileUtils;
import javax.xml.stream.XMLStreamException; 
import javax.xml.stream.XMLStreamReader; 
import java.io.ByteArrayInputStream; 
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException; 

 
public final class MessageConverter { 
	public static void main(String[] args) {
		System.out.println(getSoapEnvelope());
		System.out.println(getSoapEnvelope().getNamespace().getPrefix()+"  "+getSoapEnvelope().getNamespace().getNamespaceURI());
	}
 
    public static SOAPEnvelope getSoapEnvelope() {
    	SOAPFactory soapfactory = OMAbstractFactory.getSOAP11Factory();
    	SOAPEnvelope soapEnvelope=soapfactory.createSOAPEnvelope();
        try { 
        	File file = new File("E:/test.xml");
			byte[] buf = null;
			try {
				buf = FileUtils.readFileToByteArray(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        XMLStreamReader xmlReader = StAXUtils
	                .createXMLStreamReader(new ByteArrayInputStream(buf)); 
            StAXBuilder builder = new StAXSOAPModelBuilder(xmlReader); 
            soapEnvelope = (SOAPEnvelope) builder.getDocumentElement(); 
            soapEnvelope.build(); 
            //String soapNamespace = soapEnvelope.getNamespace().getNamespaceURI(); 
            /*if (soapEnvelope.getHeader() == null) { 
                SOAPFactory soapFactory; 
                if (soapNamespace.equals(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI)) { 
                	System.out.println("1.1");
                    soapFactory = OMAbstractFactory.getSOAP12Factory(); 
                } else { 
                    soapFactory = OMAbstractFactory.getSOAP11Factory(); 
                } 
                soapFactory.createSOAPHeader(soapEnvelope); 
            } */
             
        } catch (XMLStreamException e) { 
            e.printStackTrace();
        } 
        return soapEnvelope;
    } 
    
    private static byte[] getUTF8Bytes(String soapEnvelpe) { 
    	  byte[] bytes = null; 
    	  try { 
    	   bytes = soapEnvelpe.getBytes("UTF-8"); 
    	  } catch (UnsupportedEncodingException e) { 
    	   e.printStackTrace(); 
    	  } 
    	  return bytes; 
    	 } 
 
 
}

