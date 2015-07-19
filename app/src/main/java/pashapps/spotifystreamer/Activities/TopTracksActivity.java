package pashapps.spotifystreamer.Activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import pashapps.spotifystreamer.Fragments.ArtistListFragment;
import pashapps.spotifystreamer.Fragments.TrackListFragment;
import pashapps.spotifystreamer.R;


public class TopTracksActivity extends ActionBarActivity {

    private String mArtistID;
    private TrackListFragment mTrackListFragment;
    private FragmentTransaction mFragmentTransaction;
    private String mArtistName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_tracks);

        setTitle("Top 10 Tracks");


        mTrackListFragment = new TrackListFragment();

        Bundle bundle;
        Intent intent = getIntent();
        bundle = intent.getExtras();
        mArtistID = bundle.get(ArtistListFragment.ARTISTID) + "";
        mArtistName = bundle.get(ArtistListFragment.ARTISTNAME) +"";
        mTrackListFragment.setVals(mArtistID,mArtistName);

        getFragmentManager().beginTransaction().
                add(R.id.trackFragmentContainer, mTrackListFragment).commit();
        getSupportActionBar().setSubtitle(mArtistName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_tracks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            mFragmentTransaction = getFragmentManager().beginTransaction().remove(mTrackListFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
