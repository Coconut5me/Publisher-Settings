package com.lammyngoc.bookstore;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lammyngoc.bookstore.model.Account;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;

public class LoginActivity extends AppCompatActivity {

    EditText edtUserName;
    EditText edtPassword;
    TextView txtMessage;
    int count_exit=0;

    ImageView imgFooter;

    Dialog dialog=null;
    ImageView imgLogo;
    SharedPreferences sharedPreferences;
    String Key_Preferences="LOGIN_PREFERENCE";

    CheckBox chkSaveInfo;

    public static final String DATABASE_NAME = "BookStore.sqlite";
    public static final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;

    private void copyDataBase(){
        try{
            File dbFile = getDatabasePath(DATABASE_NAME);
            if(!dbFile.exists()){
                if(CopyDBFromAsset()){
                    Toast.makeText(LoginActivity.this,
                            "Copy database successful!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity.this,
                            "Copy database fail!", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Log.e("Error: ", e.toString());
        }
    }

    private boolean CopyDBFromAsset() {
        String dbPath = getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
        try {
            InputStream inputStream = getAssets().open(DATABASE_NAME);
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!f.exists()){
                f.mkdir();
            }
            OutputStream outputStream = new FileOutputStream(dbPath);
            byte[] buffer = new byte[1024]; int length;
            while((length=inputStream.read(buffer))>0){
                outputStream.write(buffer,0, length);
            }
            outputStream.flush();  outputStream.close(); inputStream.close();
            return  true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addViews();
        addEvents();

        readLoginInformation();

        copyDataBase();
    }

    private void addEvents() {
        imgFooter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //process for deleting...
                dialog.show();
                return false;
            }
        });
    }

    private void addViews() {
        edtUserName=findViewById(R.id.edtUserName);
        edtPassword=findViewById(R.id.edtPassword);
        txtMessage=findViewById(R.id.txtMessage);
        imgFooter=findViewById(R.id.imgFooter);

        dialog=new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.deleting_custom);


        ImageView imgRemove=dialog.findViewById(R.id.imgRemove);
        ImageView imgCancel=dialog.findViewById(R.id.imgCancel);

        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFooter.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        imgLogo=findViewById(R.id.imgLogo);
        registerForContextMenu(imgLogo);

        chkSaveInfo=findViewById(R.id.chkSaveInfo);
    }

    public void exitApp(View view) {

        //finish();
        confirmExit();
    }
    void confirmExit()
    {
        //Create builder object (instance):
        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
        //set title:
        builder.setTitle("Confirm Exit!");
        //set message (body content)
        builder.setMessage("Are you sure you want to exit?");
        //set icon:
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //set actions button for user interactions:
        builder.setPositiveButton("Có chớ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    finish();

            }
        });
        builder.setNegativeButton("Nẩu nẩu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        //creat dialog object:
        AlertDialog dialog=builder.create();
        dialog.setCanceledOnTouchOutside(false);
        //show dialog for user interactions:
        dialog.show();
    }

    public void openMainActivity(View view) {

        String userName=edtUserName.getText().toString();
        String pwd=edtPassword.getText().toString();
        //if(userName.equalsIgnoreCase("admin") && pwd.equals("123"))
        if(loginSystem(userName,pwd)!=null)
        {
            //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //Intent intent = new Intent(LoginActivity.this, SimpleListBookActivity.class);
            //Intent intent = new Intent(LoginActivity.this, SimpleListBookObjectActivity.class);
            //Intent intent = new Intent(LoginActivity.this, AdvancedListBookObjectActivity.class);
            //Intent intent = new Intent(LoginActivity.this, PublisherBookActivity.class);
            Intent intent = new Intent(LoginActivity.this, PublisherBookSqliteCRUDActivity.class);
            //Intent intent = new Intent(LoginActivity.this, MyContactAdvancedActivity.class);
            startActivity(intent);
            Toast.makeText(
                    LoginActivity.this,
                    "Login Successful!",
                    Toast.LENGTH_SHORT)
                    .show();
            sharedPreferences=getSharedPreferences(Key_Preferences,MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("USER_NAME",userName);
            editor.putString("PASSWORD",pwd);
            editor.putBoolean("SAVED",chkSaveInfo.isChecked());
            editor.commit();
        }
        else
        {
            //Allow login failed
            txtMessage.setText("Login failed, please check your account again");
            Toast.makeText(
                            LoginActivity.this,
                            "Login Failed!",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    void readLoginInformation()
    {
        sharedPreferences=getSharedPreferences(Key_Preferences,MODE_PRIVATE);
        String userName=sharedPreferences.getString("USER_NAME","");
        String pwd=sharedPreferences.getString("PASSWORD","");
        boolean saved=sharedPreferences.getBoolean("SAVED",false);
        if(saved)
        {
            edtUserName.setText(userName);
            edtPassword.setText(pwd);
        }
        else {
            edtUserName.setText("");
            edtPassword.setText("");
        }
        chkSaveInfo.setChecked(saved);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        count_exit++;
        if(count_exit>=3)
        {
            confirmExit();
            count_exit=0;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.contextmenu_logo,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mnuUpdateVersion)
        {

        }
        else if(item.getItemId()==R.id.mnuSharing)
        {

        }
        else if(item.getItemId()==R.id.mnuDirectCall)
        {

        }
        else if(item.getItemId()==R.id.mnuDialUpCall)
        {
            //confirm permission - cop on gg
            Intent intent=new Intent(Intent.ACTION_DIAL);
            Uri uri= Uri.parse("tel:0825035626");
            intent.setData(uri);
            startActivity((intent));
        }
        return super.onContextItemSelected(item);
    }

    public Account loginSystem(String userName, String pwd)
    {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        String sql="select * from Account where UserName='"+userName+"'and PassWord='"+pwd+"'";
        Cursor cursor=database.rawQuery(sql,null);
        if(cursor.moveToNext())
        {
            String usn=cursor.getString(1);
            String p=cursor.getString(2);
            Account ac=new Account(usn,p);
            cursor.close();
            return ac;
        }
        cursor.close();
        return null;
    }
}