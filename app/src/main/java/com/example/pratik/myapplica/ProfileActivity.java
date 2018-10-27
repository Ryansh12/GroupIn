package com.example.pratik.myapplica;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pratik.myapplica.m_DataObject.Spacecraft;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    String usr,userType;
    String Name,Email;
    int Phone;
    TextView textViewUsername;
    TextView textViewUserEmail;
    TextView textViewUserPhone;
    public static String urlAddress="http://groupin.orgfree.com/Profile.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        usr=getIntent().getExtras().getString("username");
        userType=getIntent().getExtras().getString("usertype");
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewUserEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewUserPhone=(TextView)findViewById(R.id.textViewPhone);
        //userType="student";
        //Map<String, String> params = new HashMap<String, String>();
        //params.put("username",usr);

        //Name Email
        try {
            sendPOST();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BottomNavigationView bottombar=(BottomNavigationView)findViewById(R.id.navigationView);
        if(userType.compareTo("teacher")==0)
            bottombar.inflateMenu(R.menu.bottom_navigation_teacher);
        else
            bottombar.inflateMenu(R.menu.bottom_navigation);
        //menu.add(Menu.NONE, MENU_ITEM_ID_ONE, Menu.NONE, getString(R.string.str_menu_one)).setIcon(R.drawable.ic_action_one);
        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Intent i;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_notification:
                        i=new Intent(ProfileActivity.this,Category.class);
                        i.putExtra("username",usr);
                        i.putExtra("usertype",userType);
                        startActivity(i);
                        break;
                    case R.id.nav_search:
                        i=new Intent(ProfileActivity.this,SearchActivity.class);
                        i.putExtra("username",usr);
                        i.putExtra("usertype",userType);
                        startActivity(i);

                        break;
                    case R.id.nav_person:

                        //startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
                        break;
                    case R.id.nav_upload:
                        if(userType.compareTo("teacher")==0) {
                            i = new Intent(ProfileActivity.this, UploadActivity.class);
                            i.putExtra("username", usr);
                            i.putExtra("usertype", userType);
                            startActivity(i);
                            break;
                        }

                }
                return false;
            }
        });
    }

    public void getUserDetails() {

    }

    private void sendPOST() throws IOException {


        String requestURL = "http://groupin.orgfree.com/Authenticate.php";
        // test sending POST request
        Map<String, String> params = new HashMap<String, String>();
        requestURL = "http://groupin.orgfree.com/Profile.php";
        //params.put("email", "prakashps26@gmail.com");
        params.put("name",usr);

        try {
            HttpUtility.sendPostRequest(requestURL, params);
            String jsonData = HttpUtility.readSingleLineRespone();
           // int i=0;
            Log.i("Response",jsonData);

            if(jsonData!=null){//When a response is recieved from server.
                try {
                    JSONArray ja=new JSONArray(jsonData);
                    JSONObject jo=null;

                    for (int i=0;i<ja.length();i++){
                        jo=ja.getJSONObject(i);
                        //int id=jo.getInt("id");
                        String name=jo.getString("name");
                        String email=jo.getString("email");
                        String Phone=jo.getString("phone");


                        textViewUserEmail.setText(email);
                        textViewUsername.setText(name);
                        textViewUserPhone.setText(Phone);

                    }
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
            else{
                Log.i("Error","No Response");
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

                dlgAlert.setMessage("NO INTERNET CONNECTION");
                dlgAlert.setTitle("Error Message...");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
            }



        } catch (IOException ex) {
            ex.printStackTrace();
        }

        HttpUtility.disconnect();
    }
}
