package com.renkun.mnpic.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.renkun.mnpic.R;
import com.renkun.mnpic.ui.activity.DetailsClassifyActivity;
import com.renkun.mnpic.ui.adapter.ClassifyAdapter;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;

/**
 *
 * 分类页面
 * A simple {@link Fragment} subclass.
 */
public class ClassifyFragment extends Fragment  {

    private GridView mGridView;
    private ClassifyAdapter mClassifyAdapter;

    public ClassifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_classify, container, false);
        mGridView= (GridView) view.findViewById(R.id.classify_gride);
        mClassifyAdapter=new ClassifyAdapter(getActivity(),R.layout.fragment_classify_item);
        mGridView.setNumColumns(3);
        mGridView.setAdapter(mClassifyAdapter);
        mGridView.setOnItemClickListener(new GrideItemClickedListener());
        initAdview(view);
        loadData();
        return view;
    }
    private void initAdview(View view){
        // 实例化广告条
        AdView adView = new AdView(getActivity(), AdSize.FIT_SCREEN);
        adView.setAdListener(new AdViewListener() {

            @Override
            public void onSwitchedAd(AdView adView) {
                // 切换广告并展示
                Log.d("youmisdk", "切换广告并展示");

            }

            @Override
            public void onReceivedAd(AdView adView) {
                // 请求广告成功
                Log.d("youmisdk", "展示成功");
            }

            @Override
            public void onFailedToReceivedAd(AdView adView) {
                Log.d("youmisdk", "展示失败");
            }
        });

        // 获取要嵌入广告条的布局
        LinearLayout adLayout=(LinearLayout)view.findViewById(R.id.adLayout);

        // 将广告条加入到布局中
        adLayout.addView(adView);
    }
    private class GrideItemClickedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(getActivity(), DetailsClassifyActivity.class);
            intent.setPackage(getActivity().getPackageName());
            intent.putExtra("classify",ClassifyAdapter.title[position]);
            getActivity().startActivity(intent);
        }
    }

    private void loadData(){

    }
}
