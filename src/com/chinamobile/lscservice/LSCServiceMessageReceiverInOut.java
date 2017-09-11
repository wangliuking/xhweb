/**
 * LSCServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.6  Built on : Jul 30, 2017 (09:08:31 BST)
 */
package com.chinamobile.lscservice;

/**
 * LSCServiceMessageReceiverInOut message receiver
 */

public class LSCServiceMessageReceiverInOut extends
		org.apache.axis2.receivers.AbstractInOutMessageReceiver {

	public void invokeBusinessLogic(
			org.apache.axis2.context.MessageContext msgContext,
			org.apache.axis2.context.MessageContext newMsgContext)
			throws org.apache.axis2.AxisFault {

		try {

			// get the implementation class for the Web Service
			Object obj = getTheImplementationObject(msgContext);

			LSCServiceSkeletonInterface skel = (LSCServiceSkeletonInterface) obj;
			// Out Envelop
			org.apache.axiom.soap.SOAPEnvelope envelope = null;
			// Find the axisOperation that has been set by the Dispatch phase.
			org.apache.axis2.description.AxisOperation op = msgContext
					.getOperationContext().getAxisOperation();
			if (op == null) {
				throw new org.apache.axis2.AxisFault(
						"Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
			}

			java.lang.String methodName;
			if ((op.getName() != null)
					&& ((methodName = org.apache.axis2.util.JavaUtils
							.xmlNameToJavaIdentifier(op.getName()
									.getLocalPart())) != null)) {

				if ("invoke".equals(methodName)) {

					com.chinamobile.lscservice.InvokeResponse invokeResponse3 = null;
					com.chinamobile.lscservice.Invoke wrappedParam = (com.chinamobile.lscservice.Invoke) fromOM(
							msgContext.getEnvelope().getBody()
									.getFirstElement(),
							com.chinamobile.lscservice.Invoke.class);

					invokeResponse3 =

					skel.invoke(wrappedParam);

					envelope = toEnvelope(getSOAPFactory(msgContext),
							invokeResponse3, false,
							new javax.xml.namespace.QName(
									"http://LSCService.chinamobile.com",
									"invokeResponse"));

				} else {
					throw new java.lang.RuntimeException("method not found");
				}

				newMsgContext.setEnvelope(envelope);
			}
		} catch (java.lang.Exception e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	//
	private org.apache.axiom.om.OMElement toOM(
			com.chinamobile.lscservice.Invoke param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					com.chinamobile.lscservice.Invoke.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(
			com.chinamobile.lscservice.InvokeResponse param,
			boolean optimizeContent) throws org.apache.axis2.AxisFault {

		try {
			return param.getOMElement(
					com.chinamobile.lscservice.InvokeResponse.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory,
			com.chinamobile.lscservice.InvokeResponse param,
			boolean optimizeContent, javax.xml.namespace.QName elementQName)
			throws org.apache.axis2.AxisFault {
		try {
			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = MessageConverter.getSoapEnvelope();
			emptyEnvelope.getBody().removeChildren();
			emptyEnvelope.getBody().addChild(
					param.getOMElement(
							com.chinamobile.lscservice.InvokeResponse.MY_QNAME,
							factory));
			return emptyEnvelope;
		} catch (org.apache.axis2.databinding.ADBException e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
	}

	private com.chinamobile.lscservice.InvokeResponse wrapinvoke() {
		com.chinamobile.lscservice.InvokeResponse wrappedElement = new com.chinamobile.lscservice.InvokeResponse();
		return wrappedElement;
	}

	/**
	 * get the default envelope
	 */
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
			org.apache.axiom.soap.SOAPFactory factory) {
		return factory.getDefaultEnvelope();
	}

	private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
			java.lang.Class type) throws org.apache.axis2.AxisFault {

		try {

			if (com.chinamobile.lscservice.Invoke.class.equals(type)) {

				return com.chinamobile.lscservice.Invoke.Factory.parse(param
						.getXMLStreamReaderWithoutCaching());

			}

			if (com.chinamobile.lscservice.InvokeResponse.class.equals(type)) {

				return com.chinamobile.lscservice.InvokeResponse.Factory
						.parse(param.getXMLStreamReaderWithoutCaching());

			}

		} catch (java.lang.Exception e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
		return null;
	}

	private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
		org.apache.axis2.AxisFault f;
		Throwable cause = e.getCause();
		if (cause != null) {
			f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
		} else {
			f = new org.apache.axis2.AxisFault(e.getMessage());
		}

		return f;
	}

}// end of class
