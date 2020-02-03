package com.example.nathaniel.recyclerview;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BalanceRelatedActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager myViewPager;
    viewPageAdapter myViewPagerAdapter;
//    EditText RechargeToNumber;
//    private static final int PICKCONTACTREQUEST=222;

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
        setContentView(R.layout.activity_balance_related);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setTitle("Gebeta");
        tabLayout = findViewById(R.id.contentTabs);
        myViewPager = findViewById(R.id.balanceRelatedContainer);

        myViewPagerAdapter = new viewPageAdapter(getSupportFragmentManager(),getApplicationContext());
        // Set up the ViewPager with the sections adapter.

        myViewPagerAdapter.AddFragment(new GebetaFragment(),"");
        myViewPagerAdapter.AddFragment(new BalanceRechargeFragment(),"");
        myViewPagerAdapter.AddFragment(new BalanceBorrowFragment(),"");
        myViewPagerAdapter.AddFragment(new BalanceTransferFragment(),"");
//        myViewPager.setPageTransformer();
        myViewPager.setAdapter(myViewPagerAdapter);

//        tabLayout.getTabAt(0).setCustomView(findViewById(R.id.customTextviewLayout).findViewById(R.id.customtextview));
//        tabLayout.getTabAt(1).setCustomView(R.layout.custom_text_view);
//        tabLayout.getTabAt(2).setCustomView(R.layout.custom_text_view);
//        tabLayout.getTabAt(3).setCustomView(R.layout.custom_text_view);
//        tabLayout.getTabAt(3).setText("Transfer");
//        tabLayout.getTabAt(0).setText("Gebeta");
//        tabLayout.getTabAt(1).setText("Recharge");
//        tabLayout.getTabAt(2).setText("Borrow");
//        LinearLayout custom_textview = findViewById(R.id.customTextviewLayout);
//        TextView customtext=  custom_textview.findViewById(R.id.customTextviewLayout);
//        customtext.setText("Gebeta");


        myViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(myViewPager));


        int pos =  getIntent().getIntExtra("ViewpagerPosition",0);
        myViewPager.setCurrentItem(pos);
        tabLayout.getTabAt(pos).select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position =tab.getPosition();
                switch (position){
                    case 0: setTitle("Gebeta");
                        break;
                    case 1: setTitle("Recharge");
                        break;
                    case 2: setTitle("Borrow");
                        break;
                    case 3: setTitle("Transfer");
                        break;
                    default:setTitle("Gebeta");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
