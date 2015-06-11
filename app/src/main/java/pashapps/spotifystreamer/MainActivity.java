package pashapps.spotifystreamer;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Artists;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ListActivity implements View.OnFocusChangeListener{

    private static final String ARTIST= "ARTIST";
    private static final String TESTINGNULL = "TESTINGNULL";
    private static final String INVIS = "INVISIBLE";
    private static final String VIS = "VISIBLE";
    private EditText mArtistSearch;
    private ArtistsPager mResults;
    private ArtistAdapter mAdapter;
    private TextView mNoResults;
    private int mItemSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpotifyApi api = new SpotifyApi();
        final SpotifyService spotifyService = api.getService();

        mArtistSearch = (EditText) findViewById(R.id.searchEditTextView);
        mNoResults = (TextView) findViewById(R.id.noResultsLabel);
        mAdapter = new ArtistAdapter(this,mResults);
        setListAdapter(mAdapter);

        mArtistSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                spotifyService.searchArtists(mArtistSearch.getText().toString(), new SpotifyCallback<ArtistsPager>() {
                    @Override
                    public void failure(SpotifyError spotifyError) {
                        Log.d(ARTIST, spotifyError.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.setCount(0);
                                mAdapter.update(mResults);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void success(final ArtistsPager artistsPager, Response response) {
                        Log.d(ARTIST, artistsPager.toString());
                        mResults = artistsPager;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.setCount(1);
                                mAdapter.update(mResults);
                                mAdapter.notifyDataSetChanged();

                                //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
                            }
                        });
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) throws NullPointerException{

                toggleNoResults();
            }
        });
    }

    public void updateDisplay() {
        mAdapter.update(mResults);
        mAdapter.notifyDataSetChanged();
        toggleNoResults();
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

    public void toggleNoResults() {
        if(mArtistSearch.getText().toString().equals("")) {
            Log.d(VIS,"VISIBLE & NOT NULL SET");
            mNoResults.setVisibility(View.VISIBLE);
        } else {
            mNoResults.setVisibility(View.INVISIBLE);
            Log.d(INVIS, "INVISIBLE OR NULL");
        }
    }
}
