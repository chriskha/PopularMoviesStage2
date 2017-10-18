package com.example.android.popularmoviesstage2;

/**
 * Created by ckha on 9/12/17.
 */

public class Movie {
    private String mId;
    private String mOriginalTitle;
    private String mPosterPath;
    private String mOverview;
    private String mUserRating;
    private String mReleaseDate;

    public Movie() {
        mId = "";
        mOriginalTitle = "";
        mPosterPath = "";
        mOverview = "";
        mUserRating = "";
        mReleaseDate = "";
    }
    public Movie(String id, String originalTitle, String posterPath,
                 String overview, String userRating, String releaseDate) {
        mId = id;
        mOriginalTitle = originalTitle;
        mPosterPath = posterPath;
        mOverview = overview;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
    }

    public void setId(String id) { mId = id; }
    public void setOriginalTitle(String originalTitle) { mOriginalTitle = originalTitle; }
    public void setPosterPath(String posterPath) { mPosterPath = posterPath; }
    public void setOverview(String overview) { mOverview = overview; }
    public void setUserRating(String userRating) { mUserRating = userRating; }
    public void setReleaseDate(String releaseDate) { mReleaseDate = releaseDate; }

    public String getId() { return mId; }
    public String getOriginalTitle() { return mOriginalTitle; }
    public String getPosterPath() { return mPosterPath; }
    public String getOverview() { return mOverview; }
    public String getUserRating() { return mUserRating; }
    public String getReleaseDate() { return mReleaseDate; }
}
