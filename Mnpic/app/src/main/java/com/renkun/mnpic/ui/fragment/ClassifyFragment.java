package com.renkun.mnpic.ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.renkun.mnpic.R;
import com.renkun.mnpic.dao.DataProvider;
import com.renkun.mnpic.ui.activity.DetailsClassifyActivity;
import com.renkun.mnpic.ui.adapter.ClassifyAdapter;

/**
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

        loadData();
        return view;
    }
    private class GrideItemClickedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(getActivity(), DetailsClassifyActivity.class);
            intent.setPackage(getActivity().getPackageName());
            intent.putExtra("position",position+1);
            getActivity().startActivity(intent);
        }
    }

    private void loadData(){

    }
}
