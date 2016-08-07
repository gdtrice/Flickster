package com.example.gtrice.flickster.models;

import com.example.gtrice.flickster.adapters.MovieArrayAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by gtrice on 8/3/16.
 */
public class MoviePager {
    private String getNextPageUrl() {
        currentPage++;
        return String.format(urlFormat, currentPage);
    }

    int currentPage;
    int maxPages;
    String nextPageUrl;
    final String urlFormat = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&page=%s";
    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;

    public boolean isLoading() {
        return isLoading;
    }

    boolean isLoading = false;

    public MoviePager (MovieArrayAdapter adapter, ArrayList<Movie> movies) {
        this.movieAdapter = adapter;
        this.movies = movies;
    }

    public void getNextMovies() {
        isLoading = false;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getNextPageUrl(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;
                try {
                    currentPage = response.getInt("page");
                    maxPages = response.getInt("total_pages");
                    movieJsonResults = response.getJSONArray("results");
                    addMoviesAndNotify(Movie.fromJSONArray(movieJsonResults));
                    isLoading = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void addMoviesAndNotify(ArrayList<Movie> movies) {
        this.movies.addAll(movies);
        this.movieAdapter.notifyDataSetChanged();
    }
}