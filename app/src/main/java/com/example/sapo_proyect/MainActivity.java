package com.example.sapo_proyect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sapo_proyect.futbol.RegisterGameActivity;
import com.example.sapo_proyect.futbol.RegisterGoalActivity;
import com.example.sapo_proyect.futbol.RegisterTeamActivty;
import com.example.sapo_proyect.futbol.ViewsResultActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton =findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Inicio_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnRegisterTeam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterTeamActivty.class));
                finish();
            }
        });

        findViewById(R.id.btnRegisterGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterGameActivity.class));
                finish();
            }
        });

        findViewById(R.id.btnRegisterGoal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterGoalActivity.class));
                finish();
            }
        });

        findViewById(R.id.btnViewResults).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewsResultActivity.class));
                finish();


            }
        });
    }
}