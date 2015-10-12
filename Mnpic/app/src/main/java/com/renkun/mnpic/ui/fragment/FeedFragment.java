package com.renkun.mnpic.ui.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.renkun.mnpic.R;
import com.renkun.mnpic.dao.DataProvider;
import com.renkun.mnpic.dao.FeedsDataHelper;
import com.renkun.mnpic.data.Api;
import com.renkun.mnpic.data.OkHttpClientManager;
import com.renkun.mnpic.module.Gallery;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class FeedFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private PullToRefreshGridView mPullRefreshGridView;
    private GridView mGridView;



    private int PIC_CLASSIFY = 1;//图片类别
    private int PIC_ROWS = 20;//图片条数
    private int PIC_PAGE = 1;//图片分页

    private Uri mUri=Uri.parse(DataProvider.SCHEME + DataProvider.AUTHORITY + String.valueOf(PIC_CLASSIFY));
    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View containerView = inflater.inflate(R.layout.fragment_feed, container, false);
        mPullRefreshGridView = (PullToRefreshGridView) containerView.findViewById(R.id.pull_refresh_grid);
        mGridView = mPullRefreshGridView.getRefreshableView();

        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshGridView.setOnRefreshListener(new OnGrideRefreshListener());
        //getLoaderManager().initLoader(0, null, this);
        loadFirst();
        return containerView;
    }

    private void loadFirst() {
        loadData();
    }

    private void loadNext() {
        loadData();
    }

    private void loadData() {
        if (!mPullRefreshGridView.isRefreshing()) {
        }
        final Request request = new Request.Builder()
                .url(String.format(Api.TNPIC_LIST, PIC_CLASSIFY, PIC_ROWS, PIC_PAGE))
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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), jsonBean.tngou.size() + "", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    //Loader的3个方法
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    //Gride的刷新监听类
    private class OnGrideRefreshListener implements PullToRefreshBase.OnRefreshListener2<GridView> {
        @Override
        public void onPullDownToRefresh(final PullToRefreshBase<GridView> refreshView) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://apis.baidu.com/txapi/mvtp/meinv?num=1")
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .addHeader("apikey", "e0f0fc16b45ab692c8341e91b0c3151c")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(final Response response) throws IOException {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String s = "aaaaa%1$d";
                            Toast.makeText(getActivity(), String.format(s, 66), Toast.LENGTH_LONG).show();
                            refreshView.onRefreshComplete();
                        }
                    });
                }
            });

        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {

        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
