package pashapps.spotifystreamer.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyCallback;
import kaaes.spotify.webapi.android.SpotifyError;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;
import pashapps.spotifystreamer.Activities.MainActivity;
import pashapps.spotifystreamer.Adapters.TracksAdapter;
import pashapps.spotifystreamer.R;
import pashapps.spotifystreamer.Activities.TrackPreviewPlayerActivity;
import pashapps.spotifystreamer.TracksP;
import retrofit.client.Response;


/**
 * A placeholder fragment containing the tracks list view.
 */
public class TrackListFragment extends Fragment {

    private ListView mTrackListView;
    private TracksAdapter mAdapter;
    private String mArtistID;
    private String mArtistName;
    private Tracks mTracksResults;
    private Handler mHandler;

    public static TracksP[] mResults;
    public static final String POSITION = "POSITION";
    public static final String TRACKLIST = "TRACKLIST";
    private static final String TRACKS = "TRACKS";

    public TrackListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.track_list_fragment,container,false);

        mTrackListView = (ListView) view.findViewById(R.id.trackList);
        mHandler = new Handler();
        getTracksResults();
        mAdapter = new TracksAdapter(getActivity(), mResults);
        mTrackListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mTrackListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Help found here: http://developer.android.com/guide/topics/ui/dialogs.html#DismissingADialog
                TrackPlayerFragment trackPlayerFragment = new TrackPlayerFragment();

                if (MainActivity.mTwoPane) {
                    trackPlayerFragment.updateFragment(mResults, position);
                    // The device is using a large layout, so show the fragment as a dialog
                    trackPlayerFragment.show(getFragmentManager(), "dialog");
                } else {
                    // The device is smaller, so show the fragment fullscreen
                    Intent intent = new Intent(getActivity(), TrackPreviewPlayerActivity.class);
                    intent.putExtra(TRACKLIST, mResults);
                    intent.putExtra(POSITION, position);
                    startActivity(intent);
                }
            }
        });
    }

    public void setVals(String artistID,String artistName){
        mArtistID = artistID;
        mArtistName = artistName;
    }

    public void updateFragment(int count) {

        mAdapter.setCount(count);
        mAdapter.update(mResults);
        mAdapter.notifyDataSetChanged();

        if(mResults == null || mResults.length == 0){
            Toast.makeText(getActivity(), "No Top Track(s) Found", Toast.LENGTH_SHORT).show();
        }

    }
    public void getTracksResults(){

        SpotifyApi api = new SpotifyApi();
        final SpotifyService spotifyService = api.getService();

        Map<String,Object> options= new HashMap<>();
        options.put("country", "US");


        spotifyService.getArtistTopTrack(mArtistID, options, new SpotifyCallback<Tracks>() {
            @Override
            public void failure(SpotifyError spotifyError) {
                Log.d(TRACKS, spotifyError.toString());
                mResults = setTracksList();

                //Help found here:http://stackoverflow.com/questions/5009816/cant-create-handler-inside-thread-which-has-not-called-looper-prepare
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                updateFragment(0);
                            }
                        });
                    }
                };
                new Thread(runnable).start();
                Toast.makeText(getActivity(), "Tracks not found...Please try again", Toast.LENGTH_LONG).show();
            }

            @Override
            public void success(Tracks tracks, Response response) {
                mTracksResults = tracks;
                mResults = setTracksList();
                //Help found here:http://stackoverflow.com/questions/5009816/cant-create-handler-inside-thread-which-has-not-called-looper-prepare
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                updateFragment(1);
                            }
                        });
                    }
                };
                new Thread(runnable).start();
            }
        });
    }

    private TracksP[] setTracksList(){
        TracksP[] tracksPs;

        if(mTracksResults!=null){
            int size  = mTracksResults.tracks.size();
            tracksPs = new TracksP[size];

            for(int i = 0;i<size;i++){
                TracksP tracksP = new TracksP();
                tracksP.setAlbumName(mTracksResults.tracks.get(i).album.name);
                tracksP.setArtistName(mArtistName);
                tracksP.setTrackName(mTracksResults.tracks.get(i).name);
                try {
                    tracksP.setAlbumImageID(mTracksResults.tracks.get(i).album.images.get(0).url);
                } catch (IndexOutOfBoundsException iob){
                    Log.d("NO_IMAGE",iob.toString());
                }
                tracksP.setPreviewURL(mTracksResults.tracks.get(i).preview_url);

                tracksPs[i] = tracksP;
            }
        } else{
            tracksPs = null;
        }

        return tracksPs;
    }

}
