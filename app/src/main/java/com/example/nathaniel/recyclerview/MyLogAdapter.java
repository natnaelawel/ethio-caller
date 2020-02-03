package com.example.nathaniel.recyclerview;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.support.v4.content.ContextCompat.startActivity;


public class MyLogAdapter extends RecyclerView.Adapter<MyLogAdapter.MyLogHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {
    public Context ct;
    List<LogModel> mlogdatalist;
    private int position;
    Dialog myDialog;
    MyLogHolder viewHolder;
    MyLogAdapter myLogAdapter;
    String[] sections;
    List<String> sectionLetters = new ArrayList<String>();
    private static final int REQUEST_CALL = 1212;
    String nameOfContact;
    Activity activity;
    List<LogModel> contactListFiltered;
    List<LogModel> names;
    static String numberOfContact;
//    private List<LogModel> blackListModelList = new ArrayList<>();
//    static List<String> blockedList;
//    private ContactsAdapterListener listener;


    public MyLogAdapter(Context ct, List<LogModel> mlogdatalist, Activity activity) {
        this.ct = ct;
        this.mlogdatalist = mlogdatalist;
        this.activity = activity;
        this.names = mlogdatalist;
        this.contactListFiltered = mlogdatalist;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

    }

    @Override
    public MyLogHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(ct).inflate(R.layout.log_view, viewGroup, false);
//        v.setTag(viewHolder.getAdapterPosition());
        viewHolder = new MyLogHolder(v, ct, contactListFiltered);
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ct,SecondActivity.class);
//                position = viewHolder.getAdapterPosition();
//                intent.putExtra("imagePosition","mathematics");
//                ct.startActivity(intent);
//          }
//        });
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyLogHolder viewHolder, final int i) {
//        System.out.println("position is"+ viewPageAdapter.pos);
        final LogModel logmodel = mlogdatalist.get(i);

        viewHolder.name.setText(logmodel.getName());
        viewHolder.photo.setImageResource(logmodel.getPhoto());
        viewHolder.phone.setText(logmodel.getNumber());
        viewHolder.timeStamp.setText(logmodel.getTimeStamp());
        viewHolder.statusPhoto.setImageResource(logmodel.getStatusPhoto());

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                myDialog = new Dialog(ct);
                myDialog.setContentView(R.layout.log_dialog);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ImageView contactPhoto = myDialog.findViewById(R.id.contact_photo);
                LinearLayout callLayout = myDialog.findViewById(R.id.callLayout);
                LinearLayout smsLayout = myDialog.findViewById(R.id.smsLayout);
                LinearLayout blockLayout = myDialog.findViewById(R.id.blockLayout);
                LinearLayout editLayout = myDialog.findViewById(R.id.editLayout);
                Button callmebtn = myDialog.findViewById(R.id.requestcallmebtn);
                final TextView contactName = myDialog.findViewById(R.id.contact_name);
                TextView contactNumber = myDialog.findViewById(R.id.contact_number);
                nameOfContact = mlogdatalist.get(i).getName();
                numberOfContact = mlogdatalist.get(i).getNumber();
                contactPhoto.setImageResource(mlogdatalist.get(i).getPhoto());
                contactName.setText(nameOfContact);
                contactNumber.setText(numberOfContact);

                callLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makePhoneCall(numberOfContact);
                        Toast.makeText(ct, "you are calling to " + nameOfContact, Toast.LENGTH_SHORT).show();
                        myDialog.dismiss();
                    }
                });
                smsLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                        smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
//                        smsIntent.setType("vnd.android-dir/mms-sms");
//                        smsIntent.setData(Uri.parse("sms:" + numberOfContact));
                        smsIntent.setType("vnd.android-dir/mms-sms");
                        smsIntent.setData(Uri.parse("sms:" + numberOfContact));
                        startActivity(ct,smsIntent,null);

                        Toast.makeText(ct, "you are going to send Message to " + nameOfContact, Toast.LENGTH_SHORT).show();
                        myDialog.dismiss();
                    }
                });
                blockLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent blacklisted = new Intent(ct, SecondActivity.class);
                        blacklisted.putExtra("itemName",1);
                        blacklisted.putExtra("PhoneNumber",numberOfContact);
                        blacklisted.putExtra("PhoneName",nameOfContact);
//                        SecondActivity secondActivity =new SecondActivity();
//                        secondActivity.saveBlacklistData();
                        ct.startActivity(blacklisted);




                        myDialog.dismiss();
                    }
                });
                editLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent editIntent =new Intent(ct,CreateNewContactActivity.class);
                        editIntent.putExtra("nameofcontact",nameOfContact);
                        editIntent.putExtra("numberofcontact",numberOfContact);

                        EditContact(numberOfContact);
