package com.android.hoolai.pack.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/27.
 */
public class LoginUser implements Parcelable {

    private String uid;
    private String token;
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    protected LoginUser(Parcel in) {
        uid = in.readString();
        token = in.readString();
        result = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(token);
        dest.writeString(result);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginUser> CREATOR = new Creator<LoginUser>() {
        @Override
        public LoginUser createFromParcel(Parcel in) {
            return new LoginUser(in);
        }

        @Override
        public LoginUser[] newArray(int size) {
            return new LoginUser[size];
        }
    };
}
