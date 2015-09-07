package com.abz.yevhenii.testapplication.app.viewholders;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.abz.yevhenii.testapplication.R;

/**
 * Created by yevhenii
 */
public class ViewHolderHeaderItem extends RecyclerView.ViewHolder {
        private TextView tv1;

        public ViewHolderHeaderItem(View v) {
            super(v);
            tv1 = (TextView) v.findViewById(R.id.helpItemTitle);
            Typeface font = Typeface.createFromAsset(tv1.getContext().getAssets(), "font/Roboto-Regular.ttf");
            tv1.setTypeface(font);
        }

        public TextView getText() {
            return tv1;
        }

        public void setText(TextView label1) {
            this.tv1 = label1;
        }

    }
