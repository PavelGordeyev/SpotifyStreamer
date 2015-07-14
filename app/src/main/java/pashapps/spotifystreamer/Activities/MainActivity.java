package pashapps.spotifystreamer.Activities;

import android.app.FragmentTransaction;
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
import android.widget.Toast;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import pashapps.spotifystreamer.ArtistP;
import pashapps.spotifystreamer.Fragments.ArtistListFragment;
import pashapps.spotifystreamer.R;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity implements View.OnFocusChangeListener{

    private static final String ARTIST= "ARTIST";
    private EditText mArtistSearch;
    private ArtistListFragment mArtistListFragment;
    private ArtistsPager mArtistResults;
    public static Boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mArtistSearch = (EditText) findViewById(R.id.searchEditTextView);
        mArtistListFragment = new ArtistListFragment();

        if(findViewById(R.id.trackFragmentContainer)==null){
            mTwoPane = false;
        } else{
            mTwoPane = true;
        }
        //getFragmentManager().beginTransaction().
          //      add(R.id.artistFragmentContainer, mArtistListFragment).commit();

        mArtistSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //getArtistResults();
            }

            @Override
            public void afterTextChanged(Editable s) throws NullPointerException {
                getArtistResults();
                if (mArtistSearch.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter new search", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().remove(mArtistListFragment);
                }else{
                    getFragmentManager().beginTransaction().
                            replace(R.id.artistFragmentContainer, mArtistListFragment).commit();
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

    private void getArtistResults() {

        SpotifyApi api = new SpotifyApi();
        final SpotifyService spotifyService = api.getService();

        if (isNetworkAvailable()) {
            spotifyService.searchArtists(mArtistSearch.getText().toString(), new SpotifyCallback<ArtistsPager>() {
                @Override
                public void failure(SpotifyError spotifyError) {
                    Log.d(ARTIST, spotifyError.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mArtistListFragment.updateFragment(0);
                        }
                    });
                }

                @Override
                public void success(final ArtistsPager artistsPager, Response response) {
                    Log.d(ARTIST, artistsPager.toString());
                    mArtistResults = artistsPager;
                    mArtistListFragment.setResults(setArtistList());
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

    private ArtistP[] setArtistList() {

        ArtistP[] artistPs;
        if(mArtistResults!=null) {
            int size = mArtistResults.artists.items.size();
            artistPs = new ArtistP[size];

            for (int i = 0; i < size; i++) {
                ArtistP artistP = new ArtistP();
                artistP.setName(mArtistResults.artists.items.get(i).name);
                artistP.setArtistID(mArtistResults.artists.items.get(i).id);
                try {
                    //int imagesSize = mArtistResults.artists.items.get(i).images.size();
                    artistP.setImageID(mArtistResults.artists.items.get(i).images.get(0).url);
                } catch(IndexOutOfBoundsException iob){
                    Log.d("NO_IMAGE",iob.toString());
                }

                artistPs[i] = artistP;
            }
        } else{
            artistPs = null;
        }

        return artistPs;
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
