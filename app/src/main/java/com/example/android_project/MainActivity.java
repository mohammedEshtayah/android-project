package com.example.android_project;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
private EditText user,password;
private Button signUp,login;
    File file;
    private Spinner TypeBook;
    private DB database;
    private sharedPreference SP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user=(EditText)findViewById(R.id.user);
        password=(EditText)findViewById(R.id.password1);
        signUp = (Button) findViewById(R.id.signUp);
        login = (Button) findViewById(R.id.login);
         database=new DB();
         SP=new sharedPreference(this);

         if(SP.readLoginState()&& SP.readLoginType().equals("Library")){

            MainActivity.this.startActivity(new Intent(MainActivity.this, HomeSeller.class));
            MainActivity.this.finish();
        }else if(SP.readLoginState()&& SP.readLoginType().equals("Users")) {
            MainActivity.this.startActivity(new Intent(MainActivity.this, HomeUser.class));
            MainActivity.this.finish();
        }

        signUp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getApplicationContext(),signUp.class));

             }
         });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(user.getText().toString().equals("Library Nablus")) {

                    Cursor cursor = database.db.rawQuery("select password from Library where name_library='Library Nablus'", null);
                    while (cursor.moveToNext())
                        if(cursor.getString(0).equals(password.getText().toString())){

                            SP.writeLoginState(user.getText().toString(),"Library",true);
                            startActivity(new Intent(getApplicationContext(),HomeSeller.class));
                        }else{
                            password.setError("error");
                        }

                }else{

boolean u=false,p=false;

                    Cursor cursor =database.db.rawQuery("select user_name , password from users",null);
                    while (cursor.moveToNext()) {
                        Log.d("aaaaaaaaaaaaaaaaaaaaa",cursor.getString(1) + cursor.getString(0));

                    if(user.getText().toString().equals(cursor.getString(0))){

                        if(  password.getText().toString().equals(cursor.getString(1))){
                            p=true;
                        }
                    }else {

                    }

                    }

if(  p ){
    SP.writeLoginState(user.getText().toString(),"Users",true);

    startActivity(new Intent(getApplicationContext(),HomeUser.class));
}else{
    password.setError("");
    user.setError("");

}


                }









            }
        }       );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchmenu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search_menu);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
      //  searchView.setSubmitButtonEnabled(true);

        searchView.setIconifiedByDefault(false);

        return true;


    }

}
