package pashapps.spotifystreamer;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.client.Response;


public class TopTracksActivity extends ListActivity {

    private String mArtistID;
    private TracksAdapter mAdapter;
    private Tracks mResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_tracks);

        SpotifyApi api = new SpotifyApi();
        final SpotifyService spotifyService = api.getService();

        mAdapter = new TracksAdapter(this, mResults);
        setListAdapter(mAdapter);
        Bundle bundle;
        Intent intent = getIntent();
        bundle = intent.getExtras();
        mArtistID = bundle.get(MainActivity.ARTISTID) + "";

        Map<String,Object> options= new HashMap<>();
        options.put("country","US");

        spotifyService.getArtistTopTrack(mArtistID, options, new SpotifyCallback<Tracks>() {
            @Override
            public void failure(SpotifyError spotifyError) {

            }

            @Override
            public void success(Tracks tracks, Response response) {
                mResults = tracks;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.update(mResults);
                        mAdapter.notifyDataSetChanged();
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
