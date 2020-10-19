package com.example.marikiti.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.marikiti.R;
import com.example.marikiti.utilities.Constant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import gautam.easydevelope.widget.GTextView;

public class Profile_upload extends AppCompatActivity {

    Button btn_pic_upload,btn_next;
    private Toolbar mToolbar;
    public static GTextView title;


    CircleImageView circleImageView;
     String full_name,name,id,email,password,phone,dob,bussiness_name,type;
    Constant c=new Constant();
    Uri path;
    private Bitmap bitmap;
    private final int IMG_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_upload);
        initToolbar();
        fetch_data_customer();

        btn_pic_upload =(Button)findViewById(R.id.btn_add_Pic_upload);
        btn_next       =(Button)findViewById(R.id.btn_next);
        circleImageView =(CircleImageView)findViewById(R.id.profile_image);
        if(circleImageView.getDrawable().getConstantState() == getApplicationContext().getResources().getDrawable(R.mipmap.ic_launcher).getConstantState()){
            Toast.makeText(getApplicationContext(),"match",Toast.LENGTH_SHORT).show();
        }

        btn_pic_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                set_data();
            }
        });
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("Pic Upload");
//        home = mToolbar.findViewById(R.id.home);
//        home.setOnClickListener(this);
        mToolbar.setTitle("");
        //title.setText("Home");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
             path = data.getData();
            Log.d("image_path", String.valueOf(path));
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                circleImageView.setImageBitmap(bitmap);
                circleImageView.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    public void fetch_data_customer()
    {
        full_name=getIntent().getStringExtra(c.USER_FULLNAME);
        name=getIntent().getStringExtra(c.USER_NAME);
        phone=getIntent().getStringExtra(c.USER_PHONE);
        email=getIntent().getStringExtra(c.USER_EMAIL);
        password=getIntent().getStringExtra(c.USER_PASS);
        dob=getIntent().getStringExtra(c.USER_DOB);
        id=getIntent().getStringExtra(c.USER_ID);
    }

    public void set_data()
    {
        Intent addLocation = new Intent(getApplicationContext(), AddLocationActivity.class);
        addLocation.putExtra("type", "customer");
        addLocation.putExtra(c.USER_FULLNAME,full_name);
        addLocation.putExtra(c.USER_NAME,name);
        addLocation.putExtra(c.USER_ID,id);
        addLocation.putExtra(c.USER_EMAIL,email);
        addLocation.putExtra(c.USER_PASS,password);
        addLocation.putExtra(c.USER_PHONE,phone);
        addLocation.putExtra(c.USER_DOB,dob);
        addLocation.setData(path);
        startActivity(addLocation);
    }
    public String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) throws IOException {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();

        int h=image.getHeight();
        int w =image.getWidth();
        h=h/4;
        w=w/4;
        image =Bitmap.createScaledBitmap(image,w,h,true);
        // image = new Compressor(this).compressToBitmap(image);
        // image = SiliCompressor.with(getApplicationContext()).getCompressBitmap(path.toString());
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }
}
