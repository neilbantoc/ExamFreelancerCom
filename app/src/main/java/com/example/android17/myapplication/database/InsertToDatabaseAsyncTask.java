package com.example.android17.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.example.android17.myapplication.shows.TvShow;
import com.orhanobut.logger.Logger;

import java.util.List;

public class InsertToDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private List<TvShow> shows;

    public InsertToDatabaseAsyncTask(Context context, List<TvShow> shows) {
        this.context = context;
        this.shows = shows;
    }

    @Override
    protected Void doInBackground(Void... params) {
        ContentValues[] values = new ContentValues[shows.size()];
        int x = 0;
        for (TvShow show: shows) {
            values[x++] = show.getContentValues();
        }

        context.getContentResolver().bulkInsert(TVShowsDatabase.CONTENT_URI_TV_SHOWS, values);
        return null;
    }
}
