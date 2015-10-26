package com.renkun.mnpic.ui.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.renkun.mnpic.R;
import com.renkun.mnpic.data.Api;
import com.renkun.mnpic.data.OkHttpClientManager;
import com.renkun.mnpic.module.BDpic;
import com.renkun.mnpic.ui.activity.DetailsClassifyActivity;
import com.renkun.mnpic.ui.adapter.BDArrayAdapter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BdFragment extends Fragment {
    //百度图片URL参数
    private int pn;
    private int rn;
    private String tag1;
    private String tag2;
    private String flags;

    private DetailsClassifyActivity mActivity;


    private GridView mGridView;
    private PullToRefreshGridView mPullRefreshGridView;
    private BDArrayAdapter mBDArrayAdapter;

    public ArrayList<BDpic.DATA> data = new ArrayList<>();
    //true是上拉  false是下拉
    private boolean freshFlag;

    public BdFragment(int pn, int rn, String tag1, String tag2, String flags) {
        this.pn = pn;
        this.rn = rn;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.flags = flags;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (DetailsClassifyActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bd, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mPullRefreshGridView = (PullToRefreshGridView) view.findViewById(R.id.pull_refresh_grid);
        mGridView = mPullRefreshGridView.getRefreshableView();
        mGridView.setNumColumns(2);
        mPullRefreshGridView.setOnRefreshListener(new OnGrideRefreshListener());
        mGridView.setOnItemClickListener(new MyOnItemClickListener());
        mBDArrayAdapter = new BDArrayAdapter(getActivity(), R.layout.fragment_bd);
        mGridView.setAdapter(mBDArrayAdapter);
        loadFirst();
    }

    private void loadFirst() {
        pn = 0;
        String url = String.format(Api.BDApiClassify, pn, rn, tag1, tag2);
        freshFlag = false;
        loadData(url);
    }

    private void loadnext() {
        pn += 5;
        String url = String.format(Api.BDApiClassify, pn, rn, tag1, tag2);
        freshFlag = true;
        loadData(url);
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

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            DetailsClassifyActivity detailsClassifyActivity = (DetailsClassifyActivity) getActivity();
            detailsClassifyActivity
                    .switchContent(detailsClassifyActivity.mContent, new BdFragmentClik(data, position));
        }
    }

    private void loadData(String url) {
        Log.d("bd", url);
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();
        OkHttpClientManager.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshGridView.onRefreshComplete();
                        Toast.makeText(getActivity(),"网络有点问题",Toast.LENGTH_LONG).show();                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                //解析字符串
                String s = response.body().string();
                BDpic bDpic = OkHttpClientManager
                        .getJsonBean(s, BDpic.class);
                if (!CheckData(bDpic)) return;
                if (freshFlag) {
                    data.addAll(bDpic.data);
                } else data = bDpic.data;

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBDArrayAdapter.data = data;
                        mBDArrayAdapter.notifyDataSetChanged();
                        mPullRefreshGridView.onRefreshComplete();
                    }
                });
            }

        });

    }

    public boolean CheckData(BDpic bDpic) {
        if (bDpic == null || bDpic.data.size() < 1) return false;

        for (BDpic.DATA data : bDpic.data) {
            if (data.id == 0) bDpic.data.remove(data);
        }
        return true;
    }

}
