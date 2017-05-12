package com.example.android.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by rishw on 3/3/2017.
 */

public class PopularMoviesAsyncTaskLoader extends AsyncTaskLoader<ArrayList<PopularMoviesData>> {

    private String mUrl;
    private ArrayList<PopularMoviesData> popularMoviesList;
    public PopularMoviesAsyncTaskLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<PopularMoviesData> loadInBackground() {
        if(mUrl == null){
            return null;
        }

            popularMoviesList = NetworkUtils.getResultFromJSONResponse(mUrl);

        return popularMoviesList;
    }
}
