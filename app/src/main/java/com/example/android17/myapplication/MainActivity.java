package com.example.android17.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android17.myapplication.shows.TvShowsListFragment;


public class MainActivity extends AppCompatActivity {
    private static final String TAG_TV_SHOWS = "shows";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, TvShowsListFragment.newInstance(), TAG_TV_SHOWS)
                    .commit();
        }
    }
}