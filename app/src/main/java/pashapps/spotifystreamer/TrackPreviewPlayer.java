package pashapps.spotifystreamer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class TrackPreviewPlayer extends ActionBarActivity {

    private TrackPlayerFragment mTrackPlayerFragment;
    private String mArtistName;
    private String mTrackName;
    private String mAlbumName;
    private String mImageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_preview_player);

        Bundle bundle;
        Intent intent = getIntent();
        bundle = intent.getExtras();

        mArtistName = bundle.get(TrackListFragment.ARTIST) + "";
        mTrackName = bundle.get(TrackListFragment.TRACK) + "";
        mAlbumName = bundle.get(TrackListFragment.ALBUM) + "";
        mImageID = bundle.get(TrackListFragment.IMAGEID) + "";



        mTrackPlayerFragment = new TrackPlayerFragment();
        mTrackPlayerFragment.updateFragment(mArtistName,mAlbumName,mTrackName,mImageID);
        getFragmentManager().beginTransaction().
                add(R.id.trackPlayerContainer, mTrackPlayerFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_track_preview_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
