package com.example.android.popularmoviesstage2;

/**
 * Created by ckha on 10/18/17.
 */

public class UserReview {
    public static String KEY_MOVIE_ID = "id";
    public static String KEY_REVIEW_ID = "id";
    public static String KEY_AUTHOR = "author";
    public static String KEY_CONTENT = "content";
    public static String KEY_URL = "url";


    private String mMovieId;
    private String mReviewId;
    private String mAuthor;
    private String mContent;
    private String mReviewUrl;

    public UserReview() {
        mMovieId = "";
        mReviewId = "";
        mAuthor = "";
        mContent = "";
        mReviewUrl = "";
    }

    public String getmMovieId() {
        return mMovieId;
    }

    public void setmMovieId(String mMovieId) {
        this.mMovieId = mMovieId;
    }

    public String getmReviewId() {
        return mReviewId;
    }

    public void setmReviewId(String mReviewId) {
        this.mReviewId = mReviewId;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmReviewUrl() {
        return mReviewUrl;
    }

    public void setmReviewUrl(String mReviewUrl) {
        this.mReviewUrl = mReviewUrl;
    }
}
