package com.example.nathaniel.recyclerview;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final int REQUEST_CALL = 1;
    private static final int READLOGCODE = 2;
    private static final int READCONTACTCODE = 3;
    private static final int READ_PHONE_NUMBERS_CODE = 4;
    private static final int CAMERAPERMISSTIONCODE = 10;
    private static final int GALLERYCODE = 11;
    private static final int PHONE_STATE_LISTEN_CODE = 14;
    private static final int MODIFY_AUDIO_SETTINGS_CODE = 15;
    private static final int READSTORAGECODE = 12;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 13;
    private static final int WRITELOGCODE = 5;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    String phoneNumber = "*804%23";
    //    private static final int MY_PERMISSTIONS_REQUEST_CALL_PHONE = ;
    private List<LogModel> mLogModleList;
    static ViewPager myViewPager;
        viewPageAdapter myViewPagerAdapter;
//        RecyclerView log_recyclerView;
      static TabLayout tabLayout;
        Context context;
    private Dialog myDialog;

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
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        boolean settingsCanWrite = false;
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            settingsCanWrite = Settings.System.canWrite(this);
            if(!settingsCanWrite) {
                // If do not have write settings permission then open the Can modify system settings panel.
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                startActivity(intent);
            }}
        PreferenceManager.setDefaultValues(this,R.xml.pref_main,false);

/////////////////////////////
//      askPermissions();
        if(checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.

        createView();



    }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.option,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.search_bar:
//                Toast.makeText(this,"setting is clicked",Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.help:
//                Toast.makeText(this,"help is clicked",Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.terms:
//                Toast.makeText(this,"Terms and regulation is clicked",Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.exit:
//                Toast.makeText(this,"bye is clicked",Toast.LENGTH_SHORT).show();
//                finish();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.gebetaFragment) {
            Intent gebeta = new Intent(this,BalanceRelatedActivity.class);
            gebeta.putExtra("ViewpagerPosition",0);
            startActivity(gebeta);
            // Handle the camera action
        }else if (id == R.id.rechargeFragment) {
            Intent rechargeBalance = new Intent(this,BalanceRelatedActivity.class);
            rechargeBalance.putExtra("ViewpagerPosition",1);
            startActivity(rechargeBalance);
        }else if (id == R.id.borrowFragment) {
            Intent borrowFragment = new Intent(this,BalanceRelatedActivity.class);
            borrowFragment.putExtra("ViewpagerPosition",2);
            startActivity(borrowFragment);

        } else if (id == R.id.transferFragment) {
            Intent transferBalance = new Intent(this,BalanceRelatedActivity.class);
            transferBalance.putExtra("ViewpagerPosition",3);
            startActivity(transferBalance);

        }
        else if (id == R.id.checkbalance) {
//            askPermissions();
//            makePhoneCall(phoneNumber);
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE,},REQUEST_CALL);
            }
            else {
                String dial = "tel:" + "*804%23";
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        }  else if (id == R.id.Emergencynum) {
            Intent emergencyNum = new Intent(this, ShortEmergencyActivity.class);
            startActivity(emergencyNum);

        } else if (id == R.id.favnum) {
            myViewPager.setCurrentItem(1);
            tabLayout.getTabAt(1).select();

        } else if (id == R.id.blacklisted) {
            Intent blacklisted = new Intent(this, SecondActivity.class);
            blacklisted.putExtra("itemName",1);
            startActivity(blacklisted);
        }else if (id == R.id.change_theme) {
            changeTheme();
        }
        else if (id == R.id.nav_help) {

        }
        else if (id == R.id.nav_exit) {
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    
    private void askPermissions(){
//           <uses-permission android:name="android.permission.CALL_PHONE" />
//    <uses-permission android:name="android.permission.READ_CALL_LOG" />
//    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
//    <uses-permission android:name="android.permission.READ_CONTACTS"/>
//    <uses-permission android:name="android.permission.READ_PHONE_NUMBERS"/>
//    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
//    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
//    <uses-permission android:name="android.permission.CAMERA"/>
//
//
//
//        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED){
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CALL_LOG},READLOGCODE);
//
//            }
//        }

