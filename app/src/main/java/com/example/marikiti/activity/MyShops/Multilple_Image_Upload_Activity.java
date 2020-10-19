package com.example.marikiti.activity.MyShops;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.marikiti.R;
import com.example.marikiti.testing.ApiConfig;
import com.example.marikiti.adapter.MyAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import gautam.easydevelope.widget.GTextView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.marikiti.testing.ServerResponse;

import static com.example.marikiti.utilities.APP_URL.HOSTNAME_;

public class Multilple_Image_Upload_Activity extends AppCompatActivity
{
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private RecyclerView listView;
    private ProgressBar mProgressBar;
    private Button btnChoose;
    int REQUEST_CODE_READ_STORAGE=0;
    ArrayList<Uri> arrayList=new ArrayList<>();
    Activity activity;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity=this;
        setContentView(R.layout.activity_multilple__image__upload_);
        initToolbar();
        listView = findViewById(R.id.listView);
        listView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(activity,1,1,false);
        listView.setLayoutManager(gridLayoutManager);
        mProgressBar = findViewById(R.id.progressBar);
        btnChoose = findViewById(R.id.btnChoose);
        btnChoose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                showChooser();
            }
        });
    }
    private void initToolbar()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("Edit Your Stock");
        home = mToolbar.findViewById(R.id.home);
        mToolbar.setTitle("Upload Images");
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
        // handle arrow click here
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            //close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    private void showChooser()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_READ_STORAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData)
    {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == REQUEST_CODE_READ_STORAGE)
            {
                if (resultData != null)
                {
                    if (resultData.getClipData() != null) {
                        int count = resultData.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count)
                        {
                            Uri imageUri = resultData.getClipData().getItemAt(currentItem).getUri();
                            currentItem = currentItem + 1;
                            Log.d("Uri Selected", imageUri.toString());
                            try
                            {
                                arrayList.add(imageUri);
                                MyAdapter mAdapter = new MyAdapter(activity, arrayList);
                                listView.setAdapter(mAdapter);

                            } catch (Exception e)
                            {
                                Log.d("response", "File select error", e);
                            }
                        }
                    } else if (resultData.getData() != null) {

                        final Uri uri = resultData.getData();
                        Log.d("response", "Uri = " + uri.toString());
                        try
                        {   arrayList.add(uri);
                            MyAdapter mAdapter = new MyAdapter(activity, arrayList);
                            listView.setAdapter(mAdapter);

                        } catch (Exception e) {
                            Log.d("response", "File select error", e);
                        }
                    }
                }
            }
        }
    }
    private void upload(String file)
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.....");
        progressDialog.show();
        Map<String, RequestBody> map = new HashMap<>();
        File file1 = new File(file);

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"),file1);
        RequestBody requestBody1 =RequestBody.create(MediaType.parse("text.plain"),"upload_supermarket");
        map.put("image\"; filename=\"" + file1.getName() + "\"", requestBody);
        map.put("type",requestBody1);

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(HOSTNAME_).addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit =builder.build();
        ApiConfig getresponse =retrofit.create(ApiConfig.class);
        Call<ServerResponse> call =getresponse.upload("token",map);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                Log.d("response","Successfully "+serverResponse.getMessage());
                Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("response",t.toString());
                Toast.makeText(getApplicationContext(),t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
