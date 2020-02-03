package com.example.nathaniel.recyclerview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShortNumFragment extends Fragment {


    private View rootView;
    private RecyclerView shortNum_recyclerView;
    private List<LogModel> mShortNumModelList;
    private ShortNumAdapter MyShortNumAdapter;

    public ShortNumFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        mShortNumModelList = new ArrayList<>();


        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
        databaseAccess.open();
        mShortNumModelList = databaseAccess.getModelList("ShortNumbers",mShortNumModelList);
        databaseAccess.close();

//        mShortNumModelList.add(new LogModel("Short num", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Bekele", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Chala", "0915670645", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Deribe", "0930536977", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Eyob", "0915670645", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Fitsum", "0930536977", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Girma", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Hayle", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Iyasu", "0915670645", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("John", "0930536977", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Kidist", "0915670645", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Lemma", "0930536977", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Melese", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Nardos", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Abeni Dewele", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Bekele", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Chala", "0915670645", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Deribe", "0930536977", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Eyob", "0915670645", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Fitsum", "0930536977", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Girma", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Hayle", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Iyasu", "0915670645", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("John", "0930536977", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Kidist", "0915670645", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Lemma", "0930536977", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Melese", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        mShortNumModelList.add(new LogModel("Nardos", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_short_num, container, false);
        shortNum_recyclerView = rootView.findViewById(R.id.ShortNumber_recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        shortNum_recyclerView.setLayoutManager(layoutManager);
        MyShortNumAdapter = new ShortNumAdapter(getContext(),mShortNumModelList);
        shortNum_recyclerView.setItemAnimator(new DefaultItemAnimator());
        shortNum_recyclerView.setAdapter(MyShortNumAdapter);
        return rootView;
    }

}
