package com.example.pratik.myapplica;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.net.*;
public class MainActivity extends AppCompatActivity {


    EditText username, password;
    Button login_btn;
    String pass,usr,userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        login_btn = (Button) findViewById(R.id.btn_login);
        login_btn.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(final View v) {

            try {
                if(!username.getText().toString().matches("") && !password.getText().toString().matches(""))
                sendPOST();
                else{
                    Log.i("No Input","Empty Field not allowed");
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);

                    dlgAlert.setMessage("PLEASE ENTER USERNAME AND PASSWORD");
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    final String POST_PARAMS = "name";

    private void sendPOST() throws IOException {


        String requestURL = "http://groupin.orgfree.com/Authenticate.php";
        /*try {
            HttpUtility.sendGetRequest(requestURL);
            String[] response = HttpUtility.readMultipleLinesRespone();
            for (String line : response) {
                Log.i("Line->",line);
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
        //HttpUtility.disconnect();


        Log.i("Line->","=====================================");
            pass=password.getText().toString();
            usr=username.getText().toString();
        // test sending POST request
        Map<String, String> params = new HashMap<String, String>();
        requestURL = "http://groupin.orgfree.com/Authenticate.php";
        //params.put("email", "prakashps26@gmail.com");
        params.put("password", pass);
        params.put("name",usr);

        try {
            HttpUtility.sendPostRequest(requestURL, params);
            String[] response = HttpUtility.readMultipleLinesRespone();
            int i=0;
            for (String line : response) {
                System.out.println(line);
            }
            if(response.length>=1){
                System.out.println(response[0]);
                if (response[0].contains("#User ID not present in database#") || response[0].contains("#Incorrect Password#")  ) {
                    Log.i("Error:",response[0]);
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

                    dlgAlert.setMessage("wrong password or username");
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
                else
                {   //When login Successfull
                    userType=""+response[0];
                    System.out.println("userType: "+userType);
                    callActivity();
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
        private void callActivity(){
            Intent intent=new Intent(this,Category.class);
            intent.putExtra("username",usr);
            intent.putExtra("usertype",userType.trim());
            startActivity(intent);
        }

}






