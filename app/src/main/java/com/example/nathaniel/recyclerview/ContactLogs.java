package com.example.nathaniel.recyclerview;


import android.Manifest;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactLogs extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private static final int WRITELOGCODE = 4;
//    private static final int READCONTACTCODE = ;
    public static List<LogModel> logModelList;
    Context context;
    Dialog myDialog;
    private static final int READLOGCODE = 3;
    private static final int READCONTACTCODE = 1;
    FloatingActionButton fab;
    //    private static final String ARG_SECTION_NUMBER = "section_number";
    RecyclerView log_recyclerView;
    MyLogAdapter MyLogAdapter;
    FrameLayout frameLayout;
    ViewPager myViewPager;
    viewPageAdapter myViewPagerAdapter;
    TabLayout tabLayout;
    View rootView;
    List<String> sectionLetters = new ArrayList<>();
    String[] sections;
    String phoneNumber;
    private static final  int MY_PERMISSTIONS_REQUEST_CALL_PHONE=2;
    private SearchView searchView;

    public ContactLogs() {
        // Required empty public constructor
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        getLayoutInflater().inflate(R.menu.activity_main_drawer);
//        menu.setHeaderTitle("Select The Action");
//
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        menu.setHeaderTitle("select Action");
        menu.setHeaderIcon(R.drawable.ic_menu_share);
    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.contact_addBlacklist) {
//            Toast.makeText(getContext(), "calling code", Toast.LENGTH_LONG).show();
//        } else if (item.getItemId() == R.id.requestcallme) {
//            Toast.makeText(getContext(), "sending sms code", Toast.LENGTH_LONG).show();
//        } else {
//            return false;
//        }
//        return true;
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        getActivity().setTitle("Logs");

        rootView = inflater.inflate(R.layout.fragment_contact_logs, container, false);
        createView();

        return rootView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        logModelList = new ArrayList<>();
        try {
            logModelList = getAllLogs();
        }catch (RuntimeException e){
            System.out.println("Exception is "+e);
            e.printStackTrace();
        }

//        if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED){
//
//            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CALL_LOG},READLOGCODE);
////            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},READCONTACTCODE);
//        }
//        Cursor cursor = getActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI,null,
//                null,null,CallLog.Calls.DATE+" DESC");
//
//        int nameIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
//        int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);
//        int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
//        int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
//        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
//
//        Calendar calendar =Calendar.getInstance();
//        Date currentTime = calendar.getTime();
//        while (cursor.moveToNext()){
//
//            String call_Date = cursor.getString(dateIndex);
//            Date callDayTime = new Date(Long.valueOf(call_Date));
//            String agotime = (String) DateUtils.getRelativeTimeSpanString(callDayTime.getTime(),calendar.getTimeInMillis(),DateUtils.MINUTE_IN_MILLIS);
//            String Name = cursor.getString(nameIndex);
//            if (Name == null ){
//                Name = "Unknown";
//            }
//            String Number = cursor.getString(numberIndex);
//            if (Number == null ){
//                Number = "No Number";
//            }
//
//            String call_Type = cursor.getString(type);
//            String callType = null;
//            int statusPhoto = R.drawable.ic_call_missed;
//            int CallTypeCode = Integer.parseInt(call_Type);
//            switch (CallTypeCode){
//                case CallLog.Calls.OUTGOING_TYPE:{
//                    statusPhoto = R.drawable.ic_call_made;
//                    callType ="OUTGOING";
//                    break;
//                }
//                case CallLog.Calls.INCOMING_TYPE:{
//                    statusPhoto = R.drawable.ic_call_received;
//                    callType ="INCOMING";
//                    break;
//                }
//                case CallLog.Calls.MISSED_TYPE:{
//                    statusPhoto = R.drawable.ic_call_missed;
//                    callType ="MISSED";
//                    break;
//                }
//            }
//            logModelList.add(new LogModel(Name, Number,agotime, R.drawable.ic_person1, statusPhoto));
//        }
//        logModelList.add(new LogModel("Abeni Dewele", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Bekele", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Chala", "0915670645", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Deribe", "0930536977", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Eyob", "0915670645", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Fitsum", "0930536977", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Girma", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Hayle", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Iyasu", "0915670645", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("John", "0930536977", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Kidist", "0915670645", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Lemma", "0930536977", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Melese", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Nardos", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Abeni Dewele", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Bekele", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Chala", "0915670645", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Deribe", "0930536977", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Eyob", "0915670645", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Fitsum", "0930536977", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Girma", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Hayle", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Iyasu", "0915670645", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("John", "0930536977", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Kidist", "0915670645", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Lemma", "0930536977", "2 min ago", R.drawable.ic_person3, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Melese", "0915670645", "2 min ago", R.drawable.ic_person1, R.drawable.ic_call_missed));
//        logModelList.add(new LogModel("Nardos", "0930536977", "2 min ago", R.drawable.ic_person2, R.drawable.ic_call_missed));
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof MyLogAdapter.MyLogHolder) {
//            viewHolder.getItemId();
//            Toast.makeText(context, "item position is"+ position, Toast.LENGTH_SHORT).show();

            String name = logModelList.get(viewHolder.getAdapterPosition()).getName();
            phoneNumber = logModelList.get(viewHolder.getAdapterPosition()).getNumber();
//            for (int i= 0; i<logModelList.size();i++){
//                if (logModelList.get(viewHolder.getAdapterPosition()).getName().equals()||logModelList.get(viewHolder.getAdapterPosition()).getNumber(),)
//            }
//            final LogModel deletedItem = logModelList.get(viewHolder.getAdapterPosition());
//            final int deletedIndex = viewHolder.getAdapterPosition();
            switch (direction) {
                case 8: {
                    makePhoneCall(phoneNumber);
                    break;
                }
                case 4: {
//                    Toast.makeText(getActivity(), "Your are Messaging to Mr "+name, Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
//                    Toast.makeText(getActivity(), "nothing", Toast.LENGTH_SHORT).show();

            }
//            MyLogAdapter.notifyItemChanged(position);
            MyLogAdapter.notifyDataSetChanged();
            //backup of removed item for undo pupose
//            Toast.makeText(getActivity(),"clicked",Toast.LENGTH_SHORT).show();
//            final LogModel deletedItem = logModelList.get(viewHolder.getAdapterPosition());
//            final int deletedIndex = viewHolder.getAdapterPosition();
//            MyLogAdapter.removeItem(viewHolder.getAdapterPosition());
//            frameLayout = rootView.findViewById(R.id.framelayout);
//            Snackbar snackbar = Snackbar.make(frameLayout,name +" is removed from the list"+direction,Snackbar.LENGTH_LONG);
//            snackbar.setAction("UNDO", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    MyLogAdapter.restoreItem(deletedItem,deletedIndex);
//                }
//            });
//            snackbar.setActionTextColor(Color.RED);
//            snackbar.show();
        }
    }
