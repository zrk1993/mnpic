package com.renkun.mnpic.ui.fragment;

import android.app.Activity;
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
import com.renkun.mnpic.module.Gallery;
import com.renkun.mnpic.ui.adapter.PicListCursorAdapter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class FeedFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private GridView mGridView;
    private PullToRefreshGridView mPullRefreshGridView;
    private PicListCursorAdapter mPicListCursorAdapter;


    private int PIC_CLASSIFY = 1;//图片类别
    private int PIC_ROWS = 3;//图片条数
    private int PIC_PAGE = 1;//图片分页
    //页面图片集，数据库地址
    private Uri mUri;

    public FeedFragment() {
        // Required empty public constructor
        mUri=Uri.parse(DataProvider.SCHEME + DataProvider.AUTHORITY + String.valueOf(PIC_CLASSIFY));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPullRefreshGridView = (PullToRefreshGridView) inflater.inflate(R.layout.fragment_feed, container, false);
        mPullRefreshGridView.setOnRefreshListener(new OnGrideRefreshListener());
        mGridView = mPullRefreshGridView.getRefreshableView();

        mPicListCursorAdapter=new PicListCursorAdapter(getActivity(),mGridView);
        mGridView.setNumColumns(1);
        mGridView.setAdapter(mPicListCursorAdapter);


        getLoaderManager().initLoader(0, null, this);
        loadFirst();
        return mPullRefreshGridView;
    }


    private void loadFirst() {
        loadData();
    }

    private void loadNext() {
        loadData();
    }

    private void loadData() {
        final Request request = new Request.Builder()
                .url(String.format(Api.TNPIC_NEWS, 55, PIC_ROWS, PIC_CLASSIFY))
                .get()
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();
        OkHttpClientManager.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }
            @Override
            public void onResponse(final Response response) throws IOException {
                //解析字符串
                String s=response.body().string();
                final Gallery jsonBean = OkHttpClientManager
                        .getJsonBean(s, Gallery.class);
                Log.d("TAG",jsonBean.total+"");
                getActivity().getContentResolver()
                        .bulkInsert(mUri,
                                Gallery.getContentValues(jsonBean));
            }
        });

    }
    //Loader的3个方法
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),mUri,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mPicListCursorAdapter.changeCursor(data);
        Snackbar.make(getView(),data.getCount()+"",Snackbar.LENGTH_LONG)
                .setAction("yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mPicListCursorAdapter.changeCursor(null);
    }

    //Gride的刷新监听类
    private class OnGrideRefreshListener implements PullToRefreshBase.OnRefreshListener2<GridView> {
        @Override
        public void onPullDownToRefresh(final PullToRefreshBase<GridView> refreshView) {
            loadNext();
            refreshView.onRefreshComplete();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
            refreshView.onRefreshComplete();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
