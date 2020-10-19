package com.example.marikiti.activity.MyAccounts;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.marikiti.R;
import com.example.marikiti.activity.HomeActivity;
import com.example.marikiti.activity.MyShops.EditStock_ProductListActivity;
import com.example.marikiti.activity.MyShops.MyShop_RegisterNewShopActivity;
import com.example.marikiti.activity.PendingCreditsActivity;
import com.example.marikiti.activity.TotalProductSalesActivity;
import com.example.marikiti.adapter.SlidingImage_Adapter1;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.model.ImageModel;
import com.example.marikiti.model.Stock;
import com.example.marikiti.utilities.Constant;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.MAIN_URL;

public class MyAccountActivity extends BaseActivity implements View.OnClickListener {
    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    private int[] myImageList = new int[]{R.drawable.my_account_1, R.drawable.my_account_2, R.drawable.my_account_3};

    RelativeLayout traderlayout;
    private ScrollView sc_business_account_views;
    private LinearLayout ll_personal_account, ll_personal_account_views, ll_business_account;
    private LinearLayout ll_view_purchase_history, ll_messages, ll_send_credit, ll_my_credit_statement,ll_apply_credit,ll_account_type;
    Constant c=new Constant();
    private RelativeLayout ll_my_shops, ll_view_new_orders, ll_pending_orders, ll_pending_credits,
            ll_withdraw_fund, ll_transfer_funds;

    private static boolean status_click=false;
    private GTextView tv_personal_account, tv_business_account;
    private RelativeLayout rl_advertising;

    private ImageView bimg1,bimg2,bimg3,bimg4,bimg5,bimg6,bimg7,bimg8,bimg9,bimg10,bimg11,bimg12,bimg13,bimg14,bimg15,bimg16,bimg17,bimg18;
    private ImageView pimg1,pimg2,pimg3,pimg4,pimg5,pimg6,pimg7,pimg8,pimg9;
//    private TextView tv_bussines ,tv_user_code;

    ArrayList<String>shop_id_list=new ArrayList<>();
    ArrayList<String>shop_name_list=new ArrayList<>();
    ArrayList<String>shop_bussiness_list=new ArrayList<>();
    ArrayList<String>UserCode_list=new ArrayList<>();
    ArrayList<String>Traderid_list=new ArrayList<>();
    Spinner spinner;
    TextView spinnerBussines,spinnerUsercode;
    String shop_name,shop_id,trader_id;
    Stock s=new Stock();
    boolean STATUS=false;

