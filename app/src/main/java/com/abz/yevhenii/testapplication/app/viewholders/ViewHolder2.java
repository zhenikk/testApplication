package com.abz.yevhenii.testapplication.app.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.abz.yevhenii.testapplication.R;

/**
 * Created by yevhenii
 */
public class ViewHolder2 extends RecyclerView.ViewHolder {
    public ImageView image;

    public ViewHolder2(View v) {
        super(v);
        image = (ImageView) v.findViewById(R.id.image_item);
    }

    public ImageView getImageView() {
        return image;
    }

    public void setImageView(ImageView label1) {
        this.image = label1;
    }

}
