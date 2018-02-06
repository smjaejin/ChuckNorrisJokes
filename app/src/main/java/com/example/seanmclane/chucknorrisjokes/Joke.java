package com.example.seanmclane.chucknorrisjokes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by per6 on 1/23/18.
 */

public class Joke implements Parcelable {

    private String iconURL, id, url, value;

    public Joke() {

    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.iconURL);
        dest.writeString(this.id);
        dest.writeString(this.url);
        dest.writeString(this.value);
    }

    protected Joke(Parcel in) {
        this.iconURL = in.readString();
        this.id = in.readString();
        this.url = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<Joke> CREATOR = new Parcelable.Creator<Joke>() {
        @Override
        public Joke createFromParcel(Parcel source) {
            return new Joke(source);
        }

        @Override
        public Joke[] newArray(int size) {
            return new Joke[size];
        }
    };
}
