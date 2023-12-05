package com.example.capstoneproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DB_ImageHelper {
    private Context mContext;
    private Image_DatabaseHelper img_db;
    private static int db_ver = 1;
    private static String db_name = "DetailsTable.db";

    private static class Image_DatabaseHelper extends SQLiteOpenHelper {

        public Image_DatabaseHelper(Context context) {
            super(context, db_name, null, db_ver);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table DetailsTable( MissingPersonName TEXT not null," +
                    "MissingPersonAge TEXT not null, FirNumber TEXT primary key," +
                    "AadhaarNumber TEXT, ContactNumber TEXT not null, MissingPersonImage blob not null)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS DetailsTable");
            onCreate(db);
        }
    }

    // below is the method for deleting our case.
    public void deleteCase(String FirNumber) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase sqDB = img_db.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        sqDB.delete("DetailsTable", "FirNumber=?", new String[]{FirNumber});
        sqDB.close();
    }

    public void reset() {
        SQLiteDatabase sqDB=img_db.getWritableDatabase();
        img_db.onUpgrade(sqDB, 1, 1);
    }

    public DB_ImageHelper(Context con) {
        mContext = con;
        img_db = new Image_DatabaseHelper(mContext);
    }

    public DB_ImageHelper open() throws SQLException {
        SQLiteDatabase sqDB = img_db.getWritableDatabase();
        return this;
    }

    public void close() {
        img_db.close();
    }


    public byte[] retrieveImage() {
        SQLiteDatabase sqDB=img_db.getReadableDatabase();
        Cursor cur = sqDB.query(true, "DetailsTable", new String[]{"MissingPersonImage",},
                null, null, null, null
                , "FirNumber" + " DESC", "1");
        if (cur.moveToNext()) {
            @SuppressLint("Range")
            byte blob[] = cur.getBlob(cur.getColumnIndex("MissingPersonImage"));
            cur.close();
            return blob;
        }
        cur.close();
        return null;
    }

    public boolean checkFir(String FirNumber) {
        SQLiteDatabase sqDB = img_db.getWritableDatabase();
        Cursor cr = sqDB.rawQuery("select * from DetailsTable where FirNumber = ? ",
                new String[]{FirNumber});
        if (cr.getCount() == 0)
            return true;
        else
            return false;
    }

    public Boolean insertDetails(String miss_per_name, String miss_per_age, String miss_per_fir,
                                 String miss_per_aadhaar, String user_number, byte imgBytes[]) {
        /*int miss_age=Integer.parseInt(miss_per_age);
        long fir=Long.parseLong(miss_per_fir);
        long aadhaar=Long.parseLong(miss_per_aadhaar);
        long mobNum=Long.parseLong(user_number);*/

        SQLiteDatabase sqDB = img_db.getWritableDatabase();
        ContentValues conVal = new ContentValues();
        conVal.put("MissingPersonName", miss_per_name);
        conVal.put("MissingPersonAge", miss_per_age);
        conVal.put("FirNumber", miss_per_fir);
        conVal.put("AadhaarNumber", miss_per_aadhaar);
        conVal.put("ContactNumber", user_number);
        conVal.put("MissingPersonImage", imgBytes);
        long result = sqDB.insert("DetailsTable", null, conVal);

        if (result == -1)
            return false;
        else
            return true;
    }
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("create table DetailsTable( MissingPersonName text not null," +
//                "MissingPersonAge text not null, FirNumber text primary key," +
//                "AadhaarNumber text, ContactNumber text not null, MissingPersonImage blob not null)");
//        //db.execSQL(create_det_table);
//    }

//    public ArrayList<MissingModal> fetchAllDetails()
//    {
//        ArrayList<MissingModal> al=new ArrayList<>();
//        SQLiteDatabase sqDB=img_db.getReadableDatabase();
//        Cursor cr=sqDB.rawQuery("select MissingPersonName, MissingPersonAge, FirNumber, AadhaarNumber, ContactNumber from DetailsTable ",null);
//
////        if (cr.moveToNext()) {
////            MissingModal model=new MissingModal();
////            model.MissingPersonName=cr.getInt(0);
////        }
////        cr.close();
//        if(cr.moveToFirst()) {
//            do {
//                al.add(new MissingModal(cr.getString(0),
//                        cr.getString(1),
//                        cr.getString(2),
//                        cr.getString(3),
//                        cr.getString(4)));
////                String missingPersonName = cr.getString(1);
////                String missingPersonAge = cr.getString(2);
////                String firNumber = cr.getString(3);
////                String aadhaarNum = cr.getString(4);
////                String userNum = cr.getString(5);
////                // byte[] missingPerImage=cr.getBlob(6);
////
////                MissingModal vc = new MissingModal(missingPersonName, missingPersonAge, firNumber,
////                        aadhaarNum, userNum);
////
////                vc.setMissingPersonName(missingPersonName);
////                vc.setMissingPersonAge(missingPersonAge);
////                vc.setFirNumber(firNumber);
////                vc.setAadhaarNum(aadhaarNum);
////                vc.setContactNumber(userNum);
////                // vc.setImgData(missingPerImage);
////
////
////                al.add(vc);
//            } while (cr.moveToNext());
//            //cr.close();
//        }
//        cr.close();
//        return al;
//    }
//
////    public Cursor fetchAllDetails()
////    {
////        sqDB = img_db.getWritableDatabase();
////        Cursor cr = sqDB.rawQuery("select MissingPersonName,MissingPersonAge,FirNumber," +
////                "                   AadhaarNumber,ContactNumber" + " from DetailsTable", null);
////        return cr;
////    }
}