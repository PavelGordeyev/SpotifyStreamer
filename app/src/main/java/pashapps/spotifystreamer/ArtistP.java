package pashapps.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by pavelG on 6/30/15.
 */
public class ArtistP implements Parcelable {

    private ArtistsPager mResults;
    private String mName;
    private String mArtistID;
    private String mImageID;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getArtistID() {
        return mArtistID;
    }

    public void setArtistID(String artistID) {
        mArtistID = artistID;
    }

    public String getImageID() {
        return mImageID;
    }

    public void setImageID(String imageID) {
        mImageID = imageID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mArtistID);
        dest.writeString(mImageID);
    }

    private ArtistP(Parcel in) {
        mName = in.readString();
        mArtistID = in.readString();
        mImageID = in.readString();
    }

    public ArtistP(){}


    public static final Creator<ArtistP> CREATOR = new Creator<ArtistP>() {
        @Override
        public ArtistP createFromParcel(Parcel source) {
            return new ArtistP(source);
        }

        @Override
        public ArtistP[] newArray(int size) {
            return new ArtistP[size];
        }
    };

}
