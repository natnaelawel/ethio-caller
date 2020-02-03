package com.example.nathaniel.recyclerview;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public class ShortEmergencyActivity extends AppCompatActivity {
    private List<LogModel> mLogModleList;
    ViewPager myShortNumViewPager;
    viewPageAdapter myshortNumViewPagerAdapter;
    RecyclerView log_recyclerView;
    TabLayout tabLayout;
    @Override
    public Resources.Theme getTheme() {
        int themeId =this.getSharedPreferences("mySPreference",0).getInt("ThemeId",R.style.AppTheme);

        Resources.Theme theme = super.getTheme();
        if (true){
            theme.applyStyle(themeId,true);
        }
        return theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_emergency);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

/////////////////////////////
        myShortNumViewPager = findViewById(R.id.viewPagerShortandImportant);

        myshortNumViewPagerAdapter = new viewPageAdapter(getSupportFragmentManager(),getApplicationContext());
        // Set up the ViewPager with the sections adapter.
        tabLayout =findViewById(R.id.tabs);

        myshortNumViewPagerAdapter.AddFragment(new ShortNumFragment(),"");
        myshortNumViewPagerAdapter.AddFragment(new Important_numberFragment(),"");


        myShortNumViewPager.setAdapter(myshortNumViewPagerAdapter);
//        tabLayout.setupWithViewPager(myViewPager);

//
//        myViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(myShortNumViewPager));

    }

}
