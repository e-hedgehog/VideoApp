package com.ehedgehog.android.videoapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable {

    private String mTitle;
    private String mLocation;
    private long mDuration;
    private long mDateAdded;

    public Video(String title, String location, long duration, long dateAdded) {
        mTitle = title;
        mLocation = location;
        mDuration = duration;
        mDateAdded = dateAdded;
    }

    public Video() {
    }

    private Video(Parcel in) {
        mTitle = in.readString();
        mLocation = in.readString();
        mDuration = in.readLong();
        mDateAdded = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mLocation);
        dest.writeLong(mDuration);
        dest.writeLong(mDateAdded);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public long getDateAdded() {
        return mDateAdded;
    }

    public void setDateAdded(long dateAdded) {
        mDateAdded = dateAdded;
    }
}
