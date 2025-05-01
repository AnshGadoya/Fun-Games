package com.example.fungames.cardMemory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fungames.R;

import java.util.Locale;

public class mg_GameScreen extends AppCompatActivity {

    int lastCard = 0;
    int score = 0;
    int fail = 0;
    GridLayout glCards;
    TextView txtTimer;
    ImageButton btnBack;
    CountDownTimer countDownTimer;
    Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        //getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.mg_gamescreen);


        glCards = findViewById(R.id.glCards);
        txtTimer = findViewById(R.id.txtTimer);
        btnBack = findViewById(R.id.btnBack);


        Intent intent = getIntent();
        int num = intent.getIntExtra("numbersId", 0);
        int flo = intent.getIntExtra("flowersId", 0);
        int emo = intent.getIntExtra("emoteId", 0);
        int spo = intent.getIntExtra("sportsId", 0);
        int shp = intent.getIntExtra("shapeId", 0);
        int dck = intent.getIntExtra("deckId", 0);

        int milisec = intent.getIntExtra("milisec",0);

        if (num == 1)
            numbers();
        else if (flo == 2)
            flowers();
        else if (emo == 3)
            emote();
        else if (spo == 4)
            sports();
        else if (shp == 5)
            shape();
        else if (dck == 6)
            deck();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    this.finalize();
                    countDownTimer.cancel();
                    Intent intent = new Intent(context, mg_Menu.class);
                    startActivity(intent);
                }
                catch (Throwable throwable){
                    throwable.printStackTrace();
                }
            }
        });

                        // CountDown Timer //
        countDownTimer = new CountDownTimer(milisec, 1000) {
            @Override
            public void onTick(long l) {
                int min = (int) (l / 1000) / 60;
                int sec = (int) (l / 1000) % 60;
                String timerformat = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                txtTimer.setText("Remaining Time: " + timerformat);
            }

            @Override
            public void onFinish() {
                Toast.makeText(context, "Time's Up", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, mg_ResultLose.class);
                intent.putExtra("score_lose", score);
                intent.putExtra("fail_lose", fail);
                startActivity(intent);
            }
        }.start();

    }

    @Override
    public void onBackPressed() {
        // Geri tuşunu pasif hale getirme //
        super.onBackPressed();
    } // Back Button Disable

    public void numbers()
    {
        glCards = findViewById(R.id.glCards);
        final mg_card_numbers cards[] = new mg_card_numbers[16];

        for (int i = 1; i <= 16; i++) {
            cards[i-1] = new mg_card_numbers(context, i);
            cards[i-1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { // Açık-Kapalı Durum Ayarlama
                    final mg_card_numbers c = (mg_card_numbers)view;
                    c.turn();
                    if (lastCard > 0)
                    {
                        final mg_card_numbers c2 = (mg_card_numbers)findViewById(lastCard);

                        if (c.frontID == c2.frontID && c.getId() != c2.getId())
                        {
                            // Eşleştiler
                            c.isTurn = false;
                            c2.isTurn = false;
                            score++;
                            lastCard = 0;
                            if (score == 8){
                                try {
                                    this.finalize();
                                    countDownTimer.cancel();
                                    Intent intent = new Intent(context, mg_ResultWin.class);
                                    intent.putExtra("score",score);
                                    intent.putExtra("fail",fail);
                                    startActivity(intent);
                                }
                                catch (Throwable throwable){
                                    throwable.printStackTrace();
                                }
                            }
                        }
                        else
                        {
                            //Eşleşmediler geri çevir
                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    c.turn();
                                    c2.turn();
                                }
                            },500);
                            lastCard = 0;
                            fail++;
                        }
                    }
                    else
                    {
                        lastCard = c.getId();
                    }
                }
            });
        }

        // Karıştır
        for (int j = 0; j < 16; j++) {
            int random = (int)(Math.random() * 16);
            mg_card_numbers c = cards[random];
            cards[random] = cards[j];
            cards[j] = c;
        }

        // Yazdır
        for (int j = 0; j < 16; j++) {
            glCards.addView(cards[j]);
        }
    }

    public void flowers()
    {
        glCards = findViewById(R.id.glCards);

        mg_card_flowers cards[] = new mg_card_flowers[16];

        for (int i = 1; i <= 16; i++) {
            cards[i-1] = new mg_card_flowers(context, i);
            cards[i-1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { // Açık-Kapalı Durum Ayarlama
                    final mg_card_flowers c = (mg_card_flowers)view;
                    c.turn();
                    if (lastCard > 0)
                    {
                        final mg_card_flowers c2 = (mg_card_flowers)findViewById(lastCard);

                        if (c.frontID == c2.frontID && c.getId() != c2.getId())
                        {
                            // Eşleştiler
                            c.isTurn = false;
                            c2.isTurn = false;
                            score++;
                            lastCard = 0;
                            if (score == 8){
                                try {
                                    this.finalize();
                                    countDownTimer.cancel();
                                    Intent intent = new Intent(context, mg_ResultWin.class);
                                    intent.putExtra("score",score);
                                    intent.putExtra("fail",fail);
                                    startActivity(intent);
                                }
                                catch (Throwable throwable){
                                    throwable.printStackTrace();
                                }
                            }
                        }
                        else
                        {
                            //Eşleşmediler geri çevir
                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    c.turn();
                                    c2.turn();
                                }
                            },500);
                            lastCard = 0;
                            fail++;
                        }
                    }
                    else
                    {
                        lastCard = c.getId();
                    }
                }
            });
        }

        // Karıştır
        for (int j = 0; j < 16; j++) {
            int random = (int)(Math.random() * 16);
            mg_card_flowers c = cards[random];
            cards[random] = cards[j];
            cards[j] = c;
        }

        // Yazdır
        for (int j = 0; j < 16; j++) {
            glCards.addView(cards[j]);
        }
    }

    public void emote()
    {
        glCards = findViewById(R.id.glCards);

        mg_card_emote cards[] = new mg_card_emote[16];

        for (int i = 1; i <= 16; i++) {
            cards[i-1] = new mg_card_emote(context, i);
            cards[i-1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { // Açık-Kapalı Durum Ayarlama
                    final mg_card_emote c = (mg_card_emote)view;
                    c.turn();
                    if (lastCard > 0)
                    {
                        final mg_card_emote c2 = (mg_card_emote)findViewById(lastCard);

                        if (c.frontID == c2.frontID && c.getId() != c2.getId())
                        {
                            // Eşleştiler
                            c.isTurn = false;
                            c2.isTurn = false;
                            score++;
                            lastCard = 0;
                            if (score == 8){
                                try {
                                    this.finalize();
                                    countDownTimer.cancel();
                                    Intent intent = new Intent(context, mg_ResultWin.class);
                                    intent.putExtra("score",score);
                                    intent.putExtra("fail",fail);
                                    startActivity(intent);
                                }
                                catch (Throwable throwable){
                                    throwable.printStackTrace();
                                }
                            }
                        }
                        else
                        {
                            //Eşleşmediler geri çevir
                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    c.turn();
                                    c2.turn();
                                }
                            },500);
                            lastCard = 0;
                            fail++;
                        }
                    }
                    else
                    {
                        lastCard = c.getId();
                    }
                }
            });
        }

        // Karıştır
        for (int j = 0; j < 16; j++) {
            int random = (int)(Math.random() * 16);
            mg_card_emote c = cards[random];
            cards[random] = cards[j];
            cards[j] = c;
        }

        // Yazdır
        for (int j = 0; j < 16; j++) {
            glCards.addView(cards[j]);
        }
    }

    public void sports()
    {
        glCards = findViewById(R.id.glCards);

        mg_card_sports cards[] = new mg_card_sports[16];

        for (int i = 1; i <= 16; i++) {
            cards[i-1] = new mg_card_sports(context, i);
            cards[i-1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { // Açık-Kapalı Durum Ayarlama
                    final mg_card_sports c = (mg_card_sports)view;
                    c.turn();
                    if (lastCard > 0)
                    {
                        final mg_card_sports c2 = (mg_card_sports)findViewById(lastCard);

                        if (c.frontID == c2.frontID && c.getId() != c2.getId())
                        {
                            // Eşleştiler
                            c.isTurn = false;
                            c2.isTurn = false;
                            score++;
                            lastCard = 0;
                            if (score == 8){
                                try {
                                    this.finalize();
                                    countDownTimer.cancel();
                                    Intent intent = new Intent(context, mg_ResultWin.class);
                                    intent.putExtra("score",score);
                                    intent.putExtra("fail",fail);
                                    startActivity(intent);
                                }
                                catch (Throwable throwable){
                                    throwable.printStackTrace();
                                }
                            }
                        }
                        else
                        {
                            //Eşleşmediler geri çevir
                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    c.turn();
                                    c2.turn();
                                }
                            },500);
                            lastCard = 0;
                            fail++;
                        }
                    }
                    else
                    {
                        lastCard = c.getId();
                    }
                }
            });
        }

        // Karıştır
        for (int j = 0; j < 16; j++) {
            int random = (int)(Math.random() * 16);
            mg_card_sports c = cards[random];
            cards[random] = cards[j];
            cards[j] = c;
        }

        // Yazdır
        for (int j = 0; j < 16; j++) {
            glCards.addView(cards[j]);
        }
    }

    public void shape()
    {
        glCards = findViewById(R.id.glCards);

        mg_card_shape cards[] = new mg_card_shape[16];

        for (int i = 1; i <= 16; i++) {
            cards[i-1] = new mg_card_shape(context, i);
            cards[i-1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { // Açık-Kapalı Durum Ayarlama
                    final mg_card_shape c = (mg_card_shape)view;
                    c.turn();
                    if (lastCard > 0)
                    {
                        final mg_card_shape c2 = (mg_card_shape)findViewById(lastCard);

                        if (c.frontID == c2.frontID && c.getId() != c2.getId())
                        {
                            // Eşleştiler
                            c.isTurn = false;
                            c2.isTurn = false;
                            score++;
                            lastCard = 0;
                            if (score == 8){
                                try {
                                    this.finalize();
                                    countDownTimer.cancel();
                                    Intent intent = new Intent(context, mg_ResultWin.class);
                                    intent.putExtra("score",score);
                                    intent.putExtra("fail",fail);
                                    startActivity(intent);
                                }
                                catch (Throwable throwable){
                                    throwable.printStackTrace();
                                }
                            }
                        }
                        else
                        {
                            //Eşleşmediler geri çevir
                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    c.turn();
                                    c2.turn();
                                }
                            },500);
                            lastCard = 0;
                            fail++;
                        }
                    }
                    else
                    {
                        lastCard = c.getId();
                    }
                }
            });
        }

        // Karıştır
        for (int j = 0; j < 16; j++) {
            int random = (int)(Math.random() * 16);
            mg_card_shape c = cards[random];
            cards[random] = cards[j];
            cards[j] = c;
        }

        // Yazdır
        for (int j = 0; j < 16; j++) {
            glCards.addView(cards[j]);
        }
    }

    public void deck()
    {
        glCards = findViewById(R.id.glCards);

        mg_card_deck cards[] = new mg_card_deck[16];

        for (int i = 1; i <= 16; i++) {
            cards[i-1] = new mg_card_deck(context, i);
            cards[i-1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { // Açık-Kapalı Durum Ayarlama
                    final mg_card_deck c = (mg_card_deck)view;
                    c.turn();
                    if (lastCard > 0)
                    {
                        final mg_card_deck c2 = (mg_card_deck)findViewById(lastCard);

                        if (c.frontID == c2.frontID && c.getId() != c2.getId())
                        {
                            // Eşleştiler
                            c.isTurn = false;
                            c2.isTurn = false;
                            score++;
                            lastCard = 0;
                            if (score == 8){
                                try {
                                    this.finalize();
                                    countDownTimer.cancel();
                                    Intent intent = new Intent(context, mg_ResultWin.class);
                                    intent.putExtra("score",score);
                                    intent.putExtra("fail",fail);
                                    startActivity(intent);
                                }
                                catch (Throwable throwable){
                                    throwable.printStackTrace();
                                }
                            }
                        }
                        else
                        {
                            //Eşleşmediler geri çevir
                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    c.turn();
                                    c2.turn();
                                }
                            },500);
                            lastCard = 0;
                            fail++;
                        }
                    }
                    else
                    {
                        lastCard = c.getId();
                    }
                }
            });
        }

        // Karıştır
        for (int j = 0; j < 16; j++) {
            int random = (int)(Math.random() * 16);
            mg_card_deck c = cards[random];
            cards[random] = cards[j];
            cards[j] = c;
        }

        // Yazdır
        for (int j = 0; j < 16; j++) {
            glCards.addView(cards[j]);
        }
    }
}
