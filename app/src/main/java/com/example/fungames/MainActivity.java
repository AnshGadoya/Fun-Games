package com.example.fungames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fungames.cardMemory.mg_Menu;
import com.example.fungames.colorHunt.ch_menu_screen;
import com.example.fungames.flappybird.Constants;
import com.example.fungames.flappybird.GamePanel;
import com.example.fungames.flappybird.fb_MainActivity;
import com.example.fungames.soundMemory.smg_gameScreen;
import com.example.fungames.TicTacToe.ttt_OfflineGameMenuActivity;
import com.example.fungames.wordSearch.ui.DashboardActivity;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageView menuIcon;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CardView ticTacToeCard = findViewById(R.id.tic_tac_toe);
        CardView memoryGame = findViewById(R.id.memory_game);
        CardView colorHunt = findViewById(R.id.color_hunt);
        CardView soundMemory = findViewById(R.id.sound_memory);
        CardView wordSearch = findViewById(R.id.word_search);
        CardView flappyBird = findViewById(R.id.flappy_bird);


        drawerLayout = findViewById(R.id.drawer_layout);
        menuIcon = findViewById(R.id.menu_icon);
        navigationView = findViewById(R.id.nav_view);


        // Set an OnClickListener on the menu icon to open the Settings screen (activity_system.xml)
        menuIcon.setOnClickListener(v -> {
            vibrateIfEnabled();
            // Start the SettingsActivity (SystemActivity) when the menu icon is clicked
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });



//        menuIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
//
//        navigationView.setNavigationItemSelectedListener(item -> {
//            int id = item.getItemId();
//
//
//
//            if (id == R.id.nav_home) {
//                Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show();
//            } else if (id == R.id.nav_about) {
//                Toast.makeText(this, "About selected", Toast.LENGTH_SHORT).show();
//            }
//
//            drawerLayout.closeDrawer(GravityCompat.START);
//            return true;
//        });




        ticTacToeCard.setOnClickListener(v -> {
            vibrateIfEnabled();
            // Handle the click event here
            Toast.makeText(getApplicationContext(), "Tic Tac Toe Clicked", Toast.LENGTH_SHORT).show();

            // Example: Navigate to a new activity
            Intent intentTTT = new Intent(MainActivity.this, ttt_OfflineGameMenuActivity.class);
            startActivity(intentTTT);

        });

        memoryGame.setOnClickListener(v -> {
            vibrateIfEnabled();
            // Handle the click event here
            Toast.makeText(getApplicationContext(), "Memory Game Clicked", Toast.LENGTH_SHORT).show();

            Intent intentMG = new Intent(MainActivity.this, mg_Menu.class);
            startActivity(intentMG);

        });

        colorHunt.setOnClickListener(v -> {
            vibrateIfEnabled();
            // Handle the click event here
            Toast.makeText(getApplicationContext(), "Color Hunt Game Clicked", Toast.LENGTH_SHORT).show();

            Intent intentMG = new Intent(MainActivity.this, ch_menu_screen.class);
            startActivity(intentMG);

        });

        soundMemory.setOnClickListener(v -> {
            vibrateIfEnabled();
            // Handle the click event here
            Toast.makeText(getApplicationContext(), "Sound Memory Game Clicked", Toast.LENGTH_SHORT).show();

            Intent intentSMG = new Intent(MainActivity.this, smg_gameScreen.class);
            startActivity(intentSMG);

        });

        wordSearch.setOnClickListener(v -> {
            vibrateIfEnabled();
            // Handle the click event here
            Toast.makeText(getApplicationContext(), "Word Search Game Clicked", Toast.LENGTH_SHORT).show();

            Intent intentSMG = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intentSMG);

        });

        flappyBird.setOnClickListener(v -> {
            vibrateIfEnabled();

            // Handle the click event here
            Toast.makeText(getApplicationContext(), "Flappy Bird Game Clicked", Toast.LENGTH_SHORT).show();

            Intent intentFB = new Intent(MainActivity.this, fb_MainActivity.class);
            startActivity(intentFB);

        });




    }


    public void vibrateIfEnabled() {
        boolean vibrationEnabled = getSharedPreferences("SettingsPrefs", MODE_PRIVATE)
                .getBoolean("vibration_enabled", false);

        if (vibrationEnabled) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null && vibrator.hasVibrator()) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(100); // Deprecated but needed for older devices
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MusicManager.start(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicManager.stop(); // Only keep music in dashboard & settings
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}