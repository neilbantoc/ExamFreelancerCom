package com.example.android17.myapplication.shows;

import android.content.ContentValues;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.view.View;
import android.widget.AbsListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.android17.myapplication.App;
import com.example.android17.myapplication.LoaderConstants;
import com.example.android17.myapplication.database.InsertToDatabaseAsyncTask;
import com.example.android17.myapplication.database.TVShowsDatabase;
import com.example.android17.myapplication.networking.GetTvShowsCallback;
import com.example.android17.myapplication.networking.GetTvShowsResponse;
import com.orhanobut.logger.Logger;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.List;

public class TvShowsListFragment extends ListFragment implements LoaderConstants, LoaderManager.LoaderCallbacks<Cursor>, AbsListView.OnScrollListener {

    private TvShowAdapter adapter;
    private boolean isDownloading;
    private ShowsCallback callback;
    private AQuery aq;
    private LoadToast toast;

    private int start;
    private int count;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = new ShowsCallback();
        isDownloading = false;
        count = 0;
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_TV_SHOW_LIST, null, this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aq = new AQuery(view);

        getListView().setDivider(null);

        if (adapter == null) {
            adapter = new TvShowAdapter(getActivity());
            start = 0;
            getShows();
        }

        getListView().setOnScrollListener(this);

        setListAdapter(adapter);
    }

    private void getShows() {
        if (isDownloading) {
            return;
        }

        Logger.i("Count: " + count + "\n Adapter: " + adapter.getCount());

        if (count == 0 || adapter.getCount() < count - 1) {
            toast = new LoadToast(getActivity());
            toast.setText("Loading Shows...");
            toast.show();

            isDownloading = true;

            callback.setStart(start);
            aq.ajax(callback);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount) {
            getShows();
        }
    }


    private class ShowsCallback extends GetTvShowsCallback {
        @Override
        public void callback(String url, String object, AjaxStatus status) {
            super.callback(url, object, status);

            isDownloading = false;

            StringBuilder builder = new StringBuilder();

            builder.append("URL: " + url + "\n");
            builder.append("CODE: " + status.getCode() + "\n");
            builder.append("MESSAGE: " + status.getMessage() + "\n");

            Logger.d(builder.toString());
            Logger.json(object);

            if (status.getCode() == 200) {
                onSuccess(object);
            } else {
                onFailure();
            }
        }
    }

    private void onSuccess(String object) {
        toast.success();

        GetTvShowsResponse response = App.gson.fromJson(object, GetTvShowsResponse.class);
        count = response.count;
        start += 10;

        ContentValues[] values = new ContentValues[response.results.size()];
        int x = 0;
        for (TvShow show: response.results) {
            values[x++] = show.getContentValues();
        }

        getActivity().getContentResolver().bulkInsert(TVShowsDatabase.CONTENT_URI_TV_SHOWS, values);
        restartLoader();
    }

    private void onFailure() {
        toast.error();
    }

    /*
     * LOADER CALLBACKS
     */
    private void restartLoader(){
        getLoaderManager().restartLoader(LOADER_TV_SHOW_LIST, null, this);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(getActivity());
        loader.setProjection(new String[]{"*"});
        loader.setUri(TVShowsDatabase.CONTENT_URI_TV_SHOWS);
        return loader;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    public static TvShowsListFragment newInstance() {
        TvShowsListFragment fragment = new TvShowsListFragment();
        return fragment;
    }

}
