package com.example.zipline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash_Screen extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView,textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        imageView=findViewById(R.id.img_splash_screen);
        textView=findViewById(R.id.tv_splash_screen);
        textView1=findViewById(R.id.tv_splash_screen2);

        imageView.setAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.fade_animation));
        textView.setAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.fade_animation_button));
        textView1.setAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.fade_animation_button));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Screen.this, Login.class);
                startActivity(intent);
                finish();
            }
        },5000);
    }

}
