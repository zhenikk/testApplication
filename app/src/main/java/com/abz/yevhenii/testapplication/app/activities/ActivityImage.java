package com.abz.yevhenii.testapplication.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abz.yevhenii.testapplication.R;
import com.abz.yevhenii.testapplication.rest.model.Item;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by yevhenii on 07.09.15.
 */
public class ActivityImage extends Activity {
    PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_image);
        ImageView iv = (ImageView)findViewById(R.id.iv_photo);
        ImageView iv_close = (ImageView)findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityImage.this.finish();
            }
        });
        TextView tv = (TextView) findViewById(R.id.tvImageTitle);
        Typeface font = Typeface.createFromAsset(tv.getContext().getAssets(), "font/Roboto-Medium.ttf");
        tv.setTypeface(font);


        Uri uri = Uri.parse("");
        Bundle bundle=new Bundle();
        Intent intent = this.getIntent();
        if (intent != null) {
            bundle = intent.getExtras();
        }
        if (bundle != null) {
            uri = bundle.getParcelable("uri");
        }

        Picasso.with(getApplicationContext()).load(uri).into(iv);
        mAttacher = new PhotoViewAttacher(iv);

    }
}
