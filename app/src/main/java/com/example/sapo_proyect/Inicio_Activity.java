package com.example.sapo_proyect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sapo_proyect.flujocalor.MainActivity2;

public class Inicio_Activity extends AppCompatActivity {

    Button btniniciar,btnmusicaa;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        btniniciar=findViewById(R.id.btninicio);

        btniniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio_Activity.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        btnmusicaa=findViewById(R.id.btnmusica);

        btnmusicaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}