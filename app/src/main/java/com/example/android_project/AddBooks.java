package com.example.android_project;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddBooks extends AppCompatActivity {
    private Button GetImage,AddBook;
    private EditText id,name,abstracts,price;
public static final int REQUEST_CODE_Image=100;
private Uri PathImage;
private Bitmap bitmap;
private   byte[] byteImage;
    private Spinner TypeBook;
    private DB database;
    private   sharedPreference SP;
    private String name_Library;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);
        database=new DB();

        GetImage = (Button) findViewById(R.id.GetImages);
        AddBook = (Button) findViewById(R.id.AddBooks);
        TypeBook=(Spinner)findViewById(R.id.TypeBooks);

        abstracts=(EditText) findViewById(R.id.abstractss);
        name=(EditText) findViewById(R.id.NameBooks);
        price=(EditText)findViewById(R.id.price);
          SP=new sharedPreference(this);
        name_Library=SP.SP.getString("user",null);
        String[] typeBook = { "India", "USA", "China", "Japan", "Other"};
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,typeBook);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TypeBook.setAdapter(aa);

    AddBook.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            ContentValues  cv = new  ContentValues();
            cv.put("name_book",   name.getText().toString());
            cv.put("image",   byteImage);
            cv.put("abstract",    abstracts.getText().toString());
            cv.put("price",     Integer.parseInt(price.getText().toString()));
            cv.put("type",    TypeBook.getSelectedItem().toString());
            cv.put("name_library",  name_Library);
            cv.put("noCopy",    0);
            cv.put("NoLike",    0);
            cv.put("NoUnlike",   0);
            database .db.insert( "books", null, cv );


        }
    });
    GetImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Bitmap im = null;
            Intent intent =new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"data"),REQUEST_CODE_Image);
 }
    });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CODE_Image && resultCode == RESULT_OK && data != null && data.getData() != null) {

            PathImage = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), PathImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
               bitmap .compress(Bitmap.CompressFormat.PNG, 100, stream);
                  byteImage = stream.toByteArray();







            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}