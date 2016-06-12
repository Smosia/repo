/*
 * add by zhangzixiao JBLW-482 20131216 NewFeature:message blacklist
 */

package com.google.android.mms.pdu;

import com.google.android.mms.InvalidHeaderValueException;

public class BlackPduHeaders {
    private PduHeaders mPduHeaders;
	
    BlackPduHeaders(PduHeaders mPduHeaders) {
	this.mPduHeaders = mPduHeaders;
    }
	
    /**
     * Get octet value by header field.
     *
     * @param field the field
     * @return the octet value of the pdu header
     *          with specified header field. Return 0 if
     *          the value is not set.
     */
    public int getOctet(int field) {
        return mPduHeaders.getOctet(field);
    }

    /**
     * Set octet value to pdu header by header field.
     *
     * @param value the value
     * @param field the field
     * @throws InvalidHeaderValueException if the value is invalid.
     */
    public void setOctet(int value, int field)
            throws InvalidHeaderValueException{
    	mPduHeaders.setOctet(value, field);
    }

    /**
     * Get TextString value by header field.
     *
     * @param field the field
     * @return the TextString value of the pdu header
     *          with specified header field
     */
    public byte[] getTextString(int field) {
        return mPduHeaders.getTextString(field);
    }

    /**
     * Set TextString value to pdu header by header field.
     *
     * @param value the value
     * @param field the field
     * @return the TextString value of the pdu header
     *          with specified header field
     * @throws NullPointerException if the value is null.
     */
    public void setTextString(byte[] value, int field) {
    	mPduHeaders.setTextString(value, field);
    }

    /**
     * Get EncodedStringValue value by header field.
     *
     * @param field the field
     * @return the EncodedStringValue value of the pdu header
     *          with specified header field
     */
    public EncodedStringValue getEncodedStringValue(int field) {
        return mPduHeaders.getEncodedStringValue(field);
    }

    /**
     * Get TO, CC or BCC header value.
     *
     * @param field the field
     * @return the EncodeStringValue array of the pdu header
     *          with specified header field
     */
    public EncodedStringValue[] getEncodedStringValues(int field) {
        return mPduHeaders.getEncodedStringValues(field);
    }

    /**
     * Set EncodedStringValue value to pdu header by header field.
     *
     * @param value the value
     * @param field the field
     * @return the EncodedStringValue value of the pdu header
     *          with specified header field
     * @throws NullPointerException if the value is null.
     */
    public void setEncodedStringValue(EncodedStringValue value, int field) {
    	mPduHeaders.setEncodedStringValue(value, field);
    }

    /**
     * Set TO, CC or BCC header value.
     *
     * @param value the value
     * @param field the field
     * @return the EncodedStringValue value array of the pdu header
     *          with specified header field
     * @throws NullPointerException if the value is null.
     */
    public void setEncodedStringValues(EncodedStringValue[] value, int field) {
    	mPduHeaders.setEncodedStringValues(value, field);
    }

    /**
     * Append one EncodedStringValue to another.
     *
     * @param value the EncodedStringValue to append
     * @param field the field
     * @throws NullPointerException if the value is null.
     */
    public void appendEncodedStringValue(EncodedStringValue value,
                                    int field) {
    	mPduHeaders.appendEncodedStringValue(value, field);
    }

    /**
     * Get LongInteger value by header field.
     *
     * @param field the field
     * @return the LongInteger value of the pdu header
     *          with specified header field. if return -1, the
     *          field is not existed in pdu header.
     */
    public long getLongInteger(int field) {
        return mPduHeaders.getLongInteger(field);
    }

    /**
     * Set LongInteger value to pdu header by header field.
     *
     * @param value the value
     * @param field the field
     */
    protected void setLongInteger(long value, int field) {
    	mPduHeaders.setLongInteger(value, field);
    }
    
}
