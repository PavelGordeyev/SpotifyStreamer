package pashapps.spotifystreamer;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class TrackPlayerFragment extends Fragment{

    private TextView mArtistLabel;
    private TextView mAlbumLabel;
    private TextView mTrackLabel;
    private ImageView mAlbumImageView;
    private String mArtist;
    private String mAlbum;
    private String mTrack;
    private String mImageID;

    public TrackPlayerFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_track_player_fragment, container, false);



        mArtistLabel = (TextView)view.findViewById(R.id.artistLabel);
        mAlbumLabel = (TextView)view.findViewById(R.id.albumLabel);
        mTrackLabel = (TextView)view.findViewById(R.id.trackLabel);
        mAlbumImageView = (ImageView)view.findViewById(R.id.albumImageView);

        mArtistLabel.setText(mArtist);
        mAlbumLabel.setText(mAlbum);
        mTrackLabel.setText(mTrack);

        try {
            Picasso.with(getActivity()).load(mImageID).into(mAlbumImageView);
        } catch(NullPointerException npe) {
            Log.d("IMAGE_ERR", npe.toString());
        }

        return view;
    }

    public void updateFragment(String artist,String album,String track,String imageID){
        mArtist = artist;
        mAlbum = album;
        mTrack = track;
        mImageID = imageID;
    }

}
