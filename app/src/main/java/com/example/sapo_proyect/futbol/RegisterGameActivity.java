package com.example.sapo_proyect.futbol;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import com.example.sapo_proyect.MainActivity;
import com.example.sapo_proyect.R;
import com.example.sapo_proyect.bd.DatabaseHelper;

import java.util.ArrayList;

public class RegisterGameActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private Spinner spinnerTeam1, spinnerTeam2;
    private EditText etTeam1Score, etTeam2Score;
    ImageButton imageButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_game);

        imageButton =findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterGameActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dbHelper = new DatabaseHelper(this);
        spinnerTeam1 = findViewById(R.id.spinnerTeam1);
        spinnerTeam2 = findViewById(R.id.spinnerTeam2);
        etTeam1Score = findViewById(R.id.etTeam1Score);
        etTeam2Score = findViewById(R.id.etTeam2Score);
        Button btnSaveGame = findViewById(R.id.btnSaveGame);

        loadTeamsIntoSpinner(spinnerTeam1);
        loadTeamsIntoSpinner(spinnerTeam2);

        btnSaveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGame();
            }
        });
    }

    @SuppressLint("Range")
    private void loadTeamsIntoSpinner(Spinner spinner) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_TEAMS, null);

        ArrayList<String> teams = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                teams.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEAM_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teams);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void saveGame() {
        String team1Name = spinnerTeam1.getSelectedItem().toString();
        String team2Name = spinnerTeam2.getSelectedItem().toString();
        int team1Score = Integer.parseInt(etTeam1Score.getText().toString().trim());
        int team2Score = Integer.parseInt(etTeam2Score.getText().toString().trim());
        int winnerId = (team1Score > team2Score) ? getTeamIdByName(team1Name) : getTeamIdByName(team2Name);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TEAM1_ID, getTeamIdByName(team1Name));
        values.put(DatabaseHelper.COLUMN_TEAM2_ID, getTeamIdByName(team2Name));
        values.put(DatabaseHelper.COLUMN_TEAM1_SCORE, team1Score);
        values.put(DatabaseHelper.COLUMN_TEAM2_SCORE, team2Score);
        values.put(DatabaseHelper.COLUMN_WINNER_ID, winnerId);
        long newRowId = db.insert(DatabaseHelper.TABLE_GAMES, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Juego registrado exitosamente", Toast.LENGTH_SHORT).show();
            etTeam1Score.setText("");
            etTeam2Score.setText("");
        } else {
            Toast.makeText(this, "Error al registrar el juego", Toast.LENGTH_SHORT).show();
        }
    }

    private int getTeamIdByName(String teamName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_TEAM_ID + " FROM " + DatabaseHelper.TABLE_TEAMS + " WHERE " + DatabaseHelper.COLUMN_TEAM_NAME + " = ?", new String[]{teamName});
        if (cursor.moveToFirst()) {
            int teamId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEAM_ID));
            cursor.close();
            return teamId;
        }
        cursor.close();
        return -1;
    }
}


