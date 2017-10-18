package com.example.android.popularmoviesstage2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ckha on 10/18/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    Trailer[] mTrailers;
    private int mNumItems;
    private Context context;

    final private TrailerItemClickListener mOnTrailerClickListener;

    public TrailerAdapter(Context context,
                          int numItems,
                          TrailerItemClickListener trailerClickListener) {
        this.context = context;
        mNumItems = numItems;
        mTrailers = new Trailer[numItems];
        mOnTrailerClickListener = trailerClickListener;
    }

    public void setTrailers(Trailer[] trailers) {
        mTrailers = trailers;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.movie_trailer_item, parent, false);

        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mTrailers.length;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView mTrailerName;

        public TrailerViewHolder(View itemView) {
            super(itemView);

            mTrailerName = (TextView) itemView.findViewById(R.id.tv_trailer_name);
            itemView.setOnClickListener(this);
        }

        public void bind(int index) {
            Trailer trailer = mTrailers[index];
            if (trailer != null) {
                mTrailerName.setText(trailer.getmName());
            }
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnTrailerClickListener.onTrailerItemClick(clickedPosition);
        }
    }

    public interface TrailerItemClickListener {
        void onTrailerItemClick(int clickedItemIndex);
    }
}
