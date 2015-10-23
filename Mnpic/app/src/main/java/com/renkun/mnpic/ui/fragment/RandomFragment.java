package com.renkun.mnpic.ui.fragment;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.renkun.mnpic.R;
import com.renkun.mnpic.dao.DataProvider;
import com.renkun.mnpic.data.Api;
import com.renkun.mnpic.data.OkHttpClientManager;
import com.renkun.mnpic.module.RandomImg;
import com.renkun.mnpic.ui.adapter.PicListCursorAdapter;
import com.renkun.mnpic.ui.adapter.RandomAdapter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RandomFragment extends Fragment{
    private View mView;
    private int num=9;
    private Uri mUri;
    private GridView mGridView;
    private PullToRefreshGridView mPullRefreshGridView;
    private RandomAdapter mRandomAdapter;


    public RandomFragment() {
        // Required empty public constructor
        mUri= Uri.parse(DataProvider.SCHEME + DataProvider.AUTHORITY + String.valueOf(90));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView=inflater.inflate(R.layout.fragment_random, container, false);
        initView();
        getActivity().getContentResolver().delete(mUri,null,null);
        return mView;
    }
    private void initView(){
        mPullRefreshGridView= (PullToRefreshGridView) mView.findViewById(R.id.pull_refresh_grid);
        mPullRefreshGridView.setOnRefreshListener(new OnGrideRefreshListener());
        mGridView = mPullRefreshGridView.getRefreshableView();
        mGridView.setNumColumns(3);

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
            public void onResponse(Response response) throws IOException {
                //解析字符串
                String s = response.body().string();
                int code = 0;
                try {
                    code = new JSONObject(s).getInt("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (code == 200) {
                    ArrayList<RandomImg.Picture_List> lists= RandomImg.getList(s, num);
                    mRandomAdapter = new RandomAdapter(getActivity(),R.layout.fragment_random_item,lists);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mGridView.setAdapter(mRandomAdapter);
                            mPullRefreshGridView.onRefreshComplete();
                        }
                    });
                } else {
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
                            mPullRefreshGridView.onRefreshComplete();
                        }
                    });
                }

            }
        });

    }

    //Gride的刷新监听类
    private class OnGrideRefreshListener implements PullToRefreshBase.OnRefreshListener2<GridView> {
        @Override
        public void onPullDownToRefresh(final PullToRefreshBase<GridView> refreshView) {
            loadData();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
            refreshView.onRefreshComplete();
        }
    }

}
