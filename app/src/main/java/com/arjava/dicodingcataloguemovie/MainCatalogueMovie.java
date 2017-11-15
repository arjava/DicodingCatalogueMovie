package com.arjava.dicodingcataloguemovie;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arjava.dicodingcataloguemovie.adapter.MovieAdapter;
import com.arjava.dicodingcataloguemovie.model.MovieItems;
import com.arjava.dicodingcataloguemovie.util.ApiClient;
import com.arjava.dicodingcataloguemovie.util.ApiInterface;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arjava on 11/13/17.
 */

public class MainCatalogueMovie extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText inputSearch;
    private Button submitSearch;
    private String TAG = MainCatalogueMovie.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_catalogue_movie);

        inputSearch = (EditText) findViewById(R.id.edtSearch);
        submitSearch = (Button) findViewById(R.id.btnSearch);
        progressBar = (ProgressBar) findViewById(R.id.progressBarMain);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        submitSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitSearch.onEditorAction(EditorInfo.IME_ACTION_DONE);
                getMovie();
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        getMovieFirst();
    }

    //menggunakan search berdasarkan input user
    public void getMovie() {
        String input_movie = inputSearch.getText().toString();
        Log.d(TAG, "onClick: input_movie "+ input_movie);
        progressBar.setVisibility(View.VISIBLE);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerMovie);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainCatalogueMovie.this));
        ApiInterface apiInterface = ApiClient.getRetrofit(getApplicationContext()).create(ApiInterface.class);
        Call<MovieItems> call = apiInterface.getMovieItems(input_movie);
        call.enqueue(new Callback<MovieItems>() {
            @Override
            public void onResponse(Call<MovieItems> call, Response<MovieItems> response) {
                MovieItems data = response.body();
                if (data.getResults().size()==0) {
                    Toast.makeText(getApplicationContext(), "maaf data yang anda cari tidak ditemukan", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }else {
                    recyclerView.setAdapter(new MovieAdapter(data.getResults(), R.layout.activity_content_movie, getApplicationContext()));
                    Log.e(TAG, "onResponse: hasil pemanggilan"+ call);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MovieItems> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                Log.d(TAG, t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    //launch pertama (menggunakan Up Coming)
    public void getMovieFirst() {

        progressBar.setVisibility(View.VISIBLE);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerMovie);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainCatalogueMovie.this));
        ApiInterface apiInterface = ApiClient.getRetrofit(getApplicationContext()).create(ApiInterface.class);
        Call<MovieItems> call = apiInterface.getMovieUpcoming();
        call.enqueue(new Callback<MovieItems>() {
            //ketika server meresponse
            @Override
            public void onResponse(Call<MovieItems> call, Response<MovieItems> response) {
                MovieItems data = response.body();
                if (data.getResults().size()==0) {
                    Toast.makeText(getApplicationContext(), "maaf data yang anda cari tidak ditemukan", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }else {
                    recyclerView.setAdapter(new MovieAdapter(data.getResults(), R.layout.activity_content_movie, getApplicationContext()));
                    Log.e(TAG, "onResponse: hasil pemanggilan"+ call);
                    progressBar.setVisibility(View.GONE);
                }
            }

            //ketika gagal mendapatkan response
            @Override
            public void onFailure(Call<MovieItems> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                Log.d(TAG, t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    //penanganan untuk tombol back
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainCatalogueMovie.this);
        alert.setMessage("Apa anda yakin Ingin Keluar ? ")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        alert.create();
        alert.show();
    }
}
