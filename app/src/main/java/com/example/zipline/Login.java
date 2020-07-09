package com.example.zipline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zipline.API.Url;
import com.example.zipline.API.UserAPI;
import com.example.zipline.Model.ResponseFromAPI;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private TextView tv_signup;
    private TextInputLayout input_login_email,input_login_password;
    private static final Pattern PASSWORD_PATTERN= Pattern.compile("^"+"(?=.*[0-9])"+ "(?=.*[a-z])" +"(?=.*[A-Z])"+"(?=.*[@#$%^&+=])"+"(?=\\S+$)"+".{8,}"+"$");

    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_signup = findViewById(R.id.tv_signup);
        input_login_email = findViewById(R.id.input_login_email);
        input_login_password = findViewById(R.id.input_login_password);
        btn_login = findViewById(R.id.btn_login);

        input_login_email.getEditText().setText("karan@gmail.com");
        input_login_password.getEditText().setText("Karan@1234");

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this , Signup.class);
                startActivity(i);
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validation()==true){
                    UserAPI userAPI= Url.getInstance().create(UserAPI.class);
                    Call<ResponseFromAPI> userCall = userAPI.login(input_login_email.getEditText().getText().toString().trim(),input_login_password.getEditText().getText().toString().trim());
                    userCall.enqueue(new Callback<ResponseFromAPI>() {
                        @Override
                        public void onResponse(Call<ResponseFromAPI> call, Response<ResponseFromAPI> response) {
                            if(!response.isSuccessful()){
                                Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }else {
                                if(response.body().getStatus()){
                                    Url.accessToken = response.body().getAccessToken();
                                    Url.id = response.body().getId();
                                    Log.d("User Id", response.body().getId()+"");
                                    Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseFromAPI> call, Throwable t) {
                            Toast.makeText(Login.this, "Error: "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });

    }

    public Boolean Validation() {
        String email = input_login_email.getEditText().getText().toString().trim();
        String password = input_login_password.getEditText().getText().toString().trim();

       if (email.isEmpty()) {
           input_login_password.setError(null);
           input_login_email.setError("Email Field cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
           input_login_password.setError(null);
           input_login_email.setError("Please enter valid email address");
            return false;
        } else if (password.isEmpty()) {
           input_login_email.setError(null);
           input_login_password.setError("Password Field cannot be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
           input_login_email.setError(null);
           input_login_password.setError("Password must include symbole,number, lower and upper case");
            return false;
        }  else {
           input_login_password.setError(null);
           input_login_email.setError(null);

            return true;

        }
    }

}
