package com.skyworthauto.speak;
import android.os.Parcel;
import android.os.Parcelable;

public class CmdInfo implements Parcelable{
	
	private String cmdKey;
	private String cmdArray;

	public CmdInfo(String key,String array){
		cmdKey =  key;
		cmdArray = array;
	}

	public String getCmdKey() {
		return cmdKey;
	}

	public void setCmdKey(String cmdKey) {
		this.cmdKey = cmdKey;
	}

	public String getCmdArray() {
		return cmdArray;
	}

	public void setCmdArray(String cmdArray) {
		this.cmdArray = cmdArray;
	}

	protected CmdInfo(Parcel in) {
		cmdKey = in.readString();
		cmdArray = in.readString();
	}

	public static final Creator<CmdInfo> CREATOR = new Creator<CmdInfo>() {
		@Override
		public CmdInfo createFromParcel(Parcel in) {
			return new CmdInfo(in);
		}

		@Override
		public CmdInfo[] newArray(int size) {
			return new CmdInfo[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(cmdKey);
		dest.writeString(cmdArray);
	}

	public void readFromParcel(Parcel _reply) {
		// TODO Auto-generated method stub

	}
}
