package com.example.estimotedrawer.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Review implements Serializable, Parcelable {
    private String comment;
    private int img;

    public Review() {
    }


    protected Review(Parcel in) {
        comment = in.readString();
        img = in.readInt();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Review{" +
                "comment='" + comment + '\'' +
                ", img=" + img +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.comment);
        dest.writeInt(this.img);
    }
}
