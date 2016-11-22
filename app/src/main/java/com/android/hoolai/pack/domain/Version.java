package com.android.hoolai.pack.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/31.
 */
public class Version implements Parcelable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAndroidTarget() {
        return androidTarget;
    }

    public void setAndroidTarget(String androidTarget) {
        this.androidTarget = androidTarget;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    private int id;
    private int productId;
    private String versionName;
    private String androidTarget;
    private String apkPath;

    protected Version(Parcel in) {
        id = in.readInt();
        productId = in.readInt();
        versionName = in.readString();
        androidTarget = in.readString();
        apkPath = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(productId);
        dest.writeString(versionName);
        dest.writeString(androidTarget);
        dest.writeString(apkPath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Version> CREATOR = new Creator<Version>() {
        @Override
        public Version createFromParcel(Parcel in) {
            return new Version(in);
        }

        @Override
        public Version[] newArray(int size) {
            return new Version[size];
        }
    };
}
