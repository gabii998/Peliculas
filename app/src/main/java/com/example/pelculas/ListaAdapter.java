package com.example.pelculas;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pelculas.Modelos.Pelicula;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolder>{
    private ArrayList<Pelicula> peliculas;
    private LayoutInflater inflator;
    private Context context;

    public ListaAdapter(Context context,ArrayList<Pelicula> peliculas){
        this.peliculas=peliculas;
        this.context=context;
        //inflator=LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(peliculas.get(position).getTitle());
        holder.generos.setText(TextUtils.join("/",peliculas.get(position).getGenres()));
        holder.ano.setText(peliculas.get(position).getYear());
        Glide.with(context)
                .load(peliculas.get(position).getPosterUrl())
                .override(1024,768)
                .into(holder.imageView);
        holder.imdbRating.setText("IMDb "+String.valueOf(peliculas.get(position).getImdbRating()));
        holder.ratingBar.setRating(promediarRatings(peliculas.get(position).getRatings()));
    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    public float promediarRatings(ArrayList<Integer> ratings){
        float suma=0;
        for (int i = 0; i < ratings.size() ; i++) {
            suma+=ratings.get(i);
        }
        suma=suma/ratings.size();
        return  suma/2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView generos;
        TextView ano;
        ImageView imageView;
        TextView imdbRating;
        RatingBar ratingBar;

        public ViewHolder(View view) {
            super(view);
            textView=view.findViewById(R.id.textView);
            generos=view.findViewById(R.id.generos);
            ano=view.findViewById(R.id.ano);
            imageView=view.findViewById(R.id.imageView);
            imdbRating=view.findViewById(R.id.imdbRating);
            ratingBar=view.findViewById(R.id.ratingBar);
            /*LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);*/
        }
    }

}
