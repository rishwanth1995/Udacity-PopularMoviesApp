package com.example.android.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by rishw on 3/2/2017.
 */

public class NetworkUtils {

    private NetworkUtils(){

    }

    public static ArrayList<PopularMoviesData> getResultFromJSONResponse(String string){
        URL url = createURL(string);
        ArrayList<PopularMoviesData> result = new ArrayList<>();
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
        result = extractResponseFromJson(jsonResponse);
        return result;
    }

    private static URL createURL(String string){
        URL url = null;
        try{
            url = new URL(string);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{

        String jsonResponse ="";
        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readStream(inputStream);

            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }

            if(inputStream != null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String line = bufferedReader.readLine();
            while (line != null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    private static ArrayList<PopularMoviesData> extractResponseFromJson(String json){

        ArrayList<PopularMoviesData> arrayList = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray moviesArray = jsonObject.getJSONArray("results");
            for(int i=0;i < moviesArray.length();i++) {
                JSONObject jsonMovies = moviesArray.getJSONObject(i);
                String moviesPosterPath = jsonMovies.getString("poster_path");
                String moviesOverView = jsonMovies.getString("overview");
                String moviesReleaseDate = jsonMovies.getString("release_date");
                String moviesOriginalTilte = jsonMovies.getString("original_title");
                Double moviesRating = jsonMovies.getDouble("vote_average");
                String moviesthumbNail = jsonMovies.getString("backdrop_path");

                PopularMoviesData popularMovies = new PopularMoviesData(moviesPosterPath,moviesOriginalTilte,moviesOverView,moviesRating,moviesthumbNail,moviesReleaseDate);
                arrayList.add(popularMovies);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return arrayList;

    }

}
