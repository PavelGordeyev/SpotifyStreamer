package pashapps.spotifystreamer;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Tracks;
import kaaes.spotify.webapi.android.models.TracksPager;
import pashapps.spotifystreamer.R;

/**
 * Created by pavelG on 6/14/15.
 */
public class TracksAdapter extends BaseAdapter{

    private Context mContext;
    private int mCount;
    private Tracks mResults;

    public TracksAdapter(Context context,Tracks tracks) {
        mContext = context;
        mResults = tracks;

    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return mResults.tracks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.track_list_item,null);
            holder = new ViewHolder();
            holder.albumImageView = (ImageView) convertView.findViewById(R.id.albumImageView);
            holder.albumLabel = (TextView) convertView.findViewById(R.id.albumLabel);
            holder.trackLabel = (TextView) convertView.findViewById(R.id.trackLabel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(mResults!=null) {
            holder.albumLabel.setText(mResults.tracks.get(position).album.name);
            holder.trackLabel.setText(mResults.tracks.get(position).name);
            try {
                Picasso.with(mContext).load(mResults.tracks.get(position).album.images.get(0).url).into(holder.albumImageView);
            } catch(IndexOutOfBoundsException npe) {
                holder.albumImageView.setBackgroundColor(Color.YELLOW);
                Log.d("IMAGE_ERR", npe.toString());
            }
            return convertView;
        } else{
            return convertView;
        }
    }

    private class ViewHolder {
        ImageView albumImageView;
        TextView albumLabel;
        TextView trackLabel;
    }
    public void update(Tracks tracks) throws NullPointerException{
        mResults = tracks;
    }
}

