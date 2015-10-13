package com.renkun.mnpic.ui.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.renkun.mnpic.R;
import com.renkun.mnpic.dao.DataProvider;
import com.renkun.mnpic.ui.adapter.ClassifyAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassifyFragment extends Fragment  {

    private GridView mGridView;
    private ClassifyAdapter mClassifyAdapter;

    private Uri mUri;
    public ClassifyFragment() {
        // Required empty public constructor
        mUri=Uri.parse(DataProvider.SCHEME + DataProvider.AUTHORITY + "classification");

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
        loadData();
        return view;
    }
    private void loadData(){

    }
}
