package com.example.android17.myapplication.shows;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android17.myapplication.R;
import com.example.android17.myapplication.database.TVShowsConstants;
import com.orhanobut.logger.Logger;
import com.rey.material.widget.TextView;

public class TvShowAdapter extends CursorAdapter {
    private TvShow show;

    public TvShowAdapter(Context context) {
        super(context, null, false);
        show = new TvShow();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tv_show, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);

        insertValues(cursor, view);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        insertValues(cursor, view);
    }

    private void insertValues(Cursor cursor, View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        show.parse(cursor);

        holder.name.setText(show.getName());
        holder.start.setText(show.getStartTime());
        holder.end.setText(show.getEndTime());
        holder.rating.setText(show.getRating());
        holder.channel.setText("Channel: " + show.getChannel());
    }

    class ViewHolder {
        TextView name;
        TextView start;
        TextView end;
        TextView rating;
        TextView channel;

        public ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.show_name);
            start = (TextView) view.findViewById(R.id.show_start);
            end = (TextView) view.findViewById(R.id.show_end);
            rating = (TextView) view.findViewById(R.id.show_rating);
            channel = (TextView) view.findViewById(R.id.show_channel);
        }
    }
}
