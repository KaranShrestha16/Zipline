package com.example.zipline.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zipline.API.BookingAPI;
import com.example.zipline.API.Url;
import com.example.zipline.Booking_History;
import com.example.zipline.Model.BookingModel;
import com.example.zipline.Model.ResponseFromAPI;
import com.example.zipline.Model.UserModel;
import com.example.zipline.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Booking extends Fragment implements DatePickerDialog.OnDateSetListener {
    private String[] zipline_types = {"Classic", "Superman", "Couple"};
    private Spinner spinner_ziplineType;
    private String zipline_type, tourist;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btn_booking;
    private LinearLayout linearLayout_date;
    private TextView tv_date_show;
    private EditText ed_total_people, ed_total_amount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_booking, container, false);
        radioGroup = view.findViewById(R.id.radioGroup_booking);
        btn_booking = view.findViewById(R.id.btn_booking);
        linearLayout_date = view.findViewById(R.id.linearLayout_date);
        tv_date_show = view.findViewById(R.id.tv_date_show);
        ed_total_amount = view.findViewById(R.id.ed_total_amount);
        ed_total_people = view.findViewById(R.id.ed_total_people);
        spinner_ziplineType = view.findViewById(R.id.spinner_zipline_type);

        ed_total_amount.setEnabled(false);
//        spinner_ziplineType.setOnItemSelectedListener(this);
        spinner_ziplineType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View views, int i, long l) {
                zipline_type = zipline_types[i];
                logic(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, zipline_types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_ziplineType.setAdapter(adapter);


        linearLayout_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = view.findViewById(selectedId);
                tourist = radioButton.getText().toString().trim();
                logic(view);
            }
        });


        ed_total_people.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View views, int i, KeyEvent keyEvent) {
                logic(view);
                return false;

            }
        });


        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()== true){

                    BookingModel bookingModel= new BookingModel();
                    bookingModel.setDate(tv_date_show.getText().toString().trim());
                    bookingModel.setTourist(tourist);
                    bookingModel.setZipline_type(zipline_type);
                    bookingModel.setTotal_people(Integer.parseInt(ed_total_people.getText().toString().trim()));
                    bookingModel.setTotal_amount(Integer.parseInt(ed_total_amount.getText().toString().trim()));
                    bookingModel.setUser_id(Url.id);

                    BookingAPI bookingAPI= Url.getInstance().create(BookingAPI.class);
                    Call<ResponseFromAPI> responseFromAPICall= bookingAPI.booking(Url.accessToken,bookingModel);
                     responseFromAPICall.enqueue(new Callback<ResponseFromAPI>() {
                         @Override
                         public void onResponse(Call<ResponseFromAPI> call, Response<ResponseFromAPI> response) {
                             if(!response.isSuccessful()){
                                 Toast.makeText(getContext(), response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                             }else {

                                 Toast.makeText(getContext(), response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                                 Intent intent = new Intent(getContext(), Booking_History.class);
                                 startActivity(intent);

                             }
                         }

                         @Override
                         public void onFailure(Call<ResponseFromAPI> call, Throwable t) {
                             Toast.makeText(getContext(), t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
                         }
                     });

                }
            }
        });


        return view;
    }


    public void DatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        tv_date_show.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
    }


    public void logic(View view) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = view.findViewById(selectedId);
        tourist = radioButton.getText().toString().trim();
        int total_amount, total_people = 0;
        try {
            total_people = Integer.parseInt(ed_total_people.getText().toString().trim());
        } catch (NumberFormatException e) {
            total_people = 0;
        }
        if (zipline_type.equals("Classic")) {
            if (tourist.equals("Local")) {
                total_amount = total_people * 2500;
                ed_total_amount.setText(total_amount + "");
            } else if (tourist.equals("SARRC")) {
                total_amount = total_people * 3000;
                ed_total_amount.setText(total_amount + "");
            } else {
                total_amount = total_people * 3500;
                ed_total_amount.setText(total_amount + "");
            }

        } else if (zipline_type.equals("Superman")) {
            if (tourist.equals("Local")) {
                total_amount = total_people * 3500;
                ed_total_amount.setText(total_amount + "");
            } else if (tourist.equals("SARRC")) {
                total_amount = total_people * 4000;
                ed_total_amount.setText(total_amount + "");
            } else {
                total_amount = total_people * 4500;
                ed_total_amount.setText(total_amount + "");
            }
        } else {
            if (tourist.equals("Local")) {
                total_amount = total_people * 4500;
                ed_total_amount.setText(total_amount + "");
            } else if (tourist.equals("SARRC")) {
                total_amount = total_people * 5000;
                ed_total_amount.setText(total_amount + "");
            } else {
                total_amount = total_people * 6000;
                ed_total_amount.setText(total_amount + "");
            }
        }
    }

    public Boolean validation() {
        if (tv_date_show.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Please Select Booking Date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (ed_total_people.getText().toString().trim().isEmpty()) {
            ed_total_people.setError("Please Enter People number ");
            Toast.makeText(getContext(), "Please Enter the People Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
}


