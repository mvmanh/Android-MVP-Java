package com.mvm.modelviewpresenter.model;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable {
    public String title;
    public String link;

    public News(String title, String link) {
        this.title = title;
        this.link = link;
    }

    protected News(Parcel in) {
        title = in.readString();
        link = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
