package com.learning.sami.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RecipeSteps implements Parcelable {

    protected RecipeSteps(Parcel in) {
        mId = in.readInt();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoUrl = in.readString();
        mThumbnailUrl = in.readString();
    }

    public static final Creator<RecipeSteps> CREATOR = new Creator<RecipeSteps>() {
        @Override
        public RecipeSteps createFromParcel(Parcel in) {
            return new RecipeSteps(in);
        }

        @Override
        public RecipeSteps[] newArray(int size) {
            return new RecipeSteps[size];
        }
    };

    @SerializedName("id")
    protected int mId;
    public int getmId() { return mId; }
    public void setmId(int mId) { this.mId = mId; }

    @SerializedName("shortDescription")
    protected String mShortDescription;
    public String getmShortDescription() { return mShortDescription; }
    public void setmShortDescription(String mShortDescription) { this.mShortDescription = mShortDescription; }

    @SerializedName("description")
    protected String mDescription;
    public String getmDescription() { return mDescription; }
    public void setmDescription(String mDescription) { this.mDescription = mDescription; }

    @SerializedName("videoURL")
    protected String mVideoUrl;
    public String getmVideoUrl() { return mVideoUrl; }
    public void setmVideoUrl(String mVideoUrl) { this.mVideoUrl = mVideoUrl; }

    @SerializedName("thumbnailURL")
    protected String mThumbnailUrl;
    public String getmThumbnailUrl() { return mThumbnailUrl; }
    public void setmThumbnailUrl(String mThumbnailUrl) { this.mThumbnailUrl = mThumbnailUrl; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeString(mVideoUrl);
        dest.writeString(mThumbnailUrl);
    }
}

