package pashapps.spotifystreamer.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pashapps.spotifystreamer.R;
import pashapps.spotifystreamer.TracksP;

/**
 * Created by pavelG on 6/14/15.
 */
public class TracksAdapter extends BaseAdapter{

    private Context mContext;
    private TracksP[] mResults;
    private int mCount;

    public TracksAdapter(Context context,TracksP[] tracks) {
        mContext = context;
        mResults = tracks;
    }

    public void setCount(int count) {
        mCount = count;
    }
    @Override
    public int getCount() {

        if (mResults==null || mCount==0) {
            return 0;
        } else {
            return mResults.length;
        }
    }

    @Override
    public Object getItem(int position) {
        return mResults[position];
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
            try {
                holder.albumLabel.setText(mResults[position].getAlbumName());
                holder.trackLabel.setText(mResults[position].getTrackName());
                Picasso.with(mContext).load(mResults[position].getAlbumImageID()).into(holder.albumImageView);
            } catch(IndexOutOfBoundsException npe) {
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

    public void update(TracksP[] tracks) throws NullPointerException{
        mResults = tracks;
    }
}