    SharedPreferences sp;
    String TRADER_ID;
    SharedPreferences.Editor ed;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_my_account);
        sp=getApplicationContext().getSharedPreferences(c.USER_DETAILS,MODE_PRIVATE);
        ed=sp.edit();
        initToolbar();
        findViewID();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (GTextView) mToolbar.findViewById(R.id.title);
        title.setText("My Account");
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

        spinner=findViewById(R.id.spn_duration);
        spinnerBussines=findViewById(R.id.spn_b_name);
        spinnerUsercode=findViewById(R.id.spn_user_code);
        fetch_traderName();

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        setup_spinner();

        traderlayout=findViewById(R.id.trader_layout);
        bimg1=findViewById(R.id.bs_img1);
        bimg2=findViewById(R.id.bs_img2);
        bimg3=findViewById(R.id.bs_img3);
        bimg4=findViewById(R.id.bs_img4);
        bimg5=findViewById(R.id.bs_img5);
        bimg6=findViewById(R.id.bs_img6);
        bimg7=findViewById(R.id.bs_img7);
        bimg8=findViewById(R.id.bs_img8);
        bimg9=findViewById(R.id.bs_img9);
        bimg10=findViewById(R.id.bs_img10);
        bimg11=findViewById(R.id.bs_img11);
        bimg12=findViewById(R.id.bs_img12);
        bimg13=findViewById(R.id.bs_img13);
        bimg14=findViewById(R.id.bs_img14);
        bimg15=findViewById(R.id.bs_img15);
        bimg16=findViewById(R.id.bs_img16);
        bimg17=findViewById(R.id.bs_img17);
        bimg18=findViewById(R.id.bs_img18);

        bimg1.setOnClickListener(this);
        bimg2.setOnClickListener(this);
        bimg3.setOnClickListener(this);
        bimg4.setOnClickListener(this);
        bimg5.setOnClickListener(this);
        bimg6.setOnClickListener(this);
        bimg7.setOnClickListener(this);
        bimg8.setOnClickListener(this);
        bimg9.setOnClickListener(this);
        bimg10.setOnClickListener(this);
        bimg11.setOnClickListener(this);
        bimg12.setOnClickListener(this);
        bimg13.setOnClickListener(this);
        bimg14.setOnClickListener(this);
        bimg15.setOnClickListener(this);
        bimg16.setOnClickListener(this);
        bimg17.setOnClickListener(this);
        bimg18.setOnClickListener(this);


        pimg1=findViewById(R.id.p_img1);

        pimg2=findViewById(R.id.p_img2);
        pimg3=findViewById(R.id.p_img3);
        pimg4=findViewById(R.id.p_img4);
        pimg5=findViewById(R.id.p_img5);
        pimg6=findViewById(R.id.p_img6);
        pimg7=findViewById(R.id.p_img7);
        pimg8=findViewById(R.id.p_img8);
        pimg9=findViewById(R.id.p_img9);

        pimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Click",Toast.LENGTH_LONG).show();
            }
        });

        pimg1.setOnClickListener(this);
        pimg2.setOnClickListener(this);
        pimg3.setOnClickListener(this);
        pimg4.setOnClickListener(this);
        pimg5.setOnClickListener(this);
        pimg6.setOnClickListener(this);
        pimg7.setOnClickListener(this);
        pimg8.setOnClickListener(this);
        pimg9.setOnClickListener(this);

        ll_account_type=findViewById(R.id.ll_account_type);
        ll_personal_account = findViewById(R.id.ll_personal_account);
        ll_personal_account.setOnClickListener(this);
        ll_business_account = findViewById(R.id.ll_business_account);
        ll_business_account.setOnClickListener(this);
        tv_personal_account = findViewById(R.id.tv_personal_account);
        tv_business_account = findViewById(R.id.tv_business_account);
        rl_advertising = findViewById(R.id.rl_advertising);
        ll_personal_account_views = findViewById(R.id.ll_personal_account_views);
        sc_business_account_views = findViewById(R.id.sc_business_account_views);

        ll_apply_credit=findViewById(R.id.myaccount_apply_loan);
        ll_send_credit = findViewById(R.id.ll_send_credit);
        ll_view_purchase_history = findViewById(R.id.ll_view_purchase_history);
        ll_messages = findViewById(R.id.ll_messages);
        ll_my_credit_statement = findViewById(R.id.ll_my_credit_statement);
        //   ll_my_loan_statement = findViewById(R.id.ll_my_loan_statement);


        ll_my_shops = findViewById(R.id.ll_my_shops);

        ll_view_new_orders = findViewById(R.id.ll_view_new_orders);
        ll_pending_orders = findViewById(R.id.ll_pending_orders);
        ll_pending_credits = findViewById(R.id.ll_pending_credits);
        ll_withdraw_fund = findViewById(R.id.ll_withdraw_fund);
        ll_transfer_funds = findViewById(R.id.ll_transfer_funds);


        //  ll_my_shops.setOnClickListener(this);
        //  ll_view_new_orders.setOnClickListener(this);
