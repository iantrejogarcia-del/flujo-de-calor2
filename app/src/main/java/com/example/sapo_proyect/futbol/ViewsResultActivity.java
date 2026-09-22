package com.example.sapo_proyect.futbol;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sapo_proyect.MainActivity;
import com.example.sapo_proyect.R;
import com.example.sapo_proyect.bd.DatabaseHelper;

import java.util.ArrayList;

public class ViewsResultActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView lvResults;

    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_views_result);

        imageButton =findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewsResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dbHelper = new DatabaseHelper(this);
        lvResults = findViewById(R.id.lvResults);

        loadResults();
    }

    private void loadResults() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_GAMES, null);

        ArrayList<String> results = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int team1Id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEAM1_ID));
                @SuppressLint("Range") int team2Id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEAM2_ID));
                @SuppressLint("Range") int team1Score = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEAM1_SCORE));
                @SuppressLint("Range") int team2Score = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEAM2_SCORE));
                @SuppressLint("Range") int winnerId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_WINNER_ID));

                String result = getTeamNameById(team1Id) + " " + team1Score + " - " + team2Score + " " + getTeamNameById(team2Id) +
                        " (Ganador: " + getTeamNameById(winnerId) + ")";
                results.add(result);
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
        lvResults.setAdapter(adapter);
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