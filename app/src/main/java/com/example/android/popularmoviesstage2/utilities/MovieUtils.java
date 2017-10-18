package com.example.android.popularmoviesstage2.utilities;

import android.database.Cursor;

import com.example.android.popularmoviesstage2.Movie;
import com.example.android.popularmoviesstage2.data.MovieContract;

/**
 * Created by ckha on 10/17/17.
 */

public class MovieUtils {

    public static Movie getMovieFromCursorPosition(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID)));
        movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE)));
        movie.setPosterPath(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH)));
        movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
        movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)));
        movie.setUserRating(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_USER_RATINGS)));
        return movie;
    }
}
