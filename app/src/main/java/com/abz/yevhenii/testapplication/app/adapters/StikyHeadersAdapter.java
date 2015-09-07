package com.abz.yevhenii.testapplication.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abz.yevhenii.testapplication.R;
import com.abz.yevhenii.testapplication.app.activities.ActivityDetailedView;
import com.abz.yevhenii.testapplication.rest.model.Data;
import com.abz.yevhenii.testapplication.rest.model.Datum;
import com.abz.yevhenii.testapplication.rest.model.Item;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;
import jp.wasabeef.picasso.transformations.ColorFilterTransformation;

/**
 * Created by yevhenii
 */
public class StikyHeadersAdapter extends RecyclerView.Adapter<StikyHeadersAdapter.ViewHolder>
        implements StickyHeaderAdapter<StikyHeadersAdapter.HeaderHolder> {
    private LayoutInflater layoutInflater;
    private Data mData;
    private List<Item> allItems;
    private List<Integer> headerPositions;

    public StikyHeadersAdapter(Context context, Data data) {
        layoutInflater = LayoutInflater.from(context);
        mData = data;
        fillMap(mData);
        convertDataToAllItems(mData);
    }

    void fillMap(Data data) {
        headerPositions = new ArrayList<>();
        int position = 0;
        headerPositions.add(0);
        for (Datum d : data.getData()) {
            position += d.getItems().size()-1;
            headerPositions.add(position);
        }

        Collections.sort(headerPositions);
    }
    void convertDataToAllItems(Data data){
        allItems = new ArrayList<>();
        for (Datum d : data.getData()) {
            for(Item i : d.getItems()){
                allItems.add(i);
            }
        }
    }

    Datum getDatumFromPosition(int position){
        Item i = allItems.get(position);
        for(Datum d: mData.getData()){
            if(i.getCategory().equals(d.getTitle()))
                return d;
        }
       return null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Define Item Views
        final Item item = allItems.get(position);
        holder.tvitem.setText(item.getTitle());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),ActivityDetailedView.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("value", item);
                i.putExtras(bundle);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        final View view = layoutInflater.inflate(R.layout.list_group_item, viewGroup, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(StikyHeadersAdapter.HeaderHolder holder, int position) {
        //setImage and Textview here
        final Datum item = getDatumFromPosition(position);
        try {
            holder.headerText.setText(item.getTitle());
           Uri uri = Uri.parse(item.getIcon());
            Context context = holder.headerImage.getContext();
            Transformation transformation = new ColorFilterTransformation(context.getResources().getColor(R.color.green_text));

            Picasso.with(context).load(uri).transform(transformation).into(holder.headerImage);
        }
        catch (NullPointerException e){

        }
    }

    @Override
    public long getHeaderId(int position) {
        for (int i = headerPositions.size()-1; i >= 0; i--) {
            if (position > headerPositions.get(i))
                return headerPositions.get(i);
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return allItems.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvitem;
        public View v;
        public ViewHolder(View itemView) {
            super(itemView);
            v=itemView;
            tvitem = (TextView) itemView.findViewById(R.id.tvSubItemText);
            Typeface font = Typeface.createFromAsset(itemView.getContext().getAssets(), "font/Roboto-Regular.ttf");
            tvitem.setTypeface(font);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView headerText;
        public ImageView headerImage;

        public HeaderHolder(View itemView) {
            super(itemView);
            headerText = (TextView) itemView.findViewById(R.id.tvListHeaderText);
            Typeface font = Typeface.createFromAsset(itemView.getContext().getAssets(), "font/Roboto-Medium.ttf");
            headerText.setTypeface(font);
            headerImage = (ImageView)itemView.findViewById(R.id.mainMenuItemIcon);
        }
    }
}
