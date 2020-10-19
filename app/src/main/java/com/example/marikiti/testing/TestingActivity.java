package com.example.marikiti.testing;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.marikiti.R;
import com.example.marikiti.adapter.TestingAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gautam.easydevelope.utils.FileUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.marikiti.utilities.APP_URL.MAIN_URL;

public class TestingActivity extends AppCompatActivity {
    private static final String TAG = TestingActivity.class.getSimpleName();

    private ListView listView;
    private ProgressBar mProgressBar;
    private Button btnChoose, btnUpload, btnEsv;

    private ArrayList<Uri> arrayList;
    ProgressDialog dialog;
    private final int REQUEST_CODE_PERMISSIONS = 1;
    private final int REQUEST_CODE_READ_STORAGE = 2;
    final int ACTIVITY_CHOOSE_FILE1 = 3;
    String EXCELPATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        listView = findViewById(R.id.listView);
        mProgressBar = findViewById(R.id.progressBar);

        btnChoose = findViewById(R.id.btnChoose);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    askForPermission();
                } else {
                    showChooser();
                }
            }
        });

        btnUpload = findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //  uploadImagesToServer();
               // uploadFile(EXCELPATH);
                uploadFile();
            }
        });

        btnEsv = findViewById(R.id.btncsv);
        btnEsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCSVFile();
            }
        });

        arrayList = new ArrayList<>();
    }

    private void selectCSVFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(Intent.createChooser(intent, "Open CSV"), ACTIVITY_CHOOSE_FILE1);
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
        Log.d("response", "resquestCode=" + requestCode + " \nresultCode" + resultCode + " \nresultData" + resultData.getData());
        if (requestCode == ACTIVITY_CHOOSE_FILE1) {

            EXCELPATH =  FilePath.getPath(this, Uri.parse(resultData.getDataString()));
            Log.d("response", "Ex=" +  FilePath.getPath(this, Uri.parse(resultData.getDataString())));

        } else if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_READ_STORAGE) {

                if (resultData != null) {
                    if (resultData.getClipData() != null) {
                        int count = resultData.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = resultData.getClipData().getItemAt(currentItem).getUri();
                            currentItem = currentItem + 1;
                            Log.d("response", "image =" + imageUri.toString());
                            try {
                                arrayList.add(imageUri);
                                TestingAdapter mAdapter = new TestingAdapter(TestingActivity.this, arrayList);
                                listView.setAdapter(mAdapter);

                            } catch (Exception e) {
                                Log.e(TAG, "File select error", e);
                            }
                        }
                    } else if (resultData.getData() != null) {

                        final Uri uri = resultData.getData();
//                        Log.i(TAG, "Uri = " + uri.toString());
                        Log.d("response", "image =" + uri.toString());

                        try {
                            arrayList.add(uri);
                            TestingAdapter mAdapter = new TestingAdapter(TestingActivity.this, arrayList);
                            listView.setAdapter(mAdapter);

                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                        }
                    }
                }
            }
        }

    }


