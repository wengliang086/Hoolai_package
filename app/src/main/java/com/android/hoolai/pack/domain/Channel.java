package com.android.hoolai.pack.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/31.
 */
public class Channel implements Parcelable {

    private int id;
    private String channel;
    private String channelName;
    private int productId;
    private String packageName;
    private int signId;
    private String icon;
    private String description;
    private int nodeId;
    private String webUrl;
    private String extendInfo;

    protected Channel(Parcel in) {
        id = in.readInt();
        channel = in.readString();
        channelName = in.readString();
        productId = in.readInt();
        packageName = in.readString();
        signId = in.readInt();
        icon = in.readString();
        description = in.readString();
        nodeId = in.readInt();
        webUrl = in.readString();
        extendInfo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(channel);
        dest.writeString(channelName);
        dest.writeInt(productId);
        dest.writeString(packageName);
        dest.writeInt(signId);
        dest.writeString(icon);
        dest.writeString(description);
        dest.writeInt(nodeId);
        dest.writeString(webUrl);
        dest.writeString(extendInfo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getSignId() {
        return signId;
    }

    public void setSignId(int signId) {
        this.signId = signId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }
}
