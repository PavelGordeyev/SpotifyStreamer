package pashapps.spotifystreamer;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A placeholder fragment containing a simple view.
 */
public class TrackListFragment extends Fragment {

    private ListView mTrackListView;
    private TracksAdapter mAdapter;
    private Tracks mResults;

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

    public void setResults(Tracks results){
        mResults = results;
    }

    public void updateFragment() {
        mAdapter.update(mResults);
        mAdapter.notifyDataSetChanged();
    }

}
