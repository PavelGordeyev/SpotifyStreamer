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
    private EditText mArtistSearch;
    private ArtistsPager mResults;
    private ArtistAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpotifyApi api = new SpotifyApi();
        final SpotifyService spotifyService = api.getService();

        mArtistSearch = (EditText) findViewById(R.id.searchEditTextView);
        mAdapter = new ArtistAdapter(this,mResults);
        setListAdapter(mAdapter);

        /*
        spotifyService.searchArtists("Nirvana", new SpotifyCallback<ArtistsPager>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.d(ARTIST, spotifyError.toString());
            }

            @Override
            public void success(final ArtistsPager artistsPager, Response response) {
                Log.d(ARTIST, artistsPager.toString());
                mResults = artistsPager;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //ArtistAdapter adapter = new ArtistAdapter(MainActivity.this, mResults);
                        //setListAdapter(adapter);
                        mAdapter.update(mResults);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });*/
            //mResults = spotifyService.searchArtists("Nirvana");


            /*mResults = spotifyService.searchArtists("Nirvana"), new SpotifyCallback<ArtistsPager>() {

                @Override
                public void failure(SpotifyError spotifyError) {
                    Log.d(ARTIST, spotifyError.toString());
                }

                @Override
                public void success(ArtistsPager artistsPager, Response response) {
                    Log.d(ARTIST, artistsPager.toString());
                    mResults = artistsPager;
                    //ArtistResults artistResults = new ArtistResults(mResults,position);
                    //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
                }
            });*/



        //ArtistAdapter adapter = new ArtistAdapter(this, mResults);
        //setListAdapter(adapter);

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
                    }

                    @Override
                    public void success(final ArtistsPager artistsPager, Response response) {
                        Log.d(ARTIST, artistsPager.toString());
                        mResults = artistsPager;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //ArtistAdapter adapter = new ArtistAdapter(MainActivity.this, mResults);
                                //setListAdapter(adapter);
                                mAdapter.update(mResults);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) throws NullPointerException{

                //Toast.makeText(MainActivity.this,"" + mArtistSearch.getText().toString(),Toast.LENGTH_LONG).show();
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
