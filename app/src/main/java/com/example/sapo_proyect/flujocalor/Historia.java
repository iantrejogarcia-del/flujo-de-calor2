package com.example.sapo_proyect.flujocalor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sapo_proyect.Inicio_Activity;
import com.example.sapo_proyect.R;

public class Historia extends AppCompatActivity {


    private ImageButton imgbtnsig,btnimghome,btnimregresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);

        btnimregresar=findViewById(R.id.btnimreg);
        btnimregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Historia.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        btnimghome=findViewById(R.id.btnimhome);
        btnimghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Historia.this, Inicio_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        imgbtnsig=findViewById(R.id.btnimhist);
        imgbtnsig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Historia.this, tabla.class);
                startActivity(intent);
                finish();
            }
        });

    }
}