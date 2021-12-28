package com.example.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import androidx.annotation.Nullable;


public class veritabani extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="sehir.db";
    public static final String TABLE_NAME ="sehir_table";
   // public static final String COL_1 ="ID";
    public static final String COL_2 ="SEHIR";
    public static final String COL_3 ="DERECE";
    public static final String COL_4 ="NEM";
    public static final String COL_5 ="HISSEDILEN";
    public veritabani(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT,SEHIR TEXT,DERECE INTEGER,NEM INTEGER,HISSEDILEN INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String sehir, String derece, String nem, String hissedilen){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put(COL_2,sehir);

        contentValues.put(COL_3,derece);

        contentValues.put(COL_4,nem);

        contentValues.put(COL_5,hissedilen);

        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result ==-1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +TABLE_NAME ,null);
        return res;


    }

    public Integer deleteData(String sehir){
        SQLiteDatabase db = this.getReadableDatabase();
       // for (int i=0;i>100;i++)
      //  listView.setAdapter(null);
        return db.delete(TABLE_NAME,"SEHIR = ?",new String[] {sehir});
    }
}


