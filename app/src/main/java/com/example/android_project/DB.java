package com.example.android_project;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;

import static android.content.Context.MODE_PRIVATE;

public class DB {
    public SQLiteDatabase db;
    private  File file;
  public  DB(){
      file = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath(), "/project-android");

      if (!file.exists())  file.mkdir();
      File myFile = new File(file.getAbsolutePath()+"/DB.db");

      db= SQLiteDatabase .openOrCreateDatabase( myFile.getPath() ,null);

      db.execSQL("create table IF NOT EXISTS Library(name_library VARCHAR(20) ,image blob\n" +
              " ,password varchar(30),phone number,location VARCHAR(50),about_us VARCHAR(10),PRIMARY key (name_library) );");



      db.execSQL("   create table IF NOT EXISTS books(name_book varchar(50) ,name_library VARCHAR(20), image blob,abstract VARCHAR(1000),\n" +
              "type varchar(30),noCopy number, NoLike number, NoUnlike number,price number, FOREIGN KEY (name_library) " +
              "REFERENCES Library,PRIMARY key (name_book) );");


      db.execSQL("  create table IF NOT EXISTS users(user_name VARCHAR(30) ,password varchar(30)\n" +
                      ",image blob,address varchar(50),phone number,email varchar(100),PRIMARY key (user_name));" );

    db.execSQL("create table IF NOT EXISTS buy(user_name VARCHAR(30),name_book varchar(50) PRIMARY key,\n" +
              "  FOREIGN KEY (name_book) REFERENCES books ,\n" +
              "  FOREIGN KEY (user_name) REFERENCES users  );" );

 /*ContentValues cv = new  ContentValues();

      cv.put("name_library", "Library Nablus");
      cv.put("password",   "111");
      cv.put("image", (byte[]) null);
      cv.put("password",   "fff");
      cv.put("phone",    13);
      cv.put("location",    "fff");

      db.insert( "Library", null, cv );




      Cursor cursor = db.rawQuery("select * from Library",null);
      while (cursor.moveToNext())   Log.d("aaaaaaaaaaaaaaaaaaaaa",cursor.getString(cursor.getColumnIndex("name_library")));
*/

  }

}
