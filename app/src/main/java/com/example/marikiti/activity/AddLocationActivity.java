package com.example.marikiti.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marikiti.R;
import com.example.marikiti.activity.MyShops.VolleyMultipartRequest;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.utilities.APP_URL;
import com.example.marikiti.utilities.Constant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;
import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GTextView;


import static com.example.marikiti.utilities.APP_URL.ADD_ID;
import static com.example.marikiti.utilities.APP_URL.REGISTER;

public class AddLocationActivity extends BaseActivity implements View.OnClickListener
{
    private RequestQueue rQueue;
    ImageView btn_pic_upload,btn_camera;
    Button btn_next;
    public static GTextView title;
    CircleImageView circleImageView;
    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public static ImageView home;
    EditText et_street,et_house_name,et_house_number;
    TextView national_id_copy;
    static String full_name,name,id,email,password,phone,dob,bussiness_name,type;
    private GButton btn_add_shops;
    APP_URL u=new APP_URL();
    Constant c=new Constant();
    public Spinner countrySpinner,wardSpinner,consitSpinner;
    ArrayList<String> CountryList =new ArrayList<>();
    ArrayList<String> ConsiList =new ArrayList<>();
    ArrayList<String> WardList =new ArrayList<>();
    Bitmap bitmap;
    Uri path;

    private final int IMG_REQUEST=1,IMG_REQUEST_PROFILE=2;
    RadioGroup radioGroup;
    String EXTEN="jpg";
    Uri NATIONAL_ID;
    String USER_ID;

