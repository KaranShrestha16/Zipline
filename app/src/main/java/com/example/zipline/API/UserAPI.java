package com.example.zipline.API;

import com.example.zipline.Model.ResponseFromAPI;
import com.example.zipline.Model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserAPI {
    @FormUrlEncoded
    @POST("dhulikhel_zipline/v1/user/login")
    Call<ResponseFromAPI> login(@Field("email") String email, @Field("password")String password);

    @POST("dhulikhel_zipline/v1/user/signup")
    Call<ResponseFromAPI> signup(@Body UserModel userModel);

    @GET("dhulikhel_zipline/v1/user/get_user/{id}")
    Call<UserModel> getUser(@Header("Authorization") String accessToken, @Path("id") int id);



}
