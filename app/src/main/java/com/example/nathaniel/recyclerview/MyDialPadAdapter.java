package com.example.nathaniel.recyclerview;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyDialPadAdapter extends RecyclerView.Adapter<MyDialPadAdapter.MyViewHolder>{
    private static final int REQUEST_CALL = 32;
    Context ct;
    List<LogModel> mdialPaddata;
    private int position;
    String phoneNumber;
    int index;


    public MyDialPadAdapter(Context ct, List<LogModel> mdialPaddata) {
        this.ct = ct;
        this.mdialPaddata = mdialPaddata;

    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        System.out.println(viewPageAdapter.pos+" position is");
        v= LayoutInflater.from(ct).inflate(R.layout.dialpad_view,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int i) {
//        System.out.println("position is"+ viewPageAdapter.pos);
        viewHolder.name.setText(mdialPaddata.get(i).getName());
        viewHolder.photo.setImageResource(mdialPaddata.get(i).getPhoto());
        viewHolder.phone.setText(mdialPaddata.get(i).getNumber());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = mdialPaddata.get(i).getNumber();
                if (number.trim().length()>0){
                    if (ContextCompat.checkSelfPermission(ct,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions((Activity) ct,new String[]{Manifest.permission.CALL_PHONE,},REQUEST_CALL);

                    }
                    else {
                        String dial = "tel:" + number;
                        ct.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    }
                }
                else if( mdialPaddata.get(i).getName()=="Create New Contact"){
                    Intent contactIntent = new Intent(ct,CreateNewContactActivity.class);
                    contactIntent.putExtra("PhoneNumber",phoneNumber);
                    ct.startActivity(contactIntent);
                }
                else{
                    Toast.makeText(ct, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdialPaddata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name;
        TextView phone;
        ArrayList<Integer> imageView;
        ImageView starImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
                        name = itemView.findViewById(R.id.favName);
                        photo = itemView.findViewById(R.id.favPhoto);
                        phone = itemView.findViewById(R.id.favNumber);
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(ct, SecondActivity.class);
//                        position = getAdapterPosition();
//                        intent.putExtra("Name", mdialPaddata.get(position).getName());
//                        intent.putExtra("Number",mdialPaddata.get(position).getNumber());
//                        ct.startActivity(intent);
//                    }
//                });
        }


    }


    public void filterList(ArrayList<LogModel> filteredList, String text) {
        mdialPaddata = filteredList;
        phoneNumber = text;
        notifyDataSetChanged();



    }

}
