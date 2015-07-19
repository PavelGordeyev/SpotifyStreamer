package pashapps.spotifystreamer.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import pashapps.spotifystreamer.R;
import pashapps.spotifystreamer.TracksP;


public class TrackPlayerFragment extends DialogFragment {

    private TextView mArtistLabel;
    private TextView mAlbumLabel;
    private TextView mTrackLabel;
    private TextView mCurrentTimeLabel;
    private TextView mEndTimeLabel;
    private ImageView mAlbumImageView;
    private Button mPlayButton;
    private Button mPauseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private SeekBar mSeekBar;
    private String mArtist;
    private String mAlbum;
    private String mTrack;
    private String mImageID;
    private String mTrackURL;
    private int mPosition;
    private MediaPlayer mp;
    private Handler mHandler;
    private TracksP[] mTracksPs;

    public TrackPlayerFragment(){}

    private Runnable mSeekBarRunnable = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_track_player_fragment, container, false);
        mHandler = new Handler();

        //Track
        mArtistLabel = (TextView)view.findViewById(R.id.artistLabel);
        mAlbumLabel = (TextView)view.findViewById(R.id.albumLabel);
        mTrackLabel = (TextView)view.findViewById(R.id.trackLabel);
        mAlbumImageView = (ImageView)view.findViewById(R.id.albumImageView);

        //Seek Bar
        mCurrentTimeLabel = (TextView)view.findViewById(R.id.currentTimeLabel);
        mEndTimeLabel = (TextView)view.findViewById(R.id.endTimeLabel);
        mSeekBar = (SeekBar)view.findViewById(R.id.trackSeekBar);

        //Play Buttons
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

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, final boolean fromUser) {
                if(fromUser){
                    mp.seekTo(progress*1000);
                    mSeekBar.setProgress(progress*1000);
                    mCurrentTimeLabel.setText(timeConvert(progress*1000));
                }else{
                    updateProgress();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        try{
            mediaPlayerControl();
        }catch(NullPointerException npe){
        }


        //Button controls
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
                if(mPosition!=TrackListFragment.mResults.length-1) {
                    mPosition++;
                    trackControl(mPosition);
                    buttonToggle("play");
                }
                //trackControl(mPosition);
            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPosition>0) {
                    mPosition--;
                    trackControl(mPosition);
                    buttonToggle("play");
                }
                //trackControl(mPosition);
            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                buttonToggle("pause");
            }
        });

        getFragmentManager().beginTransaction().addToBackStack(null).commit();

        return view;
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    public void updateFragment(TracksP[] tracks,int position){
        mArtist = tracks[position].getArtistName();
        mAlbum = tracks[position].getAlbumName();
        mTrack = tracks[position].getTrackName();
        mImageID = tracks[position].getAlbumImageID();
        mTrackURL = tracks[position].getPreviewURL();
        mPosition = position;
        mTracksPs = tracks;
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

    //Go onto the next or previous track
    public void trackControl(int position){

        //User interface update to album, artist and track
        try {
            mTrackLabel.setText(TrackListFragment.mResults[position].getTrackName());
            mArtistLabel.setText(TrackListFragment.mResults[position].getArtistName());
            mAlbumLabel.setText(TrackListFragment.mResults[position].getAlbumName());
            mTrackURL = TrackListFragment.mResults[position].getPreviewURL();
            try {
                Picasso.with(getActivity()).load(TrackListFragment.mResults[position].getAlbumImageID()).into(mAlbumImageView);
            } catch (NullPointerException npe) {
                Log.d("IMAGE_ERR", npe.toString());
            }
        } catch (IndexOutOfBoundsException iob) {
            Log.d("TRACK_PLAYER", iob.toString());
        }

        //Media player controls to change song

        //Stop previous song
        mp.stop();
        mp.release();
        mediaPlayerControl();
    }

    public void mediaPlayerControl(){
        mp = new MediaPlayer();

        //Start current song
        try {
            mp.setDataSource(mTrackURL);
        } catch (IOException ioe) {
            Log.d("Media Player", ioe.toString());
        }
        mp.prepareAsync();

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //Set seek bar attributes
                mSeekBar.setMax(mp.getDuration() / 1000);
                mEndTimeLabel.setText(timeConvert(mp.getDuration()));
                mHandler.postDelayed(mSeekBarRunnable, 1000);
                mp.start();
            }
        });
    }

    public String timeConvert(int seconds){
        String timeString;

        seconds = seconds/1000;
        if(seconds<10) {
            timeString = "0:0" + seconds;
        }else{
            timeString = "0:" + seconds;
        }
        return timeString;
    }

    public int timeProgress(int currentPosition, int duration){


        double percentage = (double)currentPosition/(double)duration;
        int progress = (int) (percentage * mSeekBar.getMax());
        return progress;

    }

    private void updateProgress() {
        mHandler.removeCallbacks(mSeekBarRunnable);
        mSeekBar.setProgress(timeProgress(mp.getCurrentPosition(), mp.getDuration()));
        mCurrentTimeLabel.setText(timeConvert(mp.getCurrentPosition()));
        mHandler.postDelayed(mSeekBarRunnable,1000);
    }


}
