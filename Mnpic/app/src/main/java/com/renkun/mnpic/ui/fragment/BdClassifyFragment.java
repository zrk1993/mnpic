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
import com.renkun.mnpic.ui.adapter.BdClassifyAdapter;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;

/**
 *
 * 分类页面
 * A simple {@link Fragment} subclass.
 */
public class BdClassifyFragment extends Fragment  {

    private GridView mGridView;
    private BdClassifyAdapter mClassifyAdapter;

    public BdClassifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_classify, container, false);
        mGridView= (GridView) view.findViewById(R.id.classify_gride);
        mClassifyAdapter=new BdClassifyAdapter(getActivity(),R.layout.fragment_bd_classify_item);
        mGridView.setNumColumns(3);
        mGridView.setAdapter(mClassifyAdapter);
        mGridView.setOnItemClickListener(new GrideItemClickedListener());

        loadData();
        return view;
    }

    private class GrideItemClickedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(getActivity(), DetailsClassifyActivity.class);
            intent.setPackage(getActivity().getPackageName());
            intent.putExtra("classify", BdClassifyAdapter.title[position]);
            getActivity().startActivity(intent);
        }
    }

    private void loadData(){

    }
}