//                        startActivity(ct,editIntent,null);
//                        Toast.makeText(ct, "you are going to edit " + nameOfContact, Toast.LENGTH_SHORT).show();
                        myDialog.dismiss();
                    }
                });
                callmebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ct, "your call me back request \n has been sent to" + nameOfContact, Toast.LENGTH_SHORT).show();
                    }
                });


                myDialog.show();
                myDialog.setCancelable(true);

//                Toast.makeText(ct, "the position ins"+i, Toast.LENGTH_SHORT).show();


                for (int x = 0; x < mlogdatalist.size(); x++) {
                    String fruit = mlogdatalist.get(x).getName();
                    String ch = fruit.charAt(0) + "";
                    ch = ch.toUpperCase(Locale.US);

                    sectionLetters.add(ch);
                }
                ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);

                sections = new String[sectionList.size()];

                sectionList.toArray(sections);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlogdatalist.size();
    }

//
//    public void removeItem(int position){
//        mlogdatalist.remove(position);
//        notifyItemRemoved(position);
//    }
//    public void  restoreItem(LogModel mdata,int position){
//        mlogdatalist.add(position,mdata);
//        notifyItemInserted(position);
//    }

//
//    @Override
//    public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints) {
//
//    }


//    @Override
//    public Object[] getSections() {
//        return new Object[0];
//    }
//
//    @Override
//    public int getPositionForSection(int sectionIndex) {
//        return 0;
//    }
//
//    @Override
//    public int getSectionForPosition(int position) {
//        return 0;
//    }


    public class MyLogHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        ImageView statusPhoto;
        TextView name;
        TextView phone;
        TextView timeStamp;
        ArrayList<Integer> imageView;
        ImageView starImage;
        RelativeLayout viewBackground,viewForeground;
        public MyLogHolder(@NonNull View itemView, final Context ct,List<LogModel> logModelList) {
            super(itemView);
            name = itemView.findViewById(R.id.log_name);
            photo = itemView.findViewById(R.id.log_photo);
            phone = itemView.findViewById(R.id.log_number);
            timeStamp = itemView.findViewById(R.id.log_timeStamp);
            statusPhoto = itemView.findViewById(R.id.log_statusPhoto);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);


        }
//
    }
    private void makePhoneCall(String number){
        if (number.trim().length()>0){
            if (ContextCompat.checkSelfPermission(ct,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions( activity, new String[]{Manifest.permission.CALL_PHONE,},REQUEST_CALL);
//                new ActivityCompat.OnRequestPermissionsResultCallback() {
//                    @Override
//                    public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints) {
//                        if (i ==REQUEST_CALL){
//                            if (ints.length>0 && ints[0] == PackageManager.PERMISSION_GRANTED){
//                                makePhoneCall(nameOfContact);
//                            }
//                            else {
//                                Toast.makeText(ct.getApplicationContext(), "Pemission Denied", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                };
            }
            else {
                String dial = "tel:" + number;
                ct.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else{
            Toast.makeText(ct, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        } }
    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints) {
        makePhoneCall(nameOfContact);
    }
//    public interface ContactsAdapterListener {
//        void onContactSelected(LogModel contact);
//    }

    public void updateList(List<String> newList){

        mlogdatalist = new ArrayList<>();
        for (String name:newList){
            if (name!= null){
                int i = newList.indexOf(name);
                if ((i<names.size())){
                    System.out.println("names"+names.size()+"i is"+i);
                    mlogdatalist.add(names.get(i));
                    System.out.println("mlogdata is inside"+mlogdatalist);
                }
                else {
                    continue;
                }}
        }
        System.out.println("mlogdata is"+mlogdatalist);
        notifyDataSetChanged();
    }
    public void NoupdateList(){
        mlogdatalist = new ArrayList<>();
        mlogdatalist = names;
    }

    private void EditContact(String number){
        // Creates a new intent for sending to the device's contacts application
//CONTENT_FILTER_URI allow to search contact by phone number
        Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
// This query will return NAME and ID of conatct, associated with phone //number.
        Cursor mcursor =this.activity.getContentResolver().query(lookupUri,new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID},null, null, null);
//Now retrive _ID from query result
        long idPhone = 0;
        try {
            if (mcursor != null) {
                if (mcursor.moveToFirst()) {
                    idPhone = Long.valueOf(mcursor.getString(mcursor.getColumnIndex(ContactsContract.PhoneLookup._ID)));
                    Log.d("", "Contact id::" + idPhone);
                }
            }
            else {
                Toast.makeText(ct, "contact not in  the list or Saved",Toast.LENGTH_SHORT).show();

            }
        } finally {
            mcursor.close();
        }

        if (idPhone > 0){
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setData(ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, idPhone));
//            startActivity(intent);
            startActivity(ct,intent,null);
        }
        else {
            Toast.makeText(ct, "contact not in  the list or Saved",Toast.LENGTH_SHORT).show();

    }
    }


}

