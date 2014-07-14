/*
 * Created on Feb 3, 2005
 *
 */
package org.realtor.rets.compliance.client.util;

/**
 * @author jthomas
 *
 */
public class InfiniteByteBuffer {
    byte buffer[] = new byte[1024];
    int position = 0;
    
    public void append(byte b) {
        if (position >= buffer.length) {
            byte newBuffer[] = new byte[buffer.length + (buffer.length / 4)];
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            buffer = newBuffer;
        }
        buffer[position++] = b;
    }
    
    public byte[] getBytes() {
        byte newBuffer[] = new byte[position];
        System.arraycopy(buffer, 0, newBuffer, 0, position);
        return newBuffer;
    }

    public String toString() {
        String content = new String(getBytes());
        return content;
    }
}
