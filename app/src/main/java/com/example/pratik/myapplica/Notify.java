package com.example.pratik.myapplica;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.internal.BottomNavigationPresenter;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.widget.Space;

import com.example.pratik.myapplica.m_DataObject.Spacecraft;
import com.example.pratik.myapplica.m_MySQL.Downloader;
import com.example.pratik.myapplica.m_UI.CustomAdapter;
import com.example.pratik.myapplica.m_UI.PicassoClient;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notify extends AppCompatActivity {
    @SuppressLint("ResourceType")
    String usr,userType;
    public static String urlAddress="http://groupin.orgfree.com/Notices.php";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        usr=getIntent().getExtras().getString("username");
        userType=getIntent().getExtras().getString("usertype");
        String cat=getIntent().getExtras().getString("category");
        Log.i("Category",cat);
        Map<String, String> params = new HashMap<String, String>();
        params.put("username",usr);
        params.put("category",cat);
        RecyclerView rv=(RecyclerView)findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        ArrayList<Spacecraft> spacecrafts=new ArrayList<>();
        spacecrafts.clear();
        Downloader d=new Downloader(Notify.this,urlAddress,rv,params,spacecrafts);
        //System.out.println("JSON RECIEVED=>"+Downloader.JSONDATA);
        d.execute();

        //Get object corresponding to BottomNavigationBarNamed navigationView
        BottomNavigationView bottombar=(BottomNavigationView)findViewById(R.id.navigationView);
        if(userType.compareTo("teacher")==0)
        bottombar.inflateMenu(R.menu.bottom_navigation_teacher);
        else
            bottombar.inflateMenu(R.menu.bottom_navigation);
        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId())
                {
                    case R.id.nav_notification:
                        i=new Intent(Notify.this,Category.class);
                        i.putExtra("username",usr);
                        i.putExtra("usertype",userType);
                        startActivity(i);
                        break;
                    case R.id.nav_search:
                        i=new Intent(Notify.this,SearchActivity.class);
                        i.putExtra("username",usr);
                        i.putExtra("usertype",userType);
                        startActivity(i);

                        break;
                    case R.id.nav_person:
                        i=new Intent(Notify.this,ProfileActivity.class);
                        i.putExtra("username",usr);
                        i.putExtra("usertype",userType);
                        startActivity(i);
                        break;
                    case R.id.nav_upload:
                        if(userType.compareTo("teacher")==0) {
                            i = new Intent(Notify.this, UploadActivity.class);
                            i.putExtra("username", usr);
                            i.putExtra("usertype", userType);
                            startActivity(i);
                        }
                        break;
                }
                return false;
            }
        });
    }

}
