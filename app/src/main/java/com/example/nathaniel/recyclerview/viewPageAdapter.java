package com.example.nathaniel.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class viewPageAdapter extends FragmentPagerAdapter  {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private List<FloatingActionButton> fabs;
    Context ct;
    public static int pos;
        public boolean isContactSelected= false;



    public viewPageAdapter(FragmentManager fm, Context context)  {
            super(fm);
            this.ct= context;

        }

        @Override
        public Fragment getItem(int position) {

            return fragmentList.get(position);

            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return titleList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

        public void AddFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            titleList.add(title);


        }

}

