package pashapps.spotifystreamer;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import pashapps.spotifystreamer.R;

/**
 * Created by pavelG on 6/9/15.
 */
public class ArtistAdapter extends BaseAdapter{

    private Context mContext;
    private ArtistsPager mResults;
    private int mCount;


    public ArtistAdapter(Context context, ArtistsPager results) {
        mContext = context;
        mResults = results;

    }

    public void setCount(int count){
        mCount = count;
    }

    @Override
    public int getCount() {
        if (mResults==null || mCount==0) {
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
            holder.artistImageView = (ImageView) convertView.findViewById(R.id.artistImageView);
            holder.artistLabel = (TextView) convertView.findViewById(R.id.artistLabel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(mResults!=null) {
            holder.artistLabel.setText(mResults.artists.items.get(position).name);
            try {
                Log.d("IMAGE", mResults.artists.items.get(position).id);
                Picasso.with(mContext).load(mResults.artists.items.get(position).images.get(0).url).into(holder.artistImageView);
            } catch(IndexOutOfBoundsException npe) {
                Log.d("IMAGE_ERR", npe.toString());
            }
            return convertView;
        } else{
            return convertView;
        }

    }

    public void update(ArtistsPager artistsPager) throws NullPointerException{
        mResults = artistsPager;
    }


    private class ViewHolder {
        ImageView artistImageView;
        TextView artistLabel;
    }


}
