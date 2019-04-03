package com.example.android_project;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ToBuy extends AppCompatActivity {
    private TextView Titel,Abstract,noSales;
    private Intent This;
    private Button toBuy;
    private DB database;
    private sharedPreference SP;
    private String user_name,name_book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_buy);
        This=getIntent();
        toBuy=(Button)findViewById(R.id.ToBuy);
        SP=new sharedPreference(this);
        database=new DB();

        Titel=(TextView)findViewById(R.id.titelBook2);
        Abstract=(TextView)findViewById(R.id.abstractBook2);
        noSales=(TextView)findViewById(R.id.noSales);

          Titel.setText(This.getStringExtra("Titel"));
        Abstract.setText( This.getStringExtra("Abstract"));
        user_name=SP.SP.getString("user",null);
        name_book=This.getStringExtra("Titel");

        toBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues  cv = new  ContentValues();
                cv.put("user_name",   user_name);
                cv.put("name_book",   name_book);
                database .db.insert( "buy", null, cv );


               // database.db.rawQuery("insert into buy values("+user_name+","+name_book+")",null);

            }
        });
    }
}
