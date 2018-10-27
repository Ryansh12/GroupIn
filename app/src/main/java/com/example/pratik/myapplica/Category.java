package com.example.pratik.myapplica;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class Category extends AppCompatActivity {
    String usr="",userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        usr=getIntent().getExtras().getString("username");
        userType=getIntent().getExtras().getString("usertype");
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
                        //startActivity(new Intent(Notify.this,Notify.class));
                        break;
                    case R.id.nav_search:

                        i=new Intent(Category.this,SearchActivity.class);
                        i.putExtra("username",usr);
                        i.putExtra("usertype",userType);
                        startActivity(i);
                        //startActivity(new Intent(Category.this,SearchActivity.class));

                        break;
                    case R.id.nav_person:
                        //Intent i;
                        i=new Intent(Category.this,ProfileActivity.class);
                        i.putExtra("username",usr);
                        i.putExtra("usertype",userType);
                        startActivity(i);
                        //startActivity(new Intent(Category.this,ProfileActivity.class));
                        break;
                    case R.id.nav_upload:
                        if(userType.compareTo("teacher")==0) {
                            i = new Intent(Category.this, UploadActivity.class);
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
        public void onClick(View v) {
            Intent i=new Intent(this,Notify.class);
            i.putExtra("username",usr);
            i.putExtra("usertype",userType);
            String ex="category";
            switch(v.getId()) {
                case R.id.first_canteen:
                   i.putExtra(ex,"canteen");
                    startActivity(i);
                    //do something
                    break;
                case R.id.second_FE:
                    //do something
                    i.putExtra(ex,"fe");
                    startActivity(i);
                    break;
                case R.id.third_IT:
                    //do something
                    i.putExtra(ex,"it");
                    startActivity(i);
                    break;
                case R.id.fourth_EnTC:
                    //do something
                    i.putExtra(ex,"entc");
                    startActivity(i);
                    break;
                case R.id.fifth_Sports:
                    //do something
                    i.putExtra(ex,"sports");
                    startActivity(i);
                    break;
                case R.id.sixth_Library:
                    //do something
                    i.putExtra(ex,"library");
                    startActivity(i);
                    break;
                case R.id.seventh_StudCount:
                    //do something
                    i.putExtra(ex,"stucounter");
                    startActivity(i);
                    break;
                case R.id.eighth_comp:
                    //do something
                    i.putExtra(ex,"comp");
                    startActivity(i);
                    break;
                case R.id.ninth_Pictoreal:
                    //do something
                    i.putExtra(ex,"pictoreal");
                    startActivity(i);
                    break;
                case R.id.tenth_ACM:
                    //do something
                    i.putExtra(ex,"acm");
                    startActivity(i);
                    break;
                case R.id.eleventh_IEEE:
                    //do something
                    i.putExtra(ex,"ieee");
                    startActivity(i);
                    break;
                case R.id.twelve_ArtCircle:
                    //do something
                    i.putExtra(ex,"arts");
                    startActivity(i);
                    break;
                case R.id.thirteen_EDC:
                    //do something
                    i.putExtra(ex,"edc");
                    startActivity(i);
                    break;
                case R.id.fourteen_INC:
                    //do something
                    i.putExtra(ex,"inc");
                    startActivity(i);
                    break;
                case R.id.fifteen_IET:
                    //do something
                    i.putExtra(ex,"iet");
                    startActivity(i);
                    break;
                case R.id.sixteen_CSI:
                    //do something
                    i.putExtra(ex,"csi");
                    startActivity(i);
                    break;
                case R.id.TnPC:
                    //do something
                    i.putExtra(ex,"tnpc");
                    startActivity(i);
                    break;
                //fourteen_INC


            }
        }
}
