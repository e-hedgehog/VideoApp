package com.ehedgehog.android.videoapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        NavigationUI.setupActionBarWithNavController(this,
                Navigation.findNavController(this, R.id.nav_host_fragment));

        Navigation.findNavController(this, R.id.nav_host_fragment)
                .addOnDestinationChangedListener((controller, destination, arguments) -> {
                    if (destination.getId() != R.id.videoPlayerFragment)
                        getSupportActionBar().setTitle(R.string.app_name);
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
                || super.onSupportNavigateUp();
    }
}
