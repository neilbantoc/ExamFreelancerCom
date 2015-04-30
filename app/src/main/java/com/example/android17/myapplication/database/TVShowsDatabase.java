package com.example.android17.myapplication.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class TVShowsDatabase extends ContentProvider implements TVShowsConstants{

    private static final String AUTHORITY = "com.example.android17.myapplication.contentprovider";

    private static final int TYPE_TV_SHOWS = 10;

    private static final String PATH_TV_SHOWS = "tvshows";

    public static final Uri CONTENT_URI_TV_SHOWS = Uri.parse("content://" + AUTHORITY + "/" + PATH_TV_SHOWS);

    private SQLiteOpenHelper helper;

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(AUTHORITY, PATH_TV_SHOWS, TYPE_TV_SHOWS);
    }

    @Override
    public boolean onCreate() {
        helper = new TVShowsDatabaseOpenHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder =  new SQLiteQueryBuilder();

        int type = matcher.match(uri);
        switch(type) {
            case TYPE_TV_SHOWS:
                builder.setTables(TABLE_TV_SHOWS);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    private String getTable(Uri uri) {
        int type = matcher.match(uri);
        String table = "";
        switch(type) {
            case TYPE_TV_SHOWS:
                table = TABLE_TV_SHOWS;
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return table;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table = getTable(uri);

        SQLiteDatabase db = helper.getWritableDatabase();
        long id = db.insert(table, null, values);

        return Uri.parse(uri + "/" + id);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        String table = getTable(uri);

        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();

        int result = 0;

        for (ContentValues value: values) {
            db.insert(table, null, value);
            result++;
        }

        db.setTransactionSuccessful();

        db.endTransaction();

        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String table = getTable(uri);

        SQLiteDatabase db = helper.getWritableDatabase();
        int result = db.delete(table, selection, selectionArgs);
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
