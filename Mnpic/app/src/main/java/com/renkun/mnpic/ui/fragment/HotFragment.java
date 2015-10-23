package com.renkun.mnpic.ui.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.renkun.mnpic.R;
import com.renkun.mnpic.dao.DataProvider;
import com.renkun.mnpic.data.Api;
import com.renkun.mnpic.data.OkHttpClientManager;
import com.renkun.mnpic.module.Gallery;
import com.renkun.mnpic.ui.activity.PhotoDetailsActivity;
import com.renkun.mnpic.ui.adapter.HotListAdapter;
import com.renkun.mnpic.ui.adapter.PicListCursorAdapter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class HotFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView mListView;
    private PullToRefreshListView mPullRefreshListView;
    private HotListAdapter mHotListAdapter;

    private int NumColumns = 1;
    private int PIC_CLASSIFY = 0;//图片类别
    private int PIC_ROWS = 3;//图片条数
    private int PIC_PAGE = 1;//图片分页
    private String MIN_ID;//当前数据里保存的该类目的图片最小id,所对应的Preference索引
    //MAX_ID所对应的Preference索引的值
    private int MIN_ID_NUM;
    private Cursor mCursor;

    //页面图片集，数据库地址
    private Uri mUri;

    public HotFragment(int classify, int NumColumns) {
        // Required empty public constructor
        PIC_CLASSIFY = classify;
        MIN_ID = "MAX_ID_" + PIC_CLASSIFY;
        this.NumColumns = NumColumns;
        mUri = Uri.parse(DataProvider.SCHEME + DataProvider.AUTHORITY + String.valueOf(PIC_CLASSIFY));

    }

    public HotFragment() {
        mUri = Uri.parse(DataProvider.SCHEME + DataProvider.AUTHORITY + String.valueOf(PIC_CLASSIFY));
    }

    @Override
    public void onResume() {
        super.onResume();
        MIN_ID_NUM = getActivity().getPreferences(Context.MODE_APPEND).getInt(MIN_ID, 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        mListView = mPullRefreshListView.getRefreshableView();
        initListeView();
        mPullRefreshListView.setOnRefreshListener(new OnListRefreshListener());
        mHotListAdapter = new HotListAdapter(getContext(), mListView);
        getLoaderManager().initLoader(0, null, this);
        mPullRefreshListView.setAdapter(mHotListAdapter);
        mListView.setOnItemClickListener(new ListViewOnclickedListener());
        loadFirst();
        return view;
    }

    private void initListeView() {
        ILoadingLayout startLabels = mPullRefreshListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("您可劲的拉");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("好嘞，松开为您刷新");// 下来达到一定距离时，显示的提示
        //设置mPullRefreshGridView上啦
        ILoadingLayout endLabels = mPullRefreshListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("您可劲的拉");// 刚上拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...");// 刷新时
        endLabels.setReleaseLabel("松开加载更多...");// 下来达到一定距离时，显示的提示
    }

    private class ListViewOnclickedListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mCursor.moveToPosition(position - 1);//position从1开始，cursor从0开始
            Intent intent = new Intent(getActivity(), PhotoDetailsActivity.class);
            intent.setPackage(getActivity().getPackageName());
            intent.putExtra("id", mCursor.getInt(mCursor.getColumnIndex("id")));
            startActivity(intent);
        }
    }

    private void loadFirst() {
        String url = "http://www.tngou.net/tnfs/api/list?rows=" + PIC_ROWS + "&page=1";
        loadData(url);
    }

    private void loadNext() {
        String url = "http://www.tngou.net/tnfs/api/news?id=" + (MIN_ID_NUM - PIC_ROWS - 1) + "&rows=" + PIC_ROWS;
        loadData(url);
    }

    private void loadData(String url) {

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();
        Log.d("http", request.urlString());
        OkHttpClientManager.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(final Response response) throws IOException {
                //解析字符串
                String s = response.body().string();
                Gallery jsonBean = OkHttpClientManager
                        .getJsonBean(s, Gallery.class);
                if (jsonBean.tngou.size() > 0) {
                    getActivity().getContentResolver()
                            .bulkInsert(mUri,
                                    Gallery.getContentValues(jsonBean));
                    if (MIN_ID_NUM == 0) MIN_ID_NUM = jsonBean.tngou.get(0).id;
                    MIN_ID_NUM = jsonBean.tngou.get(0).id >= MIN_ID_NUM
                            ? MIN_ID_NUM : jsonBean.tngou.get(0).id;
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPullRefreshListView.onRefreshComplete();
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

    //Loader的3个方法
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), mUri, null, null, null, "id desc");

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursor = data;
        mHotListAdapter.changeCursor(data);
        mPullRefreshListView.onRefreshComplete();
        Snackbar.make(getView(), data.getCount() + "", Snackbar.LENGTH_LONG)
                .setAction("yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mHotListAdapter.changeCursor(null);
    }

    //Gride的刷新监听类
    private class OnListRefreshListener implements PullToRefreshBase.OnRefreshListener2<ListView> {

        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            loadFirst();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            loadNext();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        ////MAX_ID所对应的Preference索引的值
        getActivity().getPreferences(Context.MODE_APPEND)
                .edit().putInt(MIN_ID, MIN_ID_NUM).commit();
    }
}
