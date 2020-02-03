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

public class ShortNumAdapter extends RecyclerView.Adapter<ShortNumAdapter.MyViewHolder>{
        private static final int REQUEST_CALL = 12;
        Context ct;
        List<LogModel> mShortNumData;
        private int position;
        List<LogModel> contactListFiltered;
        List<LogModel> names;

        public ShortNumAdapter(Context ct, List<LogModel> mlogdata) {
            this.ct = ct;
            this.mShortNumData = mlogdata;
            this.names = mShortNumData;
            this.contactListFiltered = mShortNumData;
        }

        //    @NonNull
    @Override
    public ShortNumAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
//        System.out.println(viewPageAdapter.pos+" position is");
        v= LayoutInflater.from(ct).inflate(R.layout.short_num_view,viewGroup,false);
        return new ShortNumAdapter.MyViewHolder(v,ct);
    }

        @Override
        public void onBindViewHolder(@NonNull final com.example.nathaniel.recyclerview.ShortNumAdapter.MyViewHolder viewHolder, final int i) {
//        System.out.println("position is"+ viewPageAdapter.pos);
            final LogModel contactModel = mShortNumData.get(i);

            viewHolder.name.setText(contactModel.getName());
            viewHolder.photo.setImageResource(contactModel.getPhoto());
            viewHolder.phone.setText(mShortNumData.get(i).getNumber());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makePhoneCall(mShortNumData.get(i).getNumber().replace("#","%23"));
                }
            });

        }

        @Override
        public int getItemCount() {
            return mShortNumData.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            ImageView photo;
            ImageView statusPhoto;
            TextView name;
            TextView phone;
            TextView timeStamp;
            ImageView callImageShortNum;
            ArrayList<Integer> imageView;
            ImageView starImage;
            public MyViewHolder(@NonNull View itemView, final Context ct) {
                super(itemView);

                name = itemView.findViewById(R.id.ShortNumName);
                photo = itemView.findViewById(R.id.ShortNumPhoto);
                phone = itemView.findViewById(R.id.ShortNumber);
                callImageShortNum = itemView.findViewById(R.id.callImage_ShortNum);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ct, SecondActivity.class);
                        position = getAdapterPosition();
                        intent.putExtra("imagePosition",position);
                        ShortNumAdapter.this.ct.startActivity(intent);
                    }
                });
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

            mShortNumData = new ArrayList<>();
            for (String name:newList){
                if (name!= null){
                    int i = newList.indexOf(name);
                    if ((i<names.size())){
//                        System.out.println("names"+names.size()+"i is"+i);

                        mShortNumData.add(names.get(i));

//                        System.out.println("mlogdata is inside"+mShortNumData);
                    }
                    else {
                        continue;
                    }}
            }
//            System.out.println("mlogdata is"+mShortNumData);
            notifyDataSetChanged();
        }
        public void NoupdateContactList(){
            mShortNumData = new ArrayList<>();
            mShortNumData = names;
            notifyDataSetChanged();
        }
    }

