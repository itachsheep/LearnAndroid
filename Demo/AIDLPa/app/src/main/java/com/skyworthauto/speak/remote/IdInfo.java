package com.skyworthauto.speak.remote;

import android.os.Parcel;
import android.os.Parcelable;

public class IdInfo implements Parcelable {
	private String id;
    
    public IdInfo(String id){
    	this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    protected IdInfo(Parcel in) {
        id = in.readString();
        
    }

    public static final Creator<IdInfo> CREATOR = new Creator<IdInfo>() {
        @Override
        public IdInfo createFromParcel(Parcel in) {
            return new IdInfo(in);
        }

        @Override
        public IdInfo[] newArray(int size) {
            return new IdInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        
    }
}
