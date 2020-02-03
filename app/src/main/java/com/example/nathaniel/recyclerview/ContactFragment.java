package com.example.nathaniel.recyclerview;


import android.Manifest;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    private static final int READCONTACTCODE = 1;
    private static final  int MY_PERMISSTIONS_REQUEST_CALL_PHONE=2;
    private List<LogModel> contactModelList;
    Context context;
    FloatingActionButton fab;
    //    private static final String ARG_SECTION_NUMBER = "section_number";
    RecyclerView log_recyclerView;
    MyContactAdapter MyContactAdapter;
    FrameLayout frameLayout;
    ViewPager myViewPager;
    viewPageAdapter myViewPagerAdapter;
    private SearchView searchView;
    TabLayout tabLayout;
    View rootView;
    private String image_uri;


    public ContactFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        getActivity().setTitle("Contacts");

        rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        log_recyclerView = rootView.findViewById(R.id.contact_recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        log_recyclerView.setLayoutManager(layoutManager);
//        contactModelList = getAllContacts();
        MyContactAdapter = new MyContactAdapter(getContext(),contactModelList);
        log_recyclerView.setItemAnimator(new DefaultItemAnimator());
        log_recyclerView.setAdapter(MyContactAdapter);


        final FloatingActionButton fab = rootView.findViewById(R.id.contact_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent contactIntent = new Intent(getActivity(),CreateNewContactActivity.class);
                startActivity(contactIntent);

            }
        });


        log_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });

//
//        ItemTouchHelper.SimpleCallback itemTouchHelperCallback=
//                new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this,1);
//        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(log_recyclerView);

        return rootView;
    }
//

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        contactModelList = new ArrayList<>();
        try {
            contactModelList = getAllContacts();

        }catch (RuntimeException e){
            e.printStackTrace();
        }
//
//        if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
//
//            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS},READCONTACTCODE);
////            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},READCONTACTCODE);
//        }
//
//        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
//                null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
//
//        int name=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
//        int number=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//
//
//        while (cursor.moveToNext()) {
//            String Name = cursor.getString(name);
//            if (Name == null) {
//                Name = "No Name";
//            }
//            String Number = cursor.getString(number);
//            if (Number == null) {
//                Number = "No Number";
//            }
//            contactModelList.add(new LogModel(Name, Number, "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//
//
//        }



//
//
//        contactModelList.add(new LogModel("Abeni Dewele", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Bekele", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Chala", "0915670645", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Deribe", "0930536977", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Eyob", "0915670645", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Fitsum", "0930536977", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Girma", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Hayle", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Iyasu", "0915670645", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("John", "0930536977", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Kidist", "0915670645", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Lemma", "0930536977", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Melese", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Nardos", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Abeni Dewele", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Bekele", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Chala", "0915670645", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Deribe", "0930536977", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Eyob", "0915670645", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Fitsum", "0930536977", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Girma", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Hayle", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Iyasu", "0915670645", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("John", "0930536977", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Kidist", "0915670645", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Lemma", "0930536977", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Melese", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        contactModelList.add(new LogModel("Nardos", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        MenuItem menuItem= menu.add("search");
//        menuItem.setIcon(R.drawable.ic_search);
//        menuItem.collapseActionView();
        setHasOptionsMenu(true);
        inflater.inflate(R.menu.option,menu);
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
//                MyLogAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
//                Toast.makeText(context, "is written"+query, Toast.LENGTH_SHORT).show();
//                MyLogAdapter.getFilter().filter(query);
//                fab.hide();

                String userInput = query.trim();
                if (userInput!= ""){
                    List<LogModel> newList = new ArrayList<>();
                    List<String> newNameList = new ArrayList<>();
                    List<String> NameList = new ArrayList<>();

                    for (LogModel model : contactModelList) {
                        newNameList.add(model.getName());
                    }
                    for (String name : newNameList) {
                        if (name.toLowerCase().contains(userInput)) {
                            NameList.add(name);
                        } else {
                            NameList.add(null);
                        }
                    }
                    MyContactAdapter.updateContactList(NameList);
//                ContactLogs.this.notifyAll();
                }
                else{
                    MyContactAdapter.NoupdateContactList();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_bar) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private   List<LogModel> getAllContacts(){
        contactModelList = new ArrayList<>();

//        if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
//
//            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS},READCONTACTCODE);
////            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},READCONTACTCODE);
//        }
        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");

        int name=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int number=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//        int photo=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO);


        while (cursor.moveToNext()) {
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
            contactModelList.add(new LogModel(Name, Number, "2 min ago",ProfilePhoto, R.drawable.ic_call_missed));

        }
        cursor.close();
        return contactModelList;
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode ==READCONTACTCODE){
//            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//               try {
//                   contactModelList =getAllContacts();
//
//               }catch (RuntimeException e){
//                   e.printStackTrace();
//               }
//            }
//            else {
//                Toast.makeText(getActivity(), "Pemission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//        if (requestCode ==MY_PERMISSTIONS_REQUEST_CALL_PHONE){
//            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                try {
//                    contactModelList = getAllContacts();
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//            else {
//                Toast.makeText(getActivity(), "Pemission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//        if (requestCode == READLOGCODE){
//            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                contactModelList = getAllContacts();
//
//            }
//        }



//    }



}

//    @Override
//    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
//        if (viewHolder instanceof MyLogAdapter.MyLogHolder){
//            String name = contactModelList.get(viewHolder.getAdapterPosition()).getName();
//            //backup of removed item for undo pupose
//            final LogModel deletedItem = contactModelList.get(viewHolder.getAdapterPosition());
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


