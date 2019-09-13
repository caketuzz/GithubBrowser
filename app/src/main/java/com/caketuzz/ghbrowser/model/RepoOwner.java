package com.caketuzz.ghbrowser.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RepoOwner implements Parcelable {

    @SerializedName("avatar_url")
    private String avatarUrl;

    private String login;

    private String id;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public String getId() {
        return id;
    }

    //// Parcelable stuff ////
    protected RepoOwner(Parcel in) {
        avatarUrl = in.readString();
        login = in.readString();
        id = in.readString();
    }

    public static final Creator<RepoOwner> CREATOR = new Creator<RepoOwner>() {
        @Override
        public RepoOwner createFromParcel(Parcel in) {
            return new RepoOwner(in);
        }

        @Override
        public RepoOwner[] newArray(int size) {
            return new RepoOwner[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(avatarUrl);
        parcel.writeString(login);
        parcel.writeString(id);
    }
}
