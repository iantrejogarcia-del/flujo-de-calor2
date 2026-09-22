package com.example.sapo_proyect.futbol;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sapo_proyect.MainActivity;
import com.example.sapo_proyect.R;
import com.example.sapo_proyect.bd.DatabaseHelper;

import java.util.ArrayList;

public class RegisterGoalActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private Spinner spinnerGame, spinnerTeam;
    private EditText etPlayerName;

    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_goal);

        dbHelper = new DatabaseHelper(this);
        spinnerGame = findViewById(R.id.spinnerGame);
        spinnerTeam = findViewById(R.id.spinnerTeam);
        etPlayerName = findViewById(R.id.etPlayerName);
        Button btnSaveGoal = findViewById(R.id.btnSaveGoal);

        loadGamesIntoSpinner(spinnerGame);
        loadTeamsIntoSpinner(spinnerTeam);

        btnSaveGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGoal();
            }
        });

        imageButton= findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterGoalActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadGamesIntoSpinner(Spinner spinner) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_GAMES, null);

        ArrayList<String> games = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int team1Id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEAM1_ID));
                @SuppressLint("Range") int team2Id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEAM2_ID));
                games.add(getTeamNameById(team1Id) + " vs " + getTeamNameById(team2Id));
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, games);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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

    private void saveGoal() {
        String game = spinnerGame.getSelectedItem().toString();
        String team = spinnerTeam.getSelectedItem().toString();
        String playerName = etPlayerName.getText().toString().trim();

        if (playerName.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese el nombre del jugador", Toast.LENGTH_SHORT).show();
            return;
        }

        int gameId = getGameIdByDescription(game);
        int teamId = getTeamIdByName(team);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_GAME_ID_FK, gameId); // Usamos COLUMN_GAME_ID_FK
        values.put(DatabaseHelper.COLUMN_PLAYER_NAME, playerName);
        values.put(DatabaseHelper.COLUMN_TEAM_ID_FK, teamId); // Usamos COLUMN_TEAM_ID_FK

        long newRowId = db.insert(DatabaseHelper.TABLE_GOALS, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Gol registrado exitosamente", Toast.LENGTH_SHORT).show();
            etPlayerName.setText("");
        } else {
            Toast.makeText(this, "Error al registrar el gol", Toast.LENGTH_SHORT).show();
        }
    }

    private int getGameIdByDescription(String description) {
        String[] teams = description.split(" vs ");
        int team1Id = getTeamIdByName(teams[0]);
        int team2Id = getTeamIdByName(teams[1]);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_GAME_ID + " FROM " + DatabaseHelper.TABLE_GAMES + " WHERE " + DatabaseHelper.COLUMN_TEAM1_ID + " = ? AND " + DatabaseHelper.COLUMN_TEAM2_ID + " = ?", new String[]{String.valueOf(team1Id), String.valueOf(team2Id)});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int gameId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_GAME_ID));
            cursor.close();
            return gameId;
        }
        cursor.close();
        return -1;
    }

    private int getTeamIdByName(String teamName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_TEAM_ID + " FROM " + DatabaseHelper.TABLE_TEAMS + " WHERE " + DatabaseHelper.COLUMN_TEAM_NAME + " = ?", new String[]{teamName});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int teamId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEAM_ID));
            cursor.close();
            return teamId;
        }
        cursor.close();
        return -1;
    }

    private String getTeamNameById(int teamId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_TEAM_NAME + " FROM " + DatabaseHelper.TABLE_TEAMS + " WHERE " + DatabaseHelper.COLUMN_TEAM_ID + " = ?", new String[]{String.valueOf(teamId)});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String teamName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEAM_NAME));
            cursor.close();
            return teamName;
        }
        cursor.close();
        return null;
    }
}