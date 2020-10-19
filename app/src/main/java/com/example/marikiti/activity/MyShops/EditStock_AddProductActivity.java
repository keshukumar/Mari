package com.example.marikiti.activity.MyShops;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.marikiti.R;
import com.example.marikiti.activity.HomeActivity;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.model.Stock;
import com.example.marikiti.utilities.APP_URL;
import com.example.marikiti.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.CATEGORY_IMAGES;
import static com.example.marikiti.utilities.APP_URL.FETCH_TRADER_SHOP_CATEGORY;
import static com.example.marikiti.utilities.APP_URL.MAIN_URL;
import static com.example.marikiti.utilities.APP_URL.TRADER_SHOP_CATEGORY;
import static com.example.marikiti.utilities.APP_URL.UPDATE_TRADER_SHOP_CATEGORY;

public class EditStock_AddProductActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    TextView tv_name,tv_shopname;
    private Context mContext;
    GCircularImageView imageView;
    Stock s=new Stock();
    APP_URL u=new APP_URL();
    SharedPreferences sp;
    int IMG_REQUEST=1;
    Bitmap bitmap;
CardView cv_delete;
    GButton btn;
    List<String>quantityList=new ArrayList<>();
    List<String>priceList=new ArrayList<>();
    List<String>proudctpriceList=new ArrayList<>();
    List<String>statusList=new ArrayList<>();
    List<String>product_size_id_list=new ArrayList<>();
    Spinner sp1,sp2,sp3,sp4,sp5,sp6;
    TextView tt1,tt2,tt3,tt4,tt5,tt6;
    EditText at1,at2,at3,at4,at5,at6,tv_desc;
    ToggleButton tb1,tb2,tb3,tb4,tb5,tb6;

    int t1,t2,t3,t4,t5,t6;
    String s1,s2,s3,s4,s5,s6,s_collect,s_delivery,s_post,s_cut;

    ToggleButton tb_collect,tb_delivery,tb_post,tb_cut;
    GButton btn_add,btn_delete;
    JSONArray quantityArray,priceArray,productArray,statusArray,product_size_array;
    Constant c=new Constant();

    String PROUDCT_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext=this;
        setContentView(R.layout.activity_edit_stock__add_product);
        initToolbar();
        sp=mContext.getSharedPreferences(c.USER_DETAILS, MODE_PRIVATE);
        tv_name =findViewById(R.id.editstock_add__productname);
        tv_desc=findViewById(R.id.editstock_add_description);
        imageView=findViewById(R.id.editstockview_image);
        imageView.setOnClickListener(this);
        //   tv_name.setText(getIntent().getStringExtra("tv_name"));

        intiView();
        // try { Glide.with(EditStock_AddProductActivity.this).load("https://marikiti.app/items_images/"+s.getItem_image()).into(imageView); }catch (Exception e){}
        tv_name.setText(s.getItem_name());

        tv_shopname=findViewById(R.id.editstock_shopname);
        tv_shopname.setText(s.getShop_name());

        try{
            PROUDCT_ID=getIntent().getStringExtra("id");
            if (!PROUDCT_ID.isEmpty())
            {
                btn_add.setText("Update");
                cv_delete.setVisibility(View.VISIBLE);
            }
            fetch_Viewdata();

        }catch (Exception e){}
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("Add Product");
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
            case R.id.btn_finish:
                finish();
                break;

            case R.id.btn_add:
                if (btn_add.getText().toString().equals("Update"))
                {
                    updateData();
                }else
                {
                    addData();
                }

                break;

            case R.id.editstockview_image:
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);
                break;

            case R.id.btn_delete:
                AlertDialog diaBox = AskOption();
                diaBox.show();

                break;


        }
    }


    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(EditStock_AddProductActivity.this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete ?")
                .setIcon(R.drawable.delete)

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        deleteItem();
                        dialog.dismiss();

                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    public void deleteItem()
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading..","Please Wait..!",false,false);

        toggleStatus();
        setUint();
        setPriceList();
        setProudctpriceList();
        Log.d("response"," mtrader_shop_item_category_id "+PROUDCT_ID);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                Log.d("response","response="+response);

                if (response.equals("1"))
                { Toast.makeText(getApplicationContext(),"Successful Delete Product",Toast.LENGTH_LONG).show();
                    finish();
                }else
                { Toast.makeText(getApplicationContext(),"Fail Delete Product",Toast.LENGTH_LONG).show();

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
                params.put("type","delete_product");
                params.put("mproduct_id",PROUDCT_ID);

                return params;
            }
        };

        Marikiti.getInstance().addToRequestQueue(stringRequest);

    }
    public void intiView()
    {
        sp1=findViewById(R.id.addproduct_quantity1);
        sp2=findViewById(R.id.addproduct_quantity2);
        sp3=findViewById(R.id.addproduct_quantity3);
        sp4=findViewById(R.id.addproduct_quantity4);
        sp5=findViewById(R.id.addproduct_quantity5);
        sp6=findViewById(R.id.addproduct_quantity6);


        tt1=findViewById(R.id.product_unit_amount1);
        tt2=findViewById(R.id.product_unit_amount2);
        tt3=findViewById(R.id.product_unit_amount3);
        tt4=findViewById(R.id.product_unit_amount4);
        tt5=findViewById(R.id.product_unit_amount5);
        tt6=findViewById(R.id.product_unit_amount5);

        at1=findViewById(R.id.product_unit_price1);
        at2=findViewById(R.id.product_unit_price2);
        at3=findViewById(R.id.product_unit_price3);
        at4=findViewById(R.id.product_unit_price4);
        at5=findViewById(R.id.product_unit_price5);
        at6=findViewById(R.id.product_unit_price6);

        tb1=findViewById(R.id.product_unittoggle1);
        tb2=findViewById(R.id.product_unittoggle2);
        tb3=findViewById(R.id.product_unittoggle3);
        tb4=findViewById(R.id.product_unittoggle4);
        tb5=findViewById(R.id.product_unittoggle5);
        tb6=findViewById(R.id.product_unittoggle6);

        tb_collect=findViewById(R.id.edittextview_tb_select_trader);
        tb_delivery=findViewById(R.id.edittextview_tb_select_trader2);
        tb_cut=findViewById(R.id.edittextview_tb_select_trader4);


        calculateValue();
        spinnerEdit();
        btn=findViewById(R.id.btn_finish);
        btn.setOnClickListener(this);

        btn_add=findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_delete=findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);

        cv_delete=findViewById(R.id.cv_delete);

    }

    public void calculateValue()
    {
        try {

            at1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {


                        int a=Integer.valueOf(String.valueOf(charSequence));
                        int u=Integer.valueOf(sp1.getSelectedItem().toString());
                        t1=a*u;
                        tt1.setText(String.valueOf(t1));
                    }catch (NumberFormatException e){}
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            at2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        int a=Integer.valueOf(String.valueOf(charSequence));
                        int u=Integer.valueOf(sp2.getSelectedItem().toString());
                        t2=a*u;
                        tt2.setText(String.valueOf(t2));
                    }catch (NumberFormatException e){}
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            at3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try{
                        int a=Integer.valueOf(String.valueOf(charSequence));
                        int u=Integer.valueOf(sp3.getSelectedItem().toString());
                        t3=a*u;
                        tt3.setText(String.valueOf(t3));
                    }catch (NumberFormatException e){}
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            at4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        int a = Integer.valueOf(String.valueOf(charSequence));
                        int u = Integer.valueOf(sp4.getSelectedItem().toString());
                        t4 = a * u;
                        tt4.setText(String.valueOf(t4));

                    }catch (NumberFormatException e){}
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            at5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        int a = Integer.valueOf(String.valueOf(charSequence));
                        int u = Integer.valueOf(sp5.getSelectedItem().toString());
                        t5 = a * u;
                        tt5.setText(String.valueOf(t5));
                    }catch (NumberFormatException e){}
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            at6.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try{
                        int a=Integer.valueOf(String.valueOf(charSequence));
                        int u=Integer.valueOf(sp6.getSelectedItem().toString());
                        t6=a*u;
                        tt6.setText(String.valueOf(t6));
                    }catch (NumberFormatException e){}
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


        }catch (EmptyStackException e){}
    }

    public void spinnerEdit()
    {
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                try{
                    String s=at1.getText().toString();
                    if (!s.isEmpty())
                    {
                        int a=Integer.valueOf(s);
                        int u=position+1;
                        t1=a*u;
                        tt1.setText(String.valueOf(t1));
                    }

                }catch (NullPointerException e){}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                try{
                    String s=at2.getText().toString();
                    if (!s.isEmpty())
                    {
                        int a=Integer.valueOf(s);
                        int u=position+1;
                        t2=a*u;
                        tt2.setText(String.valueOf(t2));
                    }
                }catch (NullPointerException e){}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                try{
                    String s=at3.getText().toString();
                    if (!s.isEmpty())
                    {
                        int a=Integer.valueOf(s);
                        int u=position+1;
                        t3=a*u;
                        tt3.setText(String.valueOf(t3));
                    }

                }catch (NullPointerException e){}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                try{
                    String s=at4.getText().toString();
                    if (!s.isEmpty())
                    {
                        int a=Integer.valueOf(s);
                        int u=position+1;
                        t4=a*u;
                        tt4.setText(String.valueOf(t4));
                    }

                }catch (NullPointerException e){}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                try{
                    String s=at5.getText().toString();
                    if (!s.isEmpty())
                    {
                        int a=Integer.valueOf(s);
                        int u=position+1;
                        t5=a*u;
                        tt5.setText(String.valueOf(t5));
                    }

                }catch (NullPointerException e){}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                try{
                    String s=at6.getText().toString();
                    if (!s.isEmpty())
                    {
                        int a=Integer.valueOf(s);
                        int u=position+1;
                        t6=a*u;
                        tt6.setText(String.valueOf(t6));
                    }

                }catch (NullPointerException e){}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void toggleStatus()
    {
        if (tb1.isChecked()) { statusList.add(0,"1");s1="1"; }else { statusList.add(0,"0");s1="0";}
        if (tb2.isChecked()) {statusList.add(1,"1"); s2="1"; }else {   statusList.add(1,"0");s2="0";}
        if (tb3.isChecked()) { statusList.add(2,"1"); s3="1"; }else {   statusList.add(2,"0");s3="0";}
        if (tb4.isChecked()) { statusList.add(3,"1"); s4="1"; }else {   statusList.add(3,"0");s4="0";}
        if (tb5.isChecked()) {  statusList.add(4,"1");s5="1"; }else {   statusList.add(4,"0");s5="0";}
        if (tb6.isChecked()) { statusList.add(5,"1"); s6="1"; }else {   statusList.add(5,"0");s6="0";}


        if (tb_collect.isChecked()) { s_collect="1"; }else {  s_collect="0";}
        if (tb_cut.isChecked()) { s_cut="1"; }else {  s_cut="0";}
        if (tb_delivery.isChecked()) { s_delivery="1"; }else {  s_delivery="0";}

        statusArray=new JSONArray(statusList);
    }


    public void setUint()
    {
        quantityList.add(0, sp1.getSelectedItem().toString());
        quantityList.add(1, sp2.getSelectedItem().toString());
        quantityList.add(2, sp3.getSelectedItem().toString());
        quantityList.add(3, sp4.getSelectedItem().toString());
        quantityList.add(4, sp5.getSelectedItem().toString());
        quantityList.add(5, sp6.getSelectedItem().toString());

        quantityArray=new JSONArray(quantityList);
    }

    public void setPriceList()
    {
        if (at1.getText().toString().isEmpty())
        { priceList.add(0,"0");
        }else
        { priceList.add(0, at1.getText().toString());
        }

        if (at2.getText().toString().isEmpty())
        { priceList.add(1,"0");
        }else
        { priceList.add(1, at2.getText().toString());
        }

        if (at3.getText().toString().isEmpty())
        { priceList.add(2,"0");
        }else
        { priceList.add(2, at3.getText().toString());
        }

        if (at4.getText().toString().isEmpty())
        { priceList.add(3,"0");
        }else
        { priceList.add(3, at4.getText().toString());
        }

        if (at5.getText().toString().isEmpty())
        { priceList.add(4,"0");
        }else
        { priceList.add(4, at5.getText().toString());
        }

        if (at6.getText().toString().isEmpty())
        { priceList.add(5,"0");
        }else
        { priceList.add(5, at6.getText().toString());
        }




        priceArray=new JSONArray(priceList);

    }


    public void setProudctpriceList()
    {
        proudctpriceList.add(0,String.valueOf(t1));
        proudctpriceList.add(1,String.valueOf(t2));
        proudctpriceList.add(2,String.valueOf(t3));
        proudctpriceList.add(3,String.valueOf(t4));
        proudctpriceList.add(4,String.valueOf(t5));
        proudctpriceList.add(5,String.valueOf(t6));

        productArray=new JSONArray(proudctpriceList);

    }


    public void addData()
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading..","Please Wait..!",false,false);

        toggleStatus();
        setUint();
        setPriceList();
        setProudctpriceList();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, TRADER_SHOP_CATEGORY, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                Log.d("response","response="+response);

                if (response.equals("111111"))
                { Toast.makeText(getApplicationContext(),"Successful Submit",Toast.LENGTH_LONG).show();
                    finish();}else
                { Toast.makeText(getApplicationContext(),"fail Submit",Toast.LENGTH_LONG).show(); }
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

                Log.d("keshav","trader="+sp.getString(c.TRADER_ID,""));

                params.put("mshop_id",s.getTrader_shop_id());
                params.put("mproduct_id",s.getTrader_shop_item_list_id());
                params.put("mtrader_id",sp.getString(c.TRADER_ID,""));
                params.put("mproduct_category",s.getItem_name());
                params.put("mcollect_status",s_collect);
                params.put("mdelivery_status",s_delivery);
                params.put("m_want_cut_status",s_cut);
                params.put("munit_quantity",quantityArray.toString());
                params.put("munit_price",priceArray.toString());
                params.put("munit_status",statusArray.toString());
                params.put("mdescription",tv_desc.getText().toString().trim());
                params.put("mproduct_price",productArray.toString());
                try { params.put("mcategory_image",encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 90)); } catch (IOException e) {}
                return params;
            }
        };

        Marikiti.getInstance().addToRequestQueue(stringRequest);
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
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }



    public void fetch_Viewdata()
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading..","Please Wait..!",false,false);

        quantityList.clear();
        priceList.clear();
        product_size_id_list.clear();
        proudctpriceList.clear();
        statusList.clear();

        Log.d("arrayresponse","unit="+quantityArray+"\n price"+priceArray+"\n product price="+productArray+"\n status"+statusArray);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, FETCH_TRADER_SHOP_CATEGORY, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                Log.d("response","response="+response);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    server_response(jsonArray);
                    JSONArray array=jsonObject.getJSONArray("category_quantity");
                    category_quantity(array,load);
                    // category_quantity(array);

                } catch (Exception e) {
                    Log.d("response_error", e.toString());
                    load.dismiss();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }


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

                params.put("mproduct_id",PROUDCT_ID);

                return params;
            }
        };

        Marikiti.getInstance().addToRequestQueue(stringRequest);
    }

    public void server_response(JSONArray jsonArray)
    {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject o = jsonArray.getJSONObject(i);
                tv_shopname.setText(o.getString("shop_name"));
                tv_name.setText(o.getString("item_name"));
                tv_desc.setText(o.getString("mdescription"));
                try { Glide.with(mContext).load(CATEGORY_IMAGES+o.getString("image_name"))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(imageView); }catch (Exception e){}

                if (o.getString("collect_status").equals("0"))
                { tb_collect.setChecked(false);
                }else
                { tb_collect.setChecked(true);
                }

                if (o.getString("deliver_status").equals("0"))
                { tb_delivery.setChecked(false);
                }else
                { tb_delivery.setChecked(true);
                }

                if (o.getString("want_cut_product_status").equals("0"))
                { tb_cut.setChecked(false);
                }else
                { tb_cut.setChecked(true);
                }

            }

        }
        catch(JSONException e){ e.printStackTrace();
        }



    }

    public void category_quantity(JSONArray jsonArray,ProgressDialog dialog)
    {
        try{
            int a;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject o = jsonArray.getJSONObject(i);
                switch (i)
                {
                    case 0:
                        a=Integer.valueOf(o.getString("product_quantity"));
                        if (a!=0){sp1.setSelection(a-1);}
                        at1.setText(  o.getString("unit_price"));
                        tt1.setText(o.getString("product_price"));
                        t1=Integer.valueOf(o.getString("product_price"));
                        if (   o.getString("action_status").equals("0"))
                        { tb1.setChecked(false); }else if (o.getString("action_status").equals("1")) { tb1.setChecked(true); }
                        product_size_id_list.add(0, o.getString("product_size_id"));
                        break;
                    case 1:
                        a=Integer.valueOf(o.getString("product_quantity"));
                        if (a!=0){sp2.setSelection(a-1);}
                        at2.setText(  o.getString("unit_price"));
                        tt2.setText(o.getString("product_price"));
                        t2=Integer.valueOf(o.getString("product_price"));
                        if (   o.getString("action_status").equals("0"))
                        { tb2.setChecked(false); }else if (o.getString("action_status").equals("1")) { tb2.setChecked(true); }
                        product_size_id_list.add(1, o.getString("product_size_id"));
                        break;
                    case 2:
                        a=Integer.valueOf(o.getString("product_quantity"));
                        if (a!=0){sp3.setSelection(a-1);}
                        at3.setText(  o.getString("unit_price"));
                        tt3.setText(o.getString("product_price"));
                        t3=Integer.valueOf(o.getString("product_price"));
                        if (   o.getString("action_status").equals("0"))
                        { tb3.setChecked(false); }else if (o.getString("action_status").equals("1")) { tb3.setChecked(true); }
                        product_size_id_list.add(2, o.getString("product_size_id"));
                        break;
                    case 3:
                        a=Integer.valueOf(o.getString("product_quantity"));
                        if (a!=0){sp4.setSelection(a-1);}
                        at4.setText(  o.getString("unit_price"));
                        tt4.setText(o.getString("product_price"));
                        t4=Integer.valueOf(o.getString("product_price"));
                        if (   o.getString("action_status").equals("0"))
                        { tb4.setChecked(false); }else if (o.getString("action_status").equals("1")) { tb4.setChecked(true); }
                        product_size_id_list.add(3,o.getString("product_size_id"));
                        break;
                    case 4:
                        a=Integer.valueOf(o.getString("product_quantity"));
                        if (a!=0){sp5.setSelection(a-1);}
                        at5.setText(  o.getString("unit_price"));
                        tt5.setText(o.getString("product_price"));
                        t5=Integer.valueOf(o.getString("product_price"));
                        if (   o.getString("action_status").equals("0"))
                        { tb5.setChecked(false); }else if (o.getString("action_status").equals("1")) { tb5.setChecked(true); }
                        product_size_id_list.add(4, o.getString("product_size_id"));
                        break;
                    case 5:
                        a=Integer.valueOf(o.getString("product_quantity"));
                        if (a!=0){sp6.setSelection(a-1);}
                        at6.setText(  o.getString("unit_price"));
                        tt6.setText(o.getString("product_price"));
                        t6=Integer.valueOf(o.getString("product_price"));
                        if (   o.getString("action_status").equals("0"))
                        { tb6.setChecked(false); }else if (o.getString("action_status").equals("1")) { tb6.setChecked(true); }
                        product_size_id_list.add(5, o.getString("product_size_id"));
                        break;

                }

            }
        }  catch(JSONException e){ e.printStackTrace();
            dialog.dismiss();}
        product_size_array=new JSONArray(product_size_id_list);
        dialog.dismiss();
    }

    public void updateData()
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading..","Please Wait..!",false,false);

        toggleStatus();
        setUint();
        setPriceList();
        setProudctpriceList();
        Log.d("response"," mtrader_shop_item_category_id "+PROUDCT_ID);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_TRADER_SHOP_CATEGORY, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                Log.d("response","response="+response);

                if (response.equals("111111"))
                { Toast.makeText(getApplicationContext(),"Successful Submit",Toast.LENGTH_LONG).show();
                    finish();
                }else
                { Toast.makeText(getApplicationContext(),"fail Submit",Toast.LENGTH_LONG).show();

                }
                quantityList.clear();
                priceList.clear();
                product_size_id_list.clear();
                proudctpriceList.clear();
                statusList.clear();
                load.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                Log.d("response_error",error.toString());
                quantityList.clear();
                priceList.clear();
                product_size_id_list.clear();
                proudctpriceList.clear();
                statusList.clear();
                load.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();

                params.put(" mtrader_shop_item_category_id",PROUDCT_ID);
                params.put("mproduct_category",tv_shopname.getText().toString());
                params.put("mproduct_size_id",product_size_array.toString());
                params.put("mcollect_status",s_collect);
                params.put("mdelivery_status",s_delivery);
                params.put("m_want_cut_status",s_cut);
                params.put("munit_quantity",quantityArray.toString());
                params.put("munit_price",priceArray.toString());
                params.put("munit_status",statusArray.toString());
                params.put("mproduct_price",productArray.toString());
                params.put("mdescription",tv_desc.getText().toString().trim());
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                try { params.put("mcategory_image",encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 90)); } catch (IOException e) {}

                return params;
            }
        };

        Marikiti.getInstance().addToRequestQueue(stringRequest);
    }
}
