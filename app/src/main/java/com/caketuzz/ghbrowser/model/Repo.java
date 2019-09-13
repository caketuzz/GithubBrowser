package com.caketuzz.ghbrowser.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Repo implements Parcelable {

    @SerializedName("id")
    private String repoId;

    private String name;

    @SerializedName("full_name")
    private String fullName;

    private RepoOwner owner;

    private String description;

    @SerializedName("stargazers_count")
    private int StargazersCount;

    @SerializedName("forks_count")
    private int forksCount;

    private String url;

    public String getUrl() {
        return url;
    }

    public String getRepoId() {
        return repoId;
    }

    public String getDescription() {
        return description;
    }

    public int getStargazersCount() {
        return StargazersCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    public boolean isFork() {
        return isFork;
    }

    @SerializedName("fork")
    private boolean isFork;

    public Repo() {
    }

    public String getId() {
        return repoId;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public RepoOwner getOwner() {
        return owner;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append("-")
                .append(repoId).append("-")
                .append(fullName);
        return builder.toString();
    }

    //// Parcelable stuff ////
    public Repo(Parcel in) {
        repoId = in.readString();
        name = in.readString();
        fullName = in.readString();
        isFork = in.readByte() != 0;
        owner = in.readParcelable(RepoOwner.class.getClassLoader());
    }

    public static final Creator<Repo> CREATOR = new Creator<Repo>() {
        @Override
        public Repo createFromParcel(Parcel in) {
            return new Repo(in);
        }

        @Override
        public Repo[] newArray(int size) {
            return new Repo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(repoId);
        parcel.writeString(name);
        parcel.writeString(fullName);
        parcel.writeByte((byte) (isFork ? 1 : 0));
        parcel.writeParcelable(owner, i);
    }
}