package com.example.fungames.cardMemory;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fungames.R;

public class mg_Menu extends AppCompatActivity {

    Button btnNumbers, btnFlowers, btnEmote, btnSports, btnShape, btnDeck;
    final int numbersId = 1;
    final int flowersId = 2;
    final int emoteId = 3;
    final int sportsId = 4;
    final int shapeId = 5;
    final int deckId = 6;
    Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        //getSupportActionBar().hide(); // hide the title bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.mg_menu);
        init();


                // Dialog Window //
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.mg_difficulty);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final Button btnEasy = dialog.findViewById(R.id.btnEasy);
        final Button btnMedium = dialog.findViewById(R.id.btnMedium);
        final Button btnHard = dialog.findViewById(R.id.btnHard);

        btnNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setCanceledOnTouchOutside(true);
                btnEasy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("numbersId", numbersId);
                        intent.putExtra("milisec", 90000);

                        startActivity(intent);
                    }
                });

                btnMedium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("numbersId", numbersId);
                        intent.putExtra("milisec", 60000);
                        startActivity(intent);
                    }
                });

                btnHard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("numbersId", numbersId);
                        intent.putExtra("milisec", 45000);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

        btnFlowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEasy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("flowersId", flowersId);
                        intent.putExtra("milisec", 90000);
                        startActivity(intent);
                    }
                });

                btnMedium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("flowersId", flowersId);
                        intent.putExtra("milisec", 60000);
                        startActivity(intent);
                    }
                });

                btnHard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("flowersId", flowersId);
                        intent.putExtra("milisec", 45000);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

        btnEmote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEasy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("emoteId", emoteId);
                        intent.putExtra("milisec", 90000);
                        startActivity(intent);
                    }
                });

                btnMedium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("emoteId", emoteId);
                        intent.putExtra("milisec", 60000);
                        startActivity(intent);
                    }
                });

                btnHard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("emoteId", emoteId);
                        intent.putExtra("milisec", 45000);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

        btnSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEasy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("sportsId", sportsId);
                        intent.putExtra("milisec", 90000);
                        startActivity(intent);
                    }
                });

                btnMedium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("sportsId", sportsId);
                        intent.putExtra("milisec", 60000);
                        startActivity(intent);
                    }
                });

                btnHard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("sportsId", sportsId);
                        intent.putExtra("milisec", 45000);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

        btnShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEasy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("shapeId", shapeId);
                        intent.putExtra("milisec", 90000);
                        startActivity(intent);
                    }
                });

                btnMedium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("shapeId", shapeId);
                        intent.putExtra("milisec", 60000);
                        startActivity(intent);
                    }
                });

                btnHard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("shapeId", shapeId);
                        intent.putExtra("milisec", 45000);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

        btnDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEasy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("deckId", deckId);
                        intent.putExtra("milisec", 90000);
                        startActivity(intent);
                    }
                });

                btnMedium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("deckId", deckId);
                        intent.putExtra("milisec", 60000);
                        startActivity(intent);
                    }
                });

                btnHard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, mg_GameScreen.class);
                        intent.putExtra("deckId", deckId);
                        intent.putExtra("milisec", 45000);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });
    }

    public void init()
    {
        btnNumbers = findViewById(R.id.btnNumbers);
        btnFlowers = findViewById(R.id.btnFlowers);
        btnEmote = findViewById(R.id.btnEmote);
        btnSports = findViewById(R.id.btnSports);
        btnShape = findViewById(R.id.btnShape);
        btnDeck = findViewById(R.id.btnDeck);
    }
}