//    private void uploadImagesToServer() {
//        if (InternetConnection.checkConnection(TestingActivity.this)) {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(ApiConfig.BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            showProgress();
//
//            // create list of file parts (photo, video, ...)
//            List<MultipartBody.Part> parts = new ArrayList<>();
//
//            // create upload service client
//            ApiConfig service = retrofit.create(ApiConfig.class);
//
//            if (arrayList != null) {
//                // create part for file (photo, video, ...)
//                for (int i = 0; i < arrayList.size(); i++) {
//                    parts.add(prepareFilePart("image"+i, arrayList.get(i)));
//                }
//            }
//
//            // create a map of data to pass along
//            RequestBody description = createPartFromString("www.androidlearning.com");
//            RequestBody size = createPartFromString(""+parts.size());
//            File file1 = new File(EXCELPATH);
//
//
//            Map<String, RequestBody> map = new HashMap<>();
//            RequestBody requestBody = RequestBody.create(MediaType.parse("/"),file1);
//            map.put("file\"; filename=\"" + file1.getName() + "\"", requestBody);
//
//            Call<ResponseBody> call = service.uploadMultiple(description, size, parts,map);
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse( Call<ResponseBody> call,Response<ResponseBody> response) {
//                    hideProgress();
//                    Log.d("response","errp ="+response.isSuccessful());
//                    if(response.isSuccessful())
//                    {
//                        Toast.makeText(TestingActivity.this,
//                                "Images successfully uploaded!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Snackbar.make(findViewById(android.R.id.content),
//                                "string_some_thing_wrong", Snackbar.LENGTH_LONG).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call,Throwable t) {
//                    hideProgress();
//                    Log.e(TAG, "Image upload failed!", t);
//                    Snackbar.make(findViewById(android.R.id.content),
//                            "Image upload failed!", Snackbar.LENGTH_LONG).show();
//                }
//            });
//
//        } else {
//            hideProgress();
//            Toast.makeText(TestingActivity.this,
//            "string_internet_connection_not_available", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void uploadFile()
    {

        Map<String, RequestBody> map = new HashMap<>();
        File file = new File(EXCELPATH);//FileUtils.getFile(this,fileuri1);
        showProgress();
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("/"), file);
        map.put("file\"; filename=\"" + file.getName() + "\"", requestBody);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(MAIN_URL)
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        ApiConfig getResponse=retrofit.create(ApiConfig.class);
        Call<ServerResponse> call = getResponse.inside_image_upload(map);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response)
            {
                if (response.isSuccessful()){
                    hideProgress();
                    if (response.body() != null){
                        ServerResponse serverResponse = response.body();
                        Toast.makeText(TestingActivity.this, serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        String mess =serverResponse.getMessage();
                    }
                }else {
                    Log.d("response",response.toString());
                    Toast.makeText(TestingActivity.this, "problem uploading image", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // hidepDialog();
                hideProgress();
                //Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();
                Log.v("Response gotten is", t.getMessage());
            }
        });
    }

//    private void uploadFile() {
//        ProgressDialog progressDialog=ProgressDialog.show(TestingActivity.this,"laoad","sadfsd",false);
//        progressDialog.show();
//
//        // Map is used to multipart the file using okhttp3.RequestBody    File file = new File(mediaPath);
//        File file = new File(EXCELPATH);
//        // Parsing any Media type file    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
//        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
//        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
//
//        ApiConfig getResponse = ApiConfig.getRetrofit().create(ApiConfig.class);
//        Call call = getResponse.uploadFile(fileToUpload, filename);
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                ServerResponse serverResponse = response.body();
//                if (serverResponse != null) {
//                    if (serverResponse.getSuccess()) {
//                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    assert serverResponse != null;
//                    Log.v("Response", serverResponse.toString());
//                }
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//
//            }
//        });
//    }

    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        btnChoose.setEnabled(false);
        btnUpload.setVisibility(View.GONE);
    }

    private void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        btnChoose.setEnabled(true);
        btnUpload.setVisibility(View.VISIBLE);
    }


    private RequestBody createPartFromString(String descriptionString)
    {
        return RequestBody.create(MediaType.parse(FileUtils.MIME_TYPE_TEXT), descriptionString);
    }


    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // use the FileUtils to get the actual file by uri
        File file = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            file = FileUtils.getFile(this, fileUri);
        }

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse(FileUtils.MIME_TYPE_IMAGE), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    /**
     * Runtime Permission
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

//                Snackbar.make(this.findViewById(android.R.id.content),
//                        "Please grant permissions to write data in sdcard",
//                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
//                        v -> ActivityCompat.requestPermissions(Test.this,
//                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                REQUEST_CODE_PERMISSIONS)).show();
            } else {
                /* Request for permission */
                ActivityCompat.requestPermissions(TestingActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_PERMISSIONS);
            }

        } else {
            showChooser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                showChooser();
            } else {
                // Permission Denied
                Toast.makeText(TestingActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TestingActivity.this);
        final AlertDialog dialog = builder.setMessage("You need to grant access to Read External Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();


        dialog.show();
    }



}
