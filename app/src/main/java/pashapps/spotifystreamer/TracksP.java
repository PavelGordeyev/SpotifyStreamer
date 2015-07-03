package pashapps.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pavelG on 7/3/15.
 */
public class TracksP implements Parcelable {

    private String mAlbumName;
    private String mTrackName;
    private String mAlbumImageID;

    public String getAlbumImageID() {
        return mAlbumImageID;
    }

    public void setAlbumImageID(String albumImageID) {
        mAlbumImageID = albumImageID;
    }

    public String getAlbumName() {
        return mAlbumName;
    }

    public void setAlbumName(String albumName) {
        mAlbumName = albumName;
    }

    public String getTrackName() {
        return mTrackName;
    }

    public void setTrackName(String trackName) {
        mTrackName = trackName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAlbumName);
        dest.writeString(mTrackName);
        dest.writeString(mAlbumImageID);
    }

    private TracksP(Parcel in){
        mAlbumName = in.readString();
        mTrackName = in.readString();
        mAlbumImageID = in.readString();
    }

    public TracksP(){}

    public static final Creator<TracksP> CREATOR = new Creator<TracksP>() {
        @Override
        public TracksP createFromParcel(Parcel source) {
            return new TracksP(source);
        }

        @Override
        public TracksP[] newArray(int size) {
            return new TracksP[size];
        }
    };
}
