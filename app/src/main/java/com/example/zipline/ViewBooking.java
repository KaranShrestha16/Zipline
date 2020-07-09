package com.example.zipline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.zipline.API.BookingAPI;
import com.example.zipline.API.Url;
import com.example.zipline.Fragments.Booking;

public class ViewBooking extends AppCompatActivity {
    private int booking_id;
    private TextView tv_date_view_booking,tv_zipline_type_view_booking,tv_tourist_view_booking
    ,tv_totalPeople_view_booking, tv_totalAmount_view_booking;
    private Button btn_delete_viewBooking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        tv_date_view_booking= findViewById(R.id.tv_totalAmount_view_booking);
        tv_zipline_type_view_booking= findViewById(R.id.tv_zipline_type_view_booking);
        tv_tourist_view_booking= findViewById(R.id.tv_tourist_view_booking);
        tv_totalPeople_view_booking= findViewById(R.id.tv_totalPeople_view_booking);
        tv_totalAmount_view_booking= findViewById(R.id.tv_totalAmount_view_booking);
        btn_delete_viewBooking= findViewById(R.id.btn_delete_booking);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            booking_id = bundle.getInt("booking_id");
        }
        BookingAPI bookingAPI = Url.getInstance().create(BookingAPI.class);






    }
}
