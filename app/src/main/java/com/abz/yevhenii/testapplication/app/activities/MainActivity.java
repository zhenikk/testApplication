package com.abz.yevhenii.testapplication.app.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.abz.yevhenii.testapplication.R;
import com.abz.yevhenii.testapplication.app.fragments.FragmentListView;
public class MainActivity extends AppCompatActivity {
    FragmentListView fragmentListView;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "mContent", fragmentListView);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.myToolBar);
        TextView tv = (TextView) findViewById(R.id.tvMainTitle);
        Typeface font = Typeface.createFromAsset(tv.getContext().getAssets(), "font/Roboto-Medium.ttf");
        tv.setTypeface(font);
        tv.setText("Help");
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            fragmentListView = (FragmentListView) getFragmentManager().getFragment(
                    savedInstanceState, "mContent");

        } else {
            fragmentListView = new FragmentListView();
        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragmentListView);
        ft.commit();

    }
}
