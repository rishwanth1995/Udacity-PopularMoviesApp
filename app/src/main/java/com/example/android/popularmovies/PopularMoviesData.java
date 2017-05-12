package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rishw on 3/2/2017.
 */

public class PopularMoviesData implements Parcelable {
    private String posterImage;
    private String originalTitle;
    private String overview;
    private Double voteAverage;
    private String imageThumbNail;
    private String releaseDate;

    public PopularMoviesData(String posterImage,String originalTitle, String overview, Double voteAverage, String imageThumbNail,String releaseDate){
        this.posterImage = posterImage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.imageThumbNail = imageThumbNail;
        this.releaseDate = releaseDate;
    }

    protected PopularMoviesData(Parcel in) {
        posterImage = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        imageThumbNail = in.readString();
        releaseDate = in.readString();
        voteAverage =in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterImage);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(imageThumbNail);
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PopularMoviesData> CREATOR = new Creator<PopularMoviesData>() {
        @Override
        public PopularMoviesData createFromParcel(Parcel in) {
            return new PopularMoviesData(in);
        }

        @Override
        public PopularMoviesData[] newArray(int size) {
            return new PopularMoviesData[size];
        }
    };

    public String getPosterImage(){
        return posterImage;
    }

    public String getOriginalTitle(){
        return  originalTitle;
    }

    public String getOverview(){
        return overview;
    }

    public Double getVoteAverage(){
        return voteAverage;
    }

    public String getImageThumbNail(){
        return imageThumbNail;
    }

    public String getReleaseDate(){
        return releaseDate;
    }
}