//        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE,},REQUEST_CALL);
//        }
//        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,},READSTORAGECODE);
//            }
//        }
//        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},WRITE_EXTERNAL_STORAGE_CODE);
//        }
//        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,},CAMERAPERMISSTIONCODE);
//        }
    }
    private void makePhoneCall(String number){
        if (number.trim().length()>0){
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE,},REQUEST_CALL);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CALL_LOG,},READLOGCODE);
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_CALL_LOG,},WRITELOGCODE);
                }
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS,},READCONTACTCODE);

            }
            else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else{
            Toast.makeText(this, "No Phone Number", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode ==1){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                createView();
            }
            else {
                Toast.makeText(this, "Pemission Denied", Toast.LENGTH_SHORT).show();
            }
        }}

        private void createView(){
            setTitle("Logs");
            myViewPager = findViewById(R.id.viewPagerContainer);
            myViewPagerAdapter = new viewPageAdapter(getSupportFragmentManager(),getApplicationContext());
            // Set up the ViewPager with the sections adapter.
            tabLayout =findViewById(R.id.tabs);

            myViewPagerAdapter.AddFragment(new ContactLogs(),"");
            myViewPagerAdapter.AddFragment(new FavFragment(),"");
            myViewPagerAdapter.AddFragment(new EmergencyFragment(),"");
            myViewPagerAdapter.AddFragment(new ContactFragment(),"");
//        myViewPager.onInterceptTouchEvent(true);
            myViewPager.setAdapter(myViewPagerAdapter);
//        tabLayout.setupWithViewPager(myViewPager);

            tabLayout.getTabAt(0).setIcon(R.drawable.ic_recents);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_favorite_call);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_phone_call_emergency);
            tabLayout.getTabAt(3).setIcon(R.drawable.ic_perm_contact);
//
//        myViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(myViewPager));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int position =tab.getPosition();
                    switch (position){
                        case 0: setTitle("Logs");
                            break;
                        case 1: setTitle("Favourites");
                            break;
                        case 2: setTitle("Emergency");
                            break;
                        case 3: setTitle("Contacts");
                            break;
                            default:setTitle("Logs");
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

    public static void changeFragment(int num){
        myViewPager.setCurrentItem(num);
        tabLayout.getTabAt(num).select();


    }

    private  boolean checkAndRequestPermissions() {
        int permissionWriteSetting = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_SETTINGS);
        int permissionModifyPhoneState = ContextCompat.checkSelfPermission(this,Manifest.permission.MODIFY_PHONE_STATE);
        int permissionAudioSetting = ContextCompat.checkSelfPermission(this,Manifest.permission.MODIFY_AUDIO_SETTINGS);
        int permissionPhoneState = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE);
        int permissionReadContact = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS);
        int permissionWriteContact = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_CONTACTS);
        int callPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int readStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int callLogPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionPhoneState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }if (permissionModifyPhoneState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        } if (permissionPhoneState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }if (permissionAudioSetting != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
        }
        if (permissionReadContact != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (permissionWriteContact != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_CONTACTS);
        }
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (readStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
        if (writeStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (callPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (callPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                listPermissionsNeeded.add(Manifest.permission.READ_CALL_LOG);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public void changeTheme(){
//        this.recreate();
        SharedPreferences sharedPreferences =getSharedPreferences("mySPreference",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.theme_change_view);
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CardView Theme1 = myDialog.findViewById(R.id.theme1);
        CardView Theme2 = myDialog.findViewById(R.id.theme2);
        CardView Theme3 = myDialog.findViewById(R.id.theme3);
        CardView Theme4 = myDialog.findViewById(R.id.theme4);
        CardView Theme5 = myDialog.findViewById(R.id.theme5);
        CardView Theme6 = myDialog.findViewById(R.id.theme6);
        CardView Theme7 = myDialog.findViewById(R.id.theme7);
        CardView Theme8 = myDialog.findViewById(R.id.theme8);
        CardView ThemeDefault = myDialog.findViewById(R.id.theme_default);

        Theme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme1AppTheme);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });

        Theme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme2AppTheme);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });

        Theme3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme3AppTheme);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });
        Theme4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme4AppTheme);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });
        Theme5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme5AppTheme);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });
        Theme6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme6AppTheme);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });
        Theme7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme7AppTheme);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });
        Theme8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme_NoActionBar_Theme8AppTheme);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });
        ThemeDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeId",R.style.AppTheme);
                editor.apply();
//                this.recreate();
                TaskStackBuilder.create(HomeActivity.this)
                        .addNextIntent(new Intent(HomeActivity.this, HomeActivity.class))
                        .addNextIntent(HomeActivity.this.getIntent())
                        .startActivities();
                myDialog.dismiss();
            }
        });

        myDialog.show();
        myDialog.setCancelable(true);
    }
}

