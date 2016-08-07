package com.example.gtrice.flickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gtrice.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Movie movie = (Movie) getIntent().getParcelableExtra("details");

        TextView tvTitle = (TextView) findViewById(R.id.tvDetailsTitle);
        tvTitle.setText(movie.getOriginalTitle());

        RatingBar rbRating = (RatingBar) findViewById(R.id.rbRating);
        rbRating.setNumStars(5);
        rbRating.setRating(movie.getRating()/(float) 2.0);

        TextView tvReleaseDate = (TextView) findViewById(R.id.tvDetailsReleaseDate);
        tvReleaseDate.setText(movie.getReleaseDate());

        TextView tvDetailsDescription = (TextView) findViewById(R.id.tvDetailsDescription);
        tvDetailsDescription.setText(movie.getOverview());

        ImageView ivDetailsPoster = (ImageView) findViewById(R.id.ivDetailsPoster);
        Picasso.with(this).load(movie.getPosterPath())
                .transform(new RoundedCornersTransformation(20, 20))
                .into(ivDetailsPoster);
    }
}