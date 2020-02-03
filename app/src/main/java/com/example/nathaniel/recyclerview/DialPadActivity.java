package com.example.nathaniel.recyclerview;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ArrowKeyMovementMethod;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DialPadActivity extends AppCompatActivity implements View.OnClickListener{
    EditText numberDisplay;
    ImageView addContact;
    RecyclerView dialpad_recyclerView;
    MyDialPadAdapter myDialPadAdapter;
    List<LogModel> mdialPaddata;

    private static final int REQUEST_CALL =12;
    Context ct;
    LinearLayout key0,key1,key2,key3,key4,key5,key6,key7,key8,key9,keyStar
            ,keyHash,keyVoicemail,keyCall,keyBackspace,dialPadLayout;

    @Override
    public Resources.Theme getTheme() {
        int themeId =this.getSharedPreferences("mySPreference",0).getInt("ThemeId",R.style.AppTheme);

        Resources.Theme theme = super.getTheme();
        if (true){
            theme.applyStyle(themeId,true);
        }
        return theme;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setSupportActionBar();

        mdialPaddata = new ArrayList<>();
        try {
//            ContactFragment contactFragment = new ContactFragment();
            mdialPaddata =  getAllContacts();
            Log.i("the data is", "onCreate: "+mdialPaddata);

        }catch (RuntimeException e){
            e.printStackTrace();
        }
        setContentView(R.layout.activity_dial_pad);
        Toolbar toolbar = findViewById(R.id.Dialtoolbar);
        toolbar.setTitle("Dial");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//
//        mdialPaddata= new ArrayList<>();
//        mdialPaddata.add(new LogModel("Abeni","0941586785",null, R.drawable.ic_person1,R.drawable.ic_call_missed));
//        mdialPaddata.add(new LogModel("Belete","0986957412",null, R.drawable.ic_person2,R.drawable.ic_call_missed));
//        mdialPaddata.add(new LogModel("Belele","0925986745",null, R.drawable.ic_person3,R.drawable.ic_call_missed));
//        mdialPaddata.add(new LogModel("Caala","0912586749",null, R.drawable.ic_person1,R.drawable.ic_call_missed));
//        mdialPaddata.add(new LogModel("Dagim","0996368574",null, R.drawable.ic_person2,R.drawable.ic_call_missed));
//        mdialPaddata.add(new LogModel("Dawit","0925781568",null, R.drawable.ic_person3,R.drawable.ic_call_missed));
//        mdialPaddata.add(new LogModel("Ermiyas","0936985679",null, R.drawable.ic_person1,R.drawable.ic_call_missed));
//        mdialPaddata.add(new LogModel("Frew","0925856545",null, R.drawable.ic_person2,R.drawable.ic_call_missed));
//        mdialPaddata.add(new LogModel("Girma","0948759681",null, R.drawable.ic_person3,R.drawable.ic_call_missed));
//        mdialPaddata.add(new LogModel("Haylu","0900528545",null, R.drawable.ic_person1,R.drawable.ic_call_missed));
//        mdialPaddata.add(new LogModel("Jerry","0963287906",null, R.drawable.ic_person2,R.drawable.ic_call_missed));
//        mdialPaddata.add(new LogModel("Kidist","0935967418",null, R.drawable.ic_person3,R.drawable.ic_call_missed));
//        mdialPaddata.add(new LogModel("Lamrot","0973256847",null, R.drawable.ic_person1,R.drawable.ic_call_missed));
//        mdialPaddata.add(new LogModel("Mariot","0935479532",null, R.drawable.ic_person2,R.drawable.ic_call_missed));


        dialpad_recyclerView = findViewById(R.id.dial_pad_recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        dialpad_recyclerView.setLayoutManager(layoutManager);
        myDialPadAdapter = new MyDialPadAdapter(this,mdialPaddata);
//        dialpad_recyclerView.setItemAnimator(new DefaultItemAnimator());
        dialpad_recyclerView.setAdapter(myDialPadAdapter);


        //////////////////////////////////////////////////
        dialPadLayout = findViewById(R.id.dialpadLayout);
        numberDisplay = findViewById(R.id.number_display);
        addContact = findViewById(R.id.key_addContact);
        key0 = findViewById(R.id.key_0);key1 = findViewById(R.id.key_1);key2 = findViewById(R.id.key_2);
        key3 = findViewById(R.id.key_3);key4 = findViewById(R.id.key_4);key5 = findViewById(R.id.key_5);
        key6 = findViewById(R.id.key_6);key7 = findViewById(R.id.key_7);key8 = findViewById(R.id.key_8);
        key9 = findViewById(R.id.key_9);keyStar = findViewById(R.id.key_star);keyHash = findViewById(R.id.key_hash);
        keyVoicemail = findViewById(R.id.key_voicemail);keyCall = findViewById(R.id.key_call);keyBackspace = findViewById(R.id.key_backspace);
//        numberDisplay.setOnClickListener(this);
        key0.setOnClickListener(this);key1.setOnClickListener(this);key2.setOnClickListener(this);
        key3.setOnClickListener(this);key4.setOnClickListener(this);key5.setOnClickListener(this);
        key6.setOnClickListener(this);key7.setOnClickListener(this);key8.setOnClickListener(this);
        keyStar.setOnClickListener(this);keyHash.setOnClickListener(this);keyCall.setOnClickListener(this);
        key9.setOnClickListener(this);keyBackspace.setOnClickListener(this);keyVoicemail.setOnClickListener(this);
        addContact.setOnClickListener(this);numberDisplay.setOnClickListener(this);
        numberDisplay.setHint("Enter Number");
        numberDisplay.setSelection(numberDisplay.getText().length(),numberDisplay.getText().length());
//        numberDisplay.requestPointerCapture();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            numberDisplay.setTextIsSelectable(true);
            numberDisplay.setShowSoftInputOnFocus(false);
        }
        else {
        numberDisplay.setTextIsSelectable(true);
        numberDisplay.setFocusable(true);
        numberDisplay.setFocusableInTouchMode(true);
        numberDisplay.setClickable(true);
        numberDisplay.setLongClickable(true);
        numberDisplay.setMovementMethod(ArrowKeyMovementMethod.getInstance());
        numberDisplay.setText(numberDisplay.getText(),TextView.BufferType.SPANNABLE);
    }

    numberDisplay.addTextChangedListener(new TextWatcher() {
        int p;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            p = start+1;
            int pos = numberDisplay.getSelectionStart();
//            System.out.println("int Start is"+start+" int before is "+before+" int count is"+count);
            int textsize = numberDisplay.getText().length();
//            System.out.println("the position"+pos+"textSize"+textsize);
            numberDisplay.setSelection(textsize);
//            System.out.println("On text changed "+ p);
        }

        @Override
        public void afterTextChanged(Editable s) {
//            System.out.println("p is "+p);
            filter(s.toString());
//            numberDisplay.setSelection(p);
        }
    });


        final CardView cardView = findViewById(R.id.dial_pad_keyboard);
        final FloatingActionButton fab = findViewById(R.id.dial_show_fab);
//        dialpad_recyclerView.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("RestrictedApi")
//            @Override
//            public void onClick(View v) {
//                cardView.animate()
//                        .translationY(cardView.getHeight())
//                        .alpha(0.0f)
//                        .setDuration(300)
//                        .setListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                super.onAnimationEnd(animation);
//                                cardView.setVisibility(View.GONE);
//                                fab.setVisibility(View.VISIBLE);                            }
//                        });
//
//
//
//
//            }
//        });

        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);

            }
        });

        dialpad_recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cardView.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                return true;
            }
        });

    }



    @Override
    public void onClick(View v) {
// this make vibration when the view tapped
        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

//        Editable current_text = (Editable) text.subSequence(0,text.length()-1);
//        numberDisplay.setText(current_text, TextView.BufferType.EDITABLE);
//        numberDisplay.setSelection(text.length());
        if (numberDisplay.getText().length()>15){
            numberDisplay.setTextSize(15);
        }
        int position;
        switch (v.getId()) {
            case R.id.key_0:{
                position = numberDisplay.getSelectionStart();
                numberDisplay.setText(numberDisplay.getText().insert(position,"0"), TextView.BufferType.EDITABLE);
                Toast.makeText(DialPadActivity.this, position+"zero is clicked", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.key_1:{
                position = numberDisplay.getSelectionStart();
                numberDisplay.setText(numberDisplay.getText().insert(position,"1"), TextView.BufferType.EDITABLE);
                break;
            }

            case R.id.key_2:{
                position = numberDisplay.getSelectionStart();
                numberDisplay.setText(numberDisplay.getText().insert(position,"2"), TextView.BufferType.EDITABLE);
                break;
            }

            case R.id.key_3: {
                position = numberDisplay.getSelectionStart();
                numberDisplay.setText(numberDisplay.getText().insert(position,"3"), TextView.BufferType.EDITABLE);
                break;
          }
//
            case R.id.key_4:
                position = numberDisplay.getSelectionStart();
                numberDisplay.setText(numberDisplay.getText().insert(position,"4"), TextView.BufferType.EDITABLE);
                break;
            case R.id.key_5:
                position = numberDisplay.getSelectionStart();
                numberDisplay.setText(numberDisplay.getText().insert(position,"5"), TextView.BufferType.EDITABLE);
                break;
            case R.id.key_6:
                position = numberDisplay.getSelectionStart();
                numberDisplay.setText(numberDisplay.getText().insert(position,"6"), TextView.BufferType.EDITABLE);
                break;
            case R.id.key_7:
                position = numberDisplay.getSelectionStart();
                numberDisplay.setText(numberDisplay.getText().insert(position,"7"), TextView.BufferType.EDITABLE);
                break;
            case R.id.key_8:
                position = numberDisplay.getSelectionStart();
                numberDisplay.setText(numberDisplay.getText().insert(position,"8"), TextView.BufferType.EDITABLE);
                break;
            case R.id.key_9:
                position = numberDisplay.getSelectionStart();
                numberDisplay.setText(numberDisplay.getText().insert(position,"9"), TextView.BufferType.EDITABLE);
                break;
            case R.id.key_voicemail:
//                Toast.makeText(DialPadActivity.this, "voicemail is clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.key_star:
                position = numberDisplay.getSelectionStart();
                CharSequence newText1 = numberDisplay.getText().insert(position,"*");
//                System.out.println("the Star value is "+newText1);
                numberDisplay.setText(newText1, TextView.BufferType.EDITABLE);
                break;
            case R.id.key_hash:
                position = numberDisplay.getSelectionStart();
                CharSequence newText = numberDisplay.getText().insert(position,"#");
//                System.out.println("the hashed value is "+newText);
                numberDisplay.setText(newText, TextView.BufferType.EDITABLE);
                break;
            case R.id.key_addContact:
                CharSequence text = numberDisplay.getText();
                Toast.makeText(DialPadActivity.this, "addconact is clicked", Toast.LENGTH_SHORT).show();
                Intent contactIntent = new Intent(this,CreateNewContactActivity.class);
                contactIntent.putExtra("PhoneNumber",text);
                startActivity(contactIntent);
                break;
            case R.id.key_backspace: {
                CharSequence enteredtext = numberDisplay.getText().toString();
                position = numberDisplay.getSelectionStart();
                if (enteredtext != null && position != 0) {
                    CharSequence firsttext = enteredtext.subSequence(0, position - 1);
                    CharSequence secondtext = enteredtext.subSequence(position, enteredtext.length());
                    CharSequence whole_text = firsttext + "" + secondtext;
                    System.out.println("first text is " + firsttext + "\n second text is" + secondtext + "\n" + "whole text is " + enteredtext);
                    numberDisplay.setText(whole_text, TextView.BufferType.EDITABLE);
//                    numberDisplay.setSelection(position+1);
//                    Toast.makeText(DialPadActivity.this, numberDisplay.getText().append("") + " is written", Toast.LENGTH_SHORT).show();
                }


                break;
            }
            case R.id.key_call:{
                makePhoneCall();
            }

            default:
//                Toast.makeText(DialPadActivity.this, "EditText is clicked", Toast.LENGTH_SHORT).show();
                break;

        }
    }
    private void makePhoneCall(){
        String number = numberDisplay.getText().toString();
        if (number.trim().length()>0){
            if (ContextCompat.checkSelfPermission(DialPadActivity.this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(DialPadActivity.this,new String[]{Manifest.permission.CALL_PHONE,},REQUEST_CALL);
                
            }
            else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
        else{
            Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode ==REQUEST_CALL){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }
            else {
                Toast.makeText(ct, "Pemission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void filter(String text) {
      ArrayList<LogModel>  filteredList = new ArrayList<>();

        for (LogModel item : mdialPaddata) {
            if (item.getNumber().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.size()<=mdialPaddata.size()){
            filteredList.add(new LogModel("Create New Contact","",null, R.drawable.ic_person_add_dial,R.drawable.ic_call_missed));
            filteredList.add(new LogModel("Add to Contact","",null, R.drawable.ic_person_add_dial,R.drawable.ic_call_missed));
            filteredList.add(new LogModel("Send Sms","",null, R.drawable.ic_message,R.drawable.ic_call_missed));

        }

        myDialPadAdapter.filterList(filteredList,text);
    }


    public  List<LogModel> getAllContacts(){
        mdialPaddata = new ArrayList<>();

//        if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
//
//            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS},READCONTACTCODE);
////            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},READCONTACTCODE);
//        }
        Cursor cursor = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
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
            mdialPaddata.add(new LogModel(Name, Number, null,ProfilePhoto, R.drawable.ic_call_missed));
//        mdialPaddata.add(new LogModel("Abeni","0941586785",null, R.drawable.ic_person1,R.drawable.ic_call_missed));

        }
        return mdialPaddata;
    }
}


