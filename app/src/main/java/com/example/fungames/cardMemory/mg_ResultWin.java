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

public class mg_ResultWin extends AppCompatActivity {

    TextView txtScoreWin, txtFailWin;
    Button btnMenuWin, btnExitWin;
    Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        //getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.mg_result_win);

        txtScoreWin = findViewById(R.id.txtScoreWin);
        txtFailWin = findViewById(R.id.txtFailWin);
        btnMenuWin = findViewById(R.id.btnMenuWin);
        btnExitWin = findViewById(R.id.btnExitWin);

        Intent intent = getIntent();
        txtScoreWin.setText("Your Score: " + intent.getIntExtra("score",0));
        txtFailWin.setText("Number of Errors: " + intent.getIntExtra("fail",0));

        btnMenuWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, mg_Menu.class);
                startActivity(intent);
            }
        });

        btnExitWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Geri tuşunu pasif hale getirme //
        super.onBackPressed();
    }
}
