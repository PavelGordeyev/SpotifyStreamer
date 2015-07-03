package pashapps.spotifystreamer;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
