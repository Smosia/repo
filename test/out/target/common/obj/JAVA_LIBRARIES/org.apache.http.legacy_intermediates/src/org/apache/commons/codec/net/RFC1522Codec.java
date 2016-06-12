package org.apache.commons.codec.net;
@java.lang.Deprecated()
 abstract class RFC1522Codec
{
RFC1522Codec() { throw new RuntimeException("Stub!"); }
protected  java.lang.String encodeText(java.lang.String text, java.lang.String charset) throws org.apache.commons.codec.EncoderException, java.io.UnsupportedEncodingException { throw new RuntimeException("Stub!"); }
protected  java.lang.String decodeText(java.lang.String text) throws org.apache.commons.codec.DecoderException, java.io.UnsupportedEncodingException { throw new RuntimeException("Stub!"); }
protected abstract  java.lang.String getEncoding();
protected abstract  byte[] doEncoding(byte[] bytes) throws org.apache.commons.codec.EncoderException;
protected abstract  byte[] doDecoding(byte[] bytes) throws org.apache.commons.codec.DecoderException;
}
