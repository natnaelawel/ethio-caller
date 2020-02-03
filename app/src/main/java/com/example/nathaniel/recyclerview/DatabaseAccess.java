package com.example.nathaniel.recyclerview;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<LogModel>  getEmergency() {
        List<LogModel> emerModelList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM Emergency", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            list.add(cursor.getString(0));
//            cursor.moveToNext();
//        }
        int index1 = cursor.getColumnIndex("id");
        int index2 = cursor.getColumnIndex("contact_name");
        int index3 = cursor.getColumnIndex("contact_number");
        while (cursor.moveToNext()){
            emerModelList.add(new LogModel(cursor.getString(index2),cursor.getString(index3),"2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));

        }
        cursor.close();
        return emerModelList;
    }
//    public List<LogModel>  getFavourites() {
//        List<LogModel> favModelList = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT * FROM Favourites", null);
//
//        int index1 = cursor.getColumnIndex("id");
//        int index2 = cursor.getColumnIndex("contact_name");
//        int index3 = cursor.getColumnIndex("contact_number");
//        while (cursor.moveToNext()){
//            favModelList.add(new LogModel(cursor.getString(index2),cursor.getString(index3),"2 min ago", R.drawable.ic_user,R.drawable.ic_call_missed));
//
//        }
//        cursor.close();
//        return favModelList;
//    }

    public List<LogModel>  getModelList(String type, List<LogModel> Modellist) {
        List<LogModel> favModelList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+ type, null);

        int index1 = cursor.getColumnIndex("id");
        int index2 = cursor.getColumnIndex("contact_name");
        int index3 = cursor.getColumnIndex("contact_number");
        int photo =  R.drawable.ic_person1;

        if (type == "Favourites"){
            photo =  R.drawable.ic_person1;
        }
        else if(type=="Emergency"){
            photo = R.drawable.ic_user;
        }else if(type=="Important"){
            photo = R.drawable.ic_user;
        }


        while (cursor.moveToNext()){
            Modellist.add(new LogModel(cursor.getString(index2),cursor.getString(index3),"2 min ago",photo,R.drawable.ic_call_missed));

        }
        cursor.close();
        return Modellist;
    }
    public List<LogModel>  getDatas(String type,List<LogModel> list) {
        List<LogModel> favModelList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM '?' ", new String[]{type});

        int index1 = cursor.getColumnIndex("id");
        int index2 = cursor.getColumnIndex("contact_name");
        int index3 = cursor.getColumnIndex("contact_number");
        int photo =  R.drawable.ic_person1;
        if (type == "favourites"){
            photo =  R.drawable.ic_person1;
        }
        else if(type=="emergency"){
            photo = R.drawable.ic_user;
        }
        while (cursor.moveToNext()){
            favModelList.add(new LogModel(cursor.getString(index2),cursor.getString(index3),"2 min ago", photo,R.drawable.ic_call_missed));

        }
        cursor.close();
        return favModelList;
    }

    public void removeFav(String favNum){

    }
}