package com.example.marikiti.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.marikiti.R;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.utilities.APP_URL;
import com.example.marikiti.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GEditText;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.PROFILE_IMAGES;

public class MyProfileActivity extends BaseActivity implements View.OnClickListener {
    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    private static String email,phone,street;
    public static GTextView title, et_name,et_id,et_dob;
    private GEditText et_email,et_phone;
    public static ImageView home,camera;
    APP_URL u=new APP_URL();
    GButton btn;
    Constant c=new Constant();
    GCircularImageView   circleImageView;
    SharedPreferences sp;
    String counrtyGet,wardGet,conGet;
    EditText et_street,et_house_name,et_house_number;
    public Spinner countrySpinner,wardSpinner,consitSpinner;
    ArrayList<String> CountryList =new ArrayList<>();
    ArrayList<String> ConsiList =new ArrayList<>();
    ArrayList<String> WardList =new ArrayList<>();
    Uri path;

    private Bitmap bitmap;
    private final int IMG_REQUEST=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_my_profile);
        sp=mContext.getSharedPreferences(c.USER_DETAILS,MODE_PRIVATE);

        initToolbar();
        findViewID();
        fetch_post();

    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(mContext).clearDiskCache();
            }
        }).start();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("My Profile");
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

        circleImageView=findViewById(R.id.img_profile_pics);
        et_name=findViewById(R.id.pr_et_name);
        et_id=findViewById(R.id.pr_et_id_number);
        et_dob=findViewById(R.id.pr_et_date_of_birth);
        et_email=findViewById(R.id.pr_et_email);
        et_phone=findViewById(R.id.pr_et_mobile);
        //et_address=findViewById(R.id.pr_et_address);
        countrySpinner=findViewById(R.id.spn_county);
        consitSpinner=findViewById(R.id.spn_constituency);
        wardSpinner=findViewById(R.id.spn_ward);
        et_house_name=findViewById(R.id.reg_house_name);
        et_house_number=findViewById(R.id.reg_house_number);
        et_street=findViewById(R.id.reg_street);
        camera =findViewById(R.id.iv_camera);
        camera.setOnClickListener(this);
        btn=findViewById(R.id.btn_update);
        btn.setOnClickListener(this);


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

            case R.id.btn_update:
                validate();
                break;
            case R.id.iv_camera:
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);
                break;
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

            } catch (IOException e) {
                e.printStackTrace();
            }
            circleImageView.setImageBitmap(bitmap);




        }

    }
    public void fetch_post()
    {

        final ProgressDialog load=ProgressDialog.show(mContext,"Loading..","Please Wait..!",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, u.EDIT_PROFILE, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {

                Log.d("response",response);
                if (response.equals(""))
                {

                }else
                {
                    try {
                        JSONObject object=new JSONObject(response);
                        et_name.setText(object.getString("full_name"));
                        et_id.setText(object.getString("id_no"));
                        et_dob.setText(object.getString("dob"));
                        et_email.setText(object.getString("user_email"));
                        et_phone.setText(object.getString("phone_no"));
                        counrtyGet=object.getString("country");
                        conGet=object.getString("constituency");;
                        wardGet=object.getString("ward");;
                        et_street.setText(object.getString("street"));
                        et_house_name.setText(object.getString("h_name"));
                        et_house_number.setText(object.getString("h_no"));
                        profileImage(object.getString("profile_pic"));
                        //et_address.setText(object.getString("street"));
                        setupSpinner();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                load.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                Log.d("response_error",error.toString());
                load.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("muserid",sp.getString(c.USER_KEY,""));
                params.put("no_edit","no");
                return params;
            }
        };

        Marikiti.getInstance().addToRequestQueue(stringRequest);
    }


    public void update()
    {

        final ProgressDialog load=ProgressDialog.show(mContext,"Loading..","Please Wait..!",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, u.EDIT_PROFILE, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {

                Log.d("response",response);
                if (response.equals("1"))
                {
                    Toast.makeText(mContext,"Successful Update Profile. !",Toast.LENGTH_LONG).show();
                }else
                {

                    Toast.makeText(mContext,"Failed Update Profile. !",Toast.LENGTH_LONG).show();

                }
                load.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Please select profile image and fill all field",Toast.LENGTH_LONG).show();
                Log.d("response_error",error.toString());
                load.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("muserid",sp.getString(c.USER_KEY,""));
                params.put("muser_email",email);
                params.put ("muser_fullname",et_name.getText().toString());
                params.put("muser_phone",phone);
                params.put("m_street",street);
                params.put("country",countrySpinner.getSelectedItem().toString());
                params.put("constituency",consitSpinner.getSelectedItem().toString());
                params.put("ward",wardSpinner.getSelectedItem().toString());
                params.put("street",et_street.getText().toString());
                params.put("h_name",et_house_name.getText().toString());
                params.put("h_no",et_house_number.getText().toString());
                try {
                    if (bitmap!=null)
                    {
                        params.put("profile_pic",encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 90));
                    }

                } catch (IOException e) {
                    Log.d("response Error","Error"+e);
                    e.printStackTrace();
                }


                return params;
            }
        };

        Marikiti.getInstance().addToRequestQueue(stringRequest);
    }

    public void validate()
    {
        email=et_email.getText().toString();
        phone=et_phone.getText().toString();
        street=et_street.getText().toString();
        if (email.isEmpty())
        {
            et_email.setError(c.ERROR_EMAIL);
            et_email.requestFocus();
        }else if (phone.isEmpty())
        {
            et_phone.setError(c.ERROR_MOBILE);
            et_phone.requestFocus();
        }else
        {
            update();
        }


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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, CountryList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    countrySpinner.setAdapter(adapter);
                    //select country name from list .
                    if (counrtyGet != null) {
                        int spinnerPosition = adapter.getPosition(counrtyGet);
                        Log.d("position","posisiotn"+spinnerPosition+counrtyGet);
                        countrySpinner.setSelection(spinnerPosition);
                    }else
                    { countrySpinner.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, CountryList));
                    }
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

                    //select consit
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, ConsiList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    consitSpinner.setAdapter(adapter);
                    //select country name from list .
                    if (conGet != null) {
                        int spinnerPosition = adapter.getPosition(conGet);
                        Log.d("position","posisiotn"+spinnerPosition+conGet);
                        consitSpinner.setSelection(spinnerPosition);
                    }else
                    {   consitSpinner.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, CountryList));
                    }

                    //  consitSpinner.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, ConsiList));
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
                    //select ward

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, WardList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    wardSpinner.setAdapter(adapter);
                    //select country name from list .
                    if (wardGet != null) {
                        int spinnerPosition = adapter.getPosition(wardGet);
                        Log.d("position","posisiotn"+spinnerPosition+wardGet);
                        wardSpinner.setSelection(spinnerPosition);
                    }else
                    {         wardSpinner.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, CountryList));
                    }
                    // wardSpinner.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, WardList));
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

    public void profileImage(final String i)
    {
        if (!TextUtils.isEmpty(i)) {


            Glide.with(mContext)
                    .load(Uri.parse(PROFILE_IMAGES + i))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(circleImageView);


        }




    }

}
