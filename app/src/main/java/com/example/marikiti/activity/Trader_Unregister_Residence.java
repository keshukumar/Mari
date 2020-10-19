package com.example.marikiti.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.model.Trader;
import com.example.marikiti.utilities.APP_URL;
import com.example.marikiti.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.ADD_ID;

public class Trader_Unregister_Residence extends AppCompatActivity implements View.OnClickListener{

    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    public static GCircularImageView profile_pic;
    EditText et_street,et_house_name,et_house_number;
    Trader model=new Trader();
    private Button btn_add_shops,btn_id_card;
    ImageView btn_profile_pic,btn_profile_camera;
    APP_URL u=new APP_URL();
    Constant c=new Constant();
    public Spinner countrySpinner,wardSpinner,consitSpinner;
    private final int IMG_REQUEST=1, CARD_IMAGE=2,IMG_CAMERA=3;
    private Bitmap bitmap,bitmap2;
    private CheckBox cb_terms;
    ArrayList<String> CountryList =new ArrayList<>();
    ArrayList<String> ConsiList =new ArrayList<>();
    ArrayList<String> WardList =new ArrayList<>();
    RadioGroup radioGroup;
    String EXTEN="jpg",NATIONAL_ID,USER_ID;

    CheckBox checkBox;

    private RequestQueue rQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_trader__unregister__residence);
        initToolbar();
        findViewID();
        setupSpinner();

    }


    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
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


    private void findViewID() {

        btn_add_shops = findViewById(R.id.btn_trader_unreigster_signup);
        btn_add_shops.setOnClickListener(this);
        countrySpinner=findViewById(R.id.spn_county);
        consitSpinner=findViewById(R.id.spn_constituency);
        wardSpinner=findViewById(R.id.spn_ward);
        et_house_name=findViewById(R.id.reg_house_name);
        et_house_number=findViewById(R.id.reg_house_number);
        et_street=findViewById(R.id.reg_street);
        btn_profile_pic=findViewById(R.id.btn_trader_unregister_profilepic);
        btn_profile_pic.setOnClickListener(this);
        btn_profile_camera=findViewById(R.id.btn_trader_unregister_camerapic);
        btn_profile_camera.setOnClickListener(this);
        profile_pic=findViewById(R.id.iv_trader_unregister_pic);
        btn_id_card=findViewById(R.id.btn_trader_unregister_idcard);
        btn_id_card.setOnClickListener(this);

        cb_terms=findViewById(R.id.cb_trader_unregister_terms);
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

    }


    public void validate()
    {
        if (wardSpinner.getSelectedItem().toString().isEmpty())
        {
            Toast.makeText(mContext,"Please Select Ward.!",Toast.LENGTH_LONG);
        }else if (consitSpinner.getSelectedItem().toString().isEmpty())
        {
            Toast.makeText(mContext,"Please Select Constituency.!",Toast.LENGTH_LONG);
        }else if (countrySpinner.getSelectedItem().toString().isEmpty())
        {
            Toast.makeText(mContext,"Please Select Country.!",Toast.LENGTH_LONG);
        }else if (!cb_terms.isChecked())
        {
            Toast.makeText(mContext,"Please check terms and conditions.!",Toast.LENGTH_LONG);
        }else if (et_street.getText().toString().isEmpty() && et_house_number.getText().toString().isEmpty())
        {
            Toast.makeText(mContext,"Please fill all fields.!",Toast.LENGTH_LONG);
        }else
        {
            signup_volley_for_trader();
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
            case R.id.home:
                Intent home = new Intent(mContext, HomeActivity.class);
                startActivity(home);
                finish();
                break;


            case R.id.btn_trader_unreigster_signup:
                validate();
                break;

            case R.id.btn_trader_unregister_profilepic:
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);
                break;


            case R.id.btn_trader_unregister_idcard:
                Intent intents=new Intent();
                if (EXTEN.equals("jpg"))
                {
                    intents.setType("image/*");
                }else if (EXTEN.equals("pdf"))
                {
                    intents.setType("application/*");
                }else if (EXTEN.equals("docx"))
                {
                    intents.setType("application/*");
                }
                intents.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intents,IMG_REQUEST);
                break;

            case R.id.btn_trader_unregister_camerapic:
                Intent intent3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent3, IMG_CAMERA);
                break;
        }
    }




    public void signup_volley_for_trader()
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);
        StringRequest request=new StringRequest(Request.Method.POST, u.SIGNUP_TRADER, new Response.Listener<String>() {
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
                            uploadCSV( Uri.parse(NATIONAL_ID));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (response.equals("1"))
                    {

                        load.dismiss();

                    }else if (response.equals("2"))
                    {
                        load.dismiss();
                        Toast.makeText(getApplicationContext(),"Fail Add Trader. Email Id Already Register.!",Toast.LENGTH_LONG).show();
                    }else if (response.equals("3"))
                    {
                        load.dismiss();
                        Toast.makeText(getApplicationContext(),"Fail Add Trader. Mobile Number Already Register.!",Toast.LENGTH_LONG).show();
                    }else if (response.equals("4"))
                    {
                        load.dismiss();
                        Toast.makeText(getApplicationContext(),"Fail . Add Trader Already Register.!",Toast.LENGTH_LONG).show();
                    }else if (response.equals("5"))
                    {
                        load.dismiss();
                        Toast.makeText(getApplicationContext(),"Fail Add Trader. ID No. Already Register.!",Toast.LENGTH_LONG).show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Fail Login. Internet Connection Fail.!",Toast.LENGTH_LONG);
                load.dismiss();

            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError
            {
                Map<String,String> params=new HashMap<>();
                params.put(c.USER_FULLNAME,model.getFull_name());
                params.put(c.USER_NAME,model.getUser_name());
                params.put(c.USER_PHONE,model.getPhone_no());
                params.put(c.USER_ID,model.getId_no());
                params.put(c.BUSSINESS_NAME,model.getBusinessName());
                params.put(c.USER_EMAIL,model.getUser_email());
                params.put(c.USER_PASS,model.getPassword());
                params.put(c.USER_COUNTRY,countrySpinner.getSelectedItem().toString());
                params.put(c.USER_CONSTI,consitSpinner.getSelectedItem().toString());
                params.put(c.USDER_WARD,wardSpinner.getSelectedItem().toString());
                params.put(c.USER_STREET,et_street.getText().toString().trim());
                params.put(c.USER_HOUSE_NAME,et_house_name.getText().toString().trim());
                params.put(c.USER_HOUSE_NUMBER,et_house_number.getText().toString().trim());
                try {
                    params.put("profile_pic",encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 90));
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(request);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                NATIONAL_ID= data.getDataString();
                //bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                btn_id_card.setText( path.getLastPathSegment());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                profile_pic.setImageBitmap(bitmap);
                profile_pic.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if (requestCode == IMG_CAMERA && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                profile_pic.setImageBitmap(bitmap);
                profile_pic.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if (requestCode == CARD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri path = data.getData();
            try {
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                btn_id_card.setText("Succesful Attach File");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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

    public String card_encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) throws IOException {
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

    private void  uploadCSV( Uri pdffile){
        final ProgressDialog dialog= ProgressDialog.show(Trader_Unregister_Residence.this,"Load...","",false);
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

                                Toast.makeText(getApplicationContext(),"Successful Add Trader .!",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(mContext,AddTraderActivity.class)
                                        .putExtra("status","register"));
                                finish();

                            }else
                            {
                                Toast.makeText(Trader_Unregister_Residence.this,"Fail To Register.",Toast.LENGTH_LONG).show();
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
                    params.put("id_image", new DataPart(btn_id_card.getText().toString() ,inputData));
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(Trader_Unregister_Residence.this);
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
