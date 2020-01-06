package com.example.myapplication.Remote;

import com.example.myapplication.Model.PlaceDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface iGoogleAPIService {

    @GET
    Call<PlaceDetail> getDetailPlace(@Url String url);

}
