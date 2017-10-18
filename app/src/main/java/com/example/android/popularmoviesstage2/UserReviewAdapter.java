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

public class UserReviewAdapter extends RecyclerView.Adapter<UserReviewAdapter.ReviewViewHolder> {
    UserReview[] mReviews;
    private Context context;

    public UserReviewAdapter(Context context, int numItems) {
        this.context = context;
        mReviews = new UserReview[numItems];
    }

    public void setReviews(UserReview[] reviews) {
        mReviews = reviews;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.movie_review_item, parent, false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mReviews.length;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView authorTextView;
        TextView contentTextView;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            authorTextView = (TextView) itemView.findViewById(R.id.tv_review_author);
            contentTextView = (TextView) itemView.findViewById(R.id.tv_review_content);
        }

        public void bind(int index) {
            UserReview review = mReviews[index];
            if (review != null) {
                authorTextView.setText(review.getmAuthor());
                contentTextView.setText(review.getmContent());
            }
        }
    }
}
