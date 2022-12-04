package com.unitapplications.postmakerwithunsplashapi.Api_files;


import static com.unitapplications.postmakerwithunsplashapi.Api_files.ApiUtils.ACCESS_KEY;

import com.unitapplications.postmakerwithunsplashapi.Models.ImageModel;
import com.unitapplications.postmakerwithunsplashapi.Models.SearchModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers("Authorization: Client-ID "+ACCESS_KEY)
    @GET("/photos")
    Call<List<ImageModel>> getImages(
            @Query("page") int page,
            @Query("per_page") int perPage
    );

    @Headers("Authorization: Client-ID "+ACCESS_KEY)
    @GET("/search/photos")
    Call<SearchModel> searchImages(
            @Query("query") String query
    );



}