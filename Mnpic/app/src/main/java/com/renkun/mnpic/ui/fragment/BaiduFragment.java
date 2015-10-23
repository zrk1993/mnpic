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
import com.renkun.mnpic.module.BDpic;
import com.renkun.mnpic.ui.adapter.BDCursorAdapter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 *
 * A simple {@link Fragment} subclass.
 */
public class BaiduFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    //百度图片URL参数
    private int pn;
    private int rn;
    private String tag1;
    private String tag2;
    private String flags;

    private GridView mGridView;
    private PullToRefreshGridView mPullRefreshGridView;
    private BDCursorAdapter mBDCursorAdapter;

    Uri mUri;
    public static int BAIDU=234;


    public BaiduFragment(int pn, int rn, String tag1, String tag2, String flags) {
        this.pn = pn;
        this.rn = rn;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.flags = flags;

        mUri=Uri.parse(DataProvider.SCHEME + DataProvider.AUTHORITY + String.valueOf(BAIDU));


    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(BAIDU, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_baidu, container, false);
        initView(view);
        return view;
    }
    private void initView(View view){
        mPullRefreshGridView= (PullToRefreshGridView) view.findViewById(R.id.pull_refresh_grid);
        mGridView = mPullRefreshGridView.getRefreshableView();
        mGridView.setNumColumns(2);
        mPullRefreshGridView.setOnRefreshListener(new OnGrideRefreshListener());

        mBDCursorAdapter=new BDCursorAdapter(getActivity(),mGridView);
        mGridView.setAdapter(mBDCursorAdapter);
    }
    private void loadFirst(){
        String url= String.format(Api.BDApiClassify, pn,rn,tag1,tag2,flags);
        loadData(url);
    }
    private void loadnext(){
        String url= String.format(Api.BDApiClassify, ++pn,rn,tag1,tag2,flags);
        loadData(url);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),mUri,null, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mBDCursorAdapter.changeCursor(data);
        mPullRefreshGridView.onRefreshComplete();
        if (data.getCount()<3)loadFirst();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mBDCursorAdapter.changeCursor(null);
    }

    //Gride的刷新监听类
    private class OnGrideRefreshListener implements PullToRefreshBase.OnRefreshListener2<GridView> {
        @Override
        public void onPullDownToRefresh(final PullToRefreshBase<GridView> refreshView) {
            loadFirst();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
            loadnext();
        }
    }

    private void loadData(String url) {
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();
        OkHttpClientManager.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse( Response response) throws IOException {
                //解析字符串
                String s=response.body().string();
                Log.d("bd",s);

                BDpic bDpic = OkHttpClientManager
                        .getJsonBean(s, BDpic.class);
                if (bDpic.data.size()>0){
                    getActivity().getContentResolver()
                            .bulkInsert(mUri,
                                    BDpic.getContentValues(bDpic));
                }else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPullRefreshGridView.onRefreshComplete();
                            Snackbar.make(getView(), "已经加载完了，等小编更新。。", Snackbar.LENGTH_SHORT)
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
}
