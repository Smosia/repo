package com.android.ota;
import android.os.Parcel;
import android.os.Parcelable;
public class UpdateInfoEntity implements Parcelable{

	private String old_Version;
	private String new_Version;
	private String description;
	private String downloadURL;
	private String size;
	private String priority;
	private String sessionId;
	private int downloadByte;
	private String packageType;
	public int getDownloadByte() {
		return downloadByte;
	}
	public void setDownloadByte(int downloadByte) {
		this.downloadByte = downloadByte;
	}
	public String getOld_Version() {
		return old_Version;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public void setOld_Version(String old_Version) {
		this.old_Version = old_Version;
	}
	public String getNew_Version() {
		return new_Version;
	}
	public void setNew_Version(String new_Version) {
		this.new_Version = new_Version;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDownloadURL() {
		return downloadURL;
	}
	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


	@Override
	public int describeContents() {		
		return 0;
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(old_Version);
		out.writeString(new_Version);
		out.writeString(description);
		out.writeString(downloadURL);
		out.writeString(size);
		out.writeString(priority);
		out.writeString(sessionId);
		out.writeInt(downloadByte);
		out.writeString(packageType);
		
		
	}
	public static final Parcelable.Creator<UpdateInfoEntity> CREATOR=new Parcelable.Creator<UpdateInfoEntity>() {

		@Override
		public UpdateInfoEntity createFromParcel(Parcel source) {
			UpdateInfoEntity upinfo=new UpdateInfoEntity();
			upinfo.old_Version=source.readString();		
			upinfo.new_Version=source.readString();
			upinfo.description=source.readString();
			upinfo.downloadURL=source.readString();
			upinfo.size=source.readString();
			upinfo.priority=source.readString();
			upinfo.sessionId=source.readString();
			upinfo.downloadByte=source.readInt();
			upinfo.packageType=source.readString();
			return upinfo;
		}

		@Override
		public UpdateInfoEntity[] newArray(int size) {
			
			return new UpdateInfoEntity[size];
		}
	};
}
