package com.example.sapo_proyect.futbol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import com.example.sapo_proyect.MainActivity;
import com.example.sapo_proyect.R;
import com.example.sapo_proyect.bd.DatabaseHelper;

public class RegisterTeamActivty extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText etTeamName;

    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_team_activty);

        imageButton =findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterTeamActivty.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dbHelper = new DatabaseHelper(this);
        etTeamName = findViewById(R.id.etTeamName);
        Button btnSaveTeam = findViewById(R.id.btnSaveTeam);

        btnSaveTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTeam();
            }
        });
    }

    private void saveTeam() {
        String teamName = etTeamName.getText().toString().trim();

        if (teamName.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese el nombre del equipo", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TEAM_NAME, teamName);
        long newRowId = db.insert(DatabaseHelper.TABLE_TEAMS, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Equipo registrado exitosamente", Toast.LENGTH_SHORT).show();
            etTeamName.setText("");
        } else {
            Toast.makeText(this, "Error al registrar el equipo", Toast.LENGTH_SHORT).show();
        }
    }
}