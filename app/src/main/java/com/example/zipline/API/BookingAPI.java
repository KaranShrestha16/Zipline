package com.example.zipline.API;

import com.example.zipline.Model.BookingModel;
import com.example.zipline.Model.ResponseFromAPI;
import com.example.zipline.Model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookingAPI {

    @POST("dhulikhel_zipline/v1/booking")
    Call<ResponseFromAPI> booking(@Header("Authorization") String accessToken, @Body BookingModel bookingModel);

    @GET("dhulikhel_zipline/v1/booking/getBookingById/{id}")
    Call<List<BookingModel>> getBookingById(@Header("Authorization") String accessToken, @Path("id") int id);


    @GET("dhulikhel_zipline/v1/booking/viewBooking/{id}")
    Call<BookingModel> viewBooking(@Header("Authorization") String accessToken, @Path("id") int id);



}
