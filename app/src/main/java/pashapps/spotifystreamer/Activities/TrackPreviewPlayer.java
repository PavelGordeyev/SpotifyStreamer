package pashapps.spotifystreamer.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import pashapps.spotifystreamer.Fragments.TrackListFragment;
import pashapps.spotifystreamer.Fragments.TrackPlayerFragment;
import pashapps.spotifystreamer.R;


public class TrackPreviewPlayer extends ActionBarActivity {

    private TrackPlayerFragment mTrackPlayerFragment;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_preview_player);

        Bundle bundle;
        Intent intent = getIntent();
        bundle = intent.getExtras();
        setTitle(R.string.app_name);

        position = (int)bundle.get(TrackListFragment.POSITION);

        mTrackPlayerFragment = new TrackPlayerFragment();
        mTrackPlayerFragment.updateFragment(position);
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
