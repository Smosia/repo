package org.apache.commons.codec.language;
@java.lang.Deprecated()
public class DoubleMetaphone
  implements org.apache.commons.codec.StringEncoder
{
public class DoubleMetaphoneResult
{
public  DoubleMetaphoneResult(int maxLength) { throw new RuntimeException("Stub!"); }
public  void append(char value) { throw new RuntimeException("Stub!"); }
public  void append(char primary, char alternate) { throw new RuntimeException("Stub!"); }
public  void appendPrimary(char value) { throw new RuntimeException("Stub!"); }
public  void appendAlternate(char value) { throw new RuntimeException("Stub!"); }
public  void append(java.lang.String value) { throw new RuntimeException("Stub!"); }
public  void append(java.lang.String primary, java.lang.String alternate) { throw new RuntimeException("Stub!"); }
public  void appendPrimary(java.lang.String value) { throw new RuntimeException("Stub!"); }
public  void appendAlternate(java.lang.String value) { throw new RuntimeException("Stub!"); }
public  java.lang.String getPrimary() { throw new RuntimeException("Stub!"); }
public  java.lang.String getAlternate() { throw new RuntimeException("Stub!"); }
public  boolean isComplete() { throw new RuntimeException("Stub!"); }
}
public  DoubleMetaphone() { throw new RuntimeException("Stub!"); }
public  java.lang.String doubleMetaphone(java.lang.String value) { throw new RuntimeException("Stub!"); }
public  java.lang.String doubleMetaphone(java.lang.String value, boolean alternate) { throw new RuntimeException("Stub!"); }
public  java.lang.Object encode(java.lang.Object obj) throws org.apache.commons.codec.EncoderException { throw new RuntimeException("Stub!"); }
public  java.lang.String encode(java.lang.String value) { throw new RuntimeException("Stub!"); }
public  boolean isDoubleMetaphoneEqual(java.lang.String value1, java.lang.String value2) { throw new RuntimeException("Stub!"); }
public  boolean isDoubleMetaphoneEqual(java.lang.String value1, java.lang.String value2, boolean alternate) { throw new RuntimeException("Stub!"); }
public  int getMaxCodeLen() { throw new RuntimeException("Stub!"); }
public  void setMaxCodeLen(int maxCodeLen) { throw new RuntimeException("Stub!"); }
protected  char charAt(java.lang.String value, int index) { throw new RuntimeException("Stub!"); }
protected static  boolean contains(java.lang.String value, int start, int length, java.lang.String[] criteria) { throw new RuntimeException("Stub!"); }
protected int maxCodeLen;
}