    CheckBox checkBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_add_location);
        initToolbar();
        findViewID();
        setupSpinner();
        checkBox =(CheckBox)findViewById(R.id.checkBox);
        national_id_copy =(TextView)findViewById(R.id.national_id_copyS);
        national_id_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                if (EXTEN.equals("jpg"))
                {
                    intent.setType("image/*");
                }else if (EXTEN.equals("pdf"))
                {
                    intent.setType("application/*");
                }else if (EXTEN.equals("docx"))
                {
                    intent.setType("application/*");
                }
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);
            }
        });

        radioGroup=findViewById(R.id.type);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {

                    case R.id.image:
                        EXTEN="jpg";
                        break;
                    case R.id.pdf:
                        EXTEN="pdf";
                        break;
                    case R.id.wordfile:
                        EXTEN="docx";
                        break;

                }
            }
        });


        btn_pic_upload =findViewById(R.id.btn_add_Pic_upload);
        btn_camera=findViewById(R.id.btn_add_Pic_camera);
        btn_next   =(Button)findViewById(R.id.btn_next);
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
                startActivityForResult(intent,IMG_REQUEST_PROFILE);
            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntent();
            }
        });

    }
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    private void initToolbar() {
        GTextView title;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("Home Address");
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

    private void findViewID() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        boolean registered = intent.getBooleanExtra("registered", false);

        btn_add_shops = findViewById(R.id.btn_add_shops);
        btn_add_shops.setOnClickListener(this);
        countrySpinner=findViewById(R.id.spn_county);
        consitSpinner=findViewById(R.id.spn_constituency);
        wardSpinner=findViewById(R.id.spn_ward);
        et_house_name=findViewById(R.id.reg_house_name);
        et_house_number=findViewById(R.id.reg_house_number);
        et_street=findViewById(R.id.reg_street);

        if(type.equals("customer"))
        {
            fetch_data_customer();
            btn_add_shops.setText("Register");
        }


    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//
            case R.id.btn_add_shops:

                if(checkBox.isChecked())
                {
                     if (bitmap!=null)
                        {
                            if (NATIONAL_ID!=null)
                            {
                                signup_volley_for_user();
                            }else
                            {
                                Toast.makeText(getApplicationContext(),"Please Attach National ID",Toast.LENGTH_LONG).show();
                            }
                        }else
                        {
                            Toast.makeText(getApplicationContext(),"Please Attach Profle Image.",Toast.LENGTH_LONG).show();
                        }


                }else
                {
                    Toast.makeText(getApplicationContext(),"Please Accept Terms And Condition",Toast.LENGTH_LONG).show();
                }

                break;

        }
    }



    public void signup_volley_for_user()
    {

        final ProgressDialog load=ProgressDialog.show(mContext,"Loading..","Please Wait..",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("response",response);
                if (!response.isEmpty())
                {
                    try {
                        JSONObject  jsonObject=new JSONObject(response);
                        JSONArray jsonArray  = jsonObject.getJSONArray("server_response");

                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject o =jsonArray.getJSONObject(i);
                            USER_ID=o.getString("user_id");
                            uploadNationalID(NATIONAL_ID);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (response.equals("1"))
                    {
                        Toast.makeText(getApplicationContext(),"Successful Register .!",Toast.LENGTH_LONG).show();
                        //                        Intent finish = new Intent(mContext, LoginActivity.class);
                        //                        startActivity(finish);
                        load.dismiss();

                    }else if (response.equals("2"))
                    {
                        load.dismiss();
                        Toast.makeText(getApplicationContext(),"Fail . Email Id Already Register.!",Toast.LENGTH_LONG).show();
                    }else if (response.equals("3"))
                    {
                        load.dismiss();
                        Toast.makeText(getApplicationContext(),"Fail . Mobile Number Already Register.!",Toast.LENGTH_LONG).show();
                    }else if (response.equals("4"))
                    {
                        load.dismiss();
                        Toast.makeText(getApplicationContext(),"Fail . User Name Already Register.!",Toast.LENGTH_LONG).show();
                    }else if (response.equals("5"))
                    {
                        load.dismiss();
                        Toast.makeText(getApplicationContext(),"Fail. ID No. Already Register.!",Toast.LENGTH_LONG).show();
                    }


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                load.dismiss();
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError
            {
                Map<String,String> params=new HashMap<>();
                params.put(c.USER_FULLNAME,full_name);
                params.put(c.USER_NAME,name);
                params.put(c.USER_PHONE,phone);
                params.put(c.USER_ID,id);
                params.put(c.USER_DOB,dob);
                params.put(c.USER_EMAIL,email);
                params.put(c.USER_PASS,password);
                params.put(c.USER_COUNTRY,countrySpinner.getSelectedItem().toString());
                params.put(c.USER_CONSTI,consitSpinner.getSelectedItem().toString());
                params.put(c.USDER_WARD,wardSpinner.getSelectedItem().toString());
                params.put(c.USER_STREET,et_street.getText().toString().trim());
                params.put(c.USER_HOUSE_NAME,et_house_name.getText().toString().trim());
                params.put(c.USER_HOUSE_NUMBER,et_house_number.getText().toString().trim());
                params.put("key",EXTEN);
                try {
                    params.put("profile_pic",encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 90));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return params;
            }
        };

        RequestQueue requestQueue =Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }







    public void setupSpinner()
    {
        loadCountrySpinnerData();
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String country=countrySpinner.getItemAtPosition(countrySpinner.getSelectedItemPosition()).toString();
                loadConsiSpinnerData(country);
                // Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        consitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country=consitSpinner.getItemAtPosition(consitSpinner.getSelectedItemPosition()).toString();

                loadWardSpinnerData(country);
                //Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        wardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country=wardSpinner.getItemAtPosition(wardSpinner.getSelectedItemPosition()).toString();

                // Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }


    private void loadCountrySpinnerData() {
        final ProgressDialog load =ProgressDialog.show(mContext,"Loading.","Please Wait..",false,false);

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, u.COUNTRY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    CountryList.clear();
                    Log.d("country",response);
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String country=jsonObject1.getString("country_name");
                        CountryList.add(country);
                        Log.d("country","\n"+ CountryList.get(i));

                    }
                    countrySpinner.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, CountryList));

                    load.dismiss();  }catch (JSONException e){e.printStackTrace();
                    load.dismiss();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                load.dismiss();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void loadConsiSpinnerData(final String country) {
        final ProgressDialog load =ProgressDialog.show(mContext,"Loading.","Please Wait..",false,false);

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, u.CONSI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    ConsiList.clear();
                    Log.d("country",response);
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String country=jsonObject1.getString("consistency_name");

                        ConsiList.add(country);
                        Log.d("country","\n"+ ConsiList.get(i));

                    }
                    consitSpinner.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, ConsiList));
                    load.dismiss(); }catch (JSONException e){e.printStackTrace();
                    load.dismiss();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                load.dismiss();
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError
            {

                Map<String,String> params=new HashMap<>();
                params.put("mcountry_name",country);

                return params;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
    private void loadWardSpinnerData(final String consi) {
        final ProgressDialog load =ProgressDialog.show(mContext,"Loading.","Please Wait..",false,false);

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, u.WARD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    WardList.clear();
                    Log.d("country",response);
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String country=jsonObject1.getString("ward_name");

                        WardList.add(country);
                        Log.d("country","\n"+WardList.get(i));

                    }
                    wardSpinner.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, WardList));
                    load.dismiss();  }catch (JSONException e){e.printStackTrace();
                    load.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                load.dismiss();
            }
        })
        {
            protected Map<String,String> getParams() throws AuthFailureError
            {

                Map<String,String> params=new HashMap<>();
                params.put("mconsistency",consi);

                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                NATIONAL_ID= data.getData();
                national_id_copy.setText( path.getLastPathSegment());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if (requestCode == IMG_REQUEST_PROFILE && resultCode == RESULT_OK && data != null) {
            path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                circleImageView.setImageBitmap(bitmap);
                circleImageView.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if (requestCode == REQUEST_CAMERA)
        {
            onCaptureImageResult(data);
        }


    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap=thumbnail;
        circleImageView.setImageBitmap(thumbnail);
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





    private void uploadNationalID(Uri pdffile){
        final ProgressDialog dialog= ProgressDialog.show(AddLocationActivity.this,"Load...","",false);
        try {

            InputStream iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,ADD_ID,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {

                            Log.d("response","CSV="+new String(response.data));

                           rQueue.getCache().clear();
                            if (new String(response.data).equals("1"))
                            {
                                Toast.makeText(AddLocationActivity.this,"Successful Register.",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(AddLocationActivity.this,LoginActivity.class));
                                finish();

                            }else
                            {
                                Toast.makeText(AddLocationActivity.this,"Fail To Register.",Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("response","error"+error);
                            dialog.dismiss();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("muser_id", USER_ID);
                    return params;
                }

                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    params.put("id_image", new DataPart(phone+"."+EXTEN ,inputData));
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(AddLocationActivity.this);
            rQueue.add(volleyMultipartRequest);



        } catch (FileNotFoundException e) {
            Log.d("response","error 1"+e);

            e.printStackTrace();
        } catch (IOException e) {
            Log.d("response","error 2"+e);
            e.printStackTrace();
        }


    }

}
