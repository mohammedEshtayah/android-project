package com.example.android_project;

import android.Manifest;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class HomeUser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
private NavigationView navigationView;
  private   View view;
  private RecyclerView recyclerView;
  private List<ListBooks> listBooks= new ArrayList<>();
  private ListBooks ClistBooks;
  private ViewBook viewBook;
  private CircleImageView imageView;
  private DB database;
  private   Cursor cursora;
    private Uri PathImage;
    private Bitmap bitmap;
    private  byte[] image;
    private   byte[] byteImage;
  private FloatingActionButton selectImage;
  private final static int REQUESTCODE_CALL=3000;
    private final static int REQUESTCODE_IMAGE=10;
    private sharedPreference SP;
    private String user_name;
    private TextView users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        navigationView = (NavigationView) findViewById(R.id.vavigation1);
        view = navigationView.inflateHeaderView(R.layout.layoutmenu);
        navigationView.setNavigationItemSelectedListener(this);
        SP=new sharedPreference(this);
        user_name=SP.SP.getString("user",null);

        selectImage=(FloatingActionButton)view.findViewById(R.id.selectimage);
        imageView=(CircleImageView) view.findViewById(R.id.viewcircles);
        users=(TextView)view.findViewById(R.id.Users);
       users.setText(user_name);
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database=new DB();

          cursora = database.db.rawQuery("select image from users where user_name ='"+user_name+"' ",null);
        cursora.moveToFirst();
        if(image!=null) imageView.setImageBitmap(BitmapFactory.decodeByteArray( cursora.getBlob(0)
                ,0,cursora.getBlob(0).length));

       viewBook();
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap im = null;
                Intent intent =new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"data"),REQUESTCODE_IMAGE);
            }
        });
    }

    private void viewBook() {


        Cursor cursor = database.db.rawQuery("select * from books",null);
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

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.arabe:
                startActivity(new Intent(getApplicationContext(),searchType.class).putExtra("type","arabe"));
                break;
            case R.id.buy:
                startActivity(new Intent(getApplicationContext(),Buy.class));
                break;
            case R.id.about:


                break;
            case R.id.call:
              call();

                    break;
            case R.id.location:
                startActivity ( new Intent ( Intent.ACTION_VIEW, Uri.parse ( "geo:0,0?q=Nablus,خان التجار" )) );
                break;
            case R.id.logout:
                SP.writeLoginState(null,"Users",false);
                finish();
                break;
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUESTCODE_CALL)
        {
            if (grantResults.length >0)
            {
                for (int i =0 ; i < grantResults.length; i++)
                    if (grantResults[i]!=PackageManager.PERMISSION_GRANTED)
                        return;

              call();
             }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void call() {
        if (checkSelfPermission ( Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions ( new String[]{Manifest.permission.CALL_PHONE},REQUESTCODE_CALL );
        }
        else {

            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:065555555")));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTCODE_IMAGE&&data.getData()!=null){

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap .compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteImage = stream.toByteArray();
                ContentValues cv = new  ContentValues();
                cv.put("image",  byteImage  );
          database.db.execSQL("update users set image='"+byteImage+"' where user_name='"+user_name+"'");

                imageView.setImageBitmap(BitmapFactory.decodeByteArray( byteImage,0,byteImage.length));
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
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
        Cursor cursora = database.db.rawQuery("select image from users where user_name ='"+user_name+"' ",null);

        while (  cursora.moveToNext()) {
            image =cursora.getBlob(cursora.getColumnIndex("image"));
if(image!=null)   imageView.setImageBitmap(BitmapFactory.decodeByteArray( image,0,image.length));
        }

        Cursor cursor = database.db.rawQuery("select * from books where name_book='"+newText+"'",null);
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


    }}
