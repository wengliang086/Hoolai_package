package com.android.hoolai.pack.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/31.
 */
public class Package implements Parcelable {

    public static final Integer STATUS_PACKAGING = 1;
    public static final Integer STATUS_PACKAGE_FAIELD = 2;
    public static final Integer STATUS_PACKAGE_SUCCESSED = 3;

    private int id;
    private int productId;
    private int channelId;
    private String channel;
    private String version;
    private String desription;
    private int status;
    private String downloadPath;
    private String packageDate;
    private int releaseStatus;
    private String mark;

    protected Package(Parcel in) {
        id = in.readInt();
        productId = in.readInt();
        channelId = in.readInt();
        channel = in.readString();
        version = in.readString();
        desription = in.readString();
        status = in.readInt();
        downloadPath = in.readString();
        packageDate = in.readString();
        releaseStatus = in.readInt();
        mark = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(productId);
        dest.writeInt(channelId);
        dest.writeString(channel);
        dest.writeString(version);
        dest.writeString(desription);
        dest.writeInt(status);
        dest.writeString(downloadPath);
        dest.writeString(packageDate);
        dest.writeInt(releaseStatus);
        dest.writeString(mark);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Package> CREATOR = new Creator<Package>() {
        @Override
        public Package createFromParcel(Parcel in) {
            return new Package(in);
        }

        @Override
        public Package[] newArray(int size) {
            return new Package[size];
        }
    };

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

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getPackageDate() {
        return packageDate;
    }

    public void setPackageDate(String packageDate) {
        this.packageDate = packageDate;
    }

    public int getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(int releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
