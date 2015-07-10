package pashapps.spotifystreamer;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A placeholder fragment containing the tracks list view.
 */
public class TrackListFragment extends Fragment {

    private ListView mTrackListView;
    private TracksAdapter mAdapter;
    private TracksP[] mResults;
    public static final String ALBUM = "ALBUM";
    public static final String TRACK = "TRACK";
    public static final String ARTIST = "ARTIST";
    public static final String IMAGEID = "IMAGE_ID";

    public TrackListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.track_list_fragment,container,false);

        mTrackListView = (ListView) view.findViewById(R.id.trackList);
        mAdapter = new TracksAdapter(getActivity(), mResults);
        mTrackListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mTrackListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TRACKURL",mResults[position].getPreviewURL());
                Intent intent = new Intent(getActivity(),TrackPreviewPlayer.class);
                intent.putExtra(ALBUM,mResults[position].getAlbumName());
                intent.putExtra(TRACK,mResults[position].getTrackName());
                intent.putExtra(ARTIST,mResults[position].getArtistName());
                try {
                    intent.putExtra(IMAGEID, mResults[position].getAlbumImageID());
                }catch(IndexOutOfBoundsException iob){
                    Log.d("IMAGE_ERR", iob.toString());
                }
                startActivity(intent);
            }
        });
    }

    public void setResults(TracksP[] results){
        mResults = results;
    }

    public void updateFragment(int count) {

        mAdapter.setCount(count);
        mAdapter.update(mResults);
        mAdapter.notifyDataSetChanged();

        if(mResults == null || mResults.length == 0){
            Toast.makeText(getActivity(),"No Top Track(s) Found",Toast.LENGTH_SHORT).show();
        }

    }

}
