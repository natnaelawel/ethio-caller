package com.example.nathaniel.recyclerview;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyContactAdapter extends RecyclerView.Adapter<MyContactAdapter.MyViewHolder>{
    private static final int REQUEST_CALL = 12;
    Context ct;
    List<LogModel> mContactData;
    private int position;
    List<LogModel> contactListFiltered;
    List<LogModel> names;



    public MyContactAdapter(Context ct, List<LogModel> mlogdata) {
        this.ct = ct;
        this.mContactData = mlogdata;
        this.names = mContactData;
        this.contactListFiltered = mContactData;
    }

//    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        System.out.println(viewPageAdapter.pos+" position is");
        v= LayoutInflater.from(ct).inflate(R.layout.contactview,viewGroup,false);
        return new MyViewHolder(v,ct);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int i) {
//        System.out.println("position is"+ viewPageAdapter.pos);
        final LogModel contactModel = mContactData.get(i);

        viewHolder.name.setText(contactModel.getName());
        viewHolder.photo.setImageResource(contactModel.getPhoto());

//        viewHolder.phone.setText(mlogdata.get(i).getNumber());

        viewHolder.callImageContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(mContactData.get(i).getNumber());
//                ContactLogs.logModelList.notify();
                notifyDataSetChanged();
            }
        });
        FavFragment favFragment = new FavFragment();


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewContact(mContactData.get(i).getNumber());
            }
        });

    }


    @Override
    public int getItemCount() {
        return mContactData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        ImageView statusPhoto;
        TextView name;
        TextView phone;
        TextView timeStamp;
        ImageView callImageContact;
        ArrayList<Integer> imageView;
        ImageView starImage;
        public MyViewHolder(@NonNull View itemView, final Context ct) {
            super(itemView);

                name = itemView.findViewById(R.id.textView_contact);
                photo = itemView.findViewById(R.id.imageView_contact);
                callImageContact = itemView.findViewById(R.id.callImage_contact);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(ct, SecondActivity.class);
//                    position = getAdapterPosition();
//                    intent.putExtra("imagePosition","mathematics");
//                    MyContactAdapter.this.ct.startActivity(intent);
//                }
//            });
        }

    }

    private void makePhoneCall(String number){
        if (number.trim().length()>0){
            if (ContextCompat.checkSelfPermission(ct,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions((Activity) ct, new String[]{Manifest.permission.CALL_PHONE,},REQUEST_CALL);
            }
            else {
                String dial = "tel:" + number;
                ct.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else{
            Toast.makeText(ct, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        } }


    public void updateContactList(List<String> newList){

        mContactData = new ArrayList<>();
        for (String name:newList){
            if (name!= null){
                int i = newList.indexOf(name);
                if ((i<names.size())){
                    System.out.println("names"+names.size()+"i is"+i);

                    mContactData.add(names.get(i));

                    System.out.println("mlogdata is inside"+mContactData);
                }
                else {
                    continue;
                }}
        }
        System.out.println("mlogdata is"+mContactData);
        notifyDataSetChanged();
    }
    public void NoupdateContactList(){
        mContactData = new ArrayList<>();
        mContactData = names;
        notifyDataSetChanged();
    }


    public void ViewContact(String number){
        // Creates a new intent for sending to the device's contacts application
//CONTENT_FILTER_URI allow to search contact by phone number
        Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
// This query will return NAME and ID of conatct, associated with phone //number.
        Cursor mcursor =ct.getContentResolver().query(lookupUri,new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID},null, null, null);
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
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, idPhone));
//            startActivity(intent);
//            startActivity(intent,null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ct.startActivity(intent,null);
            }
        }
        else {
            Toast.makeText(ct, "contact not in  the list or Saved",Toast.LENGTH_SHORT).show();

        }
    }

}
