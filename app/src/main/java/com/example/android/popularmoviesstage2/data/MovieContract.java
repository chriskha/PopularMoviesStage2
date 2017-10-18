package com.example.android.popularmoviesstage2.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ckha on 10/16/17.
 */

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.popularmoviesstage2";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_USER_RATINGS = "user_ratings";
        public static final String COLUMN_RELEASE_DATE = "release_date";

        /*
        Table Structure should look like the following
        movies
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        | _id |   original title    |   overview    |   user_ratings  |  release_date   |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        |  1  |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


         */
    }
}
