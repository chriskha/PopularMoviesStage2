package com.example.android.popularmoviesstage2;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmoviesstage2.utilities.MovieUtils;
import com.example.android.popularmoviesstage2.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by ckha on 9/11/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    Cursor mCursor;
    private Context context;

    final private GridItemClickListener mOnClickListener;

    public MovieAdapter(GridItemClickListener listener) {
        mOnClickListener = listener;
    }

    public void swapCursor(Cursor cursor) {
        mCursor = cursor;
        this.notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForGridItem = R.layout.movies_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForGridItem, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movieItemImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);

            movieItemImageView = (ImageView) itemView.findViewById(R.id.iv_movie_item);
            itemView.setOnClickListener(this);
        }

        public void bind(int index) {
            mCursor.moveToPosition(index);
            Movie movie = MovieUtils.getMovieFromCursorPosition(mCursor);
            String posterUrlPath = "temp";
            if (movie != null) {
                posterUrlPath = NetworkUtils.buildPosterUrlString(movie.getPosterPath());
            }
            // Use Picasso to load the poster image from the string URL.
            Picasso.with(context).load(posterUrlPath).into(movieItemImageView);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onGridItemClick(clickedPosition);
        }
    }

    public interface GridItemClickListener {
        void onGridItemClick(int clickedItemIndex);
    }
}
