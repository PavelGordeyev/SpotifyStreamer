package pashapps.spotifystreamer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity implements View.OnFocusChangeListener{

    private static final String ARTIST= "ARTIST";
    private EditText mArtistSearch;
    private ArtistListFragment mArtistListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpotifyApi api = new SpotifyApi();
        final SpotifyService spotifyService = api.getService();

        mArtistSearch = (EditText) findViewById(R.id.searchEditTextView);
        mArtistListFragment = new ArtistListFragment();
        getFragmentManager().beginTransaction().
                add(R.id.artistFragmentContainer, mArtistListFragment).commit();

        mArtistSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isNetworkAvailable()) {
                    spotifyService.searchArtists(mArtistSearch.getText().toString(), new SpotifyCallback<ArtistsPager>() {
                        @Override
                        public void failure(SpotifyError spotifyError) {
                            Log.d(ARTIST, spotifyError.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Toast.makeText(getApplicationContext(), "Artist not found...Please try again", Toast.LENGTH_LONG).show();
                                    mArtistListFragment.updateFragment(0);
                                }
                            });
                        }

                        @Override
                        public void success(final ArtistsPager artistsPager, Response response) {
                            Log.d(ARTIST, artistsPager.toString());
                            mArtistListFragment.setResults(artistsPager);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getFragmentManager().beginTransaction().
                                            replace(R.id.artistFragmentContainer, mArtistListFragment).commit();
                                    mArtistListFragment.updateFragment(1);
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Network unavailable", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) throws NullPointerException {

                if (mArtistSearch.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter new search", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if(networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }



}
