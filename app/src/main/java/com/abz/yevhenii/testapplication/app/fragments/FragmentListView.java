package com.abz.yevhenii.testapplication.app.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abz.yevhenii.testapplication.R;
import com.abz.yevhenii.testapplication.app.Utils.SimpleDividerItemDecoration;
import com.abz.yevhenii.testapplication.app.adapters.StikyHeadersAdapter;
import com.abz.yevhenii.testapplication.rest.RestClient;
import com.abz.yevhenii.testapplication.rest.model.Content;
import com.abz.yevhenii.testapplication.rest.model.Data;
import com.abz.yevhenii.testapplication.rest.model.Datum;
import com.abz.yevhenii.testapplication.rest.model.Item;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by yevhenii
 */
public class FragmentListView extends Fragment {
    @Nullable

    private RecyclerView mRecyclerView;
    private StikyHeadersAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RestClient rc;
    Data currentData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_listview, container, false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) v.findViewById(R.id.main_recycler_view);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            currentData = (Data) savedInstanceState.getSerializable("currentData");
        }
        getData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("currentData", currentData);
    }

    public void getData() {
        //choose here from database or Internet...
        if (currentData != null) {
            setAdapters(currentData);
        } else {
            final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading...");

            if (isNetworkAvailable(getActivity())) {
                rc = new RestClient();
                mProgressDialog.show();
                rc.getApiService().getHelpData("06070aad9d3237daeb6afd5af078119d76e8292e", new Callback<Data>() {
                    @Override
                    public void success(Data data, Response response) {
                        currentData = data;
                        setAdapters(currentData);
                        clearDatabaseTables();
                        saveToDatabase(currentData);
                        Log.d("FragmentListView", "data is loaded from internet and saved to database");
                        if (mProgressDialog.isShowing())
                            mProgressDialog.dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        mProgressDialog.dismiss();
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            } else {
                setAdapters(readDataFromDataBase());
                Log.d("FragmentListView", "No internet, data was readed from local database");

            }
        }
    }

    private void setAdapters(Data mdata) {
        mAdapter = new StikyHeadersAdapter(getActivity(), mdata);
        StickyHeaderDecoration decor;
        decor = new StickyHeaderDecoration(mAdapter);
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addItemDecoration(decor, 0);
        }
    }

    public void clearDatabaseTables() {
        new Delete().from(Datum.class).execute();
        new Delete().from(Item.class).execute();
        new Delete().from(Content.class).execute();

    }

    public void saveToDatabase(Data data) {

        for (Datum datum : data.getData()) {
            datum.save();
            for (Item i : datum.getItems()) {
                i.datum = datum;
                i.save();
                for (Content c : i.getContent()) {
                    c.item = i;
                    c.save();
                }
            }
        }
    }

    Data readDataFromDataBase() {
        ArrayList<Datum> resultDatum = new ArrayList<>();
        List<Datum> queryresult = new Select().from(Datum.class).execute();

        for (Datum d : queryresult) {
            List<Item> itemsList = new Select().from(Item.class).where("Datum = ?", d.getId()).execute();

            for (Item i : itemsList) {
                List<Content> contentList = new Select().from(Content.class).where("Item = ?", i.getId()).execute();

                i.setContent(contentList);
            }
            d.setItems(itemsList);
            resultDatum.add(d);
        }

        return new Data(true, resultDatum);
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
