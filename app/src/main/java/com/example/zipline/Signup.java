package com.example.zipline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zipline.API.Url;
import com.example.zipline.API.UserAPI;
import com.example.zipline.Model.ResponseFromAPI;
import com.example.zipline.Model.UserModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {
    private TextView tv_signup_back;
    private Button btn_signup;
    private TextInputLayout input_signup_email,input_signup_password,input_signup_re_password,input_signup_username;
    private static final Pattern PASSWORD_PATTERN= Pattern.compile("^"+"(?=.*[0-9])"+ "(?=.*[a-z])" +"(?=.*[A-Z])"+"(?=.*[@#$%^&+=])"+"(?=\\S+$)"+".{8,}"+"$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        tv_signup_back= findViewById(R.id.tv_signup_back);
        input_signup_email= findViewById(R.id.input_signup_email);
        input_signup_password= findViewById(R.id.input_signup_password);
        input_signup_re_password= findViewById(R.id.input_signup_re_password);
        input_signup_username= findViewById(R.id.input_signup_username);
        btn_signup= findViewById(R.id.btn_signup);

        tv_signup_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Signup.this, Login.class);
                startActivity(i);
                finish();

            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validation()== true){
                    UserModel userModel= new UserModel();
                    userModel.setEmail(input_signup_email.getEditText().getText().toString().trim());
                    userModel.setPassword(input_signup_password.getEditText().getText().toString().trim());
                    userModel.setUsername(input_signup_re_password.getEditText().getText().toString().trim());

                    UserAPI userAPI= Url.getInstance().create(UserAPI.class);
                    Call<ResponseFromAPI> responseFromAPICall= userAPI.signup(userModel);
                    responseFromAPICall.enqueue(new Callback<ResponseFromAPI>() {
                        @Override
                        public void onResponse(Call<ResponseFromAPI> call, Response<ResponseFromAPI> response) {
                            if(!response.isSuccessful()){
                                Toast.makeText(Signup.this, response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                            }else {
                                if(response.body().getStatus()){
                                    Toast.makeText(Signup.this, response.body().getMessage()+"", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(Signup.this, Login.class);
                                    startActivity(i);
                                    finish();
                                }else {
                                    Toast.makeText(Signup.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseFromAPI> call, Throwable t) {
                            Toast.makeText(Signup.this, "Error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                        }
                    });



                }


            }
        });

        input_signup_re_password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                return false;
            }
        });


    }

    public Boolean Validation() {
        String email = input_signup_email.getEditText().getText().toString().trim();
        String username = input_signup_username.getEditText().getText().toString().trim();
        String password = input_signup_password.getEditText().getText().toString().trim();
        String repassword = input_signup_re_password.getEditText().getText().toString().trim();

        if (username.isEmpty()) {
            input_signup_email.setError(null);
            input_signup_password.setError(null);
            input_signup_re_password.setError(null);
            input_signup_username.setError("Username Field cannot be empty");
            return false;
        } else if (username.length() < 5) {
            input_signup_email.setError(null);
            input_signup_password.setError(null);
            input_signup_re_password.setError(null);
            input_signup_username.setError("Username must be 5 character long ");
            return false;
        } else if (email.isEmpty()) {
            input_signup_username.setError(null);
            input_signup_password.setError(null);
            input_signup_re_password.setError(null);
            input_signup_email.setError("Email Field cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_signup_username.setError(null);
            input_signup_password.setError(null);
            input_signup_re_password.setError(null);
            input_signup_email.setError("Please enter valid email address");
            return false;
        } else if (password.isEmpty()) {
            input_signup_username.setError(null);
            input_signup_re_password.setError(null);
            input_signup_email.setError(null);
            input_signup_password.setError("Password Field cannot be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            input_signup_username.setError(null);
            input_signup_re_password.setError(null);
            input_signup_email.setError(null);
            input_signup_password.setError("Password must include symbole,number, lower and upper case");
            return false;
        } else if (repassword.isEmpty()) {
            input_signup_username.setError(null);
            input_signup_password.setError(null);
            input_signup_email.setError(null);
            input_signup_re_password.setError("Password Do not Match");
            return false;
        } else if (!repassword.equals(password)) {
            input_signup_username.setError(null);
            input_signup_password.setError(null);
            input_signup_email.setError(null);
            input_signup_re_password.setError("Password Do not Match");
            return false;
        } else {
            input_signup_username.setError(null);
            input_signup_password.setError(null);
            input_signup_email.setError(null);
            input_signup_re_password.setError(null);
            return true;

        }
    }

}
