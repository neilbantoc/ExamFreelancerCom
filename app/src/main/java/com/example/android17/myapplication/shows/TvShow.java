package com.example.android17.myapplication.shows;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.android17.myapplication.database.TVShowsConstants;

public class TvShow implements TVShowsConstants{
    private String name;
    private String start_time;
    private String end_time;
    private String channel;
    private String rating;

    public void parse(Cursor cursor) {
        name = getString(cursor, NAME);
        start_time = getString(cursor, START_TIME);
        end_time = getString(cursor, END_TIME);
        channel = getString(cursor, CHANNEL);
        rating = getString(cursor, RATING);
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(NAME, name);
        values.put(START_TIME, start_time);
        values.put(END_TIME, end_time);
        values.put(CHANNEL, channel);
        values.put(RATING, rating);

        return values;
    }

    public String getString(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEndTime() {
        return end_time;
    }

    public void setEndTime(String end_time) {
        this.end_time = end_time;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
