package com.abz.yevhenii.testapplication.app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abz.yevhenii.testapplication.R;
import com.abz.yevhenii.testapplication.app.adapters.DetailedViewAdapter;
import com.abz.yevhenii.testapplication.rest.model.Item;

/**
 * Created by yevhenii
 */
public class FragmentDetailedView extends Fragment {
    private Item items;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    DetailedViewAdapter detailedViewAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            items = (Item) bundle.getSerializable("value");
        }
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(items.getTitle());
        }

        View v = inflater.inflate(R.layout.fragment_detailed_view, container, false);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) v.findViewById(R.id.detailed_recycler_view);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            items = (Item) savedInstanceState.getSerializable("currentItem");
        }
        Log.d("FragmentDetailedView","OnActivityCreated");
        detailedViewAdapter = new DetailedViewAdapter(items);
        mRecyclerView.setAdapter(detailedViewAdapter);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("currentItem", items);
    }
}