//        ll_pending_orders.setOnClickListener(this);
        //      ll_pending_credits.setOnClickListener(this);
        //  ll_withdraw_fund.setOnClickListener(this);
        //   ll_transfer_funds.setOnClickListener(this);

        ll_send_credit.setOnClickListener(this);
        ll_view_purchase_history.setOnClickListener(this);
        ll_messages.setOnClickListener(this);
        ll_my_credit_statement.setOnClickListener(this);
        //    ll_my_loan_statement.setOnClickListener(this);

        ll_apply_credit.setOnClickListener(this);

        mPager = (ViewPager) findViewById(R.id.myaccount_pager);
        mPager.setAdapter(new SlidingImage_Adapter1(mContext, imageModelArrayList));
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.myaccount_indicator);
        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
        //Set circle indicator radius
        indicator.setRadius(5 * density);
        NUM_PAGES = imageModelArrayList.size();
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int pos) {
            }
        });

    }

    private ArrayList<ImageModel> populateList() {
        ArrayList<ImageModel> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
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
            case R.id.ll_personal_account:
                title.setText("Personal Account");
                ll_business_account.setBackgroundResource(R.drawable.border_round_corner);
                tv_business_account.setTextColor(getResources().getColor(R.color.colorBlack));
                ll_personal_account.setBackgroundResource(R.drawable.border_round_corner_primary);
                tv_personal_account.setTextColor(getResources().getColor(R.color.colorWhite));
                ll_account_type.setVisibility(View.GONE);
                rl_advertising.setVisibility(View.GONE);
                sc_business_account_views.setVisibility(View.GONE);
                ll_personal_account_views.setVisibility(View.VISIBLE);
                status_click=false;
                STATUS=true;

                break;
            case R.id.ll_business_account:
try {
    if (TRADER_ID.length() > 0) {
        traderlayout.setVisibility(View.INVISIBLE);
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.popup_bussiness_account);
        dialog.show();
        GButton gButton = dialog.findViewById(R.id.bussiness_account_next);
        gButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Business Account");
                ll_account_type.setVisibility(View.GONE);
                rl_advertising.setVisibility(View.GONE);
                ll_personal_account.setBackgroundResource(R.drawable.border_round_corner);
                tv_personal_account.setTextColor(getResources().getColor(R.color.colorBlack));
                ll_business_account.setBackgroundResource(R.drawable.border_round_corner_primary);
                tv_business_account.setTextColor(getResources().getColor(R.color.colorWhite));
                ll_personal_account_views.setVisibility(View.GONE);
                sc_business_account_views.setVisibility(View.VISIBLE);
                dialog.dismiss();
                STATUS = true;
            }
        });

    } else {
        traderlayout.setVisibility(View.VISIBLE);
    }
}catch (NullPointerException e)
{
    traderlayout.setVisibility(View.VISIBLE);
}
                break;
            case R.id.ll_view_purchase_history:

                break;
            case R.id.ll_messages:

                break;
            case R.id.ll_my_credit_statement:

                break;


            case R.id.ll_my_shops:
                Intent myshopdetail = new Intent(mContext, MyShopsDetailActivity.class);
                startActivity(myshopdetail);
                break;

            case R.id.ll_view_new_orders:
                Intent viewNewOrder = new Intent(mContext, ViewNewOrderActivity.class);
                startActivity(viewNewOrder);
                break;

            case R.id.ll_pending_orders:
                Intent pendingOrder = new Intent(mContext, PendingOrdersActivity.class);
                startActivity(pendingOrder);
                break;

            case R.id.ll_pending_credits:
                Intent pendingCredit = new Intent(mContext, PendingCreditsActivity.class);
                startActivity(pendingCredit);
                break;

            case R.id.ll_send_credit :
                break;

            case R.id.myaccount_apply_loan:
                break;


            case R.id.p_img1:
                Intent viewTransstatement = new Intent(mContext, ViewPurchaseHistory.class);
                startActivity(viewTransstatement);
                break;
            case R.id.p_img2:
                Intent mycreditStatement = new Intent(mContext, MyCreditStatementActivity.class);
                mycreditStatement.putExtra("status","personal");
                startActivity(mycreditStatement);
                break;
            case R.id.p_img3:
                Intent sendcredit = new Intent(mContext, MyAcccount_SendCredit.class);
                startActivity(sendcredit);
                break;
            case R.id.p_img4:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.p_img5:
                Intent applycredit=new Intent(mContext, MyAccount_ApplyCredit.class);
                startActivity(applycredit);
                break;
            case R.id.p_img6:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.p_img7:
                Intent messages = new Intent(mContext, MessageActivity.class);
                startActivity(messages);
                break;
            case R.id.p_img8:

                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.p_img9:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;

            case R.id.bs_img1:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img2:
                Intent sale=new Intent(mContext,MyAccount_SalesActivity.class);
                startActivity(sale);
                break;
            case R.id.bs_img3:
                String tid=spinnerUsercode.getText().toString();

                s.setTrader_shop_id(shop_id_list.get(spinner.getSelectedItemPosition()));
                s.setShop_name(spinner.getSelectedItem().toString());
                SharedPreferences sp=getSharedPreferences(c.USER_DETAILS,Context.MODE_PRIVATE);
                SharedPreferences.Editor ed=sp.edit();
                ed.putString(c.TRADER_ID,TRADER_ID);
                ed.apply();
                ed.commit();
                  startActivity(new Intent(MyAccountActivity.this, EditStock_ProductListActivity.class));
