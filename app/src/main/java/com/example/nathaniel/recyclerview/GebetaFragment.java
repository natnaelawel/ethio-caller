package com.example.nathaniel.recyclerview;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.card.MaterialCardView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.nathaniel.recyclerview.viewPageAdapter.pos;


/**
 * A simple {@link Fragment} subclass.
 */
public class GebetaFragment extends Fragment {
    private static final int PICKCONTACTREQUEST=22;
    Spinner durationSpinner;
    Spinner amountSpinner;
    Spinner TypeSpinner;
    RadioGroup toWhoItemGroup;
    Button gebetaBuybtn;
    String firstItem;
    String secondItem;
    String thirdItem;
    int firstItemPosition,secondItemPosition,thirdItemPosition;
    String toWhoItem;
    String[] PackageType = {"Voice Package", "Internet", "SMS", "Premium"};
    String[] VoicePackageDuration = {"Daily", "Weekly", "Monthly"};
    String[] SMSPackageDuration = {"Daily", "Weekly", "Monthly"};
    String[] InternetPackageDuration = {"Daily", "Weekly", "Monthly","Night","Weekend"};

    String[] PackagePerMinute = {"For Call: 0.50 per Minute", "For Internet: 0.20 per Minute", "For SMS: 0.20 per SMS"};

//    String[] PackageDuration = {"Daily", "Weekly", "Monthly", "Night"};
    String[] VoiceDaily = {"3 Birr 8 Minute", "5 Birr 13 Minute", "10 Birr 28 Minute"};
    String[] VoiceWeekly = {"15 Birr 46 Minute", "20 Birr 65 Minute"};
    String[] VoiceMonthly = {
              "60 Birr 166 Min Plus 5MP+30SMS", "100 Birr 285 Minute Plus 5MP+30SMS"
            , "140 Birr 415 Min Plus 5MP+50SMS", "150 Birr 450 Min Plus 5MP+50SMS"
            , "200 Birr 600 Min Plus 5MP+50SMS", "250 Birr 750 Min Plus 5MP+50SMS"
            , "270 Birr 830 Min Plus 5MP+80SMS",".300 Birr 930 Min Plus 5MP+80SMS"
            , "350 Birr 1080 Min Plus 50MP+80SMS",".400 Birr 1230 Min Plus 50MP+80SMS"
            , "450 Birr 1380 Min Plus 50MP+80SMS",".500 Birr 1545 Min Plus 50MP+80SMS"
            , "540 Birr 1660 Min Plus 50MP+100SMS",".600 Birr 1845 Min Plus 50MP+100SMS"
            , "1350 Birr 4150 Min Plus 50MP+350SMS"};

    String[] SMSDaily = {"2 Birr 18 SMS", "3 Birr 35 SMS", "5 Birr 70 SMS"};
    String[] SMSWeekly = {"10 Birr 140 SMS", "15 Birr 283 SMS"};
    String[] SMSMonthly = {"30 Birr 252 SMS", "50 Birr 1050 SMS", "5 Birr 70 SMS"};

    String[] InternetDaily = {"3 Birr 25MB", "5 Birr 45MB", "10 Birr 100MB","15 Birr 200MB","35 Birr 500MB"};
    String[] InternetWeekly = {"50 Birr 500MB", "60 Birr 700MB", "80 Birr 1GB","15 Birr 200MB","35 Birr 500MB"};
    String[] InternetMonthly = {"55 Birr 500MB", "100 Birr 1GB", "190 Birr 2GB","250 Birr 4GB","600 Birr 8GB",
                                "700 Birr 10GB", "1300 Birr 20GB", "1800 Birr 30GB","4900 Birr UNLIMITED",
    };
    String[] InternetNight = {"3 Birr 50MB", "5 Birr 100MB", "7 Birr 160MB"};
    String[] InternetWeekend = {"35 Birr 500MB", "60 Birr 1GB", "110 Birr 2GB"};
    String[] PremiumType = {"Premium Limited","Premium UnLimited","Premium LimitedAll","Premium Limited SMSandVoice"};

