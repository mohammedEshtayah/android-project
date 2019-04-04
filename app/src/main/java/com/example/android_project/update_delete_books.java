package com.example.android_project;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class update_delete_books extends AppCompatActivity {
private TextView Titel;
    private EditText Abstract,noSales;
    private Intent This;
    private Button Update,delete;
    private DB database;
    private sharedPreference SP;
    private String user_name,name_book;
    private  int price;
    private String abstracts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_books);
        This=getIntent();
        Update=(Button)findViewById(R.id.update);
        delete=(Button)findViewById(R.id.delete);
        SP=new sharedPreference(this);
        database=new DB();
        user_name=SP.SP.getString("user",null);
        Titel=(TextView)findViewById(R.id.titelBook2);
        Abstract=(EditText) findViewById(R.id.abstracts);
        noSales=(EditText) findViewById(R.id.noSales);
        abstracts=This.getStringExtra("Abstract");

        name_book=This.getStringExtra("Titel");
        Titel.setText(name_book);
         price=This.getIntExtra("price",0);

Abstract.setText(abstracts);
noSales.setText(Integer.toString(price));

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             if(!Abstract.getText().toString().equals(abstracts) || !noSales.getText().toString().equals(Integer.toString(price))) {
abstracts=Abstract.getText().toString();
price=Integer.parseInt(noSales.getText().toString());
                 database.db.execSQL("update books set abstract='"+abstracts+"' , price ="+price+" where name_book ='" + name_book + "'");
             }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    database.db.execSQL("delete from books where name_book ='" + name_book + "'");

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(getApplicationContext(),HomeSeller.class));
    }
}
