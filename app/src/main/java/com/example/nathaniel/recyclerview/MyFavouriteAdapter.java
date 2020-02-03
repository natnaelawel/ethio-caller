package com.example.nathaniel.recyclerview;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyFavouriteAdapter  extends RecyclerView.Adapter<MyFavouriteAdapter.MyViewHolder> {

    private static final int REQUEST_CALL = 23;
    Context ct;
    List<LogModel> mFavdata;
    private int position;

    public MyFavouriteAdapter(Context ct, List<LogModel> mlogdata, int position) {
        this.ct = ct;
        this.mFavdata = mlogdata;
        this.position = position;
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = null;
        System.out.println(viewPageAdapter.pos+" position is");
        switch (position){

            case 1: v= LayoutInflater.from(ct).inflate(R.layout.fav_view,viewGroup,false);
                break;
            case 2: v= LayoutInflater.from(ct).inflate(R.layout.emergency_view,viewGroup,false);
                break;
            case 3: v= LayoutInflater.from(ct).inflate(R.layout.fav_view,viewGroup,false);

                break;
//            default:
//                v = LayoutInflater.from(ct).inflate(R.layout.blacklist_view,viewGroup,false);
        }
        return new MyViewHolder(v,ct);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int i) {
//        System.out.println("position is"+ viewPageAdapter.pos);
        viewHolder.name.setText(mFavdata.get(i).getName());
        viewHolder.photo.setImageResource(mFavdata.get(i).getPhoto());
        viewHolder.phone.setText(mFavdata.get(i).getNumber());
//
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyContactAdapter myContactAdapter = new MyContactAdapter(ct, null);
                myContactAdapter.ViewContact(mFavdata.get(i).getNumber());
            }
        });

        viewHolder.starImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(viewHolder.phone.getText().toString());

            }
        });

//        viewHolder.starImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                removeItem(i);
//               FrameLayout frameLayout =findViewById(R.id.fav_framelayout);
//                Snackbar snackbar = Snackbar.make(frameLayout,name +" is removed from the list",Snackbar.LENGTH_LONG);
//                snackbar.setAction("UNDO", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        MyFavouriteAdapter.restoreItem(deletedItem,deletedIndex);
//                    }
//                });
//                snackbar.setActionTextColor(Color.RED);
//                snackbar.show();
//            }
//        });
//        viewHolder.starImage.setImageResource(R.drawable.ic_block);

    }

    @Override
    public int getItemCount() {
        return mFavdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name;
        TextView phone;
        ArrayList<Integer> imageView;
        ImageView starImage;
        RelativeLayout viewBackground,viewForeground;


        public MyViewHolder(@NonNull View itemView, final Context ct) {
            super(itemView);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            switch (position){
                case 1: name = itemView.findViewById(R.id.favName);
                    photo = itemView.findViewById(R.id.favPhoto);
                    phone = itemView.findViewById(R.id.favNumber);
                    starImage = itemView.findViewById(R.id.starImage_fav);

                    break;
                case 2: name = itemView.findViewById(R.id.textView_emergency);
                    photo = itemView.findViewById(R.id.imageView_emergency);
                    phone = itemView.findViewById(R.id.phone_emergency);
                    starImage = itemView.findViewById(R.id.starImage_emergency);

                    break;
                case 3: name = itemView.findViewById(R.id.favName);
                    photo = itemView.findViewById(R.id.favPhoto);
                    phone = itemView.findViewById(R.id.favNumber);
                    starImage = itemView.findViewById(R.id.starImage_fav);
                    starImage.setImageResource(R.drawable.ic_x_button_remove);
                    break;
//                default:name = itemView.findViewById(R.id.name_blacklist);
//                    photo = itemView.findViewById(R.id.photo_blacklist);
//                    phone = itemView.findViewById(R.id.phone_blacklist);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ct, SecondActivity.class);
                    position = getAdapterPosition();
                    intent.putExtra("Name", mFavdata.get(position).getName());
                    intent.putExtra("Number",mFavdata.get(position).getNumber());
                    ct.startActivity(intent);
                }
            });
        }
    }

    public void removeItem(int position){
        mFavdata.remove(position);
        SecondActivity secondActivity = new SecondActivity();
//        try {
//            secondActivity.saveBlacklistData(mFavdata);
//
//        }catch (IllegalArgumentException e){
//            e.printStackTrace();
//        }
        notifyItemRemoved(position);
    }
    public void  restoreItem(LogModel mdata,int position){
        mFavdata.add(position,mdata);
        SecondActivity secondActivity = new SecondActivity();
//        try {
//            secondActivity.saveBlacklistData(mFavdata);
//
//        }catch (IllegalArgumentException e){
//            e.printStackTrace();
//        }
        notifyItemInserted(position);
    }

    private void makePhoneCall(String number){
        if (number.trim().length()>0){
            if (ContextCompat.checkSelfPermission(ct, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
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
}
