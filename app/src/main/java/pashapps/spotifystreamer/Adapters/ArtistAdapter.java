package pashapps.spotifystreamer.Adapters;

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

import pashapps.spotifystreamer.ArtistP;
import pashapps.spotifystreamer.Fragments.ArtistListFragment;
import pashapps.spotifystreamer.R;

/**
 * Created by pavelG on 6/9/15.
 */
public class ArtistAdapter extends BaseAdapter{

    private Context mContext;
    private ArtistP[] mResults;
    private int mCount;


    public ArtistAdapter(Context context, ArtistP[] results) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.artist_list_item,null);
            holder = new ViewHolder();
            holder.artistImageView = (ImageView) convertView.findViewById(R.id.artistImageView);
            holder.artistLabel = (TextView) convertView.findViewById(R.id.artistLabel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if(position == ArtistListFragment.selected){
            convertView.setBackgroundColor(Color.parseColor("#FFA500"));
        } else{
            convertView.setBackgroundColor(Color.parseColor("#EEEEEE"));
        }

        if(mResults!=null) {
            holder.artistLabel.setText(mResults[position].getName());
            try {
                Log.d("IMAGE", mResults[position].getArtistID());
                Picasso.with(mContext).load(mResults[position].getImageID()).into(holder.artistImageView);
            } catch(IndexOutOfBoundsException npe) {
                Picasso.with(mContext).load(R.drawable.image_unavailable).into(holder.artistImageView);
                Log.d("IMAGE_ERR", npe.toString());

            }
            return convertView;
        } else{
            return convertView;
        }

    }

    public void update(ArtistP[] artistPs) throws NullPointerException{
        mResults = artistPs;
    }


    private class ViewHolder {
        ImageView artistImageView;
        TextView artistLabel;
    }


}
