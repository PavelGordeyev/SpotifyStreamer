package pashapps.spotifystreamer.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import pashapps.spotifystreamer.Adapters.TracksAdapter;
import pashapps.spotifystreamer.R;
import pashapps.spotifystreamer.Activities.TrackPreviewPlayer;
import pashapps.spotifystreamer.TracksP;


/**
 * A placeholder fragment containing the tracks list view.
 */
public class TrackListFragment extends Fragment {

    private ListView mTrackListView;
    private TracksAdapter mAdapter;
    public static TracksP[] mResults;
    public static final String POSITION = "POSITION";
    public static final String TRACKLIST = "TRACKLIST";

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

                Intent intent = new Intent(getActivity(),TrackPreviewPlayer.class);
                intent.putExtra(TRACKLIST,mResults);
                intent.putExtra(POSITION,position);
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
