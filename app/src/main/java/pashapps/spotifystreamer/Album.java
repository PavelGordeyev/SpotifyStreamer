package pashapps.spotifystreamer;

/**
 * Created by pavelG on 6/9/15.
 */
public class Album {

    private String mName;


    public Album(String name){
        mName = name;
    }

    public String getName(){
        return mName;
    }

    public void setName(String name){
        mName = name;
    }
}
