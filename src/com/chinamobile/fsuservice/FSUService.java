
/**
 * FSUService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.6  Built on : Jul 30, 2017 (09:08:31 BST)
 */

package com.chinamobile.fsuservice;

/*
 *  FSUService java interface
 */

public interface FSUService {

	/**
	 * Auto generated method signature
	 * 
	 * @param invoke0
	 */

	public com.chinamobile.fsuservice.InvokeResponse invoke(

	com.chinamobile.fsuservice.Invoke invoke0) throws java.rmi.RemoteException;

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @param invoke0
	 */
	public void startinvoke(

	com.chinamobile.fsuservice.Invoke invoke0,

	final com.chinamobile.fsuservice.FSUServiceCallbackHandler callback)

	throws java.rmi.RemoteException;

	//
}
