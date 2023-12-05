package com.example.capstoneproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME="Login.db";
    public DBHelper(Context context)
    {
        super(context,"Login.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create table users(username TEXT primary key, password Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("drop table if exists users");
    }

    public  Boolean insertData(String username, String password)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues conVal = new ContentValues();
        conVal.put("username",username);
        conVal.put("password",password);
        long result=myDB.insert("users",null,conVal);
        if(result== -1)
            return false;
        else
            return true;
    }

    public  Boolean passwordUpdate(String username, String password)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues conVal = new ContentValues();
        conVal.put("password",password);
        long result=myDB.update("users",conVal,"username=? ",
                new String[]{username});
        if(result== -1)
            return false;
        else
            return true;
    }
    public Boolean checkUsername(String username)
    {
        SQLiteDatabase myDB=this.getWritableDatabase();
        Cursor cr=myDB.rawQuery("select * from users where username = ?", new String[]{username});
        if(cr.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkUserPassword(String username, String password)
    {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cr=myDB.rawQuery("select * from users where username = ? and password = ?",
                new String[]{username,password});
        if(cr.getCount()>0)
            return true;
        else
            return false;
    }
}