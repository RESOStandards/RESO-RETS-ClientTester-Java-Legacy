/*
 * Created on Dec 2, 2004
 *
 */
package org.realtor.rets.compliance.client;

/**
 * @author jthomas
 *
 */
public class TestFailedException extends Exception {
	public TestFailedException(String s){
		super(s);
	}
	
	public TestFailedException(Exception parent){
		super(parent);
	}
	
	public TestFailedException(String message, Exception parent){
		super(message, parent);
	}
}
