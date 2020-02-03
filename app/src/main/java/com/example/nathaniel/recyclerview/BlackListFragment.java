package com.example.nathaniel.recyclerview;


import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.BlockedNumberContract;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlackListFragment extends Fragment implements secondRecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    Context context;
    FloatingActionButton fab;
    //    private static final String ARG_SECTION_NUMBER = "section_number";
    RecyclerView blackList_recyclerView;
    MyFavouriteAdapter MyFavouriteAdapter;
    FrameLayout frameLayout;
    ViewPager myViewPager;
    viewPageAdapter myViewPagerAdapter;
    TabLayout tabLayout;
    View rootView;
//    String blockedNumber = SecondActivity.blockPhoneNumber;
    private List<LogModel> blackListModelList;
    private List<String> blockedList;

    public BlackListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        TelecomManager telecomManager = TelecomManager(context.getSystemService);
//        getActivity().startActivity(telecomManager.createManageBlockedNumbersIntent(), null);
//        getActivity().startActivity(TelecomManager.C);
        // Inflate the layout for this fragment
//        Intent intent  = new Intent(Contacts.Intents.UI.LIST_STARRED_ACTION);


        getActivity().setTitle("BlackListed Numbers");
        rootView = inflater.inflate(R.layout.fragment_black_list, container, false);
        blackList_recyclerView = rootView.findViewById(R.id.blacklist_recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        blackList_recyclerView.setLayoutManager(layoutManager);

        MyFavouriteAdapter = new MyFavouriteAdapter(getContext(),blackListModelList,3);
        blackList_recyclerView.setItemAnimator(new DefaultItemAnimator());

        blackList_recyclerView.setAdapter(MyFavouriteAdapter);


        FloatingActionButton fab = rootView.findViewById(R.id.favourite_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1=
                new secondRecyclerItemTouchHelper(0,ItemTouchHelper.RIGHT,this);
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(blackList_recyclerView);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback2=
                new secondRecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallback2).attachToRecyclerView(blackList_recyclerView);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        blackListModelList = SecondActivity.blackListModelList;
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof MyFavouriteAdapter.MyViewHolder){
            String name = blackListModelList.get(viewHolder.getAdapterPosition()).getName();
            //backup of removed item for undo pupose
            final LogModel deletedItem = blackListModelList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            MyFavouriteAdapter.removeItem(viewHolder.getAdapterPosition());
            frameLayout = rootView.findViewById(R.id.BlackListframelayout);
            Snackbar snackbar = Snackbar.make(frameLayout,name +" is removed from the list",Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(13);
            View sbView = snackbarView;
            sbView.setBackgroundColor(Color.rgb(12,23,54));
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyFavouriteAdapter.restoreItem(deletedItem,deletedIndex);
                }
            });

            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }
    }

    private void putBlacklist( Context context,String number){
            ContentResolver contentResolver = context.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER, number);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Uri uri = contentResolver.insert(BlockedNumberContract.BlockedNumbers.CONTENT_URI, values);
            }
        }

    private void deleteBlacklist(){
        ContentValues values = new ContentValues();
        values.put(BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER, "1234567890");
        Uri uri = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            uri =getActivity().getContentResolver().insert(BlockedNumberContract.BlockedNumbers.CONTENT_URI, values);
            getActivity().getContentResolver().delete(uri, null, null);

        }
    }
    @TargetApi(Build.VERSION_CODES.N)
    private void unBlockNumber(String PhoneNumber){

        BlockedNumberContract.unblock(getActivity(),PhoneNumber);
    }

    private  void fetchBlacklist(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Cursor c = getActivity().getContentResolver().query(BlockedNumberContract.BlockedNumbers.CONTENT_URI,
                    new String[]{BlockedNumberContract.BlockedNumbers.COLUMN_ID, BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER,
                            BlockedNumberContract.BlockedNumbers.COLUMN_E164_NUMBER}, null, null, null);
        }
    }

    }
