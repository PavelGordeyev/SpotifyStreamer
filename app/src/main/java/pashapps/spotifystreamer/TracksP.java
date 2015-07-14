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
    private String mPreviewURL;
    private String mArtistName;

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

    public String getPreviewURL() {
        return mPreviewURL;
    }

    public void setPreviewURL(String previewURL) {
        mPreviewURL = previewURL;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public void setArtistName(String artistName) {
        mArtistName = artistName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAlbumName);
        dest.writeString(mArtistName);
        dest.writeString(mTrackName);
        dest.writeString(mAlbumImageID);
        dest.writeString(mPreviewURL);
    }

    private TracksP(Parcel in){
        mAlbumName = in.readString();
        mArtistName = in.readString();
        mTrackName = in.readString();
        mAlbumImageID = in.readString();
        mPreviewURL = in.readString();
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
