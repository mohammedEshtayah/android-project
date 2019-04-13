package com.example.android_project;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RePassword extends AppCompatActivity {
private EditText cpassword,newpassword,newpassword1;
private Button change;
private DB db;
private String nameuser;
private  Cursor cursor;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_password);
       nameuser= getIntent().getStringExtra("name");
       db=new DB();
        cpassword=(EditText)findViewById(R.id.cpassword);
        newpassword=(EditText)findViewById(R.id.newPassword);
        newpassword1=(EditText)findViewById(R.id.newPassword1);
        change=(Button)findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameuser.equals("Library Nablus")) {
                    cursor = db.db.rawQuery("select password from Library where name_library= '"+nameuser+"'", null);
                 cursor.moveToFirst();
                 if(cursor.getString(cursor.getColumnIndex("password")).equals(cpassword.getText().toString())){
                      if(newpassword.getText().toString().equals(newpassword1.getText().toString())){
                          db.db.execSQL("update Library set password ='"+newpassword1.getText().toString()+"' where name_library= '"+nameuser+"' ");
                      }

                  }
                }else{
                    cursor = db.db.rawQuery("select password from users where user_name= '"+nameuser+"'", null);
                    cursor.moveToFirst();
                    if(cursor.getString(cursor.getColumnIndex("password")).equals(cpassword.getText().toString())){
                        if(newpassword.getText().toString().equals(newpassword1.getText().toString())){
                            db.db.execSQL("update users set password ='"+newpassword1.getText().toString()+"' where user_name= '"+nameuser+"' ");
                        }

                    }


                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
