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
public class Important_numberFragment extends Fragment {


    private View rootView;
    private RecyclerView important_recyclerView;
    private List<LogModel> mImportantNumModelList;
    private ShortNumAdapter MyImportantNumAdapter;

    public Important_numberFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        mImportantNumModelList = new ArrayList<>();

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
        databaseAccess.open();
        mImportantNumModelList = databaseAccess.getModelList("Important",mImportantNumModelList);
        databaseAccess.close();

//        mImportantNumModelList.add(new LogModel("Addis Ababa Police", "+251911552822", null, R.drawable.ic_person1, 0));
//        mImportantNumModelList.add(new LogModel("Federal Police", "+2519115512744", null, R.drawable.ic_person2, 0));
//        mImportantNumModelList.add(new LogModel("Addis AbabaFire and Prevention control", "0915670645", null, R.drawable.ic_person3, 0));
//        mImportantNumModelList.add(new LogModel("Addis Internation Bank", "+251115543775", null, R.drawable.ic_person1, 0));
//        mImportantNumModelList.add(new LogModel("African Dev. Bank", "+251115546336", null, R.drawable.ic_person2, 0));
//        mImportantNumModelList.add(new LogModel("Awash Int. Bank", "+251116637754", null, R.drawable.ic_person3, 0));
//        mImportantNumModelList.add(new LogModel("Bank of Abyssinia", "+251114160824", null, R.drawable.ic_person1, 0));
//        mImportantNumModelList.add(new LogModel("Berhan Int. Bank", "+251116623421", null, R.drawable.ic_person2, 0));
//        mImportantNumModelList.add(new LogModel("Buna Int. Bank", "+251112782243", null, R.drawable.ic_person3, 0));
//        mImportantNumModelList.add(new LogModel("Commericial Bank of Ethiopia", "+251115515004", null, R.drawable.ic_person1, 0));
//        mImportantNumModelList.add(new LogModel("Construction and Business Bank", "+251115512556", null, R.drawable.ic_person2, 0));
//        mImportantNumModelList.add(new LogModel("Cooperative Bank of Oromia", "+251115509427", null, R.drawable.ic_person3, 0));
//        mImportantNumModelList.add(new LogModel("Dashen Bank", "+251114661380", null, R.drawable.ic_person1, 0));
//        mImportantNumModelList.add(new LogModel("Development Bank of Ethiopia", "+251115511188", null, R.drawable.ic_person2, 0));
//        mImportantNumModelList.add(new LogModel("Enat Bank", "+251116620759", null, R.drawable.ic_person1, 0));
//        mImportantNumModelList.add(new LogModel("Ethiopian Bankers Association", "+2511155333874", null, R.drawable.ic_person2, 0));
//        mImportantNumModelList.add(new LogModel("Hewan Bank", "+251115541818", null, R.drawable.ic_person3, 0));
//        mImportantNumModelList.add(new LogModel("National Bank of Ethiopia", "+251115513857", null, R.drawable.ic_person1, 0));
//        mImportantNumModelList.add(new LogModel("Nib Int. Bank", "+251115503304", null, R.drawable.ic_person2, 0));
//        mImportantNumModelList.add(new LogModel("Oromia Int Bank", "+251111563669", null, R.drawable.ic_person3, 0));
//        mImportantNumModelList.add(new LogModel("United Bank", "+251911250588", null, R.drawable.ic_person1, 0));
//        mImportantNumModelList.add(new LogModel("Wegagen Bank", "+251115523800", null, R.drawable.ic_person2, 0));
//        mImportantNumModelList.add(new LogModel("Blood Bank", "+251115151558", null, R.drawable.ic_person3, 0));
//        mImportantNumModelList.add(new LogModel("John", "0930536977", null, R.drawable.ic_person1, 0));
//        mImportantNumModelList.add(new LogModel("Kidist", "0915670645", null, R.drawable.ic_person2, 0));
//        mImportantNumModelList.add(new LogModel("Lemma", "0930536977", null, R.drawable.ic_person3, 0));
//        mImportantNumModelList.add(new LogModel("Melese", "0915670645", null, R.drawable.ic_person1, 0));
//        mImportantNumModelList.add(new LogModel("Nardos", "0930536977", null, R.drawable.ic_person2, 0));

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Important Numbers");

        rootView = inflater.inflate(R.layout.fragment_important_number, container, false);
        important_recyclerView = rootView.findViewById(R.id.ImportantNum_recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        important_recyclerView.setLayoutManager(layoutManager);
        MyImportantNumAdapter = new ShortNumAdapter(getContext(),mImportantNumModelList);
        important_recyclerView.setItemAnimator(new DefaultItemAnimator());
        important_recyclerView.setAdapter(MyImportantNumAdapter);
        return rootView;
    }

}
