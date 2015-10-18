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

    private int NumColumns=1;
    private int PIC_CLASSIFY = 1;//图片类别
    private int PIC_ROWS = 6;//图片条数
    private int PIC_PAGE = 1;//图片分页
    private String MAX_ID;//当前数据里保存的最大该类目的图片id,所对应的Preference索引
    //MAX_ID所对应的Preference索引的值
    private int MAX_ID_NUM;
    private Cursor mCursor;

    //页面图片集，数据库地址
    private Uri mUri;

    public HotFragment(int classify,int NumColumns) {
        // Required empty public constructor
        PIC_CLASSIFY=classify;
        MAX_ID="MAX_ID_"+PIC_CLASSIFY;
        this.NumColumns=NumColumns;
        mUri=Uri.parse(DataProvider.SCHEME + DataProvider.AUTHORITY + String.valueOf(PIC_CLASSIFY));

    }
    public HotFragment(){
        mUri=Uri.parse(DataProvider.SCHEME + DataProvider.AUTHORITY + String.valueOf(PIC_CLASSIFY));
    }

    @Override
    public void onResume() {
        super.onResume();
        MAX_ID_NUM=getActivity().getPreferences(Context.MODE_APPEND).getInt(MAX_ID, 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_hot, container, false);
        mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        mListView=mPullRefreshListView.getRefreshableView();
        mPullRefreshListView.setOnRefreshListener(new OnListRefreshListener());
        mHotListAdapter=new HotListAdapter(getContext(),mListView);
        getLoaderManager().initLoader(0, null, this);
        mPullRefreshListView.setAdapter(mHotListAdapter);
        mListView.setOnItemClickListener(new ListViewOnclickedListener());
        //loadFirst();
        return view;
    }

    private class ListViewOnclickedListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mCursor.moveToPosition(position-1);//position从1开始，cursor从0开始
            Intent intent=new Intent(getActivity(), PhotoDetailsActivity.class);
            intent.setPackage(getActivity().getPackageName());
            intent.putExtra("id", mCursor.getInt(mCursor.getColumnIndex("id")));
            startActivity(intent);
        }
    }
    private void loadFirst() {
        loadData();
    }

    private void loadNext() {
        loadData();
    }

    private void loadData() {
        final Request request = new Request.Builder()
                .url(String.format(Api.TNPIC_NEWS,MAX_ID_NUM,
                        PIC_ROWS, PIC_CLASSIFY))
                .get()
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();
        Log.d("http",request.urlString());
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
                Log.d("http", jsonBean.tngou.size() + "///" + s);
                getActivity().getContentResolver()
                        .bulkInsert(mUri,
                                Gallery.getContentValues(jsonBean));
                MAX_ID_NUM=jsonBean.tngou.get(jsonBean.tngou.size()-1).id;
            }
        });

    }
    //Loader的3个方法
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),mUri,null, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursor=data;
        mHotListAdapter.changeCursor(data);
        mPullRefreshListView.onRefreshComplete();
        Snackbar.make(getView(),data.getCount()+"",Snackbar.LENGTH_LONG)
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

        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

        }
    }


    @Override
    public void onPause() {
        super.onPause();
        ////MAX_ID所对应的Preference索引的值
        getActivity().getPreferences(Context.MODE_APPEND)
                .edit().putInt(MAX_ID,MAX_ID_NUM).commit();
    }
}
