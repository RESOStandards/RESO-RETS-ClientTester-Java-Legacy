/*
 * Created on Jul 14, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.realtor.rets.compliance.client.http.filters;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.realtor.rets.compliance.client.persistence.PersistenceException;
import org.realtor.rets.compliance.client.user.UserNotFoundException;


abstract class Interceptor extends Thread {
    boolean inputDone = false;

    InputStream inputStream;

    OutputStream outputStream;

    Interceptor(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    abstract void bufferInput(byte buffer[], int len)
            throws IOException, UserNotFoundException, PersistenceException;

}