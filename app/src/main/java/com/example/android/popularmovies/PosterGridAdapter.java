package com.example.android.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by rishw on 5/11/2017.
 */

public class PosterGridAdapter extends RecyclerView.Adapter<PosterGridAdapter.ViewHolder> {

    public static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    private List<PopularMoviesData> mPopularMoviesDatas;
    private Context context;

    public interface OnItemClickListener{
        void OnItemClick(PopularMoviesData data);
    }

    private final OnItemClickListener listener;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View PosterView = layoutInflater.inflate(R.layout.poster_grid,parent,false);
        ViewHolder viewHolder = new ViewHolder(PosterView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {



        holder.bind(mPopularMoviesDatas.get(position),listener);


    }

    @Override
    public int getItemCount() {
        return mPopularMoviesDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;

        public ViewHolder(View itemView) {

            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.image_posters);

        }

        public void bind(final PopularMoviesData data, final OnItemClickListener listener){

            String posterUri = data.getPosterImage();

            DisplayMetrics displayMetrics = itemView.getContext().getResources().getDisplayMetrics();

            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;

            Uri baseUri = Uri.parse(BASE_IMAGE_URL);

            Uri.Builder builder = baseUri.buildUpon();
            builder.path("/t/p/w185/"+posterUri);
            if(MainActivity.orientation1 == 1) {
                Picasso.with(itemView.getContext()).load(builder.toString()).resize(width / 2, height / 2).noPlaceholder().into(mImageView);
            }else if(MainActivity.orientation1 == 0){
                Picasso.with(itemView.getContext()).load(builder.toString()).resize(width / 2, height).noPlaceholder().into(mImageView);
            }
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    listener.OnItemClick(data);
                }
            });

        }

    }

    public PosterGridAdapter(Context context,List<PopularMoviesData> movieList,OnItemClickListener listener){
        this.mPopularMoviesDatas = movieList;
        this.context = context;
        this.listener = listener;
    }

    private Context getContext(){
        return context;
    }
}
