package com.example.marikiti.activity.MyShops;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.marikiti.utilities.Constant;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.marikiti.R;
import com.example.marikiti.testing.InternetConnection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gautam.easydevelope.utils.FileUtils;
import gautam.easydevelope.widget.GTextView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.marikiti.utilities.APP_URL.EXCEL_UPLOAD;


public class EditStock_Supermarket_Activity extends AppCompatActivity implements View.OnClickListener{
    private static final int REQUEST_CODE_PERMISSIONS = 0;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private Activity activity;
    private Button btnMultipleImage,btnExcelfile,btnUpload;
    private ProgressBar progressZip,progressImage;
    private ArrayList<Uri> arrayList=new ArrayList<>();
    List<String> image_name_list=new ArrayList<>();
    private final int REQUEST_CODE_READ_STORAGE = 2;
    final int ACTIVITY_CHOOSE_FILE1 = 3;
    String EXCELPATH,FILE_NAME;
    private static final String TAG = EditStock_Supermarket_Activity.class.getSimpleName();
    private RequestQueue rQueue;
    Constant c=new Constant();
    TextView success;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.activity=this;
        setContentView(R.layout.activity_edit_stock__supermarket_);
        initToolbar();
        intiView();

    }

    public void intiView()
    {
        btnMultipleImage=findViewById(R.id.btnMultipleImage);
        btnExcelfile=findViewById(R.id.btnExcelfile);
        btnMultipleImage.setOnClickListener(this);
        btnExcelfile.setOnClickListener(this);
        progressImage = findViewById(R.id.progressImage);
        progressZip=findViewById(R.id.progresszip);
        btnUpload=findViewById(R.id.btnSubmit);
        btnUpload.setOnClickListener(this);
        success=findViewById(R.id.successful);
    }
    private void initToolbar()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("Edit Your Stock");
        home = mToolbar.findViewById(R.id.home);
        home.setOnClickListener(this);
        mToolbar.setTitle("");
        //title.setText("Home");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnExcelfile:
                selectCSVFile();

                break;
            case R.id.btnMultipleImage:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    askForPermission();
                } else {
                    showChooser();
                }
                break;

            case R.id.btnSubmit:
                try {

                    if( FILE_NAME.length()>0 &&arrayList.size()>0)
                    {
                        uploadCSV(Uri.parse(EXCELPATH));
                       // uploadImagesToServer();
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"Please select both file than upload.",Toast.LENGTH_LONG).show();
                    }
                }catch (NullPointerException e){
                    Toast.makeText(getApplicationContext(),"Please select both file than upload.",Toast.LENGTH_LONG).show();
                }



                break;
        }
    }

//    private void showChooser() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        startActivityForResult(intent, REQUEST_CODE_READ_STORAGE);
//    }
//
//    private void askForPermission() {
//        if ((ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE) +
//                ContextCompat.checkSelfPermission(this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE))
//                != PackageManager.PERMISSION_GRANTED) {
//            /* Ask for permission */
//            // need to request permission
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
//                    Manifest.permission.READ_EXTERNAL_STORAGE) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(activity,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
////
//            } else {
//                /* Request for permission */
//                ActivityCompat.requestPermissions(activity,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        REQUEST_CODE_PERMISSIONS);
//            }
//
//        } else {
//            showChooser();
//        }
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_CODE_PERMISSIONS)
//        {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission Granted
//                showChooser();
//            } else {
//                // Permission Denied
//                Toast.makeText(activity, "Permission Denied!", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
//
    private void selectCSVFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(Intent.createChooser(intent, "Open CSV"), ACTIVITY_CHOOSE_FILE1);
    }
