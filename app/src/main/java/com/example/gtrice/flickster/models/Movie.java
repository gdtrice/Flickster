package com.example.gtrice.flickster.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gtrice on 8/1/16.
 */
public class Movie implements Parcelable {

    String backdropPath;
    String posterPath;
    String originalTitle;
    String overview;
    String releaseDate;
    float rating;

    static final String imageFormat = "https://image.tmdb.org/t/p/w342/%s";

    public String getBackdropPath() {
        return String.format(imageFormat, backdropPath);
    }

    public String getPosterPath() {
        return String.format(imageFormat, posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public float getRating() { return rating; }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(backdropPath);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeFloat(rating);
        dest.writeString(releaseDate);
    }

    private Movie(Parcel in) {
        backdropPath = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        rating = in.readFloat();
        releaseDate = in.readString();
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.rating = jsonObject.getInt("vote_average");
        this.releaseDate = jsonObject.getString("release_date");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}