package com.example.gtrice.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gtrice.flickster.adapters.MovieArrayAdapter;
import com.example.gtrice.flickster.models.Movie;
import com.example.gtrice.flickster.models.MoviePager;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    MoviePager pager;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        lvItems = (ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);
        pager = new MoviePager(movieAdapter, movies);
        pager.getNextMovies();


        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MovieActivity.this, MovieDetails.class);
                i.putExtra("details", movies.get(position));
                startActivity(i);
            }
        });

        lvItems.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // Do nothing
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean canGetMovies = totalItemCount != 0 &&
                        ((double)firstVisibleItem / (double) totalItemCount) > 0.6 &&
                        !pager.isLoading();
                if (canGetMovies) {
                    pager.getNextMovies();
                }
            }
        });
    }
}
