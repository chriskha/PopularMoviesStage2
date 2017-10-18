package com.example.android.popularmoviesstage2.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.android.popularmoviesstage2.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by ckha on 9/6/17.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static final String VIDEOS = "videos";
    public static final String REVIEWS = "reviews";

    private static final String POSTER_SIZE_W92 = "w92";
    private static final String POSTER_SIZE_W154 = "w154";
    private static final String POSTER_SIZE_W185 = "w185";
    private static final String POSTER_SIZE_W342 = "w342";
    private static final String POSTER_SIZE_W500 = "w500";
    private static final String POSTER_SIZE_W780 = "w780";
    private static final String POSTER_SIZE_ORIGINAL = "original";

    private static final String DYNAMIC_MOVIES_POSTER_IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String DYNAMIC_MOVIES_POPULAR_URL =
            "http://api.themoviedb.org/3/movie/popular";
    private static final String DYNAMIC_MOVIES_TOP_RATED_URL =
            "http://api.themoviedb.org/3/movie/top_rated";
    private static final String DYNAMIC_MOVIES_VIDEOS_URL =
            "http://api.themoviedb.org/3/movie/%s/videos";
    private static final String DYNAMIC_MOVIES_REVIEWS_URL =
            "http://api.themoviedb.org/3/movie/%s/reviews";
    private static final String YOUTUBE_URL =
            "https://www.youtube.com/watch";
    private static final String YOUTUBE_PARAM_VIDEO = "v";

    private static final String MOVIES_BASE_URL = DYNAMIC_MOVIES_POPULAR_URL;

    private final static String API_KEY_PARAM = "api_key";

    public static URL buildUrl() {
        return buildUrl(R.id.action_sort_most_popular);
    }

    public static URL buildUrl(int sortType) {
        String base_url;

        switch (sortType) {
            case R.id.action_sort_most_popular:
                base_url = DYNAMIC_MOVIES_POPULAR_URL;
                break;
            case R.id.action_sort_top_rated:
                base_url = DYNAMIC_MOVIES_TOP_RATED_URL;
                break;
            default:
                base_url = DYNAMIC_MOVIES_POPULAR_URL;
                break;
        }

        Uri builtUri = Uri.parse(base_url).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, MovieConstants.API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static URL buildTrailersOrReviewsUrlWithMovieId(String movieId, String option) {
        String base_url;

        if (option.equals(VIDEOS)) {
            base_url = String.format(DYNAMIC_MOVIES_VIDEOS_URL, movieId);
        } else if (option.equals(REVIEWS)) {
            base_url = String.format(DYNAMIC_MOVIES_REVIEWS_URL, movieId);
        } else {
            base_url = String.format(DYNAMIC_MOVIES_VIDEOS_URL, movieId);
        }

        Uri builtUri = Uri.parse(base_url).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, MovieConstants.API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static Uri buildYoutubeUri(String videoId) {
        Uri builtUri = Uri.parse(YOUTUBE_URL).buildUpon()
                .appendQueryParameter(YOUTUBE_PARAM_VIDEO, videoId)
                .build();
        return builtUri;
    }

    public static String buildPosterUrlString(String posterPath) {
        return DYNAMIC_MOVIES_POSTER_IMAGE_URL + POSTER_SIZE_W500 + "/" + posterPath;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
