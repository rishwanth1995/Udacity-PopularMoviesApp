package com.example.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class MoviesDetailsActivity extends AppCompatActivity {
    public static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/original/";
    private Toolbar toolbar;
    private ImageView imageView;
    private ImageView posterImageView;
    private TextView mReleaseDateView;
    private TextView mRatingView;
    private TextView mOverView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.DetailActivityTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        Intent intent = getIntent();
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setTitle(intent.getStringExtra("originalTitle"));

        imageView = (ImageView)findViewById(R.id.thumbnail);
        Uri baseUri = Uri.parse(BASE_IMAGE_URL);
        Uri.Builder builder = baseUri.buildUpon();
        builder.path("/t/p/w185/"+intent.getStringExtra("ThumbNail"));
        Picasso.with(this).load(builder.toString()).into(imageView);
        posterImageView = (ImageView) findViewById(R.id.posterImage);
        builder.path("/t/p/w185/"+intent.getStringExtra("posterimage"));
        Picasso.with(this).load(builder.toString()).resize(400,600).into(posterImageView);
        mReleaseDateView = (TextView) findViewById(R.id.releasedate);
        mReleaseDateView.setText(intent.getStringExtra("releasedate"));
        mRatingView = (TextView) findViewById(R.id.rating);
        mRatingView.setText(intent.getDoubleExtra("rating",0.0)+"/10");
        mOverView = (TextView) findViewById(R.id.overview);
        mOverView.setText(intent.getStringExtra("Overview"));
    }

}
