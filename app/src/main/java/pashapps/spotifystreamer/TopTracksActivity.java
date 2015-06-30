package pashapps.spotifystreamer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.client.Response;


public class TopTracksActivity extends ActionBarActivity {

    private String mArtistID;
    private TrackListFragment mTrackListFragment;
    private static final String TRACKS = "TRACKS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_tracks);

        setTitle("Top 10 Tracks");

        SpotifyApi api = new SpotifyApi();
        final SpotifyService spotifyService = api.getService();

        mTrackListFragment = new TrackListFragment();
        getFragmentManager().beginTransaction().
                add(R.id.trackFragmentContainer, mTrackListFragment).commit();

        Bundle bundle;
        Intent intent = getIntent();
        bundle = intent.getExtras();
        mArtistID = bundle.get(ArtistListFragment.ARTISTID) + "";
        getSupportActionBar().setSubtitle(bundle.get(ArtistListFragment.ARTISTNAME) + "");

        Map<String,Object> options= new HashMap<>();
        options.put("country", "US");


        spotifyService.getArtistTopTrack(mArtistID, options, new SpotifyCallback<Tracks>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.d(TRACKS, spotifyError.toString());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTrackListFragment.updateFragment(0);
                        Context context = getApplicationContext();
                        Toast.makeText(context, "Tracks not found...Please try again", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void success(Tracks tracks, Response response) {
                mTrackListFragment.setResults(tracks);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getFragmentManager().beginTransaction().
                                replace(R.id.trackFragmentContainer, mTrackListFragment).commit();
                        mTrackListFragment.updateFragment(1);
                    }
                });

            }
        });

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
