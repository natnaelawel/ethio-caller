package com.example.nathaniel.recyclerview;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceRechargeFragment extends Fragment {
    private static final int MY_PERMISSTIONS_REQUEST_CAMERA = 1;
    private static final int MY_PERMISSTIONS_REQUEST_READ_PHONE_STORAGE=2;
    View rootView;
    TabLayout tabLayout;
    ImageView RechargePhotoDisplay;
    CardView myviaNumberLayout,myviaCameraLayout;
    private Uri image_uri;
    Button ByCamera,ByGallery;

    public BalanceRechargeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        getActivity().setTitle("Balance Recharge");

        rootView =  inflater.inflate(R.layout.fragment_balance_recharge, container, false);
        tabLayout = rootView.findViewById(R.id.rechargeBytab);
        myviaCameraLayout =rootView.findViewById(R.id.viaCamera);
        myviaNumberLayout =rootView.findViewById(R.id.viaNumber);
        RechargePhotoDisplay = rootView.findViewById(R.id.DisplayRechargeImage);
        ByCamera = rootView.findViewById(R.id.rechargebyCamera);
        ByGallery = rootView.findViewById(R.id.rechargebyGallery);
        ByCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FromCamera();
            }
        });
        ByGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FromCard();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                    {
//                        Toast.makeText(getActivity(), "first", Toast.LENGTH_SHORT).show();
                        myviaCameraLayout.setVisibility(View.GONE);
                        myviaNumberLayout.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 1:
                    {
//                        Toast.makeText(getActivity(), "second", Toast.LENGTH_SHORT).show();
                        myviaNumberLayout.setVisibility(View.GONE);
                        myviaCameraLayout.setVisibility(View.VISIBLE);
                        break;
                    }
                    default:
                    {
//                        Toast.makeText(getActivity(), "no", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        Toast.makeText(getActivity(), "This is Recharge", Toast.LENGTH_SHORT).show();

        return rootView;
    }



    public void FromCamera() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
////            System.out.println("Pemission is not granted");
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSTIONS_REQUEST_CAMERA);
//        }
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
////            System.out.println("Pemission is not granted");
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSTIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
//        }

        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            try {
                File file = new File(getActivity().getCacheDir(),String.valueOf(System.currentTimeMillis())+".jpg");
                image_uri = Uri.fromFile(file);
//                image_uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
//                takePicture.putExtra(MediaStore.ACTION_IMAGE_CAPTURE,image_uri);
//                takePicture.putExtra("return-data",true);
            }catch (ActivityNotFoundException e){
                e.printStackTrace();
            }
            startActivityForResult(takePicture, MY_PERMISSTIONS_REQUEST_CAMERA);//zero can be replaced with any action code

        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
//            try {
//                File file = new File(getActivity().getExternalCacheDir(),String.valueOf(System.currentTimeMillis())+".jpg");
//                image_uri = Uri.fromFile(file);
////                image_uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
//                takePicture.putExtra(MediaStore.ACTION_IMAGE_CAPTURE,image_uri);
//                takePicture.putExtra("return-data",true);
//            }catch (ActivityNotFoundException e){
//                e.printStackTrace();
//            }
            startActivityForResult(takePicture, MY_PERMISSTIONS_REQUEST_CAMERA);//zero can be replaced with any action code

        }
        else  if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
//
//            ContentValues values = new ContentValues();
//            values.put(MediaStore.Images.Media.TITLE,"New Pic");
//            values.put(MediaStore.Images.Media.DESCRIPTION,"Image to Text");
//            image_uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
//            takePicture.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
            startActivityForResult(takePicture, MY_PERMISSTIONS_REQUEST_CAMERA);//zero can be replaced with any action code

        }

      else  if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
//
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE,"New Pic");
            values.put(MediaStore.Images.Media.DESCRIPTION,"Image to Text");
            image_uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
            startActivityForResult(takePicture, MY_PERMISSTIONS_REQUEST_CAMERA);//zero can be replaced with any action code

        }


    }

    public void FromCard() {
//        pickProfileImage();
//        Intent i = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");

        startActivityForResult(i, MY_PERMISSTIONS_REQUEST_READ_PHONE_STORAGE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Toast.makeText(getActivity(), "is"+ (image_uri==null), Toast.LENGTH_LONG).show();
        if (resultCode == RESULT_OK) {
            if (requestCode==MY_PERMISSTIONS_REQUEST_READ_PHONE_STORAGE) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    CropImage.activity(selectedImage)
                            .start(getContext(), this);
                }
            }

            if (requestCode==MY_PERMISSTIONS_REQUEST_CAMERA) {
                try {
                Uri selectedImage = data.getData();
                if (image_uri!= null){
                    CropImage.activity(image_uri)
                            .start(getContext(), this);
                }
                else {
                    CropImage.activity(selectedImage)
                            .start(getContext(), this);
                }
            }
            catch (IllegalArgumentException e){
                    e.printStackTrace();
            }}}

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                RechargePhotoDisplay.setImageURI(resultUri);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) RechargePhotoDisplay.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                TextRecognizer textRecognizer = new TextRecognizer.Builder(getContext()).build();
                if (!textRecognizer.isOperational()){
                    Toast.makeText(getActivity(), "Error on TextRecognizer", Toast.LENGTH_SHORT).show();
                }
                else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = textRecognizer.detect(frame);
                    StringBuilder stringBuilder = new StringBuilder();

                    for (int i= 0; i<items.size(); i++){
                        TextBlock myItem = items.valueAt(i);
                        stringBuilder.append(myItem.getValue());
                        stringBuilder.append("\n");
                    }
                    Toast.makeText(getActivity(), "the text is "+ stringBuilder.toString(), Toast.LENGTH_LONG).show();
                    makeTransfer(stringBuilder.toString());
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
//                Toast.makeText(getActivity(), "the text is "+ error, Toast.LENGTH_SHORT).show();

            }
        }

    }

    private void makeTransfer(String Number){
        if (Number.trim().length()>0){
            String dial = "tel:*805*"+Number+"%23";
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
        else{
            Toast.makeText(getActivity(), "No Detected Number", Toast.LENGTH_SHORT).show();
        }
    }


}