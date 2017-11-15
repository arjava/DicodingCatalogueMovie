package com.arjava.dicodingcataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arjava.dicodingcataloguemovie.R;
import com.arjava.dicodingcataloguemovie.activity.DetailMovies;
import com.arjava.dicodingcataloguemovie.listener.RecyclerViewItemClickListener;
import com.arjava.dicodingcataloguemovie.model.MovieItems;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by arjava on 11/15/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //create Object
    private List<MovieItems.ResultsBean> movieItemsList;
    private int rowLayout;
    private Context context;
    private static final String TAG = MovieAdapter.class.getSimpleName();
    private RecyclerViewItemClickListener recyclerViewItemClickListener;
    private String url_image = "http://image.tmdb.org/t/p/w342/";

    //konstruktor
    public MovieAdapter(List<MovieItems.ResultsBean> movieItems, int rowLayout, Context context) {
        this.movieItemsList = movieItems;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    //mengatur tampilan layout
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        final MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = movieViewHolder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    recyclerViewItemClickListener.onItemClick(adapterPosition, movieViewHolder.itemView);
                }
            }
        });
        return movieViewHolder;
    }

    //menampilkan hasil dari data yang kita ambil dari API
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //mengambil posisi
        final MovieItems.ResultsBean result = movieItemsList.get(position);
        //membuat holder
        final MovieAdapter.MovieViewHolder movieViewHolder = (MovieAdapter.MovieViewHolder) holder;
        //poster_id (untuk mengambil gambar)
        final String id_poster = result.getPoster_path();
        final String poster_image = url_image+id_poster;

        //menampilkan ke dalam textView
        movieViewHolder.textViewTitle.setText(result.getTitle());
        movieViewHolder.textViewDescription.setText(result.getOverview());
        movieViewHolder.textViewdate.setText(result.getRelease_date());

        //menampilkan gambar
        Glide
                .with(context)
                .load(poster_image)
                .centerCrop()
                .override(300,400)
                .placeholder(R.drawable.ic_crop_original_black_24dp)
                .into(movieViewHolder.imageView);

        //penanganan ketika layout diklik
        movieViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent det = new Intent(view.getContext(), DetailMovies.class);
                det.putExtra("vote", result.getVote_average());
                det.putExtra("orititle", result.getOriginal_title());
                det.putExtra("overview", result.getOverview());
                det.putExtra("poster", result.getPoster_path());
                view.getContext().startActivity(det);
            }
        });

    }

    //inisiasi object
    static class MovieViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        ImageView imageView;
        TextView textViewTitle, textViewDescription, textViewdate;

        public MovieViewHolder(View itemView) {
            super(itemView);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutContentMovie);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewItemMovie);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViemItemTitle);
            textViewDescription = (TextView) itemView.findViewById(R.id.textViemItemDesc);
            textViewdate = (TextView) itemView.findViewById(R.id.textViemItemDate);

        }
    }

    //mengambil posisi
    @Override
    public int getItemCount() {
        return movieItemsList == null ? 0: movieItemsList.size();
    }
}
