package com.example.android17.myapplication.networking;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;

public class GetTvShowsCallback extends AjaxCallback<String>{
    private static final String URL = "http://whatsbeef.net/wabz/guide.php";

    private int start;

    public GetTvShowsCallback() {
        start = 0;
        setUrl(start);
        method(AQuery.METHOD_GET);
        type(String.class);
    }

    public void setStart(int start) {
        this.start = start;
        setUrl(start);
    }

    private void setUrl(int start) {
        url(URL + "?start=" + start);
    }
}
