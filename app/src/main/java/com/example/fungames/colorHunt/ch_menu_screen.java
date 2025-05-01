package com.example.fungames.colorHunt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fungames.R;

public class ch_menu_screen extends AppCompatActivity {

   Button easyButton, mediumButton, hardButton;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.ch_menu); // Create this XML file

       easyButton = findViewById(R.id.easyButton);
       mediumButton = findViewById(R.id.mediumButton);
       hardButton = findViewById(R.id.hardButton);

       easyButton.setOnClickListener(v -> openGame(10000));  // 10 seconds
       mediumButton.setOnClickListener(v -> openGame(7000)); // 7 seconds
       hardButton.setOnClickListener(v -> openGame(4000));   // 4 seconds
   }

   private void openGame(int timeLimit) {
       Intent intent = new Intent(ch_menu_screen.this, ch_game_screen.class);
       intent.putExtra("timeLimit", timeLimit); // Pass the time limit
       startActivity(intent);
   }
}
