package com.dawn.dawning.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ProgramDetail {
    @SerializedName("program_id")
    private int programId;
    @SerializedName("program_name")
    private String programName;
    @SerializedName("upload_time")
    private Date uploadTime;
    @SerializedName("play_count")
    private int playCount;
    private String duration;

    public ProgramDetail(int programId, String programName, Date uploadTime, int playCount, String duration) {
        this.programId = programId;
        this.programName = programName;
        this.uploadTime = uploadTime;
        this.playCount = playCount;
        this.duration = duration;
    }

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

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ProgramDetail{" +
                "programId=" + programId +
                ", programName=" + programName +
                ", uploadTime=" + uploadTime +
                ", playCount=" + playCount +
                ", duration=" + duration +
                '}';
    }
}
