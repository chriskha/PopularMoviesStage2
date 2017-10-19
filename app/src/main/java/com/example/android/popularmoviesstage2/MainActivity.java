package com.example.android.popularmoviesstage2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmoviesstage2.data.MovieContract;
import com.example.android.popularmoviesstage2.utilities.MovieConstants;
import com.example.android.popularmoviesstage2.utilities.MovieUtils;
import com.example.android.popularmoviesstage2.utilities.MoviesDatabaseJsonUtils;
import com.example.android.popularmoviesstage2.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements
        MovieAdapter.GridItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int GRID_SPAN = 2;
    private MovieAdapter mAdapter;
    private RecyclerView mMoviesGrid;

    private static final String MOVIE_URL_EXTRA = "movie_url";
    private static final String MOVIE_SORT_TYPE_EXTRA = "sort_type";
    private static final int MOVIE_LOADER_ID = 31;

    private static final String RV_SAVE_STATE = "RecyclerViewSaveState";
    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesGrid = (RecyclerView) findViewById(R.id.rv_movies);

        // Create GridLayoutManager
        gridLayoutManager = new GridLayoutManager(this, GRID_SPAN);

        mAdapter = new MovieAdapter(this);
        mMoviesGrid.setAdapter(mAdapter);

        mMoviesGrid.setLayoutManager(gridLayoutManager);
        mMoviesGrid.setHasFixedSize(true);

        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);

        // On first launch, fetch some data.
        if (savedInstanceState == null) {
            fetchMovies();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onGridItemClick(int clickedItemIndex) {
        Context context = MainActivity.this;
        Class destinationActivity = DetailActivity.class;

        Intent startActivityIntent = new Intent(context, destinationActivity);

        mAdapter.mCursor.moveToPosition(clickedItemIndex);
        Movie movieData = MovieUtils.getMovieFromCursorPosition(mAdapter.mCursor);

        // Put the extra data of this item into intent so we can display it in DetailActivity.
        startActivityIntent.putExtra(MovieConstants.MOVIE_ID_EXTRA, movieData.getId());
        startActivityIntent.putExtra(MovieConstants.TITLE_EXTRA, movieData.getOriginalTitle());
        startActivityIntent.putExtra(MovieConstants.OVERVIEW_EXTRA, movieData.getOverview());
        startActivityIntent.putExtra(MovieConstants.POSTER_PATH_EXTRA, movieData.getPosterPath());
        startActivityIntent.putExtra(MovieConstants.RELEASE_DATE_EXTRA, movieData.getReleaseDate());
        startActivityIntent.putExtra(MovieConstants.USER_RATINGS_EXTRA, movieData.getUserRating());

        startActivity(startActivityIntent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        switch (menuItemSelected) {
            case R.id.action_sort_most_popular:
                fetchMovies(R.id.action_sort_most_popular);
                break;
            case R.id.action_sort_top_rated:
                fetchMovies(R.id.action_sort_top_rated);
                break;
            case R.id.action_sort_favorites:
                fetchMovies(R.id.action_sort_favorites);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mMovieData;

            @Override
            protected void onStartLoading() {
                if (args == null) return;

                if (mMovieData != null) {
                    deliverResult(mMovieData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                String queryUrlString = args.getString(MOVIE_URL_EXTRA);
                int sortType = args.getInt(MOVIE_SORT_TYPE_EXTRA);

                if (sortType == R.id.action_sort_most_popular ||
                        sortType == R.id.action_sort_top_rated) {
                    if (queryUrlString == null || TextUtils.isEmpty(queryUrlString)) {
                        return null;
                    }
                    try {
                        URL url = new URL(queryUrlString);
                        String results = NetworkUtils.getResponseFromHttpUrl(url);
                        return MoviesDatabaseJsonUtils.getMoviesCursorFromJson(results);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                } else if (sortType == R.id.action_sort_favorites) {
                    try {
                        return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                                null,
                                null,
                                null,
                                null);
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to asyncronously load data.");
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    return null;
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                mMovieData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    private void fetchMovies() {
        fetchMovies(R.id.action_sort_most_popular);
    }

    private void fetchMovies(int sortType) {
        URL url = NetworkUtils.buildUrl(sortType);

        // Loader takes a Bundle. Load the url into the bundle.
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIE_SORT_TYPE_EXTRA, sortType);
        bundle.putString(MOVIE_URL_EXTRA, url.toString());

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Cursor> movieLoader = loaderManager.getLoader(MOVIE_LOADER_ID);
        if (movieLoader == null) {
            loaderManager.initLoader(MOVIE_LOADER_ID, bundle, this);
        } else {
            loaderManager.restartLoader(MOVIE_LOADER_ID, bundle, this);
        }

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            // Restore the state of GridLayoutManager if we saved it.
            Parcelable savedState = savedInstanceState.getParcelable(RV_SAVE_STATE);
            gridLayoutManager.onRestoreInstanceState(savedState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save state of our RecyclerView using GridLayoutManager as freebie.
        outState.putParcelable(RV_SAVE_STATE, gridLayoutManager.onSaveInstanceState());
    }

}
