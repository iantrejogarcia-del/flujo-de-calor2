package com.example.sapo_proyect.flujocalor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sapo_proyect.Inicio_Activity;
import com.example.sapo_proyect.R;

public class ejercicios extends AppCompatActivity {

    EditText etxtA1, etxtE1, etxtA2, etxtE2, etxtA3, etxtE3, etxtTf, etxtTi;
    Spinner spinE1, spinE2, spinE3;
    TextView txtResultado;
    private ImageButton imgbtnregg, btnimghome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);

        imgbtnregg = findViewById(R.id.btnimregg);
        imgbtnregg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ejercicios.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        btnimghome = findViewById(R.id.btnimhomee);
        btnimghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ejercicios.this, Inicio_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        // Resultado
        txtResultado = findViewById(R.id.txtResult);

        // Areas
        etxtTi = findViewById(R.id.etxtTempI);
        etxtTf = findViewById(R.id.etxtTempF);
        etxtA1 = findViewById(R.id.etxtValA1);
        etxtA2 = findViewById(R.id.etxtValA2);
        etxtA3 = findViewById(R.id.etxtValA3);

        // Espesor
        etxtE1 = findViewById(R.id.etxtValE1);
        etxtE2 = findViewById(R.id.etxtValE2);
        etxtE3 = findViewById(R.id.etxtValE3);

        // Spinner
        spinE1 = findViewById(R.id.spinE1);
        spinE2 = findViewById(R.id.spinE2);
        spinE3 = findViewById(R.id.spinE3);

        // Crear contenido de cada spinner
        String[] spinElementos = {"Aluminio", "Cobre", "Zinc", "Bronce", "Vidrio"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinElementos);
        spinE1.setAdapter(adapter);
        spinE2.setAdapter(adapter);
        spinE3.setAdapter(adapter);
    }

    public void calcular(View view) {
        try {
            // Parse the input values
            double area1 = Double.parseDouble(etxtA1.getText().toString());
            double espe1 = Double.parseDouble(etxtE1.getText().toString());
            double area2 = Double.parseDouble(etxtA2.getText().toString());
            double espe2 = Double.parseDouble(etxtE2.getText().toString());
            double area3 = Double.parseDouble(etxtA3.getText().toString());
            double espe3 = Double.parseDouble(etxtE3.getText().toString());
            double tempI = Double.parseDouble(etxtTi.getText().toString());
            double tempF = Double.parseDouble(etxtTf.getText().toString());

            // Calculate resistances for each material
            double r1 = calcularResistencia(area1, espe1, spinE1.getSelectedItemPosition() + 1);
            double r2 = calcularResistencia(area2, espe2, spinE2.getSelectedItemPosition() + 1);
            double r3 = calcularResistencia(area3, espe3, spinE3.getSelectedItemPosition() + 1);

            // Display individual resistances
            String resultadoParcial = "Resistencias:\n";
            resultadoParcial += "Material 1: " + obtenerNombreMaterial(spinE1.getSelectedItemPosition() + 1) + " " + r1 + "\n";
            resultadoParcial += "Material 2: " + obtenerNombreMaterial(spinE2.getSelectedItemPosition() + 1) + " " + r2 + "\n";
            resultadoParcial += "Material 3: " + obtenerNombreMaterial(spinE3.getSelectedItemPosition() + 1) + " " + r3 + "\n";

            // Calculate total resistance
            double resistenciaTotal = r1 + r2 + r3;
            double qk = (tempI - tempF) / resistenciaTotal;

            // Display total resistance
            txtResultado.setText(resultadoParcial + "Resistencia total: " + qk);
        } catch (NumberFormatException e) {
            // Clear text fields and show error message
            etxtA1.setText("");
            etxtE1.setText("");
            etxtA2.setText("");
            etxtE2.setText("");
            etxtA3.setText("");
            etxtE3.setText("");
            etxtTi.setText("");
            etxtTf.setText("");
            txtResultado.setText("Por favor, ingrese todos los valores correctamente.");
        }
    }

    private double calcularResistencia(double area, double espesor, int material) {
        double conductividad = 0;
        switch (material) {
            case 1:
                conductividad = 205; // Aluminio
                break;
            case 2:
                conductividad = 385; // Cobre
                break;
            case 3:
                conductividad = 116; // Zinc
                break;
            case 4:
                conductividad = 186; // Bronce
                break;
            case 5:
                conductividad = 0.81; // Vidrio
                break;
        }
        return espesor / (area * conductividad);
    }

    private String obtenerNombreMaterial(int material) {
        switch (material) {
            case 1:
                return "Aluminio";
            case 2:
                return "Cobre";
            case 3:
                return "Zinc";
            case 4:
                return "Bronce";
            case 5:
                return "Vidrio";
            default:
                return "Desconocido";
        }
    }
}