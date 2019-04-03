package com.example.android_project;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Buy extends AppCompatActivity {
private TextView Titel,Abstract,noSales;
private Intent This;

    private RecyclerView recyclerView;
    private List<ListBooks> listBooks= new ArrayList<>();
    private ListBooks ClistBooks;
    private ViewBuy viewBuy;
    private DB database;
   private String username;
   private sharedPreference SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        database=new DB();
        SP=new sharedPreference(this);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerBuy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        username=SP.SP.getString("user",null);

     viewbook();
    }

    public void viewbook() {
        Cursor cursorUser = database.db.rawQuery("select name_book from buy where user_name='"+username+"'  ",null);

        while (cursorUser.moveToNext()){
            String book=cursorUser.getString(0);
            Log.d("aaaaaaaaa",book);
            Cursor cursor = database.db.rawQuery("select * from books where name_book = '"+book+"' ",null);

            while( cursor.moveToNext()) {
                ClistBooks = new ListBooks(cursor.getString(cursor.getColumnIndex("name_book")), cursor.getBlob(cursor.getColumnIndex("image")),
                        cursor.getString(cursor.getColumnIndex("abstract")), cursor.getInt(cursor.getColumnIndex("NoLike"))
                        , cursor.getInt(cursor.getColumnIndex("NoUnlike")), cursor.getInt(cursor.getColumnIndex("noCopy"))
                        , cursor.getInt(cursor.getColumnIndex("price")));
                listBooks.add(ClistBooks);
            }

        }/* */



        viewBuy=new ViewBuy(listBooks,getApplicationContext());
        recyclerView.setAdapter(viewBuy);
    }
}
