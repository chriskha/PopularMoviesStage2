package com.example.android.popularmoviesstage2;

/**
 * Created by ckha on 10/18/17.
 */

public class Trailer {

    public static String KEY_MOVIE_ID = "id";
    public static String KEY_TRAILER_ID = "id";
    public static String KEY_LANGUAGE = "iso_639_1";
    public static String KEY_COUNTRY = "iso_3166_1";
    public static String KEY_VIDEO_KEY = "key";
    public static String KEY_NAME = "name";
    public static String KEY_SITE = "site";
    public static String KEY_SIZE = "size";
    public static String KEY_TYPE = "type";

    private String mMovieId;
    private String mTrailerId;
    private String mLanguage;
    private String mCountry;
    private String mKey;
    private String mName;
    private String mSite;
    private String mSize;
    private String mType;

    public Trailer() {
        mMovieId = "";
        mTrailerId = "";
        mLanguage = "";
        mCountry = "";
        mKey = "";
        mName = "";
        mSite = "";
        mSize = "";
        mType = "";
    }

    @Override
    public String toString() {
        return  "mTrailerId: " + mTrailerId + "\n" +
                "mKey: " + mKey + "\n" +
                "mName: " + mName + "\n" +
                "mSite: " + mSite + "\n";

    }

    public String getmMovieId() {
        return mMovieId;
    }

    public void setmMovieId(String mMovieId) {
        this.mMovieId = mMovieId;
    }

    public String getmTrailerId() {
        return mTrailerId;
    }

    public void setmTrailerId(String mTrailerId) {
        this.mTrailerId = mTrailerId;
    }

    public String getmLanguage() {
        return mLanguage;
    }

    public void setmLanguage(String mLanguage) {
        this.mLanguage = mLanguage;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmSite() {
        return mSite;
    }

    public void setmSite(String mSite) {
        this.mSite = mSite;
    }

    public String getmSize() {
        return mSize;
    }

    public void setmSize(String mSize) {
        this.mSize = mSize;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }
}
