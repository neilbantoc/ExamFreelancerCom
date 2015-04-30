package com.example.android17.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TVShowsDatabaseOpenHelper extends SQLiteOpenHelper implements TVShowsConstants{

    private static final String DATABASE_NAME = "tvShows";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_TV_SHOWS = "create table " + TABLE_TV_SHOWS + "("
            + _ID + " integer primary key autoincrement, "
            + NAME + " text not null, "
            + CHANNEL + " text not null, "
            + START_TIME + " text not null, "
            + END_TIME + " text not null, "
            + RATING + " text, "
            + " unique (" + NAME + "," + CHANNEL + ") on conflict replace"
            +")";

    public TVShowsDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TV_SHOWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TV_SHOWS);
    }
}
