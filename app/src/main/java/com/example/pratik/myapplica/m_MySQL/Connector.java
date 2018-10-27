package com.example.pratik.myapplica.m_MySQL;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class Connector {
    private static HttpURLConnection httpConn;
    public static HttpURLConnection connect(String urlAddress)
    {
        try{
            URL url=new URL(urlAddress);
            httpConn= (HttpURLConnection) url.openConnection();

            httpConn.setRequestMethod("GET");
            httpConn.setConnectTimeout(20000);
            httpConn.setReadTimeout(20000);
            httpConn.setDoInput(true);

            return httpConn;

        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public static HttpURLConnection sendPostRequest(String requestURL,Map<String, String> params){
        httpConn=null;
        try {
            URL url = new URL(requestURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setUseCaches(false);
            httpConn.setConnectTimeout(200000);
            httpConn.setReadTimeout(200000);
//        httpConn.setRequestProperty("Cookie",cookies);
            httpConn.setRequestProperty("User-Agent", "Mozilla");
            httpConn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            httpConn.addRequestProperty("Referer", "google.com");
            httpConn.setDoInput(true); // true indicates the server returns response
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            //String cookies = httpConn.getHeaderField("Set-Cookie");
            //  System.out.println("Cookies:"+cookies);
            StringBuffer requestParams = new StringBuffer();
            if (params != null && params.size() > 0) {

                httpConn.setDoOutput(true); // true indicates POST request

                // creates the params string, encode them using URLEncoder
                Iterator<String> paramIterator = params.keySet().iterator();
                boolean a = true;
                while (paramIterator.hasNext()) {
                    if (a) {
                        a = false;

                    } else
                        requestParams.append("&");
                    String key = paramIterator.next();
                    String value = params.get(key);
                    requestParams.append(URLEncoder.encode(key, "UTF-8"));
                    requestParams.append("=").append(
                            URLEncoder.encode(value, "UTF-8"));
                }
            }
            // sends POST data
            OutputStreamWriter writer = new OutputStreamWriter(
                    httpConn.getOutputStream());
            writer.write(requestParams.toString());
            writer.flush();
        }
        catch (Exception E){E.printStackTrace();}
        return httpConn;
    }

}
