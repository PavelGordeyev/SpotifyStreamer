package pashapps.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by pavelG on 6/9/15.
 */
public class ArtistAdapter extends BaseAdapter{

    private Context mContext;
    private ArtistsPager mResults;


    public ArtistAdapter(Context context, ArtistsPager results) {
        mContext = context;
        mResults = results;

    }

    @Override
    public int getCount() {
        if (mResults==null) {
            return 0;
        } else {
            return mResults.artists.items.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return mResults.artists.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.artist_list_item,null);
            holder = new ViewHolder();
            //holder.artistImageView = (ImageView) convertView.findViewById(R.id.artistImageView);
            holder.artistLabel = (TextView) convertView.findViewById(R.id.artistLabel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //String tester = mResults.artists.items.get(0).name;
        String tester2 = "fff";
        //Artist artist = mArtistResults.get(position);
        //String testt = artist.name;
        //holder.artistImageView.setImageResource(mArtistResults.getArtistImageURL(position));
        //holder.artistLabel.setText(mArtistResults.get(0).name + "");
        if(mResults!=null) {
            holder.artistLabel.setText(mResults.artists.items.get(position).name);
            return convertView;
        } else{

        }
        return convertView;
    }

    public void update(ArtistsPager artistsPager) {
        mResults = artistsPager;
    }

    private class ViewHolder {
        ImageView artistImageView;
        TextView artistLabel;
    }
}
