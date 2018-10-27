package com.example.pratik.myapplica;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pratik.myapplica.m_DataObject.Spacecraft;
import com.example.pratik.myapplica.m_MySQL.Downloader;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    String usr,userType;
    String urlAddress="http://groupin.orgfree.com/Search.php";
    EditText tv;
    ImageButton srch;
    ArrayList<Spacecraft> spacecrafts=new ArrayList<>();
    RecyclerView rv;
    Map<String, String> params = new HashMap<String, String>();
    Downloader d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        usr=getIntent().getExtras().getString("username");
        userType=getIntent().getExtras().getString("usertype");
        tv=(EditText) findViewById(R.id.Search_text);
        srch=(ImageButton)findViewById(R.id.SearchImgBtn);

       // usr=getIntent().getExtras().getString("username");
      //  userType=getIntent().getExtras().getString("usertype");
        //String cat=getIntent().getExtras().getString("category");


        //params.put("username",usr);
        //params.put("category",cat);
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
                        i=new Intent(SearchActivity.this,Category.class);
                        i.putExtra("username",usr);
                        i.putExtra("usertype",userType);
                        startActivity(i);
                        break;
                    case R.id.nav_search:
                       // startActivity(new Intent(SearchActivity.this,SearchActivity.class));

                        break;
                    case R.id.nav_person:
                        i=new Intent(SearchActivity.this,ProfileActivity.class);
                        i.putExtra("username",usr);
                        i.putExtra("usertype",userType);
                        startActivity(i);
                        break;
                    case R.id.nav_upload:
                        if(userType.compareTo("teacher")==0){
                        i=new Intent(SearchActivity.this,UploadActivity.class);
                        i.putExtra("username",usr);
                        i.putExtra("usertype",userType);
                        startActivity(i);
                        }
                        break;
                }
                return false;
            }
        });
    }
    public void search(View V){
        String srch=tv.getText().toString();
        rv=(RecyclerView)findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        spacecrafts.clear();
        params.put("title",srch);
        d=new Downloader(SearchActivity.this,urlAddress,rv,params,spacecrafts);
        //System.out.println("JSON RECIEVED=>"+Downloader.JSONDATA);
        d.execute();

    }
}
