/* $Header: /usr/local/cvsroot/rets_client/src/org/realtor/rets/compliance/client/persistence/PersistenceException.java,v 1.1 2005/01/18 19:47:57 jthomas Exp $  */
package org.realtor.rets.compliance.client.persistence;

/**
 *  PersistenceException.java Created May 19, 2004
 *   
 * 
 *  Copyright 2004, Avantia inc.
 *  @version $Revision: 1755 $
 *  @author scohen
 */
public class PersistenceException extends Exception {
	public PersistenceException(String s){
		super(s);
	}
	
	public PersistenceException(Exception parent){
		super(parent);
	}
	
	public PersistenceException(String message, Exception parent){
		super(message, parent);
	}
}
