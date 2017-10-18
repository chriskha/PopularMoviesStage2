package com.example.android.popularmoviesstage2.utilities;

import android.database.Cursor;
import android.database.MatrixCursor;

import com.example.android.popularmoviesstage2.Movie;
import com.example.android.popularmoviesstage2.Trailer;
import com.example.android.popularmoviesstage2.UserReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.android.popularmoviesstage2.data.MovieContract.MovieEntry.COLUMN_MOVIE_ID;
import static com.example.android.popularmoviesstage2.data.MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE;
import static com.example.android.popularmoviesstage2.data.MovieContract.MovieEntry.COLUMN_OVERVIEW;
import static com.example.android.popularmoviesstage2.data.MovieContract.MovieEntry.COLUMN_POSTER_PATH;
import static com.example.android.popularmoviesstage2.data.MovieContract.MovieEntry.COLUMN_RELEASE_DATE;
import static com.example.android.popularmoviesstage2.data.MovieContract.MovieEntry.COLUMN_USER_RATINGS;


/**
 * Created by ckha on 9/6/17.
 */

public class MoviesDatabaseJsonUtils {

    public static final String KEY_ID = "id";
    public static final String KEY_ORIGINAL_TITLE = "original_title";
    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_USER_RATING = "vote_average";
    public static final String KEY_RELEASE_DATE = "release_date";

    public static String[] getSimpleMoviesStringsFromJson(String moviesJsonStr)
            throws JSONException {

        String[] parsedMoviesData = null;
        JSONObject moviesJson = new JSONObject(moviesJsonStr);

        JSONArray moviesArray = moviesJson.getJSONArray("results");

        parsedMoviesData = new String[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {
            String movieId;
            String movieOriginalTitle;
            String moviePosterPath;
            String movieOverview;
            String movieUserRating;
            String movieReleaseDate;

            JSONObject movieJson = moviesArray.getJSONObject(i);

            movieId = movieJson.getString(KEY_ID);
            movieOriginalTitle = movieJson.getString(KEY_ORIGINAL_TITLE);
            moviePosterPath = movieJson.getString(KEY_POSTER_PATH);
            movieOverview = movieJson.getString(KEY_OVERVIEW);
            movieUserRating = movieJson.getString(KEY_USER_RATING);
            movieReleaseDate = movieJson.getString(KEY_RELEASE_DATE);

//            parsedMoviesData[i] = movieOriginalTitle + " - " + movieReleaseDate + " - " +
//                    movieUserRating + " - " + movieOverview;
            parsedMoviesData[i] = moviePosterPath;
        }

        return parsedMoviesData;
    }

    public static Movie[] getMoviesObjectsFromJson(String moviesJsonStr)
            throws JSONException {
        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray("results");
        Movie[] movies = new Movie[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieJson = moviesArray.getJSONObject(i);

            Movie movie = new Movie();
            movie.setId(movieJson.getString(KEY_ID));
            movie.setOriginalTitle(movieJson.getString(KEY_ORIGINAL_TITLE));
            movie.setPosterPath(movieJson.getString(KEY_POSTER_PATH));
            movie.setOverview(movieJson.getString(KEY_OVERVIEW));
            movie.setUserRating(movieJson.getString(KEY_USER_RATING));
            movie.setReleaseDate(movieJson.getString(KEY_RELEASE_DATE));

            movies[i] = movie;
        }

        return movies;
    }

    public static Cursor getMoviesCursorFromJson(String moviesJsonStr)
            throws JSONException {
        Movie[] movies = getMoviesObjectsFromJson(moviesJsonStr);
        String[] columnNames = {COLUMN_MOVIE_ID,
                COLUMN_ORIGINAL_TITLE,
                COLUMN_POSTER_PATH,
                COLUMN_OVERVIEW,
                COLUMN_USER_RATINGS,
                COLUMN_RELEASE_DATE};
        MatrixCursor matrixCursor = new MatrixCursor(columnNames);

        for (int i = 0; i < movies.length; i++) {
            matrixCursor.newRow()
                    .add(COLUMN_MOVIE_ID, movies[i].getId())
                    .add(COLUMN_ORIGINAL_TITLE, movies[i].getOriginalTitle())
                    .add(COLUMN_POSTER_PATH, movies[i].getPosterPath())
                    .add(COLUMN_OVERVIEW, movies[i].getOverview())
                    .add(COLUMN_USER_RATINGS, movies[i].getUserRating())
                    .add(COLUMN_RELEASE_DATE, movies[i].getReleaseDate());
        }

        return matrixCursor;
    }

    public static Trailer[] getTrailersObjectsFromJson(String trailersJsonStr)
            throws JSONException {
        JSONObject trailersJson = new JSONObject(trailersJsonStr);
        JSONArray trailersArray = trailersJson.getJSONArray("results");
        Trailer[] trailers = new Trailer[trailersArray.length()];

        for (int i = 0; i < trailersArray.length(); i++) {
            JSONObject trailerJson = trailersArray.getJSONObject(i);

            Trailer trailer = new Trailer();
            trailer.setmTrailerId(trailerJson.getString(Trailer.KEY_TRAILER_ID));
            trailer.setmLanguage(trailerJson.getString(Trailer.KEY_LANGUAGE));
            trailer.setmCountry(trailerJson.getString(Trailer.KEY_COUNTRY));
            trailer.setmKey(trailerJson.getString(Trailer.KEY_VIDEO_KEY));
            trailer.setmName(trailerJson.getString(Trailer.KEY_NAME));
            trailer.setmSite(trailerJson.getString(Trailer.KEY_SITE));
            trailer.setmSize(trailerJson.getString(Trailer.KEY_SIZE));
            trailer.setmType(trailerJson.getString(Trailer.KEY_TYPE));


            trailers[i] = trailer;
        }

        return trailers;
    }

    public static UserReview[] getUserReviewsObjectsFromJson(String userReviewsJsonStr)
            throws JSONException {
        JSONObject userReviewsJson = new JSONObject(userReviewsJsonStr);
        JSONArray userReviewsArray = userReviewsJson.getJSONArray("results");
        UserReview[] userReviews = new UserReview[userReviewsArray.length()];

        for (int i = 0; i < userReviewsArray.length(); i++) {
            JSONObject userReviewJson = userReviewsArray.getJSONObject(i);

            UserReview userReview = new UserReview();
            userReview.setmReviewId(userReviewJson.getString(UserReview.KEY_REVIEW_ID));
            userReview.setmAuthor(userReviewJson.getString(UserReview.KEY_AUTHOR));
            userReview.setmContent(userReviewJson.getString(UserReview.KEY_CONTENT));
            userReview.setmReviewUrl(userReviewJson.getString(UserReview.KEY_URL));

            userReviews[i] = userReview;
        }

        return userReviews;
    }
}
