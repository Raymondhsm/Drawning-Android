package com.dawn.dawning.Entity;

import com.google.gson.annotations.SerializedName;

public class Program {
    @SerializedName("program_id")
    private int programId;
    @SerializedName("program_name")
    private String programName;
    @SerializedName("radio_name")
    private String radioName;
    @SerializedName("cover_link")
    private String coverLink;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
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

    public Program(int programId, String programName, String radioName, String coverLink) {

        this.programId = programId;
        this.programName = programName;
        this.radioName = radioName;
        this.coverLink = coverLink;
    }
}
