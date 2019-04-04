package com.example.android_project;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class searchType extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ListBooks> listBooks= new ArrayList<>();
    private ListBooks ClistBooks;
    private ViewBook viewBook;
    private CircleImageView imageView;
    private DB database;
    private  byte[] image;
    private   byte[] byteImage;
    private sharedPreference SP;
    private String user_name;
    private String type;
    private Cursor cursora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_type);
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database=new DB();
SP=new sharedPreference(this);
        user_name=SP.SP.getString("user",null);
      type=getIntent().getStringExtra("type");
      viewBook();

    }
    private void viewBook() {


        Cursor cursor = database.db.rawQuery("select * from books where type='"+type+"'",null);
        while (  cursor.moveToNext()) {

            Log.d("aaaa", cursor.getString(cursor.getColumnIndex("type")));
            ClistBooks=new ListBooks(cursor.getString(cursor.getColumnIndex("name_book")),cursor.getBlob(cursor.getColumnIndex("image")),
                    cursor.getString(cursor.getColumnIndex("abstract")),cursor.getInt(cursor.getColumnIndex("NoLike"))
                    ,cursor.getInt(cursor.getColumnIndex("NoUnlike")),cursor.getInt(cursor.getColumnIndex("noCopy"))
                    ,cursor.getInt(cursor.getColumnIndex("price")));
            listBooks.add(ClistBooks);

        }


        viewBook=new ViewBook(listBooks,getApplicationContext());
        recyclerView.setAdapter(viewBook);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchmenu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search_menu);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerView.removeAllViewsInLayout();
                listBooks.clear();

                Cursor cursor = database.db.rawQuery("select * from books where type='"+type+"' and name_book='"+newText+"'",null);
                while (  cursor.moveToNext()) {

                    Log.d("aaaa", String.valueOf(cursor.getColumnIndex("name_book")));
                    ClistBooks=new ListBooks(cursor.getString(cursor.getColumnIndex("name_book")),cursor.getBlob(cursor.getColumnIndex("image")),
                            cursor.getString(cursor.getColumnIndex("abstract")),cursor.getInt(cursor.getColumnIndex("NoLike"))
                            ,cursor.getInt(cursor.getColumnIndex("NoUnlike")),cursor.getInt(cursor.getColumnIndex("noCopy"))
                            ,cursor.getInt(cursor.getColumnIndex("price")));
                    listBooks.add(ClistBooks);

                }


                viewBook=new ViewBook(listBooks,getApplicationContext());
                recyclerView.setAdapter(viewBook);
                if(newText.isEmpty()){
                    viewBook();

                }
                return false;
            }
        });
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);

        searchView.setIconifiedByDefault(false);

        return true;


    }

}
