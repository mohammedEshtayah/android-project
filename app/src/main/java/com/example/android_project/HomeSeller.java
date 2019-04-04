package com.example.android_project;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeSeller extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private View view;
    private sharedPreference SP;
    private DB database;
    private List<ListBooks> listBooks= new ArrayList<>();
    private ListBooks ClistBooks;
    private ViewBook viewBook;
    private CircleImageView imageView;
    private RecyclerView recyclerView;

    private Uri PathImage;
    private Bitmap bitmap;
    private  byte[] image;
    private   byte[] byteImage;
    private FloatingActionButton selectImage;
    private final static int REQUESTCODE_CALL=3000;
    private final static int REQUESTCODE_IMAGE=10;
    private String user_name;
    private TextView users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_seller);

        navigationView = (NavigationView) findViewById(R.id.sellervavigation);
        view = navigationView.inflateHeaderView(R.layout.layoutmenu);
        navigationView.setNavigationItemSelectedListener(this);
        SP=new sharedPreference(this);
        user_name=SP.SP.getString("user",null);

        selectImage=(FloatingActionButton)view.findViewById(R.id.selectimage);
        imageView=(CircleImageView) view.findViewById(R.id.viewcircles);
        users=(TextView)view.findViewById(R.id.Users);
        users.setText(user_name);

        recyclerView=(RecyclerView)findViewById(R.id.srecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database=new DB();

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



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.sADDBOOK:
                startActivity(new Intent(getApplicationContext(),AddBooks.class));
                finish();
                break;

                case R.id.setaboutus:
                    SP.writeLoginState(null,"Libaray",false);
                    finish();
                break;


                case  R.id.sLogout:
                SP.writeLoginState(null,"Libaray",false);
              this.finish();
                break;
        }
        return false;
    }

    private void viewBook() {
        Cursor cursora = database.db.rawQuery("select image from users where user_name ='"+user_name+"' ",null);

        while (  cursora.moveToNext()) {
            image =cursora.getBlob(cursora.getColumnIndex("image"));
            imageView.setImageBitmap(BitmapFactory.decodeByteArray( image,0,image.length));
        }

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
                database.db.execSQL("update Libara set image='"+byteImage+"' where user_name= '"+user_name +"'");
                // database.db.update("users",cv,"user_name= '"+user_name +"',null);

                imageView.setImageBitmap(BitmapFactory.decodeByteArray( byteImage,0,byteImage.length));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