//
//    @Override
//    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//        myDialog = new Dialog(context);
//        myDialog.setContentView(R.layout.log_dialog);
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        Toast.makeText(context, parent.getSelectedItem()+"Is the View", Toast.LENGTH_SHORT).show();
//
//        return true;
//    }

//    private void checkForPhonePermission(String dial,String name){
//        if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
//            System.out.println("Pemission is not granted");
//            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},MY_PERMISSTIONS_REQUEST_CALL_PHONE);
//        }
//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Toast.makeText(getActivity(), "Your are Calling to Mr " + name+"with this "+dial, Toast.LENGTH_SHORT).show();
//        callIntent.setData(Uri.parse(dial));
//        getActivity().startActivity(callIntent);
//
//    }


    private void makePhoneCall(String number){
        if (number.trim().length()>0){
//            if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE,},MY_PERMISSTIONS_REQUEST_CALL_PHONE);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CALL_LOG,},MY_PERMISSTIONS_REQUEST_CALL_PHONE);
//                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_CALL_LOG,},WRITELOGCODE);
//                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS,},READCONTACTCODE);
//                }
//            }
//            else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
//            }
        }
        else{
            Toast.makeText(getActivity(), "No Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode ==1){
//            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                createView();
//            }
//            else {
//                Toast.makeText(getActivity(), "Pemission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//        if (requestCode == 1){
//            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                getAllLogs();
////                getCallLogs();
//            }
//        }
//        if (;){
//            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                makePhoneCall(phoneNumber);
//            }
//            else {
//                Toast.makeText(getActivity(), "Pemission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//       MenuItem menuItem= menu.add("search");
        //        getMenuInflater().inflate(R.menu.option,menu);
        inflater.inflate(R.menu.option,menu);
//        menuItem.setIcon(R.drawable.ic_search);
//        menuItem.collapseActionView();
//        super.onCreateOptionsMenu(menu, inflater);
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_bar)
                .getActionView();
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

                    for (LogModel model : logModelList) {
                        newNameList.add(model.getName());
                    }
                    for (String name : newNameList) {
                        if (name.toLowerCase().contains(userInput)) {
                            NameList.add(name);
                        } else {
                            NameList.add(null);
                        }
                    }
                    MyLogAdapter.updateList(NameList);
                    MyLogAdapter.notifyDataSetChanged();
//                ContactLogs.this.notifyAll();
                }
                else{
                    MyLogAdapter.NoupdateList();
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
        else if (id == R.id.exit){
            getActivity().finish();
        }
        if (id == R.id.action_settings){
            Toast.makeText(context, "setting will be done next time", Toast.LENGTH_SHORT).show();
//            Intent settingIntent = new Intent(getContext(),SettingsPrefActivity.class);
//            startActivity(settingIntent);
        }

        return super.onOptionsItemSelected(item);
    }


//    private List<LogModel> getCallLogs(){
//        List<LogModel> logModelList  = new ArrayList<>();
//        if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED){
//
//            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CALL_LOG},READLOGCODE);
////            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},READCONTACTCODE);
//        }
//
//        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,null,
//                null,null,CallLog.Calls.DATE+ " ASC");
//
//        int nameIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
//        int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);
//        int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
//        int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
//
//        while (cursor.moveToNext()){
//            logModelList.add(new LogModel(cursor.getString(nameIndex), cursor.getString(numberIndex), cursor.getString(dateIndex), R.drawable.ic_person1, R.drawable.ic_call_missed));
//
//        }
//        return logModelList;
//    }

    private List<LogModel> getAllLogs(){
        logModelList = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CALL_LOG},READLOGCODE);
            }

        }

        Cursor cursor = getActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI,null,
                null,null,CallLog.Calls.DATE+" DESC");

        int nameIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);
        int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
        int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);

        Calendar calendar =Calendar.getInstance();
