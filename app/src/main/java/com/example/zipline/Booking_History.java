package com.example.zipline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zipline.API.BookingAPI;
import com.example.zipline.API.Url;
import com.example.zipline.Adapter.BookingAdapter;
import com.example.zipline.Model.BookingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Booking_History extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking__history);
        recyclerView= findViewById(R.id.booking_recyclearvView);
        searchButton= findViewById(R.id.ed_search_booking_history);

        BookingAPI bookingAPI = Url.getInstance().create(BookingAPI.class);
        Call<List<BookingModel>> listCall= bookingAPI.getBookingById(Url.accessToken,Url.id);
        listCall.enqueue(new Callback<List<BookingModel>>() {
            @Override
            public void onResponse(Call<List<BookingModel>> call, Response<List<BookingModel>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Booking_History.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if (response.body().isEmpty()){
                        Toast.makeText(Booking_History.this, "You didn't booked Yet", Toast.LENGTH_SHORT).show();
                    }else{
                        List<BookingModel> bookingModels= response.body();
                        final BookingAdapter bookingAdapter= new BookingAdapter(Booking_History.this, bookingModels);
                        recyclerView.setAdapter(bookingAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Booking_History.this));
                        searchButton.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                bookingAdapter.getFilter().filter(charSequence);
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });

                    }

                }
            }

            @Override
            public void onFailure(Call<List<BookingModel>> call, Throwable t) {
                Toast.makeText(Booking_History.this, t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
