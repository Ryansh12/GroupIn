package com.example.pratik.myapplica.m_DetailActivity;



import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.prakash.groupin.R;
import com.example.pratik.myapplica.R;
import com.example.pratik.myapplica.m_UI.PicassoClient;

public class DetailActivity extends AppCompatActivity {

    //VIEWS
    ImageView img;
    TextView titleTxt,descTxt,byTxt,dateTxt,categoryTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

        //REFERENCE VIEWS FROM XML
        img= (ImageView) findViewById(R.id.imageViewDetail);
        titleTxt= (TextView) findViewById(R.id.titleViewDetail);
        descTxt= (TextView) findViewById(R.id.descViewDetail);
        byTxt=(TextView)findViewById(R.id.by);
        dateTxt=(TextView)findViewById(R.id.uploaded_date);
        categoryTxt=(TextView)findViewById(R.id.categoryViewDetail);
        //RECEIVE OUR DATA
        Intent i=getIntent();
        //String name=i.getExtras().getString("json");
        String desc=i.getExtras().getString("description");
        String imageurl=i.getExtras().getString("url");
        String title=i.getExtras().getString("title");
        String by=i.getExtras().getString("by");
        String date=i.getExtras().getString("date");
        String cat=i.getExtras().getString("cat");
        //BIND
        titleTxt.setText(title);
        descTxt.setText(desc);
        byTxt.setText(by);
        dateTxt.setText(date);
        categoryTxt.setText(cat);
        PicassoClient.downloadImage(this,imageurl,img);
    }

}