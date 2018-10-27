package com.example.pratik.myapplica.m_MySQL;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.pratik.myapplica.RecyclerItemClickListener;
import com.example.pratik.myapplica.m_DataObject.Spacecraft;
import com.example.pratik.myapplica.m_DetailActivity.DetailActivity;
import com.example.pratik.myapplica.m_UI.CustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataParser extends AsyncTask<Void,Void,Integer> {

    Context c;
    String jsonData;
    RecyclerView rv;
    ProgressDialog pd;
    public ArrayList<Spacecraft> spacecrafts;

    public DataParser(Context c, String jsonData, RecyclerView rv,ArrayList<Spacecraft> spacecrafts) {
        this.c = c;
        this.jsonData = jsonData;
        this.rv = rv;
        this.spacecrafts=spacecrafts;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(c);
        pd.setTitle("parse");
        pd.setMessage("Parsing......Please Wait");
        pd.show();
    }

    @Override
    protected Integer doInBackground(Void... voids) {

        return this.parseData();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        pd.dismiss();
        if (result==0){
            Toast.makeText(c,"Unable to parse",Toast.LENGTH_SHORT).show();
        }
        else {
            System.out.println("FINISHED");
            //BIND DATA TO RECYCLER VIEW
            Toast.makeText(c,"Adapter being attached",Toast.LENGTH_SHORT).show();
            CustomAdapter adapter=new CustomAdapter(c,spacecrafts);
            rv.setAdapter(adapter);
            rv.addOnItemTouchListener(new RecyclerItemClickListener(c, rv, new RecyclerItemClickListener
                    .OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //handle click events here
                    //Intent i=new Intent(Notify.this,Category.class);
                  //  i.putExtra("username",usr);
                   // i.putExtra("usertype",userType);

                    System.out.println("Position:"+position);
                    Spacecraft s=spacecrafts.get(position);
                    System.out.println("Selected TITLE:"+spacecrafts.get(position).description);
                    Intent i=new Intent(c, DetailActivity.class);
                    i.putExtra("title",s.title);
                    i.putExtra("description",s.description);
                    i.putExtra("by",s.by);
                    i.putExtra("date",s.date_uploaded);
                    i.putExtra("url",s.imageUrl);
                    i.putExtra("cat",s.cat);
                    c.startActivity(i);
                   // startActivity(i);
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    //handle longClick if any
                }
            }));
        }
    }
    private int parseData(){
        try {
            JSONArray ja=new JSONArray(jsonData);
            JSONObject jo=null;
            spacecrafts.clear();
            Spacecraft spacecraft;
            for (int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                int id=jo.getInt("id");
                String title=jo.getString("title");
                String imageUrl=jo.getString("url");
                String date_uploaded=jo.getString("date_uploaded");
                String description=jo.getString("description");
                String by=jo.getString("up_by");
                String cat=jo.getString("category");
                spacecraft=new Spacecraft();
                spacecraft.setId(id);
                spacecraft.setName(title);
                spacecraft.setImageUrl(imageUrl);
                spacecraft.date_uploaded=date_uploaded;
                spacecraft.by=by;
                spacecraft.cat=cat;
                spacecraft.description=description;
                spacecrafts.add(spacecraft);
            }
            System.out.println("SpaceCRAFTS OBJECT[0] in DataParser:"+spacecrafts.get(0).getName());
            System.out.println("SpaceCRAFTS OBJECT[0] in DataParser:"+spacecrafts.get(0).description);
            return 1;
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return 0;
    }
}
