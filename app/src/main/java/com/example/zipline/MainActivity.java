package com.example.zipline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.zipline.Fragments.About;
import com.example.zipline.Fragments.Booking;
import com.example.zipline.Fragments.Home;
import com.example.zipline.Fragments.Profile;
import com.example.zipline.Fragments.Zipline_Type;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Home()).commit();
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new Home();
                            break;
                        case R.id.nav_type:
                            selectedFragment = new Zipline_Type();
                            break;
                        case R.id.nav_booking:
                            selectedFragment = new Booking();
                            break;
                        case R.id.nav_about:
                            selectedFragment = new About();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new Profile();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}
