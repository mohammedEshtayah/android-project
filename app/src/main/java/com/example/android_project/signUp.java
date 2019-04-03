package com.example.android_project;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class signUp extends AppCompatActivity {
private EditText user,email,password1,password2,phone,address;
private TextView showPath;
private Button signUp,getImage;
    public static final int REQUEST_CODE_Image=100;
    private Uri PathImage;
    private Bitmap bitmap;
    private   byte[] byteImage;
    private DB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
database=new DB();
showPath=(TextView)findViewById(R.id.showpath);
        user=(EditText)findViewById(R.id.user);
        email=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.phone);
        getImage=(Button)findViewById(R.id.getimage);
        password1=(EditText)findViewById(R.id.password1);
        password2=(EditText)findViewById(R.id.repassword2);
        address=(EditText)findViewById(R.id.address);
        signUp = (Button) findViewById(R.id.signup);

        getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap im = null;
                Intent intent =new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"data"),REQUEST_CODE_Image);
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               ContentValues cv = new  ContentValues();
                cv.put("user_name",   user.getText().toString());
                cv.put("image",   byteImage);
                cv.put("password",   password1.getText().toString());
                cv.put("address",    address.getText().toString());
                cv.put("email",    email.getText().toString());
                cv.put("phone",   Integer.parseInt( phone.getText().toString()));

               database .db.insert( "users", null, cv );

                //   database.db.execSQL("insert into users ("+user.getText().toString()+","+password1.getText().toString()+","+byteImage+","+address.getText().toString()+","+Integer.parseInt(phone.getText().toString())+","+email.getText().toString()+")");





            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CODE_Image && resultCode == RESULT_OK && data != null && data.getData() != null) {

            PathImage = data.getData();
            //showPath.setText(data.getDataString());

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
}
