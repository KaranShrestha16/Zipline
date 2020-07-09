package com.example.zipline.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zipline.API.Url;
import com.example.zipline.API.UserAPI;
import com.example.zipline.Booking_History;
import com.example.zipline.Edite_Profile;
import com.example.zipline.Model.UserModel;
import com.example.zipline.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends Fragment {

    private TextView tv_username_profile,tv_user_profile_username,tv_profile_editProfile,tv_email_profile,tv_profile_booking_history;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        tv_user_profile_username= view.findViewById(R.id.tv_user_profile_username);
        tv_username_profile= view.findViewById(R.id.tv_username_profile);
        tv_profile_editProfile= view.findViewById(R.id.tv_profile_editProfile);
        tv_email_profile= view.findViewById(R.id.tv_email_profile);
        tv_profile_booking_history= view.findViewById(R.id.tv_profile_booking_history);


        UserAPI userAPI= Url.getInstance().create(UserAPI.class);
        Call<UserModel> userModelCall= userAPI.getUser(Url.accessToken,Url.id);
        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Data Not found", Toast.LENGTH_SHORT).show();
                }else{
                    tv_user_profile_username.setText(response.body().getUsername()+"");
                    tv_username_profile.setText(response.body().getUsername()+"");
                    tv_email_profile.setText(response.body().getEmail()+"");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });



        tv_profile_booking_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), Booking_History.class);
                startActivity(intent);
            }
        });
        tv_profile_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), Edite_Profile.class);
                startActivity(intent);
            }
        });


        return  view;
    }


}
