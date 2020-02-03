package com.example.nathaniel.recyclerview;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    public static String blockPhoneNumber = "No Number";
    public static String blockPhoneName = "No Name";
    static List<LogModel> blackListModelList;

    static List<String> blockedList;
    static List<String> removedOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_short_emergency);

        int itemName = getIntent().getIntExtra("itemName",0);
        if (itemName ==1) {
            Fragment blacklist = new BlackListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.blacklist_container, blacklist).commit();
        }


        assert !blockPhoneNumber.equals("");
        blockPhoneNumber = getIntent().getStringExtra("PhoneNumber");
        blockPhoneName = getIntent().getStringExtra("PhoneName");
        loadBlacklistData();

        if (blackListModelList.size()<1){
            saveBlacklistData(blackListModelList);

        }
        blockedList = new ArrayList<>();
        removedOne = new ArrayList<>();

        if (blackListModelList != null){
        for (LogModel number : blackListModelList) {
            blockedList.add(number.getNumber());
        }
        for (LogModel number : blackListModelList) {
            blockedList.add(number.getNumber());
        }

//        System.out.println("blocked list "+ blockedList);
        if (!(blockedList.contains(blockPhoneNumber))){
            blockedList.add(blockPhoneNumber);
            blackListModelList.add(new LogModel(blockPhoneName, blockPhoneNumber,"2 min ago", R.drawable.ic_person3,R.drawable.ic_call_missed));

//            putBlacklist(context,number.getNumber());

        }}
        if (blackListModelList.size()==1){
//            blackListModelList.indexOf()
            String noNumber = "No number In BlackList";
//            if (blackListModelList.get(0).getNumber().equals("")) {
                blackListModelList.get(0).setName(noNumber);
                blackListModelList.get(0).setNumber("Click Plus To Add");
//            }
        }
        List<LogModel> newArrayList = blackListModelList;
        saveBlacklistData( newArrayList);
//        Toast.makeText(this, "you blocked " + blockPhoneNumber, Toast.LENGTH_SHORT).show();

////////////////////////////////////////////////

   }
    public void saveBlacklistData( List<LogModel> newList){
        SharedPreferences pref =getSharedPreferences("blacklistModelList", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(newList);
        editor.putString("blacklist",json);
        editor.apply();
    }

    public void loadBlacklistData(){

        SharedPreferences pref =getSharedPreferences("blacklistModelList", 0); // 0 - for private mode
        Gson gson = new Gson();
        String json = pref.getString("blacklist",null);
        Type type = new TypeToken<ArrayList<LogModel>>() {}.getType();
        blackListModelList = gson.fromJson(json,type);
        if (blackListModelList == null){
            blackListModelList = new ArrayList<>();
            blackListModelList.add(new LogModel("No Number", null,"2 min ago", R.drawable.ic_person3,R.drawable.ic_call_missed));

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveBlacklistData(blackListModelList);
    }
}
