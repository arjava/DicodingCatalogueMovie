package com.arjava.dicodingcataloguemovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arjava.dicodingcataloguemovie.R;
import com.bumptech.glide.Glide;

/**
 * Created by arjava on 11/15/17.
 */

public class DetailMovies extends AppCompatActivity {

    ImageView imageViewDetails;
    TextView textViewVote, textViewTitle, textViewOverview;
    String url_image = "http://image.tmdb.org/t/p/w342/";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_content_movie);

        imageViewDetails = (ImageView) findViewById(R.id.imageViewDetails);
        textViewVote = (TextView) findViewById(R.id.textViewVote);
        textViewTitle = (TextView) findViewById(R.id.textTitleDetails);
        textViewOverview = (TextView) findViewById(R.id.overViewDetails);
        progressBar = (ProgressBar) findViewById(R.id.progressBarDetail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);

        Intent movie = getIntent();
        final String titleOri = movie.getStringExtra("orititle");
        final Double voteAvg = movie.getDoubleExtra("vote",0.0);
        final String overview = movie.getStringExtra("overview");
        final String poster_path = movie.getStringExtra("poster");
        final String imageLoad = url_image+poster_path;

        Glide
                .with(DetailMovies.this)
                .load(imageLoad)
                .placeholder(R.drawable.ic_crop_original_black_24dp)
                .into(imageViewDetails);
        textViewVote.setText(String.valueOf(voteAvg));
        textViewTitle.setText(titleOri);
        textViewOverview.setText(overview);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