//                Intent editStock = new Intent(mContext, EditStockActivity.class);
//                startActivity(editStock);
                break;
            case R.id.bs_img4:
                Intent creditStatement = new Intent(mContext, MyCreditStatementActivity.class);
                startActivity(creditStatement);

                break;
            case R.id.bs_img5:
                Intent salesStatement = new Intent(mContext, SalesStatementActivity.class);
                startActivity(salesStatement);
                break;
            case R.id.bs_img6:
                Intent totalSales = new Intent(mContext, TotalProductSalesActivity.class);
                startActivity(totalSales);
                break;
            case R.id.bs_img7:
                Intent loanStatement = new Intent(mContext, MyLoanStatementActivity.class);
                startActivity(loanStatement);
                break;
            case R.id.bs_img8:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img9:
                Intent purchase = new Intent(mContext, Transporter_Activity.class);
                startActivity(purchase);
                break;
            case R.id.bs_img10:
                startActivity(new Intent(mContext, MyAccount_OverDraftActivity.class));
                break;
            case R.id.bs_img11:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img12:
                Intent registerShop=new Intent(mContext, MyShop_RegisterNewShopActivity.class);
                startActivity(registerShop);
                break;
            case R.id.bs_img13:
                Intent sendcreditbusiness = new Intent(mContext, MyAcccount_SendCredit.class);
                startActivity(sendcreditbusiness);
                break;
            case R.id.bs_img14:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img15:
                Intent messagesb = new Intent(mContext, MessageActivity.class);
                startActivity(messagesb);
                break;
            case R.id.bs_img16:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img17:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.bs_img18:
                Toast.makeText(mContext,"service will be available soon",Toast.LENGTH_LONG).show();
                break;
        }

    }


    //advertise
    public void setup_spinner(){
        fetch_shop_list();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                shop_name=spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                shop_id=shop_id_list.get(spinner.getSelectedItemPosition());
                Log.d("response","shiop_id"+shop_id);
                //fetch_trader(shop_id);
                //Toast.makeText(MyAccountActivity.this,shop_name+"=="+shop_id,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void fetch_shop_list()
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        shop_name_list.add(object.getString("shop_name"));
                        shop_id_list.add(object.getString("shop_id"));
                    }

                    spinner.setAdapter(new ArrayAdapter<String>(mContext,R.layout.spinner_text, shop_name_list));

                }catch (Exception e)
                {

                }
                load.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                load.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("type","all_shops_list");
                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(stringRequest);
    }


    public void fetch_trader(final String id)
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Traderid_list.add(object.getString("trader_id"));
                        shop_bussiness_list.add(object.getString("business_name"));
                        UserCode_list.add(object.getString("user_code"));
                    }



                }catch (Exception e)
                {

                }
                load.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                load.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("type","list_of_shop_trader");
                params.put("mshop_id",id);
                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {

        if (!STATUS)
        {
            finish();
        }else
        {
            ll_account_type.setVisibility(View.VISIBLE);
            rl_advertising.setVisibility(View.VISIBLE);
            ll_personal_account_views.setVisibility(View.GONE);
            sc_business_account_views.setVisibility(View.GONE);
            STATUS=false;
        }

    }


    public void fetch_traderName()
    {
        final ProgressDialog load=ProgressDialog.show(mContext,"Loading","Please Wait..",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response","trader id="+sp.getString(c.USER_KEY,"")+"\n"+response);
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        spinnerBussines.setText(object.getString("business_name"));
                        spinnerUsercode.setText(object.getString("user_code"));
                        TRADER_ID=object.getString("trader_id");
                       ed.putString(c.TRADER_ID,TRADER_ID);
                       ed.commit();
                       ed.apply();
                    }

                }catch (Exception e)
                {

                }
                load.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                load.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params.put("type","check_trader");
                params.put("muser_id",sp.getString(c.USER_KEY,""));
                return params;
            }
        };
        Marikiti.getInstance().addToRequestQueue(stringRequest);
    }
}
