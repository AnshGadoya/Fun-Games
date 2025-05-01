package com.example.fungames.colorHunt;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fungames.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;




public class ch_game_screen extends AppCompatActivity {

    int score = 0;
    TextView colorText, timerText, scoreText;
    Button option1, option2, option3;
    CountDownTimer countDownTimer;




    String[] colorNames = {
            "Red", "Blue", "Green", "Yellow", "Black", "Orange", "Purple", "Pink", "Cyan", "Brown", "Gray"
    };
    int[] colorValues = {
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.BLACK,
            Color.parseColor("#FFA500"), // Orange
            Color.parseColor("#800080"), // Purple
            Color.parseColor("#FFC0CB"), // Pink
            Color.parseColor("#00FFFF"), // Cyan
            Color.parseColor("#A52A2A"), // Brown
            Color.GRAY                  // Gray
    };


    String correctAnswer;
    Random random = new Random();

    int totalLives = 5;
    int timeLimit = 5000; // Default 5 seconds

    ImageView[] hearts;


    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Stop timer on pause
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Stop timer on destroy
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ch_game_screen);

        hearts = new ImageView[]{
                findViewById(R.id.heart1),
                findViewById(R.id.heart2),
                findViewById(R.id.heart3),
                findViewById(R.id.heart4),
                findViewById(R.id.heart5)
        };

        colorText = findViewById(R.id.colorText);
        timerText = findViewById(R.id.timerText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        scoreText = findViewById(R.id.scoreText);

        //  livesText = findViewById(R.id.livesText); // Add this TextView in layout for lives

        // Get time limit from menu
        timeLimit = getIntent().getIntExtra("timeLimit", 5000); // Default 5 sec if not passed

        updateLivesDisplay();
        startNewRound();

        // Button click listener (updated)
        View.OnClickListener answerListener = v -> {
            Button clickedButton = (Button) v;
            String selectedAnswer = clickedButton.getText().toString();

            if (selectedAnswer.equals(correctAnswer)) {
                Toast.makeText(ch_game_screen.this, "Correct!", Toast.LENGTH_SHORT).show();
                score++; // Increase score
                updateScoreDisplay(); // Update score display
            } else {
                totalLives--;
                updateLivesDisplay();
                Toast.makeText(ch_game_screen.this, "Wrong! Correct: " + correctAnswer, Toast.LENGTH_SHORT).show();
                checkGameOver();
            }

            countDownTimer.cancel();
            startNewRound(); // Start next round
        };

        option1.setOnClickListener(answerListener);
        option2.setOnClickListener(answerListener);
        option3.setOnClickListener(answerListener);
    }


    private void startNewRound() {
        // Pick a random color name to display as text
        int colorNameIndex = random.nextInt(colorNames.length);
        String colorNameToDisplay = colorNames[colorNameIndex];

        // Pick a random color to set as text color
        int colorValueIndex = random.nextInt(colorValues.length);
        int colorOfText = colorValues[colorValueIndex];

        // Set the word (could be "Red", "Blue", etc.)
        colorText.setText(colorNameToDisplay);
        // Set the text color (e.g., yellow color)
        colorText.setTextColor(colorOfText);

        // Now, we need to get the color name from the color value
        correctAnswer = getColorNameFromValue(colorOfText);

        // Prepare options: 1 correct + 2 random wrong options
        List<String> options = new ArrayList<>();
        options.add(correctAnswer); // Correct color
//        options.add(colorNameToDisplay); // Displayed word

        if (!colorNameToDisplay.equals(correctAnswer)) {
            options.add(colorNameToDisplay);
        }

        // Add 2 wrong options
        while (options.size() < 3) {
            String randomOption = colorNames[random.nextInt(colorNames.length)];
            if (!options.contains(randomOption)) {
                options.add(randomOption);
            }
        }

        // Set button texts
        Collections.shuffle(options);
        option1.setText(options.get(0));
        option2.setText(options.get(1));
        option3.setText(options.get(2));

        // Start Timer
        startTimer();
    }

    // Map color value to its name
    private String getColorNameFromValue(int colorValue) {
        for (int i = 0; i < colorValues.length; i++) {
            if (colorValues[i] == colorValue) {
                return colorNames[i];
            }
        }
        return "Unknown";
    }

    private void updateScoreDisplay() {
        scoreText.setText("Score: " + score);
    }
    private void updateLivesDisplay() {
//        livesText.setText("Lives: " + totalLives);
        for (int i = 0; i < hearts.length; i++) {
            if (i < totalLives) {
                hearts[i].setImageResource(R.drawable.heart_filled);
            } else {
                hearts[i].setImageResource(R.drawable.heart_empty);
            }
        }
    }
    private void checkGameOver() {
        if (totalLives <= 0) {
            Toast.makeText(ch_game_screen.this, "Game Over!", Toast.LENGTH_LONG).show();
            finish(); // End game and return to ch_MenuActivity
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLimit, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                totalLives--; // Lose a life when time runs out
                updateLivesDisplay();
                Toast.makeText(ch_game_screen.this, "Time's up! Correct: " + correctAnswer, Toast.LENGTH_SHORT).show();
                checkGameOver();
                startNewRound(); // Next round
            }
        }.start();
    }
}

