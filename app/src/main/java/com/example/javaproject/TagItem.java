package com.example.javaproject;

import android.os.Parcel;
import android.os.Parcelable;

public class TagItem implements Parcelable {

    public final String name;

    public TagItem(String name){
        this.name = name;
    }

    protected TagItem(Parcel in) {
        name = in.readString();
    }

    public static final Creator<TagItem> CREATOR = new Creator<TagItem>() {
        @Override
        public TagItem createFromParcel(Parcel in) {
            return new TagItem(in);
        }

        @Override
        public TagItem[] newArray(int size) {
            return new TagItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
