package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOCAL_KEY = "parcelable" ;
    private final String BASE_URL = "http://api.themoviedb.org";
    private PosterGridAdapter posterGridAdapter;
    private RecyclerView recyclerView;
    public static int orientation1;
    private PopularMoviesData data;
    private ProgressBar mProgressBar;
    private ArrayList<PopularMoviesData> popularMoviesDatas;
    private TextView mErrorTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mErrorTextView = (TextView) findViewById(R.id.error_text_view);
        Uri baseUri = Uri.parse(BASE_URL);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String orderby = sharedPreferences.getString(getString(R.string.order_by_key),getString(R.string.Orderby_popularityvalues));
        Uri.Builder builder1= baseUri.buildUpon();
        builder1.path("/3"+orderby);
        builder1.appendQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_TOKEN);
        orientation1 = 1;
        if(savedInstanceState != null){
            popularMoviesDatas = savedInstanceState.getParcelableArrayList(LOCAL_KEY);
            updateUI(popularMoviesDatas);
        }else {
            PopularMoviesAsyncTask asyncTask = new PopularMoviesAsyncTask();
            asyncTask.execute(builder1.toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList(LOCAL_KEY,popularMoviesDatas);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = newConfig.orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            orientation1 = 1;
        }else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            orientation1 = 0;
        }else
        {
            orientation1 = -1;
        }
    }

    public void updateUI(final ArrayList<PopularMoviesData> datas) {
        posterGridAdapter = new PosterGridAdapter(this,datas,new PosterGridAdapter.OnItemClickListener(){

            @Override
            public void OnItemClick(PopularMoviesData data) {
                Intent intent = new Intent(MainActivity.this,MoviesDetailsActivity.class);
                intent.putExtra("posterimage", data.getPosterImage());
                intent.putExtra("Overview", data.getOverview());
                intent.putExtra("ThumbNail",data.getImageThumbNail());
                intent.putExtra("rating", data.getVoteAverage());
                intent.putExtra("releasedate", data.getReleaseDate());
                intent.putExtra("originalTitle", data.getOriginalTitle());
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.activity_main);
        recyclerView.setAdapter(posterGridAdapter);
        //GridLayoutManager gridLayoutManager = null;

             GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.settings_menu){
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }
        if(id == R.id.popularityMenu){
            Uri baseUri = Uri.parse(BASE_URL);
            Uri.Builder builder = baseUri.buildUpon();
            builder.path("/3"+getString(R.string.Orderby_popularityvalues));
            builder.appendQueryParameter("api_key", "fddf5257dc90ca9b3812b4aab36a8b8a");
            Log.d("hey",builder.toString());
            new PopularMoviesAsyncTask().execute(builder.toString());
        }

        if(id == R.id.topratedMenu){
            Uri baseUri = Uri.parse(BASE_URL);
            Uri.Builder builder = baseUri.buildUpon();
            builder.path("/3"+getString(R.string.Orderby_topratingvalue));
            builder.appendQueryParameter("api_key", "fddf5257dc90ca9b3812b4aab36a8b8a");
            new PopularMoviesAsyncTask().execute(builder.toString());
        }
        return super.onOptionsItemSelected(item);
    }




    public class PopularMoviesAsyncTask extends AsyncTask<String,Void,ArrayList<PopularMoviesData>>{
        @Override
        protected void onPreExecute() {
            mErrorTextView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected ArrayList<PopularMoviesData> doInBackground(String... params) {


                popularMoviesDatas = NetworkUtils.getResultFromJSONResponse(params[0]);


            return popularMoviesDatas;
        }

        @Override
        protected void onPostExecute(ArrayList<PopularMoviesData> popularMoviesDatas) {
            mProgressBar.setVisibility(View.GONE);
            if(popularMoviesDatas != null && popularMoviesDatas.size() != 0) {
                mErrorTextView.setVisibility(View.GONE);
                updateUI(popularMoviesDatas);

            }else {
                if(recyclerView != null) {
                    recyclerView.setVisibility(View.GONE);
                }
                mErrorTextView.setVisibility(View.VISIBLE);
            }

        }
    }




}
