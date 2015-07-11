package pashapps.spotifystreamer.Fragments;

import android.app.Fragment;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.InjectView;
import kaaes.spotify.webapi.android.models.Image;
import pashapps.spotifystreamer.R;


public class TrackPlayerFragment extends Fragment{

    private TextView mArtistLabel;
    private TextView mAlbumLabel;
    private TextView mTrackLabel;
    private ImageView mAlbumImageView;
    private Button mPlayButton;
    private Button mPauseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private String mArtist;
    private String mAlbum;
    private String mTrack;
    private String mImageID;
    private String mTrackURL;
    private MediaPlayer mp;


    public TrackPlayerFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_track_player_fragment, container, false);

        mArtistLabel = (TextView)view.findViewById(R.id.artistLabel);
        mAlbumLabel = (TextView)view.findViewById(R.id.albumLabel);
        mTrackLabel = (TextView)view.findViewById(R.id.trackLabel);
        mAlbumImageView = (ImageView)view.findViewById(R.id.albumImageView);
        mPlayButton = (Button)view.findViewById(R.id.playButton);
        mPauseButton = (Button)view.findViewById(R.id.pauseButton);
        mNextButton = (Button)view.findViewById(R.id.nextButton);
        mPreviousButton = (Button)view.findViewById(R.id.previousButton);

        //When created hide play button
        mPlayButton.setVisibility(View.INVISIBLE);

        //Set the song information on the screen
        mArtistLabel.setText(mArtist);
        mAlbumLabel.setText(mAlbum);
        mTrackLabel.setText(mTrack);

        try {
            Picasso.with(getActivity()).load(mImageID).into(mAlbumImageView);
        } catch(NullPointerException npe) {
            Log.d("IMAGE_ERR", npe.toString());
        }

        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mp.setDataSource(mTrackURL);
        }catch(IOException ioe){
            Log.d("Media Player",ioe.toString());
        }
        mp.prepareAsync();
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonToggle("pause");
                mp.pause();
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonToggle("play");
                mp.start();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    public void updateFragment(String artist,String album,String track,String imageID, String trackURL){
        mArtist = artist;
        mAlbum = album;
        mTrack = track;
        mImageID = imageID;
        mTrackURL = trackURL;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mp.stop();
    }

    //Hides/shows play or pause buttons
    public void buttonToggle(String button){
        if(button=="play"){
            mPlayButton.setVisibility(View.INVISIBLE);
            mPauseButton.setVisibility(View.VISIBLE);
        }else{
            mPauseButton.setVisibility(View.INVISIBLE);
            mPlayButton.setVisibility(View.VISIBLE);
        }
    }
}
