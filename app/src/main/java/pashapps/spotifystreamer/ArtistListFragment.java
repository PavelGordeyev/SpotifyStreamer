package pashapps.spotifystreamer;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import kaaes.spotify.webapi.android.models.ArtistsPager;


public class ArtistListFragment extends Fragment {

    private ListView mArtistListView;
    private ArtistAdapter mAdapter;
    private ArtistsPager mResults;
    private String mArtistid;
    public static final String ARTISTID = "ARTIST ID";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.artist_list_fragment,container,false);

        mArtistListView = (ListView) view.findViewById(R.id.artistList);
        mAdapter = new ArtistAdapter(getActivity(),mResults);
        mArtistListView.setAdapter(mAdapter);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mArtistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mArtistid = mResults.artists.items.get(position).id;
                Intent intent = new Intent(getActivity(),TopTracksActivity.class);
                intent.putExtra(ARTISTID, mArtistid);
                startActivity(intent);
            }
        });
    }

    public void setResults(ArtistsPager results){
        mResults = results;
    }

    public void updateFragment(int count) {
        mAdapter.setCount(count);
        mAdapter.update(mResults);
        mAdapter.notifyDataSetChanged();
    }

}
