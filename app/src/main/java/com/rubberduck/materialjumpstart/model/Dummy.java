package com.rubberduck.materialjumpstart.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by akshayt on 28/04/15.
 */
public class Dummy implements Parcelable {
    private int id;
    private String header;
    private String headerText;
    private String subheader;
    private String subheaderText;
    private int likesCount;
    private String imageUri;
    private String largeImageUri;

    public Dummy(int id, String header, String headerText, String subheader, String subheaderText, int likesCount) {
        this.id = id;
        this.header = header;
        this.headerText = headerText;
        this.subheader = subheader;
        this.subheaderText = subheaderText;
        this.likesCount = likesCount;
        this.imageUri = "@drawable/img_" + id;
        this.largeImageUri = "@drawable/img_large_" + id;
    }

    public int getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public String getHeaderText() {
        return headerText;
    }

    public String getSubheader() {
        return subheader;
    }

    public String getSubheaderText() {
        return subheaderText;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getLargeImageUri() {
        return largeImageUri;
    }

    protected Dummy(Parcel in) {
        id = in.readInt();
        header = in.readString();
        headerText = in.readString();
        subheader = in.readString();
        subheaderText = in.readString();
        likesCount = in.readInt();
        imageUri = in.readString();
        largeImageUri = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(header);
        dest.writeString(headerText);
        dest.writeString(subheader);
        dest.writeString(subheaderText);
        dest.writeInt(likesCount);
        dest.writeString(imageUri);
        dest.writeString(largeImageUri);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Dummy> CREATOR = new Parcelable.Creator<Dummy>() {
        @Override
        public Dummy createFromParcel(Parcel in) {
            return new Dummy(in);
        }

        @Override
        public Dummy[] newArray(int size) {
            return new Dummy[size];
        }
    };
}
