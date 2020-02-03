package com.example.nathaniel.recyclerview;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.function.Function;


/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceBorrowFragment extends Fragment {
    View rootView;
    String[] BorrowAmount = {"5 Birr", "10 Birr", "15 Birr", "25 Birr","50 Birr"};
    Spinner BorrowAmountSpinner;
    Button borrowBtn;
    String borrowAmount;
    public BalanceBorrowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        getActivity().setTitle("Balance Borrow");
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_balance_borrow, container, false);
        ArrayAdapter<String> BorrowarrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_dropdown_item_1line, BorrowAmount);
        BorrowAmountSpinner = rootView.findViewById(R.id.Borrow_amount_spinner);
        borrowBtn = rootView.findViewById(R.id.borrowbtn);
        BorrowAmountSpinner.setAdapter(BorrowarrayAdapter);
//        Toast.makeText(getActivity(), "This is Borrow", Toast.LENGTH_SHORT).show();

        BorrowAmountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getSelectedItem().toString();
                switch (parent.getSelectedItemPosition()){

                    case 0:
                    {
//                        Toast.makeText(getActivity(), value+"is selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 1:
                    {
//                        Toast.makeText(getActivity(), value+" is selected", Toast.LENGTH_SHORT).show();
                        break;

                    }
                    case 2:
                    {
//                        Toast.makeText(getActivity(), value+" is selected", Toast.LENGTH_SHORT).show();
                        break;

                    }
                    case 3:
                    {
//                        Toast.makeText(getActivity(), value+" is selected", Toast.LENGTH_SHORT).show();
                        break;

                    }
                    case 4:
                    {
//                        Toast.makeText(getActivity(), value+" is selected", Toast.LENGTH_SHORT).show();
                        break;

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        borrowAmount = BorrowAmountSpinner.getSelectedItem().toString();

        borrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog(getContext());
            }
        });
        return rootView;
    }

    public void alertDialog(Context ct){
            new AlertDialog.Builder(ct)
                    .setTitle("Confirm Borrowing")
                    .setMessage("Do you want to Borrow "+ borrowAmount)
                    .setIcon(R.drawable.ic_confirmation)
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    makeBorrow(borrowAmount);
                                }
                            })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @TargetApi(11)
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).show();

    }

    private void makeBorrow(String Amount){
        if (Amount.trim().length()>0){
            String dial = "tel:*810*" + Amount+"%23";
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
        else{
            Toast.makeText(getActivity(), "No Phone Number", Toast.LENGTH_SHORT).show();
        }
    }
}
