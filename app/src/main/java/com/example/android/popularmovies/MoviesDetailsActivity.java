package com.example.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MoviesDetailsActivity extends AppCompatActivity {
    public static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private TextView mTitleView;
    private ImageView imageView;
    private TextView mReleaseDateView;
    private TextView mRatingView;
    private TextView mOverView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("originalTitle"));
        mTitleView = (TextView) findViewById(R.id.originaltitle);
        mTitleView.setText(intent.getStringExtra("originalTitle"));
        imageView = (ImageView)findViewById(R.id.thumbnail);
        Uri baseUri = Uri.parse(BASE_IMAGE_URL);
        Uri.Builder builder = baseUri.buildUpon();
        builder.path("/t/p/w185/"+intent.getStringExtra("posterimage"));
        Picasso.with(this).load(builder.toString()).resize(600,800).into(imageView);
        mReleaseDateView = (TextView) findViewById(R.id.releasedate);
        mReleaseDateView.setText(intent.getStringExtra("releasedate"));
        mRatingView = (TextView) findViewById(R.id.rating);
        mRatingView.setText(intent.getDoubleExtra("rating",0.0)+"/10");
        mOverView = (TextView) findViewById(R.id.overview);
        mOverView.setText(intent.getStringExtra("Overview"));
    }

}
