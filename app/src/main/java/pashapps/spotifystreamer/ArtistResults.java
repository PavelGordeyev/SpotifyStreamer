package pashapps.spotifystreamer;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by pavelG on 6/9/15.
 */
public class ArtistResults {

    private List<Artist> mResults;
    private int mPosition;

    public ArtistResults(List<Artist> results,int position) {
        mResults = results;
        mPosition = position;
    }

    public void setPosition(int position){
        mPosition = position;
    }

    public String getArtistName() {
        return mResults.get(mPosition).name;
    }

    public String getArtistID() {
        return mResults.get(mPosition).id;
    }

    public String getArtistImageURL() {
        return mResults.get(mPosition).images.get(0).url;
    }
}
