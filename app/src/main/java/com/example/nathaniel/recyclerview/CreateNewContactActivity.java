package com.example.nathaniel.recyclerview;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class CreateNewContactActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 121;
    private static final int MY_PERMISSTIONS_REQUEST_READ_PHONE_STORAGE = 1;
    private static final int MY_PERMISSTIONS_REQUEST_CAMERA = 2;
    private static final int MY_PERMISSTIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 3;
    EditText contactFirstName, contactLastName, contactNumber, contactEmail;
    ImageView contactProfileImage;
    ImageView addPhotoCamera;
    ImageView addPhotoGallery;
    Spinner contactNumberSpinner;
    CharSequence writtenPhoneNumber;
    String[] numberType = {"Mobile", "Work", "Work Fax", "Home", "Home Fax", "Pager", "Other"};
    String fullName;
    ActionMode actionMode = null;
    private boolean taken;
    private boolean imgCapFlag;
    private Bitmap bitmap;
    private String path;
    private File destination;
    private String imgPath;


    @Override
    public Resources.Theme getTheme() {
        int themeId =this.getSharedPreferences("mySPreference",0).getInt("ThemeId",R.style.AppTheme);

        Resources.Theme theme = super.getTheme();
        if (true){
            theme.applyStyle(themeId,true);
        }
        return theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        contactFirstName = findViewById(R.id.contactFirstName);
        contactLastName = findViewById(R.id.contactLastName);
        contactNumber = findViewById(R.id.contactNumber);
        contactEmail = findViewById(R.id.contactEmail);
        contactProfileImage = findViewById(R.id.contactProfileImage);
        addPhotoCamera = findViewById(R.id.addPhotoCamera);
        addPhotoGallery = findViewById(R.id.addPhotoGallery);

        String nameofcontact = getIntent().getStringExtra("nameofcontact");
        String numberofcontact = getIntent().getStringExtra("numberofcontact");
        if (nameofcontact != null && numberofcontact != null) {
            contactFirstName.setText(nameofcontact, TextView.BufferType.EDITABLE);
            contactNumber.setText(numberofcontact, TextView.BufferType.EDITABLE);

        } else {
            writtenPhoneNumber = getIntent().getCharSequenceExtra("PhoneNumber");
            contactNumber.setText(writtenPhoneNumber, TextView.BufferType.EDITABLE);
            contactNumberSpinner = findViewById(R.id.contactNumberSpinner);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numberType);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            contactNumberSpinner.setAdapter(spinnerArrayAdapter);
        }
        fullName = contactFirstName.getText().toString() + " " + contactLastName.getText().toString();
        addPhotoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FromCard();
            }
        });
        addPhotoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FromCamera();
            }
        });

        if (actionMode != null) {
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            actionMode = startActionMode(actionModeCallBack);
        }

    }

    private ActionMode.Callback actionModeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.newcontactmenu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }


        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {

                case R.id.cancel:
                    Toast.makeText(CreateNewContactActivity.this, "Operation Canceled", Toast.LENGTH_SHORT).show();
                case R.id.save:
                    createContact();
                    Toast.makeText(CreateNewContactActivity.this, "Operation Saved", Toast.LENGTH_SHORT).show();
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            Intent intent = new Intent(CreateNewContactActivity.this, HomeActivity.class);
            startActivity(intent);

        }
    };

    public void pickProfileImage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            System.out.println("Pemission is not granted");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSTIONS_REQUEST_READ_PHONE_STORAGE);
        }


    }


    public void FromCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            System.out.println("Pemission is not granted");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSTIONS_REQUEST_CAMERA);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            System.out.println("Pemission is not granted");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSTIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, MY_PERMISSTIONS_REQUEST_CAMERA);//zero can be replaced with any action code

    }

    public void FromCard() {
        pickProfileImage();
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, MY_PERMISSTIONS_REQUEST_READ_PHONE_STORAGE);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MY_PERMISSTIONS_REQUEST_READ_PHONE_STORAGE: {
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        bitmap = BitmapFactory.decodeFile(picturePath);
                        BitmapDrawable background = new BitmapDrawable(bitmap);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            File f = new File(picturePath);
                            selectedImage = Uri.fromFile(f);
                            contactProfileImage.setImageURI(selectedImage);
                        }
                    }
                }
                break;
            }
            case MY_PERMISSTIONS_REQUEST_CAMERA: {
                try {
                    Uri selectedImage = data.getData();
                    bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                    Log.e("Activity", "Pick from Camera::>>> ");

                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                    destination = new File(Environment.getExternalStorageDirectory() + "/" +
                            getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                    FileOutputStream fo;
                    try {
                        destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    imgPath = destination.getAbsolutePath();
                    contactProfileImage.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSTIONS_REQUEST_READ_PHONE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                FromCard();
            } else {
                Toast.makeText(this, "Pemission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == MY_PERMISSTIONS_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                FromCamera();
            } else {
                Toast.makeText(this, "Pemission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void createContact(){
        ArrayList<ContentProviderOperation> ops =new ArrayList<>();

        int rawContactID = ops.size();

        // Adding insert operation to operations list
        // to insert a new raw contact in the table ContactsContract.RawContacts
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        // Adding insert operation to operations list
        // to insert display name in the table ContactsContract.Data
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                .withValue(ContactsContract.Data.MIMETYPE, CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,contactFirstName.getText().toString()+" "+contactLastName.getText().toString())
                .build());

        // Adding insert operation to operations list
        // to  insert Home Phone Number in the table ContactsContract.Data
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                .withValue(ContactsContract.Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(CommonDataKinds.Phone.NUMBER, contactNumber.getText().toString())
                .withValue(CommonDataKinds.Phone.TYPE, CommonDataKinds.Phone.TYPE_MOBILE)
                .build());

        // Adding insert operation to operations list
        // to insert Home Email in the table ContactsContract.Data
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                .withValue(ContactsContract.Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(CommonDataKinds.Email.ADDRESS, contactEmail.getText().toString())
                .withValue(CommonDataKinds.Email.TYPE, CommonDataKinds.Email.TYPE_HOME)
                .build());


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if(bitmap!=null){    // If an image is selected successfully
            bitmap.compress(Bitmap.CompressFormat.PNG , 75, stream);

            // Adding insert operation to operations list
            // to insert Photo in the table ContactsContract.Data
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                    .withValue(ContactsContract.Data.IS_SUPER_PRIMARY, 1)
                    .withValue(ContactsContract.Data.MIMETYPE,CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO,stream.toByteArray())
                    .build());

            try {
                stream.flush();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        try{
            // Executing all the insert operations as a single database transaction
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            Toast.makeText(getBaseContext(), "Contact is successfully added", Toast.LENGTH_SHORT).show();
        }catch (RemoteException e) {
            e.printStackTrace();
        }catch (OperationApplicationException e) {
            e.printStackTrace();
        }

    }


//    private long getRawContactId()
//    {
//        // Inser an empty contact.
//        ContentValues contentValues = new ContentValues();
//        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
//        // Get the newly created contact raw id.
//        long ret = ContentUris.parseId(rawContactUri);
//        return ret;
//    }

}