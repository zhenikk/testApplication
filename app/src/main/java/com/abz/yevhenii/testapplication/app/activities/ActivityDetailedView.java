package com.abz.yevhenii.testapplication.app.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.abz.yevhenii.testapplication.R;
import com.abz.yevhenii.testapplication.app.fragments.FragmentDetailedView;
import com.abz.yevhenii.testapplication.rest.model.Item;

/**
 * Created by yevhenii
 */
public class ActivityDetailedView extends AppCompatActivity {
    FragmentDetailedView fragmentDetailedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            fragmentDetailedView = (FragmentDetailedView) getFragmentManager().getFragment(
                    savedInstanceState, "mContent");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbDetailedView);
        setSupportActionBar(toolbar);
        TextView tv = (TextView) findViewById(R.id.tvDetailedHeader);
        Typeface font = Typeface.createFromAsset(tv.getContext().getAssets(), "font/Roboto-Medium.ttf");
        tv.setTypeface(font);

        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        Bundle bundle = new Bundle();
        Intent intent = this.getIntent();
        Item item = new Item();
        if (intent != null) {
            bundle = intent.getExtras();
            if (bundle != null) {
                item = (Item) bundle.getSerializable("value");
            }
        }
        if (item != null) tv.setText(item.getTitle());

        fragmentDetailedView
                = new FragmentDetailedView();
        fragmentDetailedView.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragmentDetailedView);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                     case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "mContent", fragmentDetailedView);

    }
}
