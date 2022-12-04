package com.unitapplications.postmakerwithunsplashapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.unitapplications.postmakerwithunsplashapi.Adapters.ImageAdapter;
import com.unitapplications.postmakerwithunsplashapi.Api_files.ApiUtils;
import com.unitapplications.postmakerwithunsplashapi.Models.ImageModel;
import com.unitapplications.postmakerwithunsplashapi.Models.SearchModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class unsplash extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ImageModel> list;
    private GridLayoutManager layoutManager;
    private ImageAdapter imageAdapter;
    SearchView searchView;

    private int  page = 1;
    private int  pageSize = 30;
    private boolean isLoading;
    private boolean isLastPage;
    private boolean isSearch=false;
    private String search_txt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsplash);
        Objects.requireNonNull(getSupportActionBar()).hide();
        searchView = findViewById(R.id.search_bar);

        recyclerView = findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        imageAdapter = new ImageAdapter(this,list);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(imageAdapter);


        getData();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItem = layoutManager.getChildCount();
                int totalItem = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();



                if (!isLoading && !isLastPage){
                    if ((visibleItem+firstVisibleItemPosition >= totalItem)
                            && firstVisibleItemPosition >= 0
                            && totalItem >= pageSize){
                        page++;
                        if (isSearch){
                            searchData(search_txt);
                            Toast.makeText(unsplash.this, search_txt, Toast.LENGTH_SHORT).show();
                        }else getData();

                    }
                }
            }
        });
        searchView.setOnClickListener(view -> isSearch = true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchData(s);
                search_txt=s;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchData(s);
                search_txt=s;
                return true;
            }

        });
    }//onCreate

    private void searchData(String s) {
        isSearch = true;
        isLoading = true;
        ApiUtils.getApiInterface().searchImages(s).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                list.clear();
                list.addAll(response.body().getResults());
                imageAdapter.notifyDataSetChanged();
                isLoading = false;
                if (list.size()>0){
                    isLastPage = list.size()<pageSize;
                }else{
                    isLastPage=true;
                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

            }
        });
    }

    private void getData() {
        isLoading = true;
        ApiUtils.getApiInterface().getImages(page,30).enqueue(new Callback<List<ImageModel>>() {
            @Override
            public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {
                if (response.body()!=null){
                    list.addAll(response.body());
                    imageAdapter.notifyDataSetChanged();
                    Toast.makeText(unsplash.this, "Success", Toast.LENGTH_SHORT).show();
                    isLoading = false;
                    if (list.size()>0){
                        isLastPage = list.size()<pageSize;
                    }else{
                        isLastPage=true;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                Log.d("TAG2", "onFailure: "+t.getLocalizedMessage());
            }
        });
    }

}