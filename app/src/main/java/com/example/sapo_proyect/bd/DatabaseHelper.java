package com.example.sapo_proyect.bd;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cecytem_tournament.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_TEAMS = "teams";
    public static final String COLUMN_TEAM_ID = "team_id";
    public static final String COLUMN_TEAM_NAME = "team_name";

    public static final String TABLE_GAMES = "games";
    public static final String COLUMN_GAME_ID = "game_id";
    public static final String COLUMN_TEAM1_ID = "team1_id";
    public static final String COLUMN_TEAM2_ID = "team2_id";
    public static final String COLUMN_TEAM1_SCORE = "team1_score";
    public static final String COLUMN_TEAM2_SCORE = "team2_score";
    public static final String COLUMN_WINNER_ID = "winner_id";

    public static final String TABLE_GOALS = "goals";
    public static final String COLUMN_GOAL_ID = "goal_id";
    public static final String COLUMN_GAME_ID_FK = "game_id_fk";
    public static final String COLUMN_PLAYER_NAME = "player_name";
    public static final String COLUMN_TEAM_ID_FK = "team_id_fk";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de equipos
        String createTeamsTable = "CREATE TABLE " + TABLE_TEAMS + " (" +
                COLUMN_TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TEAM_NAME + " TEXT NOT NULL);";

        // Crear tabla de juegos
        String createGamesTable = "CREATE TABLE " + TABLE_GAMES + " (" +
                COLUMN_GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TEAM1_ID + " INTEGER, " +
                COLUMN_TEAM2_ID + " INTEGER, " +
                COLUMN_TEAM1_SCORE + " INTEGER, " +
                COLUMN_TEAM2_SCORE + " INTEGER, " +
                COLUMN_WINNER_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_TEAM1_ID + ") REFERENCES " + TABLE_TEAMS + "(" + COLUMN_TEAM_ID + "), " +
                "FOREIGN KEY(" + COLUMN_TEAM2_ID + ") REFERENCES " + TABLE_TEAMS + "(" + COLUMN_TEAM_ID + "), " +
                "FOREIGN KEY(" + COLUMN_WINNER_ID + ") REFERENCES " + TABLE_TEAMS + "(" + COLUMN_TEAM_ID + "));";

        // Crear tabla de goles
        String createGoalsTable = "CREATE TABLE " + TABLE_GOALS + " (" +
                COLUMN_GOAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GAME_ID_FK + " INTEGER, " +
                COLUMN_PLAYER_NAME + " TEXT, " +
                COLUMN_TEAM_ID_FK + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_GAME_ID_FK + ") REFERENCES " + TABLE_GAMES + "(" + COLUMN_GAME_ID + "), " +
                "FOREIGN KEY(" + COLUMN_TEAM_ID_FK + ") REFERENCES " + TABLE_TEAMS + "(" + COLUMN_TEAM_ID + "));";

        db.execSQL(createTeamsTable);
        db.execSQL(createGamesTable);
        db.execSQL(createGoalsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Esto elimina las tablas antiguas y las recrea
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        onCreate(db);
    }
}