//
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
//        super.onActivityResult(requestCode, resultCode, resultData);
//
//        if (requestCode == ACTIVITY_CHOOSE_FILE1)
//        {
//            EXCELPATH =  resultData.getDataString();
//            File file=new File(EXCELPATH);
//            FILE_NAME= file.getName();
//
//        } else if (resultCode == RESULT_OK)
//        {
//            if (requestCode == REQUEST_CODE_READ_STORAGE) {
//                if (resultData != null) {
//                    if (resultData.getClipData() != null) {
//                        int count = resultData.getClipData().getItemCount();
//                        int currentItem = 0;
//                        while (currentItem < count) {
//                            Uri imageUri = resultData.getClipData().getItemAt(currentItem).getUri();
//                            currentItem = currentItem + 1;
//
//                            Log.d("Uri Selected", imageUri.toString());
//
//                            try {
//                                arrayList.add(imageUri);
//
//
//                            } catch (Exception e) {
//
//                            }
//                        }
//                    } else if (resultData.getData() != null) {
//
//                        final Uri uri = resultData.getData();
//
//
//                        try {
//                            arrayList.add(uri);
//
//
//                        } catch (Exception e) {
//
//                        }
//                    }
//                }
//
//            }
//        }
//
//    }
//
//
//
//


    private void showProgressZip() {
      progressZip.setVisibility(View.VISIBLE);
        btnExcelfile.setEnabled(false);
        btnUpload.setVisibility(View.GONE);
    }

    private void hideProgressZip() {
        progressZip.setVisibility(View.GONE);
        btnExcelfile.setEnabled(true);
        btnUpload.setVisibility(View.VISIBLE);

    }
    private void showProgressImage() {
        progressImage.setVisibility(View.VISIBLE);
        btnMultipleImage.setEnabled(false);

    }

    private void hideProgressImage() {
        progressImage.setVisibility(View.GONE);
        btnMultipleImage.setEnabled(true);
        btnUpload.setVisibility(View.VISIBLE);
    }

    private void uploadCSV( Uri pdffile){
       showProgressZip();
        InputStream iStream = null;
        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,EXCEL_UPLOAD,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                           hideProgressZip();
                            Log.d("response","CSV="+new String(response.data));
                            rQueue.getCache().clear();
                            if (new String(response.data).equals("1"))
                            {

                                btnExcelfile.setText("CSV File successfully uploaded!");
                             // uploadImagesToServer();// uploadNationalID();
                               uploadImagesToServer();
                            }else
                            {
                                success.setVisibility(View.VISIBLE);
                                success.setText("Fail to upload file.\nPlease try again.");
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                           hideProgressZip();
                            Log.d("response",error.toString());
                            success.setVisibility(View.VISIBLE);
                            success.setText("Fail to upload file.\nPlease try again.");
                        }
                    }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    // params.put("tags", "ccccc");  add string parameters
                    SharedPreferences sp=getSharedPreferences(c.USER_DETAILS, Context.MODE_PRIVATE);
                    Log.d("response","trader_id"+sp.getString(c.TRADER_ID,""));
                    params.put("mtrader_id",sp.getString(c.TRADER_ID,""));
                    return params;
                }


                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                 params.put("filename", new DataPart("fielname.csv" ,inputData));
                    return params;
                }
            };

            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(EditStock_Supermarket_Activity.this);
            rQueue.add(volleyMultipartRequest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    private void showChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_READ_STORAGE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == ACTIVITY_CHOOSE_FILE1)
        {
            EXCELPATH =  resultData.getDataString();
            File file=new File(EXCELPATH);
            FILE_NAME= file.getName();

        } else if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_READ_STORAGE) {
                if (resultData != null) {
                    if (resultData.getClipData() != null) {
                        int count = resultData.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = resultData.getClipData().getItemAt(currentItem).getUri();
                            currentItem = currentItem + 1;

                            Log.d("Uri Selected", imageUri.toString());

                            try {
                                arrayList.add(imageUri);


                            } catch (Exception e) {
                                Log.e(TAG, "File select error", e);
                            }
                        }
                    } else if (resultData.getData() != null) {

                        final Uri uri = resultData.getData();
                        Log.i(TAG, "Uri = " + uri.toString());

                        try {
                            arrayList.add(uri);


                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                        }
                    }
                }
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void uploadImagesToServer() {

        if (InternetConnection.checkConnection(EditStock_Supermarket_Activity.this)) {
            showProgressImage();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            // create list of file parts (photo, video, ...)
            List<MultipartBody.Part> parts = new ArrayList<>();

            // create upload service client
            ApiService service = retrofit.create(ApiService.class);

            if (arrayList != null) {
                // create part for file (photo, video, ...)
                for (int i = 0; i < arrayList.size(); i++) {
                    parts.add(prepareFilePart("image"+i, arrayList.get(i)));
                }
            }

            // create a map of data to pass along
            RequestBody description = createPartFromString("www.androidlearning.com");
            RequestBody size = createPartFromString(""+parts.size());

            // finally, execute the request
            Call<ResponseBody> call = service.uploadMultiple(description, size, parts);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {

                    if(response.isSuccessful()) {
                        btnMultipleImage.setText("Images successfully uploaded!");
                        Toast.makeText(activity,
                                "Images successfully uploaded!", Toast.LENGTH_SHORT).show();
                    } else {
                        btnMultipleImage.setText("Some thing wrong !");
                        Toast.makeText(activity,
                                "Image upload failed!", Toast.LENGTH_SHORT).show();
                    }
                    hideProgressImage();
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    btnMultipleImage.setText("Some thing wrong !");
                    Toast.makeText(activity,
                            "Image upload failed!", Toast.LENGTH_SHORT).show();
                    hideProgressImage();
                    Log.d("response",t.getMessage().toString());
                }
            });

        } else {
            hideProgressImage();
            Toast.makeText(activity,
                    "internet connection not available", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse(FileUtils.MIME_TYPE_TEXT), descriptionString);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create (MediaType.parse(FileUtils.MIME_TYPE_IMAGE), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    /**
     *  Runtime Permission
     */
    private void askForPermission() {
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE))
                != PackageManager.PERMISSION_GRANTED) {
            /* Ask for permission */
            // need to request permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Snackbar.make(this.findViewById(android.R.id.content),
                        "Please grant permissions to write data in sdcard",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        REQUEST_CODE_PERMISSIONS);
                            }
                        }).show();
            } else {
                /* Request for permission */
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_PERMISSIONS);
            }

        } else {
            showChooser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                showChooser();
            } else {
                // Permission Denied
                Toast.makeText(activity, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}