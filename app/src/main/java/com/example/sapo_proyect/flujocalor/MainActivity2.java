package com.example.sapo_proyect.flujocalor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sapo_proyect.Inicio_Activity;
import com.example.sapo_proyect.R;

public class MainActivity2 extends AppCompatActivity {

    private ImageButton imgbtnhist,btnimghome;
    private Button btnejercicios;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imgbtnhist=findViewById(R.id.btnimhist);
        imgbtnhist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Historia.class);
                startActivity(intent);
                finish();
            }
        });
        btnimghome=findViewById(R.id.btnimhome);
        btnimghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Inicio_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnejercicios=findViewById(R.id.btnejercicio);
        btnejercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, ejercicios.class);
                startActivity(intent);
                finish();
            }
        });
    }
}