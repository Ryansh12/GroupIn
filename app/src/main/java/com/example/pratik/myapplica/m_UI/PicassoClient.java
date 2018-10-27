package com.example.pratik.myapplica.m_UI;

import android.content.Context;
import android.widget.ImageView;

import com.example.pratik.myapplica.R;
import com.example.pratik.myapplica.m_DataObject.Spacecraft;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PicassoClient {
    public static void downloadImage(Context c, String imageUrl, ImageView img) {
        if (imageUrl.length() > 0 && imageUrl != null)
            Picasso.with(c).load(imageUrl).placeholder(R.drawable.backg).into(img);
        else
            Picasso.with(c).load(R.drawable.backg).into(img);

    }
}