//        Date currentTime = calendar.getTime();
        while (cursor.moveToNext()){

            String call_Date = cursor.getString(dateIndex);
            Date callDayTime = new Date(Long.valueOf(call_Date));
            String agotime = (String) DateUtils.getRelativeTimeSpanString(callDayTime.getTime(),calendar.getTimeInMillis(),DateUtils.MINUTE_IN_MILLIS);
            String Name = cursor.getString(nameIndex);
            if (Name == null ){
                Name = "Unknown";
            }
            String Number = cursor.getString(numberIndex);
            if (Number == null ){
                Number = "No Number";
            }

            String call_Type = cursor.getString(type);
            String callType = null;
            int statusPhoto = R.drawable.ic_call_missed;
            int CallTypeCode = Integer.parseInt(call_Type);
            switch (CallTypeCode){
                case CallLog.Calls.OUTGOING_TYPE:{
                    statusPhoto = R.drawable.ic_call_made;
                    callType ="OUTGOING";
                    break;
                }
                case CallLog.Calls.INCOMING_TYPE:{
                    statusPhoto = R.drawable.ic_call_received;
                    callType ="INCOMING";
                    break;
                }
                case CallLog.Calls.MISSED_TYPE:{
                    statusPhoto = R.drawable.ic_call_missed;
                    callType ="MISSED";
                    break;
                }
            }
            logModelList.add(new LogModel(Name, Number,agotime, R.drawable.ic_person1, statusPhoto));
        }
        cursor.close();
        return logModelList;
    }
    private void createView(){
        log_recyclerView = rootView.findViewById(R.id.log_recyclerView);
//        log_recyclerView.setNestedScrollingEnabled(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        log_recyclerView.setLayoutManager(layoutManager);
        MyLogAdapter = new MyLogAdapter(getContext(), logModelList,getActivity());
        log_recyclerView.setItemAnimator(new DefaultItemAnimator());
        log_recyclerView.setAdapter(MyLogAdapter);
        MyLogAdapter.notifyDataSetChanged();

        final FloatingActionButton fab = rootView.findViewById(R.id.dial_fab);
//        FloatingActionButton.Behavior b = fab.getBehaviour();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent dialintent = new Intent(getContext(), DialPadActivity.class);
                startActivity(dialintent);
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
//        log_recyclerView.setOnScrollListener(scrollListener);
//        log_recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
//            @Override
//            public boolean onFling(int i, int i1) {
//                Toast.makeText(context, "fling worked", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
        registerForContextMenu(log_recyclerView);

        /////////////////////////////


//
//        for (int x = 0; x < logModelList.size(); x++) {
//            String fruit = logModelList.get(x).getName();
//            String ch = fruit.charAt(0)+"";
//            ch = ch.toUpperCase(Locale.US);
//
//            sectionLetters.add(ch);
//        }
//        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
//
//        sections = new String[sectionList.size()];
//
//        sectionList.toArray(sections);


        ///////////////////////////////////////
//
//        RecyclerView rv= rootView.findViewById(R.id.log_recyclerView);
//        MyLogAdapter = new MyLogAdapter(getContext(),logModelList);
//        rv.setItemAnimator(new DefaultItemAnimator());
//        System.out.println(logModelList.get(2).getName()+ "is all the data");
//        rv.setLayoutManager(new LinearLayoutManager(getContext()));
////        rv.setAdapter(recyclerAdapter(this, SpaceshipsCollection.getSpaceships()));
//        rv.setAdapter(MyLogAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(log_recyclerView);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback2 =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback2).attachToRecyclerView(log_recyclerView);

        /////////////////////


    }

}

