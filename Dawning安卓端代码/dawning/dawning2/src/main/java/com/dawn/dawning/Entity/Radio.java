package com.dawn.dawning.Entity;

import com.google.gson.annotations.SerializedName;

public class Radio {
    @SerializedName("radio_id")
    private int radioId;
    @SerializedName("radio_name")
    private String radioName;
    @SerializedName("cover_link")
    private String coverLink;
    @SerializedName("subscribe_count")
    private int subscribeCount;

    public int getRadioId() {
        return radioId;
    }

    public void setRadioId(int radioId) {
        this.radioId = radioId;
    }

    public String getRadioName() {
        return radioName;
    }

    public void setRadioName(String radioName) {
        this.radioName = radioName;
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }

    public int getSubscribeCount() {
        return subscribeCount;
    }

    public void setSubscribeCount(int subscribeCount) {
        this.subscribeCount = subscribeCount;
    }

    public Radio(int radioId, String radioName, String coverLink, int subscribeCount) {

        this.radioId = radioId;
        this.radioName = radioName;
        this.coverLink = coverLink;
        this.subscribeCount = subscribeCount;
    }
}
