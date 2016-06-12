/*
 * add by zhangzixiao JBLW-482 20131216 NewFeature:message blacklist
 */
package com.android.mms.ui;

public class BlackMessageItem {
	public static final int SMS = 1;
	public static final int MMS = 2;
	private Long _id;
	private Integer blacktextId;
	private Integer blackMsgType; // SMS or MMS
	private String address; // Inbox.address
	private Long date; // Inbox.DATE
	private Long dateSent; // Inbox.DATE_SENT
	private Integer protocol; // Inbox.PROTOCOL
	private Integer read; // Inbox.READ
	private Integer seen; // Inbox.SEEN
	private String subject; // Inbox.SUBJECT
	private String body; // Inbox.BODY
	private Integer replyPathPresent; // Inbox.REPLY_PATH_PRESENT
	private String serviceCenter; // Inbox.SERVICE_CENTER
	private Integer errorCode; // Inbox.ERROR_CODE
	private Long simId;
	private String retrieveText; // Mms.RETRIEVE_TEXT
	private String sub; // Mms.SUBJECT
	private Integer subjectCharset; // Mms.SUBJECT_CHARSET
	private Integer retrieveTextCharset; // Mms.RETRIEVE_TEXT_CHARSET
	private String contentLocation; // Mms.CONTENT_LOCATION
	private String contentType; // Mms.CONTENT_TYPE
	private String messageClass; // Mms.MESSAGE_CLASS
	private String messageId; // Mms.MESSAGE_ID
	private String responseText; // Mms.RESPONSE_TEXT
	private String transactionId; // Mms.TRANSACTION_ID
	private Integer contentClass; // Mms.CONTENT_CLASS
	private Integer deliveryReport; // Mms.DELIVERY_REPORT
	private Integer messageType; // Mms.MESSAGE_TYPE
	private Integer mmsVersion; // Mms.MMS_VERSION
	private Integer priority; // Mms.PRIORITY
	private Integer readReport; // Mms.READ_REPORT
	private Integer readStatus; // Mms.READ_STATUS
	private Integer reportAllowed; // Mms.REPORT_ALLOWED
	private Integer reportStatus; // Mms.REPORT_STATUS
	private Integer retrieveStatus; // Mms.RETRIEVE_STATUS
	private Integer status; // Mms.STATUS
	private Long deliveryTime; // Mms.DELIVERY_TIME
	private Long expiry; // Mms.EXPIRY
	private Integer messageSize; // Mms.MESSAGE_SIZE
	private Integer flag1;
	private Integer flag2;
	private Integer flag3;
	public boolean checked = false;

	public Long getId() {
		return _id;
	}

	public void setId(Long _id) {
		this._id = _id;
	}

	public Integer getBlacktextId() {
		return blacktextId;
	}

	public void setBlacktextId(Integer blacktextId) {
		this.blacktextId = blacktextId;
	}

	public Integer getBlackMsgType() {
		return blackMsgType;
	}

	public void setBlackMsgType(Integer blackMsgType) {
		this.blackMsgType = blackMsgType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Long getDateSent() {
		return dateSent;
	}

	public void setDateSent(Long dateSent) {
		this.dateSent = dateSent;
	}

	public Integer getProtocol() {
		return protocol;
	}

	public void setProtocol(Integer protocol) {
		this.protocol = protocol;
	}

	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public Integer getSeen() {
		return seen;
	}

	public void setSeen(Integer seen) {
		this.seen = seen;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Integer getReplyPathPresent() {
		return replyPathPresent;
	}

	public void setReplyPathPresent(Integer replyPathPresent) {
		this.replyPathPresent = replyPathPresent;
	}

	public String getServiceCenter() {
		return serviceCenter;
	}

	public void setServiceCenter(String serviceCenter) {
		this.serviceCenter = serviceCenter;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

    public Long getSimId() {
		return simId;
	}

	public void setSimId(Long simId) {
		this.simId = simId;
	}

	public String getRetrieveText() {
		return retrieveText;
	}

	public void setRetrieveText(String retrieveText) {
		this.retrieveText = retrieveText;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public Integer getSubjectCharset() {
		return subjectCharset;
  	}

	public void setSubjectCharset(Integer subjectCharset) {
	  	this.subjectCharset = subjectCharset;
	}

	public Integer getRetrieveTextCharset() {
		return retrieveTextCharset;
	}

	public void setRetrieveTextCharset(Integer retrieveTextCharset) {
		this.retrieveTextCharset = retrieveTextCharset;
	}

	public String getContentLocation() {
		return contentLocation;
	}

	public void setContentLocation(String contentLocation) {
		this.contentLocation = contentLocation;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getMessageClass() {
		return messageClass;
	}

	public void setMessageClass(String messageClass) {
		this.messageClass = messageClass;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getContentClass() {
		return contentClass;
	}

	public void setContentClass(Integer contentClass) {
		this.contentClass = contentClass;
	}

	public Integer getDeliveryReport() {
		return deliveryReport;
	}

	public void setDeliveryReport(Integer deliveryReport) {
		this.deliveryReport = deliveryReport;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public Integer getMmsVersion() {
		return mmsVersion;
	}

	public void setMmsVersion(Integer mmsVersion) {
		this.mmsVersion = mmsVersion;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getReadReport() {
		return readReport;
	}

	public void setReadReport(Integer readReport) {
		this.readReport = readReport;
	}

	public Integer getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
	}

	public Integer getReportAllowed() {
		return reportAllowed;
	}

	public void setReportAllowed(Integer reportAllowed) {
		this.reportAllowed = reportAllowed;
	}

	public Integer getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}

	public Integer getRetrieveStatus() {
		return retrieveStatus;
	}

	public void setRetrieveStatus(Integer retrieveStatus) {
		this.retrieveStatus = retrieveStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Long deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Long getExpiry() {
		return expiry;
	}

	public void setExpiry(Long expiry) {
		this.expiry = expiry;
	}

	public Integer getMessageSize() {
		return messageSize;
	}

	public void setMessageSize(Integer messageSize) {
		this.messageSize = messageSize;
	}

	public Integer getFlag1() {
		return flag1;
	}

	public void setFlag1(Integer flag1) {
		this.flag1 = flag1;
	}

	public Integer getFlag2() {
		return flag2;
	}

	public void setFlag2(Integer flag2) {
		this.flag2 = flag2;
	}

	public Integer getFlag3() {
		return flag3;
	}

	public void setFlag3(Integer flag3) {
		this.flag3 = flag3;
	}

}