    String[] PremiumLimited = {"6999 Birr Local Minute 9000Min Local SMS 870 SMS", "International Minute 100 Min Data 60GB","International SMS 50 SMS"};
    String[] PremiumUnLimited = {"9999 Birr Local Minute Unlimited Local SMS Unlimited \n International Minute 100 Min \n  Data UnLimited \n International SMS 100 SMS"};
    String[] PremiumLimitedAll = {"Unlimited"};
    String[] PremiumLimitedSMSandVoice = {"Unlimited"};
    String choice1,choice2,choice3;
    TextView PackagePer;

    View rootView;
    int toWhoItemPosition;
    private String Phonenumber;
    ImageView addPhoneNumber;
    EditText RechargeToNumber,GiftToPhoneNumber;
    RadioButton checkedRadio;
    public GebetaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        getActivity().setTitle("Gebeta");

        rootView = inflater.inflate(R.layout.fragment_gebeta, container, false);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_dropdown_item_1line, PackageType);
//        ArrayAdapter<String> durationArrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_dropdown_item_1line, PackageType);
//        final MaterialBetterSpinner typeSpinner =rootView.findViewById(R.id.type_spinner);
//        final MaterialBetterSpinner durationSpinner =rootView.findViewById(R.id.duration_spinner);
//        final MaterialBetterSpinner amountSpinner =rootView.findViewById(R.id.amount_spinner);
        TypeSpinner =rootView.findViewById(R.id.type_spinner);
        durationSpinner =rootView.findViewById(R.id.duration_spinner);
        amountSpinner = rootView.findViewById(R.id.amount_spinner);
        gebetaBuybtn= rootView.findViewById(R.id.gebetaBuybtn);
        RechargeToNumber = rootView.findViewById(R.id.giftToPhoneNumber);
        addPhoneNumber = rootView.findViewById(R.id.addGiftNumToEditText);
        addPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickContact();
            }
        });

        final CardView forgiftcardView = rootView.findViewById(R.id.forGiftLayout);
        toWhoItemGroup = rootView.findViewById(R.id.toWhoItem);
//        final int selectedId = toWhoItemGroup.getCheckedRadioButtonId();
        toWhoItemGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton1)
                {
//                    Toast.makeText(getContext(), "for You checked", Toast.LENGTH_SHORT).show();
                    forgiftcardView.setVisibility(View.GONE);
                }
               else if (checkedId == R.id.radioButton2)
               {
//                    Toast.makeText(getContext(), "for gift checked", Toast.LENGTH_SHORT).show();
                    forgiftcardView.setVisibility(View.VISIBLE);
                }
                else {
                    forgiftcardView.setVisibility(View.GONE);
                }
            }
        });
        TypeSpinner.setAdapter(arrayAdapter);
        TypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> dataAdapter;
                Object item = parent.getItemAtPosition(pos);
//                Toast.makeText(getActivity(), item.toString()+"is the Position", Toast.LENGTH_SHORT).show();
                choice1 =PackageType[parent.getSelectedItemPosition()];
