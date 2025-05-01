package com.example.fungames.cardMemory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fungames.R;

public class mg_ResultLose extends AppCompatActivity {

    TextView txtScore, txtFail;
    Button btnMenu, btnExit;
    Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        //getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.mg_result_lose);

        txtScore = findViewById(R.id.txtScore);
        txtFail = findViewById(R.id.txtFail);
        btnMenu = findViewById(R.id.btnMenu);
        btnExit = findViewById(R.id.btnExit);

        Intent intent = getIntent();
        txtScore.setText("Your score: " + intent.getIntExtra("score_lose",0));
        txtFail.setText("Number of Errors: " + intent.getIntExtra("fail_lose",0));


        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, mg_Menu.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }

    @Override
    public void onBackPressed(){
        // Geri tuşunu pasif hale getirme //
    }
}
