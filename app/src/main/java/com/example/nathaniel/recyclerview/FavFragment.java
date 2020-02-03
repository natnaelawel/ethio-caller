package com.example.nathaniel.recyclerview;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment implements secondRecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private List<LogModel> favModelList;
    Context context;
    FloatingActionButton fab;
    //    private static final String ARG_SECTION_NUMBER = "section_number";
    RecyclerView fav_recyclerView;
    MyFavouriteAdapter MyFavouriteAdapter;
    FrameLayout frameLayout;
    ViewPager myViewPager;
    viewPageAdapter myViewPagerAdapter;
    TabLayout tabLayout;
    View rootView;
    boolean isFavRemoved = false;
    private List<LogModel> databaseFavModelList;


    public FavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        getActivity().setTitle("Favourite Numbers");

        rootView = inflater.inflate(R.layout.fragment_fav, container, false);
        fav_recyclerView = rootView.findViewById(R.id.favourite_recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        fav_recyclerView.setLayoutManager(layoutManager);
        MyFavouriteAdapter = new MyFavouriteAdapter(getContext(),favModelList,1);
        fav_recyclerView.setItemAnimator(new DefaultItemAnimator());
        fav_recyclerView.setAdapter(MyFavouriteAdapter);


        FloatingActionButton fab = rootView.findViewById(R.id.favourite_fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                    FavFragment favFragment = new FavFragment();
                    Bundle bundle =new Bundle();
                    bundle.putString("message","pick");
                    favFragment.setArguments(bundle);
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fav_framelayout, new ContactFragment());
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//
                    HomeActivity homeActivity = new HomeActivity();
                    homeActivity.changeFragment(3);
//                Intent intent = new Intent(getActivity(),ContactFragment.class);
//                intent.putExtra("message", "pickFav");
//                startActivityForResult(intent, 1);

            }
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1=
                new secondRecyclerItemTouchHelper(0,ItemTouchHelper.RIGHT,this);
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(fav_recyclerView);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback2=
                new secondRecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallback2).attachToRecyclerView(fav_recyclerView);

        /////////////////////

        return rootView;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        favModelList= new ArrayList<>();
        try {
            favModelList = getAllFavContacts();

        }catch (RuntimeException e){
            e.printStackTrace();
        }
//
//        databaseFavModelList = new ArrayList<>();
//        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
//        databaseAccess.open();
//        databaseFavModelList = databaseAccess.getModelList("Favourites",favModelList);
//        databaseAccess.close();
//        favModelList.addAll(databaseFavModelList);


//        favModelList.add(new LogModel("Abeni fadede","0915670645","2 min ago", R.drawable.ic_person2,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Bekele","0930536977","2 min ago", R.drawable.ic_person3,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Chala","0915670645","2 min ago", R.drawable.ic_person1,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Deribe","0930536977","2 min ago", R.drawable.ic_person2,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Eyob","0915670645","2 min ago", R.drawable.ic_person3,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Fitsum","0930536977","2 min ago", R.drawable.ic_person1,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Girma","0915670645","2 min ago", R.drawable.ic_person2,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Hayle","0930536977","2 min ago", R.drawable.ic_person3,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Iyasu","0915670645","2 min ago", R.drawable.ic_person1,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("John","0930536977","2 min ago", R.drawable.ic_person2,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Kidist","0915670645","2 min ago", R.drawable.ic_person3,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Lemma","0930536977","2 min ago", R.drawable.ic_person1,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Melese","0915670645","2 min ago", R.drawable.ic_person2,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Nardos","0930536977","2 min ago", R.drawable.ic_person3,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Girma","0915670645","2 min ago", R.drawable.ic_person2,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Hayle","0930536977","2 min ago", R.drawable.ic_person3,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Iyasu","0915670645","2 min ago", R.drawable.ic_person1,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("John","0930536977","2 min ago", R.drawable.ic_person2,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Kidist","0915670645","2 min ago", R.drawable.ic_person3,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Lemma","0930536977","2 min ago", R.drawable.ic_person1,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Melese","0915670645","2 min ago", R.drawable.ic_person2,R.drawable.ic_call_missed));
//        favModelList.add(new LogModel("Nardos","0930536977","2 min ago", R.drawable.ic_person3,R.drawable.ic_call_missed));
//

        super.onCreate(savedInstanceState);
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof MyFavouriteAdapter.MyViewHolder){
            String name = favModelList.get(viewHolder.getAdapterPosition()).getName();
            //backup of removed item for undo pupose
            final LogModel deletedItem = favModelList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            MyFavouriteAdapter.removeItem(viewHolder.getAdapterPosition());
            isFavRemoved = true;
            frameLayout = rootView.findViewById(R.id.fav_framelayout);
            Snackbar snackbar = Snackbar.make(frameLayout,name +" is removed from the Favourite",Snackbar.LENGTH_LONG);

            View snackbarView = snackbar.getView();
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(13);
            View sbView = snackbarView;
            sbView.setBackgroundColor(Color.rgb(12,23,54));

            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyFavouriteAdapter.restoreItem(deletedItem,deletedIndex);
                    isFavRemoved = false;
                }
            });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();

        }
    }
    private   List<LogModel> getAllFavContacts(){
        favModelList = new ArrayList<>();

        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.Contacts.STARRED};
        try {
            Cursor cursor = getActivity().managedQuery(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, "starred=?",
                    new String[] {"1"}, null);

            System.out.println((cursor==null) +"is cursor");
            if (cursor== null){
//                Log.e("cursor", "getAllFavContacts: value is"+ cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            }
//            Log.e("cursor", "getAllFavContacts: value is"+cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME) );


            //        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
//                null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");

            int name=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int number=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//        int photo=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO);


            while (cursor.moveToNext()) {

//                Log.e("cursor", "getAllFavContacts:one value is"
//                        +cursor.getString( cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
//                        +cursor.getString( cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
//                );
                String Name = cursor.getString(name);
                if (Name == null) {
                    Name = "No Name";
                }
                String Number = cursor.getString(number);
                if (Number == null) {
                    Number = "No Number";
                }
//            InputStream inputStream = new ByteArrayInputStream(cursor.getBlob(photo));
                int ProfilePhoto = R.drawable.ic_person1;
//            if (PhotoID != 0){
//                ProfilePhoto = PhotoID;
//            }
//        ContentResolver cr = getActivity().getContentResolver();
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//        image_uri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                favModelList.add(new LogModel(Name, Number, "2 min ago",ProfilePhoto, R.drawable.ic_call_missed));
//            favModelList.add(new LogModel("Abeni fadede","0915670645","2 min ago", R.drawable.ic_person2,0));

            }
        }catch (CursorIndexOutOfBoundsException e){
            e.printStackTrace();
        }



        return favModelList;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isFavRemoved){
            //remove contact from favourite
            // use PhoneLookup.CONTENT_FILTER_URI in order to
        }
    }
}
