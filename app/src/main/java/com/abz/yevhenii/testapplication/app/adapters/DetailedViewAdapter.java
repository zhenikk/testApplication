package com.abz.yevhenii.testapplication.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abz.yevhenii.testapplication.R;
import com.abz.yevhenii.testapplication.app.activities.ActivityImage;
import com.abz.yevhenii.testapplication.app.viewholders.ViewHolder1;
import com.abz.yevhenii.testapplication.app.viewholders.ViewHolder2;
import com.abz.yevhenii.testapplication.app.viewholders.ViewHolderHeaderItem;
import com.abz.yevhenii.testapplication.rest.model.Content;
import com.abz.yevhenii.testapplication.rest.model.Item;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by yevhenii
 */
public class DetailedViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Item items;
    private final int TEXT = 0, IMAGE = 1, HEADER = 2;

    public DetailedViewAdapter(Item items) {
        this.items = items;
        Log.d("MyTag","DetailedVIewAdapter Constructor");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        Log.d("MyTag","DetailedVIewAdapter onCreateViewHolder()");


        switch (viewType) {
            case TEXT:
                View v1 = inflater.inflate(R.layout.layout_viewholder_text_item, parent, false);
                viewHolder = new ViewHolder1(v1);
                Log.d("MyTag","DetailedVIewAdapter onCreateViewHolder() type TEXT");

                break;
            case IMAGE:
                View v2 = inflater.inflate(R.layout.layout_viewholder_image_item, parent, false);
                viewHolder = new ViewHolder2(v2);
                Log.d("MyTag","DetailedVIewAdapter onCreateViewHolder() type IMAGE ");

                break;
            case HEADER:
                View v3 = inflater.inflate(R.layout.layout_viewholder_header_text_item, parent, false);
                viewHolder = new ViewHolderHeaderItem(v3);
                Log.d("MyTag","DetailedVIewAdapter onCreateViewHolder() type HEADER");

                break;
            default:
                //View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
//                viewHolder = new RecyclerViewSimpleTextViewHolder(v);
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        position = position - 1;

        switch (holder.getItemViewType()) {
            case TEXT:
                ViewHolder1 vh1 = (ViewHolder1) holder;
                configureViewHolder1(vh1, position);
                Log.d("MyTag", "DetailedVIewAdapter onBindViewHolder() type TEXT");

                break;
            case IMAGE:
                ViewHolder2 vh2 = (ViewHolder2) holder;
                configureViewHolder2(vh2, position);
                Log.d("MyTag", "DetailedVIewAdapter onBindViewHolder() type IMAGE");

                break;
            case HEADER:
                ViewHolderHeaderItem vh3 = (ViewHolderHeaderItem) holder;
                configureViewHolderHeaderItem(vh3);
                Log.d("MyTag", "DetailedVIewAdapter onBindViewHolder() type HEADER");

                break;
            default:
                //  RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                // configureDefaultViewHolder(vh, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        int i = items.getContent().size() + 1;
        Log.d("MyTag", "DetailedVIewAdapter getItemCount = " + i);
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return HEADER;
        int realPosition = position - 1;
        if (items.getContent().get(realPosition).getType().equals("text")) {
            return TEXT;
        } else if (items.getContent().get(realPosition).getType().equals("image")) {
            return IMAGE;
        }
        return -1;
    }

    private void configureViewHolder1(ViewHolder1 vh1, int position) {
        Content i = items.getContent().get(position);
        if (i != null) {
            vh1.getText().setText(i.getContent());
        }
    }

    private void configureViewHolder2(final ViewHolder2 vh2, int position) {
        //vh2.getImageView().setImageResource(R.drawable.sample_golden_gate);
        Content i = items.getContent().get(position);
        if (i != null) {
            final Context c = vh2.image.getContext();
            final Uri uri = Uri.parse(getPrettyUrl(i.getContent()));
            Picasso.with(vh2.getImageView()
                    .getContext())
                    .load(uri)
                    .placeholder(R.drawable.placeholder)
                    .into(vh2.image, new Callback() {
                        @Override
                        public void onSuccess() {
                            vh2.image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(v.getContext(), ActivityImage.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("uri", uri);
                                    i.putExtras(bundle);
                                    c.startActivity(i);
                                }
                            });
                        }

                        @Override
                        public void onError() {

                        }
            });

        }
    }

    private void configureViewHolderHeaderItem(ViewHolderHeaderItem vh3) {

        vh3.getText().setText(items.getTitle());
    }

    private String getPrettyUrl(String string) {
        string = string.replace("//", "/");
        if (!string.contains("http:/")) {
            StringBuilder sb = new StringBuilder(string);
            string = sb.insert(0, "http:/").toString();
        }
        return string;
    }

}
