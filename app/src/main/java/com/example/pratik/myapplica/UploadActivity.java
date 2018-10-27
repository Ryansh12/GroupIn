package com.example.pratik.myapplica;



import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;
//import java.net.URI;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class UploadActivity extends AppCompatActivity {
    final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    EditText titleEditText,descriptionEditText;
    Spinner sp;
    private ImageView teacherImageView;
    private Button showChooserBtn,sendToMySQLBtn;
    private ProgressBar uploadProgressBar;
    String name,category,description,title;
    File imageFile;
    //$_POST fields->$title=$_POST['title'];
    //                            $des=$_POST['description'];
    //                            $url="http://groupin.orgfree.com/uploads/".$upload_file_name;
    //                            $cat=$_POST['category'];
    //
    /******************************************************************************/

    /*
    Our data object. THE POJO CLASS
     */
    /******************************************************************************/
    /*
    CLASS TO UPLOAD BOTH IMAGES AND TEXT
     */
    public class MyUploader {
        //YOU CAN USE EITHER YOUR IP ADDRESS OR  10.0.2.2 I depends on the Emulator. Make sure you append
        //the `index.php` when making a POST request
        //Most emulators support this
        //private static final String DATA_UPLOAD_URL="http://10.0.2.2/php/spiritualteachers/index.php";
        //if you use genymotion you can use this
        //private static final String DATA_UPLOAD_URL="http://10.0.3.2/php/spiritualteachers/index.php";
        //You can get your ip adrress by typing ipconfig/all in cmd
        private static final String DATA_UPLOAD_URL="http://groupin.orgfree.com/FileUpload.php";

        //INSTANCE FIELDS
        private final Context c;
        public MyUploader(Context c) {this.c = c;}
        /*
        SAVE/INSERT
         */
        public void upload(final View...inputViews)
        {
            if(filePath == null){Toast.makeText(c, "No Data To Save", Toast.LENGTH_SHORT).show();}
            else {

                try {
                    Log.i("FilePAth In MyUploader", String.valueOf(filePath));
                    //String fURL=(getImagePath(filePath));
                    String fstring=getImagePath(filePath);
                    //imageFile = new File(fstring);//filePath is android.net.uri type
                   // imageFile=new File(getImagePath(filePath));
                    System.out.println("------------------------------" +
                            "---------------------------" +
                            "-------------------------" +
                            "--------------------------" +
                            "-----------------------\n" +
                            "}}}}}}}}}}}}}}}}}}}}}}}}}}}\n" +
                            "\nsddsf sd fds g fdsgdfghgd" +
                            "\n dsssssssssssssssssss" +
                            "\nsddddddddddddddddddddddddd" +
                            "\ndssssssssssssssssssdsdsdsdsdsds" +
                            "\n+++++++++++++++++++++++ABSOLUTE FilePAth+++++++++++++++++++++++++++" +
                            "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n" +
                            ""+imageFile.getAbsolutePath()+"\nsddddddddddddddddddddddddddd" +
                            "\nCAn Execeute->" +
                                    imageFile.canExecute()+"\nIs File" +
                            ":"+imageFile.isFile()+"\nFile Exits=>"+imageFile.exists()+"\n"+
                            "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
                    if(imageFile==null){
                        System.out.print("imageFile is empty ...............................\ncvxcvxczxvxc\n");
                    }

                }catch (Exception e){
                    Toast.makeText(c, "Please pick an Image From Right Place, maybe Gallery or File Explorer so that we can get its path."+e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }

                uploadProgressBar.setVisibility(View.VISIBLE);

                AndroidNetworking.upload(DATA_UPLOAD_URL)
                        .addMultipartFile("image",imageFile)
                        .addMultipartParameter("name",name)
                        .addMultipartParameter("description",description)
                        .addMultipartParameter("category",category)
                        .addMultipartParameter("title",title)
                        .setTag("MYSQL_UPLOAD")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if(response != null) {
                                    try{
                                        //SHOW RESPONSE FROM SERVER
                                        String responseString = response.get("STATUS").toString();
                                        Toast.makeText(c, "PHP SERVER RESPONSE : " + responseString, Toast.LENGTH_LONG).show();

                                        if (responseString.equalsIgnoreCase("#SUCCESS#")) {
                                            //RESET VIEWS
                                            EditText nameEditText = (EditText) inputViews[0];
                                            EditText descriptionEditText = (EditText) inputViews[1];
                                            ImageView teacherImageView = (ImageView) inputViews[2];

                                            titleEditText.setText("");
                                            descriptionEditText.setText("");

                                            teacherImageView.setImageResource(R.drawable.placeholder);

                                        } else {
                                            Toast.makeText(c, "PHP WASN'T SUCCESSFUL. ", Toast.LENGTH_LONG).show();
                                        }
                                    }catch(Exception e)
                                    {
                                        e.printStackTrace();
                                        Toast.makeText(c, "JSONException "+e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(c, "NULL RESPONSE. ", Toast.LENGTH_LONG).show();
                                }
                                uploadProgressBar.setVisibility(View.GONE);
                            }
                            @Override
                            public void onError(ANError error) {
                                error.printStackTrace();
                                uploadProgressBar.setVisibility(View.GONE);
                                Toast.makeText(c, "UNSUCCESSFUL :  ERROR IS : \n"+error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }
    }
    /******************************************************************************/

    /*
    Show File Chooser Dialog
     */
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image To Upload"), PICK_IMAGE_REQUEST);
        //Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(pickPhotoIntent, PICK_IMAGE_REQUEST);
    }
    /*
    Receive Image data from FileChooser and set it to ImageView as Bitmap
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Log.i("*****FilePath******", String.valueOf(filePath));
            //String fstring=filePath.getPath().substring(0,filePath.getPath().lastIndexOf('.'));
           // System.out.println("***FilePathAfterCutting**"+fstring);
            String fURL=getImagePath(filePath);
            //Log.i("FIle URL String",fURL);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                teacherImageView.setImageBitmap(bitmap);
                String lastPart=filePath.getLastPathSegment();
                String newFileName=lastPart;
                System.out.println("LAST PATH SEGMENT OF FILE:"+lastPart);

                if(lastPart.lastIndexOf('/')!=-1)
                newFileName=filePath.getLastPathSegment().substring(lastPart.lastIndexOf('/'));
                System.out.println("NEWFILENAME"+newFileName);
                imageFile = new File(getCacheDir(),"UpF");
                imageFile.createNewFile();

//Convert bitmap to byte array

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
               bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                FileOutputStream fos = new FileOutputStream(imageFile);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    Get Image Path provided its  android.net.Uri
     */
    public String getImagePath(Uri uri)
    {
        String[] projection={MediaStore.Images.ImageColumns.DATA};
        Cursor cursor=this.getContentResolver().query(uri,projection,null,null,null);
        if(cursor == null){
            System.out.println("The CURSOR IS NULL");
            return null;
        }
        int columnIndex= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        //int columnIndex=cursor.getColumnIndex(projection[0]);
        cursor.moveToFirst();
        String s=cursor.getString(columnIndex);
        System.out.println("From getImagePathImageFilePath :"+s);
        cursor.close();
        return s;
/*
        Cursor cursor = null;
        String column = "_data";
        String[] projection = { column };
        String s=null;
        try {
            cursor = this.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(column);
                s= cursor.getString(column_index);
            }
        }
        finally {
            if (cursor != null)
                cursor.close();
        }
        return s;
    */
    }



    //Get FilePaTH FOR ALL apiS

    @SuppressLint("NewApi")
    public String getFilePath(Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }




















    /*
    Perform basic data validation
     */
    private boolean validateData()
    {
        //title=titleEditText.getText().toString();
        //description=descriptionEditText.getText().toString();
        System.out.println("=============================================================================");
        System.out.println("TITLE---->"+title);
        System.out.println("CATEGORY---->"+category);
        System.out.println("DESCRIPTION"+description);
        System.out.println("filePath"+filePath);
        System.out.println("=============================================================================");
        System.out.println("=============================================================================");
        if( title == null || description == null){  return false;  }

        if(title == "" || description == ""){  return false;  }

        if(filePath == null){return false;}

        return true;
    }

    /*
    OnCreate method. When activity is created
     */
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        name=getIntent().getExtras().getString("username");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++" +
                "++++++++++++++++++++++++++++++++++++++++++++++++++++" +
                "\n" +
                "|||||||||||||||||||||||||||||||||||||||||||||||||||\\\n" +
                "{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{" +
                "\ndddddddddddddddddddddddddddddddddddddddddddddddddddddd\n"+"dssssssssssssssssssssssssssssssssssssssssssssssss"+"\nUserName===="+name);
        titleEditText=findViewById(R.id.titleEditText);
        descriptionEditText=findViewById(R.id.descriptionEditText);
        showChooserBtn= findViewById(R.id.chooseBtn);
        sendToMySQLBtn=findViewById(R.id.sendBtn);
       // Button openActivityBtn=findViewById(R.id.openActivityBtn);
        teacherImageView=findViewById(R.id.imageView);
        uploadProgressBar=findViewById(R.id.myProgressBar);
        sp=(Spinner)findViewById(R.id.categorySpinner);
        ArrayAdapter<String>  spinAdapter=new ArrayAdapter<String>(UploadActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.categories) );
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(spinAdapter);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
            Toast.makeText(this,"Succesfull",1);
            return;
        }
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
                Toast.makeText(this,"Succesfull",1);
                return;
            }

        }
        showChooserBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                showFileChooser();

            }
        });

        sendToMySQLBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleEditText.getText().toString();
                description = descriptionEditText.getText().toString();
                category = sp.getSelectedItem().toString();
                if (validateData()) {
                    //GET VALUES

                    System.out.println("=============================================================================");
                    System.out.println("TITLE---->"+title);
                    System.out.println("CATEGORY---->"+category);
                    System.out.println("DESCRIPTION"+description);
                    System.out.println("=============================================================================");
                    System.out.println("=============================================================================");
                    //upload data to mysql
                    new MyUploader(UploadActivity.this).upload(titleEditText, descriptionEditText, teacherImageView);
                } else {
                    Toast.makeText(UploadActivity.this, "PLEASE ENTER ALL FIELDS CORRECTLY ", Toast.LENGTH_LONG).show();
                }
            }
        });

       /* openActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UploadActivity.this,Upload_ItemsActivity.class);
                startActivity(intent);
            }
        });*/
    }
}

