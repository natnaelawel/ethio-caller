package com.example.nathaniel.recyclerview;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmergencyFragment extends Fragment{
    private List<LogModel> emerModelList;
    Context context;
    FloatingActionButton fab;
    //    private static final String ARG_SECTION_NUMBER = "section_number";
    RecyclerView log_recyclerView;
//    MyFavouriteAdapter MyEmergencyAdapter;
    ShortNumAdapter MyEmergencyAdapter;
    FrameLayout frameLayout;
    ViewPager myViewPager;
    viewPageAdapter myViewPagerAdapter;
    TabLayout tabLayout;
    View rootView;

    public EmergencyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        getActivity().setTitle("Emergency Numbers");

        rootView = inflater.inflate(R.layout.fragment_emergency, container, false);
        log_recyclerView = rootView.findViewById(R.id.emergency_recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        log_recyclerView.setLayoutManager(layoutManager);
//        MyEmergencyAdapter = new MyFavouriteAdapter(getContext(),emerModelList,2);
        MyEmergencyAdapter = new ShortNumAdapter(getContext(),emerModelList);
        log_recyclerView.setItemAnimator(new DefaultItemAnimator());
        log_recyclerView.setAdapter(MyEmergencyAdapter);

//        FloatingActionButton fab = rootView.findViewById(R.id.emergency_fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        /////////////////////////////
//
//        RecyclerView rv= rootView.findViewById(R.id.log_recyclerView);
//        MyLogAdapter = new MyLogAdapter(getContext(),logModelList);
//        rv.setItemAnimator(new DefaultItemAnimator());
//        System.out.println(logModelList.get(2).getName()+ "is all the data");
//        rv.setLayoutManager(new LinearLayoutManager(getContext()));
////        rv.setAdapter(recyclerAdapter(this, SpaceshipsCollection.getSpaceships()));
//        rv.setAdapter(MyLogAdapter);
//
//        ItemTouchHelper.SimpleCallback itemTouchHelperCallback=
//                new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
//        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(log_recyclerView);




        /////////////////////

        return rootView;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        emerModelList= new ArrayList<>();
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
        databaseAccess.open();
        emerModelList = databaseAccess.getModelList("Emergency",emerModelList);
        databaseAccess.close();
//        System.out.println("list is "+emerModelList);
//        Log.d("list is", "createView: list is"+ emerModelList);

//
//        emerModelList.add(new LogModel("Abeni fadede","0915670645","2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//        emerModelList.add(new LogModel("Bekele","0930536977","2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//        emerModelList.add(new LogModel("Chala","0915670645","2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//        emerModelList.add(new LogModel("Deribe","0930536977","2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//        emerModelList.add(new LogModel("Eyob","0915670645","2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//        emerModelList.add(new LogModel("Fitsum","0930536977","2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//        emerModelList.add(new LogModel("Girma","0915670645","2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//        emerModelList.add(new LogModel("Hayle","0930536977","2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//        emerModelList.add(new LogModel("Iyasu","0915670645","2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//        emerModelList.add(new LogModel("John","0930536977","2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//        emerModelList.add(new LogModel("Kidist","0915670645","2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//        emerModelList.add(new LogModel("Lemma","0930536977","2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//        emerModelList.add(new LogModel("Melese","0915670645","2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//        emerModelList.add(new LogModel("Nardos","0930536977","2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//

        super.onCreate(savedInstanceState);
    }

//
//    @Override
//    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
//        if (viewHolder instanceof MyLogAdapter.MyLogHolder){
//            String name = emerModelList.get(viewHolder.getAdapterPosition()).getName();
//            //backup of removed item for undo pupose
//            final LogModel deletedItem = emerModelList.get(viewHolder.getAdapterPosition());
//            final int deletedIndex = viewHolder.getAdapterPosition();
//            MyLogAdapter.removeItem(viewHolder.getAdapterPosition());
//            frameLayout = rootView.findViewById(R.id.framelayout);
//            Snackbar snackbar = Snackbar.make(frameLayout,name +" is removed from the list",Snackbar.LENGTH_LONG);
//            snackbar.setAction("UNDO", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    MyLogAdapter.restoreItem(deletedItem,deletedIndex);
//                }
//            });
//            snackbar.setActionTextColor(Color.RED);
//            snackbar.show();
//        }
//    }

}
