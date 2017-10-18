package com.example.android.popularmoviesstage2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviesstage2.data.MovieContract;
import com.example.android.popularmoviesstage2.utilities.MovieConstants;
import com.example.android.popularmoviesstage2.utilities.MoviesDatabaseJsonUtils;
import com.example.android.popularmoviesstage2.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

import static com.example.android.popularmoviesstage2.utilities.NetworkUtils.REVIEWS;
import static com.example.android.popularmoviesstage2.utilities.NetworkUtils.VIDEOS;

/**
 * Created by ckha on 9/12/17.
 */

public class DetailActivity extends AppCompatActivity implements
        TrailerAdapter.TrailerItemClickListener {

    private String TAG = DetailActivity.class.getSimpleName();

    private String mMovieId;

    private TextView mMovieTitleDisplay;
    private TextView mMovieOverview;
    private TextView mMovieUserRating;
    private TextView mMovieReleaseDate;
    private ImageView mMoviePoster;
    private Button mButton;

    private String mMoviePosterPath;

    private boolean isFavorite;

    private TrailerAdapter mTrailerAdapter;
    private RecyclerView mTrailersView;

    private UserReviewAdapter mReviewAdapter;
    private RecyclerView mReviewsView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovieTitleDisplay = (TextView) findViewById(R.id.tv_movie_title);
        mMovieOverview = (TextView) findViewById(R.id.tv_overview);
        mMovieUserRating = (TextView) findViewById(R.id.tv_user_rating);
        mMovieReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        mMoviePoster = (ImageView) findViewById(R.id.iv_movie_poster);
        String moviePosterPath = null;
        mButton = (Button) findViewById(R.id.b_favorite);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(MovieConstants.MOVIE_ID_EXTRA)) {
            mMovieId = intentThatStartedThisActivity.getStringExtra(MovieConstants.MOVIE_ID_EXTRA);
        }
        if (intentThatStartedThisActivity.hasExtra(MovieConstants.TITLE_EXTRA)) {
            mMovieTitleDisplay.setText(intentThatStartedThisActivity.getStringExtra(MovieConstants.TITLE_EXTRA));
        }
        if (intentThatStartedThisActivity.hasExtra(MovieConstants.OVERVIEW_EXTRA)) {
            mMovieOverview.setText(intentThatStartedThisActivity.getStringExtra(MovieConstants.OVERVIEW_EXTRA));
        }
        if (intentThatStartedThisActivity.hasExtra(MovieConstants.USER_RATINGS_EXTRA)) {
            mMovieUserRating.setText(intentThatStartedThisActivity.getStringExtra(MovieConstants.USER_RATINGS_EXTRA));
        }
        if (intentThatStartedThisActivity.hasExtra(MovieConstants.RELEASE_DATE_EXTRA)) {
            mMovieReleaseDate.setText(intentThatStartedThisActivity.getStringExtra(MovieConstants.RELEASE_DATE_EXTRA));
        }
        if (intentThatStartedThisActivity.hasExtra(MovieConstants.POSTER_PATH_EXTRA)) {
            mMoviePosterPath = intentThatStartedThisActivity.getStringExtra(MovieConstants.POSTER_PATH_EXTRA);
            moviePosterPath = NetworkUtils.buildPosterUrlString(mMoviePosterPath);
        }


        isFavorite = isMovieFavorite();
        setFavoriteText(isFavorite);

        // Load the movie poster into the ImageView
        Picasso.with(this).load(moviePosterPath).into(mMoviePoster);

        fetchTrailers();
        fetchReviews();

        setupTrailers();
        setupReviews();

    }

    private void setupTrailers() {
        mTrailersView = (RecyclerView) findViewById(R.id.rv_trailers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mTrailersView.setLayoutManager(layoutManager);
        mTrailerAdapter = new TrailerAdapter(this, 10, this);
        mTrailersView.setAdapter(mTrailerAdapter);
    }

    private void setupReviews() {
        mReviewsView = (RecyclerView) findViewById(R.id.rv_reviews);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mReviewsView.setLayoutManager(layoutManager);
        mReviewAdapter = new UserReviewAdapter(this, 10);
        mReviewsView.setAdapter(mReviewAdapter);
    }

    private void setFavoriteText(boolean isFavorite) {
        if (isFavorite) {
            mButton.setText(getString(R.string.favorited));
        } else {
            mButton.setText(getString(R.string.mark_favorite));
        }
    }

    public void setOrUnsetFavorite(View view) {
        if (isFavorite) {
            isFavorite = false;
            // Delete from db using content resolver
            deleteMovieFromDb();
        } else {
            isFavorite = true;
            // Insert into db using content resolver
            insertMovieIntoDb();
        }
        setFavoriteText(isFavorite);

    }

    private boolean isMovieFavorite() {
        boolean isFavorite = false;
        String[] projection = {MovieContract.MovieEntry.COLUMN_MOVIE_ID};
        String selection = "movie_id=?";
        String[] selectionArgs = {mMovieId};
        Uri queryUri = MovieContract.MovieEntry.CONTENT_URI.buildUpon()
                .appendPath(mMovieId).build();
        Cursor cursor = getContentResolver().query(
                queryUri,
                projection,
                selection,
                selectionArgs,
                null);
        if (cursor.getCount() != 0) {
            isFavorite = true;
        }
        cursor.close();
        return isFavorite;
    }

    private void deleteMovieFromDb() {
        String[] selectionArgs = {mMovieId};
        Uri deleteUri = MovieContract.MovieEntry.CONTENT_URI.buildUpon()
                .appendPath(mMovieId).build();
        getContentResolver().delete(deleteUri, "movie_id=?", selectionArgs);
    }

    private void insertMovieIntoDb() {
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, mMovieId);
        cv.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, mMovieTitleDisplay.getText().toString());
        cv.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, mMoviePosterPath);
        cv.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, mMovieOverview.getText().toString());
        cv.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, mMovieReleaseDate.getText().toString());
        cv.put(MovieContract.MovieEntry.COLUMN_USER_RATINGS, mMovieUserRating.getText().toString());

        getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, cv);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
        return true;

    }

    private void fetchTrailers() {
        FetchDetailsTask fetchTrailers = new FetchDetailsTask();
        fetchTrailers.execute(VIDEOS);
    }

    private void fetchReviews() {
        FetchDetailsTask fetchReviews = new FetchDetailsTask();
        fetchReviews.execute(REVIEWS);
    }

    @Override
    public void onTrailerItemClick(int clickedItemIndex) {
        openWebPage(mTrailerAdapter.mTrailers[clickedItemIndex].getmKey());
    }

    private void openWebPage(String videoId) {
        Uri youtubeUri = NetworkUtils.buildYoutubeUri(videoId);
        Intent intent = new Intent(Intent.ACTION_VIEW, youtubeUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private class FetchDetailsTask extends AsyncTask<String, Void, String> {

        private String option;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            option = strings[0];
            URL url = NetworkUtils.buildTrailersOrReviewsUrlWithMovieId(mMovieId, option);

            try {
                String results = NetworkUtils.getResponseFromHttpUrl(url);

                return results;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if (option.equals(VIDEOS)) {
                    Trailer[] trailers = MoviesDatabaseJsonUtils.getTrailersObjectsFromJson(s);
                    mTrailerAdapter.setTrailers(trailers);
                    mTrailerAdapter.notifyDataSetChanged();
                } else if (option.equals(REVIEWS)) {
                    UserReview[] reviews = MoviesDatabaseJsonUtils.getUserReviewsObjectsFromJson(s);
                    mReviewAdapter.setReviews(reviews);
                    mReviewAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

        }
    }
}
