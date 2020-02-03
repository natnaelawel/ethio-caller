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
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceTransferFragment extends Fragment {
    EditText RechargeToNumber,TransferAmountEditText;

    private static final int PICKCONTACTREQUEST=222;
    CharSequence Phonenumber;
    ImageView addPhoneNumber;
    Button transferBtn;
    String transferAmount;
    String PhoneNum;

    View rootView;
    public BalanceTransferFragment() {
        // Required empty public constructor
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            pickContact();
        }
        else {
            Toast.makeText(getActivity(), "Pemission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        getActivity().setTitle("Balance Transfer");

//        Toast.makeText(getActivity(), "This is Transfer", Toast.LENGTH_SHORT).show();
        rootView=  inflater.inflate(R.layout.fragment_balance_transfer, container, false);
       RechargeToNumber = rootView.findViewById(R.id.rechargeToPhoneNumber);
       TransferAmountEditText= rootView.findViewById(R.id.tranferAmountEditText);
       addPhoneNumber = rootView.findViewById(R.id.addContacttoEditText);
       addPhoneNumber.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               pickContact();
           }
       });
       transferBtn = rootView.findViewById(R.id.transferbtn);
       transferBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               transferAmount = TransferAmountEditText.getText().toString();
               PhoneNum = RechargeToNumber.getText().toString();
               if (!transferAmount.equals("") && !PhoneNum.equals("")) {
                   alertDialog(getContext(), PhoneNum, transferAmount);
               }
               else {
                   Toast.makeText(getContext(), "Enter Phone Number or Amount", Toast.LENGTH_SHORT).show();
               }
           }
       });

        return rootView;
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

    public void alertDialog(Context ct, final String PhoneNum, final String transferAmount){
        new AlertDialog.Builder(ct)
                .setTitle("Confirm Transfering")
                .setMessage("Do you want to Transfer "+ transferAmount+" Birr to "+PhoneNum )
                .setIcon(R.drawable.ic_confirmation)
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String NormalizedNumber = PhoneNum.replace("(","")
                                        .replace(")","")
                                        .replace("+","")
                                        .replace("-","");
                                makeTransfer(transferAmount,NormalizedNumber);
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();

    }

    private void makeTransfer(String Amount, String Number){
        if (Amount.trim().length()>0){
            String dial = "tel:*806*"+Number+"*" + Amount+"%23";
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
        else{
            Toast.makeText(getActivity(), "No Phone Number", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICKCONTACTREQUEST) {

            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String[] projection =new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};

              Cursor  cursor = getActivity().getApplicationContext().getContentResolver().query(uri,projection,null,null,null);
//                if (cursor!= null && cursor.moveToFirst()){
            if (cursor == null){
//                Toast.makeText(getActivity(), "Cursor is Null", Toast.LENGTH_SHORT).show();
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
