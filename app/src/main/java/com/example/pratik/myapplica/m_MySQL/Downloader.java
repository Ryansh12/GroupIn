package com.example.pratik.myapplica.m_MySQL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.pratik.myapplica.m_DataObject.Spacecraft;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;

public class Downloader extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    RecyclerView rv;
    Map<String,String> params;
    ProgressDialog pd;
    public ArrayList<Spacecraft> spacecrafts;
    public static String JSONDATA;
    //JSON
    public Downloader(Context c, String urlAddress, RecyclerView rv,Map<String, String> params,ArrayList<Spacecraft> spacecrafts) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.rv = rv;
        this.params=params;
        this.spacecrafts=spacecrafts;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Retrieve");
        pd.setMessage("Retrieving..please wait");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.downloadData();
    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);

        pd.dismiss();

        if(jsonData==null){
            Toast.makeText(c,"Unsuccessful,No Data Retrieved",Toast.LENGTH_SHORT).show();
        }
        else {
            //PARSER
            DataParser parser = new DataParser(c, jsonData, rv, spacecrafts);
            parser.execute();

            //System.out.println("Status.FINISHED:"+Status.FINISHED);
            //while(parser.getStatus().compareTo(Status.FINISHED)!=0){};
            //System.out.println("PARSER FINISHED");
            //System.out.println(" SpaceCRAFTS IN DOWNLOADER:"+spacecrafts.size());
        }
    }

    private String downloadData()
    {
        HttpURLConnection con=Connector.sendPostRequest(urlAddress,params);

        if(con==null) {
            return null;
        }

        try {
            InputStream is=new BufferedInputStream(con.getInputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(is));

            String line;
            StringBuffer jsonData=new StringBuffer();

            while ((line=br.readLine())!=null){
                jsonData.append(line+"\n");
            }
            JSONDATA= String.valueOf(jsonData);
            System.out.println(" JSONDATAT IN DOWNLOADER:"+jsonData);
            System.out.println(" JSONDATA IN DOWNLOADER:"+jsonData);
            //System.out.println(" SpaceCRAFTS IN DOWNLOADER:"+spacecrafts.get(0).description);
            br.close();
            is.close();

            return jsonData.toString();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