//                Toast.makeText(getActivity(), "is the choice 1"+choice1, Toast.LENGTH_SHORT).show();
                PackagePer = rootView.findViewById(R.id.PackagePerMinute);
                switch (parent.getSelectedItemPosition()){

                    case 0:{
                        PackagePer.setText(PackagePerMinute[0]);
                        List<String> VoicePackageDurationList = new ArrayList<String>(Arrays.asList(VoicePackageDuration));
                        dataAdapter = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        durationSpinner.setAdapter(dataAdapter);
                        durationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                ArrayAdapter<String> dataAdapter2;
                                Object item = parent.getItemAtPosition(pos);
//                                Toast.makeText(getActivity(), item.toString()+"is the Position", Toast.LENGTH_SHORT).show();
                                switch (parent.getSelectedItemPosition()){
                                    case 0: {
                                        List<String> VoicePackageDurationList = new ArrayList<>(Arrays.asList(VoiceDaily));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }
                                    case 1: {
                                        List<String> VoicePackageDurationList = new ArrayList<>(Arrays.asList(VoiceWeekly));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }
                                    case 2: {
                                        List<String> VoicePackageDurationList = new ArrayList<>(Arrays.asList(VoiceMonthly));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }
                                    default:{
                                        String[] VoicePackageDuration = {"Daily", "Weekly", "Monthly"};
                                        List<String> VoicePackageDurationList = new ArrayList<String>(Arrays.asList(VoicePackageDuration));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    }
                    case 1:{
                        PackagePer.setText(PackagePerMinute[1]);

                        //                        List<String> SMSPackageDuration = new ArrayList<String>(Arrays.asList(SMSPackageDuration));
                        dataAdapter = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_spinner_item, InternetPackageDuration);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        durationSpinner.setAdapter(dataAdapter);
                        durationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                ArrayAdapter<String> dataAdapter2;
                                Object item = parent.getItemAtPosition(pos);
                                Toast.makeText(getActivity(), item.toString()+"is the Position", Toast.LENGTH_SHORT).show();
                                switch (parent.getSelectedItemPosition()){
                                    case 0: {
                                        List<String> VoicePackageDurationList = new ArrayList<>(Arrays.asList(InternetDaily));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }
                                    case 1: {
                                        List<String> VoicePackageDurationList = new ArrayList<>(Arrays.asList(InternetWeekly));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }
                                    case 2: {
                                        List<String> VoicePackageDurationList = new ArrayList<>(Arrays.asList(InternetMonthly));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }
                                    case 3: {
                                        List<String> VoicePackageDurationList = new ArrayList<>(Arrays.asList(InternetNight));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }
                                    case 4: {
                                        List<String> VoicePackageDurationList = new ArrayList<>(Arrays.asList(InternetWeekend));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }
                                    default:{
                                        String[] VoicePackageDuration = {"Daily", "Weekly", "Monthly"};
                                        List<String> VoicePackageDurationList = new ArrayList<String>(Arrays.asList(VoicePackageDuration));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    }
                    case 2:{
                        PackagePer.setText(PackagePerMinute[2]);
                        List<String> InternetPackageDurationList = new ArrayList<String>(Arrays.asList(SMSPackageDuration));
                        dataAdapter = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_spinner_item, InternetPackageDurationList);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        durationSpinner.setAdapter(dataAdapter);
                        durationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                ArrayAdapter<String> dataAdapter2;
                                Object item = parent.getItemAtPosition(pos);
//                                Toast.makeText(getActivity(), item.toString()+"is the Position", Toast.LENGTH_SHORT).show();
                                switch (parent.getSelectedItemPosition()){
                                    case 0: {
                                        List<String> VoicePackageDurationList = new ArrayList<>(Arrays.asList(SMSDaily));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }
                                    case 1: {
                                        List<String> VoicePackageDurationList = new ArrayList<>(Arrays.asList(SMSWeekly));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }
                                    case 2: {
                                        List<String> VoicePackageDurationList = new ArrayList<>(Arrays.asList(SMSMonthly));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }
                                    default:{
                                        List<String> VoicePackageDurationList = new ArrayList<String>(Arrays.asList(SMSDaily));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    }
                    case 3:{
                        PackagePer.setText(PackagePerMinute[0]+PackagePerMinute[1]+PackagePerMinute[2]);
                        List<String> InternetPackageDurationList = new ArrayList<String>(Arrays.asList(PremiumType));
                        dataAdapter = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_spinner_item, InternetPackageDurationList);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter.notifyDataSetChanged();
                        durationSpinner.setAdapter(dataAdapter);
                        durationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                ArrayAdapter<String> dataAdapter2;
                                Object item = parent.getItemAtPosition(pos);
//                                Toast.makeText(getActivity(), item.toString()+"is the Position", Toast.LENGTH_SHORT).show();
                                switch (parent.getSelectedItemPosition()){
                                    case 0: {
                                        List<String> VoicePackageDurationList = new ArrayList<>(Arrays.asList(PremiumLimited));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }
                                    case 1: {
                                        List<String> VoicePackageDurationList = new ArrayList<>(Arrays.asList(PremiumUnLimited));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }
                                    case 2: {
                                        List<String> VoicePackageDurationList = new ArrayList<>(Arrays.asList(PremiumLimitedAll));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }
                                    case 3: {
                                        List<String> VoicePackageDurationList = new ArrayList<>(Arrays.asList(PremiumLimitedSMSandVoice));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }
                                    default:{
                                        String[] VoicePackageDuration = {"Daily", "Weekly", "Monthly"};
                                        List<String> VoicePackageDurationList = new ArrayList<String>(Arrays.asList(VoicePackageDuration));
                                        dataAdapter2 = new ArrayAdapter<>(getActivity(),
                                                android.R.layout.simple_spinner_item, VoicePackageDurationList);
                                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        dataAdapter2.notifyDataSetChanged();
                                        amountSpinner.setAdapter(dataAdapter2);
                                        break;

                                    }

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gebetaBuybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = toWhoItemGroup.getCheckedRadioButtonId();
                switch (id){
                    case R.id.radioButton1: {
                            toWhoItemPosition = 1;
                            toWhoItem = "for you";
                            break;}
                    case R.id.radioButton2: {
                            toWhoItemPosition = 2;
                            toWhoItem = "for gift";
                            break;}
                    default:{
                        toWhoItemPosition = 1;
                    }

                }
                firstItem = TypeSpinner.getSelectedItem().toString();
                secondItem = durationSpinner.getSelectedItem().toString();
                thirdItem = amountSpinner.getSelectedItem().toString();
                firstItemPosition = TypeSpinner.getSelectedItemPosition()+1;
                secondItemPosition = durationSpinner.getSelectedItemPosition()+1;
                thirdItemPosition = amountSpinner.getSelectedItemPosition()+1;
                alertDialog(getContext(),toWhoItem,firstItem,secondItem,thirdItem);
            }
        });


        return rootView;
    }


    public void alertDialog(Context ct, final String toWho, final String firstItem,final String secondItem,final String thirdItem){
        new AlertDialog.Builder(ct)
                .setTitle("Confirm Package")
                .setMessage("Do you want to Buy "+ toWho )
                .setIcon(R.drawable.ic_confirmation)
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            String number = RechargeToNumber.getText().toString();
                            String NormalizedNumber = number.replace("(","")
                                    .replace(")","")
                                    .replace("+","")
                                    .replace("-","");
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                makeTransfer(toWhoItemPosition,firstItemPosition,secondItemPosition,thirdItemPosition,NormalizedNumber);
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();

    }

    private void makeTransfer(int toWhoItemPosition,int firstItemPosition,int secondItemPosition,int thirdItemPosition,String numforgift){
        if (numforgift.trim().length()>0||toWhoItemPosition ==1){
            String dial = "tel:*999*1*1*"+firstItemPosition+secondItemPosition+thirdItemPosition+"%23";

            if (toWhoItemPosition==1){
                dial = "tel:*999*1*1*"+firstItemPosition+"*"+secondItemPosition+"*"+thirdItemPosition+"*1*%23";
                Toast.makeText(getContext(), dial, Toast.LENGTH_SHORT).show();
            }
            else if(toWhoItemPosition ==2){
                dial = "tel:*999*1*2*"+firstItemPosition+"*"+secondItemPosition+"*"+thirdItemPosition+"*"+numforgift+"*1*%23";
                Toast.makeText(getContext(), dial, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getContext(), "there is error", Toast.LENGTH_SHORT).show();
            }

            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
        else{
            Toast.makeText(getActivity(), "No Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    public void pickContact() {
        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS,},PICKCONTACTREQUEST);

        }
        else {
            Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            startActivityForResult(pickContact,PICKCONTACTREQUEST);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICKCONTACTREQUEST) {

            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String[] projection =new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};

                Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri,projection,null,null,null);
//                if (cursor!= null && cursor.moveToFirst()){
                if (cursor == null){
//                    Toast.makeText(getActivity(), "Cursor is Null", Toast.LENGTH_SHORT).show();
                }
                cursor.moveToFirst();
                int number= cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                Phonenumber = cursor.getString(number);
//                Toast.makeText(getActivity(), "phone Number is"+ Phonenumber, Toast.LENGTH_SHORT).show();
                RechargeToNumber.setText(Phonenumber,TextView.BufferType.EDITABLE);
//                TextView transferHeader = rootView.findViewById(R.id.transferHeader);

//                }
                cursor.close();
            }}
        super.onActivityResult(requestCode, resultCode, data);    }


}