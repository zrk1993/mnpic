package com.renkun.mnpic.ui.fragment;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renkun.mnpic.R;
import com.renkun.mnpic.dao.DataProvider;
import com.renkun.mnpic.data.Api;
import com.renkun.mnpic.data.OkHttpClientManager;
import com.renkun.mnpic.module.Gallery;
import com.renkun.mnpic.module.RandomImg;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class RandomFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private View mView;
    private int num=8;
    private Uri mUri;

    public RandomFragment() {
        // Required empty public constructor
        mUri= Uri.parse(DataProvider.SCHEME + DataProvider.AUTHORITY + String.valueOf(100));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView=inflater.inflate(R.layout.fragment_random, container, false);
        initView();
        return mView;
    }
    private void initView(){
        loadData();
    }
    private void loadData() {
        final Request request = new Request.Builder()
                .url(Api.TianXingUrl+"?num="+num)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("apikey",Api.apikey)
                .build();
        OkHttpClientManager.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse( Response response) throws IOException {
                //解析字符串
                String s=response.body().string();
                int code=0;
                try {
                    code=new JSONObject(s).getInt("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (code==200){
                    getActivity().getContentResolver()
                            .bulkInsert(mUri,
                                    RandomImg.getContentValues(s,num));
                }else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(getView(), "出错，请重新刷新", Snackbar.LENGTH_SHORT)
                                    .setAction("好吧", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                        }
                                    })
                                    .show();
                        }
                    });
                }

            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),mUri,null, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
