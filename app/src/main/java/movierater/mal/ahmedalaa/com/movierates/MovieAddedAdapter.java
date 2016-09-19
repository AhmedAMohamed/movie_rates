package movierater.mal.ahmedalaa.com.movierates;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ahmedalaa on 9/17/16.
 */
public class MovieAddedAdapter extends RecyclerView.Adapter<MovieAddedAdapter.MovieAddedHolder> {

    private List<MovieAdded> mData = Collections.emptyList();
    private Activity activity;

    public MovieAddedAdapter(List<MovieAdded> mData, Activity activity) {
        this.mData = mData;
        this.activity = activity;
    }

    public MovieAddedAdapter() {

    }

    @Override
    public MovieAddedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.added_list_item, parent, false);
        MovieAddedHolder mh = new MovieAddedHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final MovieAddedHolder holder, int position) {

        final MovieAdded m = mData.get(position);
        if (m.isState()) { // true means trailers
            Trailer trailer = (Trailer) m;
            holder.title.setText(trailer.getName());
            if (holder.title.getText().toString().equalsIgnoreCase("trailers")) {
                holder.content.setScaleY((float) 0.00001);
                holder.url.setScaleY((float)0.00001);
            }
            holder.content.setText(trailer.getKey());
            holder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    watchYoutubeVideo(((Trailer) m).getKey());
                }
            });
            holder.url.setText(trailer.getSite());
        }
        else {
            Review review = (Review) m;
            holder.content.setText(review.getContent());
            holder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.content.getEllipsize() == TextUtils.TruncateAt.END) {
                        holder.content.setEllipsize(null);
                        holder.content.setMaxLines(10000);
                    }
                    else {
                        holder.content.setEllipsize(TextUtils.TruncateAt.END);
                        holder.content.setMaxLines(2);
                    }

                }
            });

            holder.title.setText(review.getAuthor());
            holder.url.setText(review.getUrl());

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updateList(List<MovieAdded> data) {
        for (MovieAdded m : data) {
            mData.add(m);
        }
        notifyDataSetChanged();
    }

    public List<MovieAdded> getmData() {
        return mData;
    }

    public void setmData(List<MovieAdded> mData) {
        this.mData = mData;
    }

    public void addToData(ArrayList<MovieAdded> trailers, ArrayList<MovieAdded> reviews) {
        mData = new ArrayList();
        for (MovieAdded m : trailers) {
            mData.add(m);
        }
        for (MovieAdded m : reviews) {
            mData.add(m);
        }
        notifyDataSetChanged();
    }

    public void addToData(ArrayList<MovieAdded> data) {
        for (MovieAdded m : data) {
            mData.add(m);
        }
        notifyDataSetChanged();
    }

    private void watchYoutubeVideo(String id){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            activity.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            activity.startActivity(intent);
        }
    }

    public class MovieAddedHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView content;
        public TextView url;

        public MovieAddedHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.data);
            url = (TextView) itemView.findViewById(R.id.uri);
        }
    }
}
