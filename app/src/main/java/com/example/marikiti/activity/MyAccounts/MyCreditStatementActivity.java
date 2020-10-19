package com.example.marikiti.activity.MyAccounts;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marikiti.R;
import com.example.marikiti.activity.HomeActivity;
import com.example.marikiti.adapter.MyLoanStatementAdapter;
import com.example.marikiti.adapter.OnRecyclerListItemClickListener;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.fragment.MonthYearPickerDialog;
import com.example.marikiti.model.MyLoanStatement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GTextView;

public class MyCreditStatementActivity extends BaseActivity implements View.OnClickListener {
    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;

    private RecyclerView rec_viewcredit_statement;
    private TextView tv_no_trader_found,tv_shopname;
////    private MyCreditStatementAdapter<MyCreditStatement> myCreditStatementAdapter;
//    private List<MyCreditStatement> myCreditStatementList;


    private MyLoanStatementAdapter<MyLoanStatement> myLoanStatementAdapter;
    private List<MyLoanStatement> myLoanStatementList;
    private TextView tv_date;
    String monthYearStr;
    SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_my_credit_statement);
        initToolbar();
        initView();
        initList();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        tv_date = findViewById(R.id.tv_date);
        tv_date.setOnClickListener(this);

        tv_no_trader_found = findViewById(R.id.tv_no_trader_found);
        rec_viewcredit_statement = findViewById(R.id.rec_viewcredit_statement);
        rec_viewcredit_statement.setHasFixedSize(true);
        rec_viewcredit_statement.setLayoutManager(new LinearLayoutManager(mContext));
        tv_shopname=findViewById(R.id.creidtstatement_shopname);

        try{
            String status=getIntent().getStringExtra("status");
            if (status.equals("personal"))
            {
                tv_shopname.setVisibility(View.GONE);
            }
        }catch (NullPointerException e){}



    }

//    private void initList() {
//        myCreditStatementList = new ArrayList<>();
//        myCreditStatementAdapter = new MyCreditStatementAdapter<>(mContext, myCreditStatementList);
//        myCreditStatementAdapter.setOnRecyclerListItemClickListener(new OnRecyclerListItemClickListener<MyCreditStatement>() {
//            @Override
//            public void onItemClick(View view, View rootView, MyCreditStatement myCreditStatement, int position) {
//                //Toast.makeText(gContext, "click ", Toast.LENGTH_SHORT).show();
//                /*Intent detail = new Intent(mContext, ViewNewOrderDetailActivity.class);
//                startActivity(detail);*/
//
//            }
//        });
//        rec_viewcredit_statement.setAdapter(myCreditStatementAdapter);
//
//        for (int i = 0; i < 5; i++) {
//            MyCreditStatement myCreditStatement = new MyCreditStatement();
//            myCreditStatement.setTraderName("Customer Name");
//            myCreditStatementList.add(myCreditStatement);
//            myCreditStatementAdapter.notifyDataSetChanged();
//        }
//    }

    private void initList() {
        myLoanStatementList = new ArrayList<>();
        myLoanStatementAdapter = new MyLoanStatementAdapter<>(mContext, myLoanStatementList);
        myLoanStatementAdapter.setOnRecyclerListItemClickListener(new OnRecyclerListItemClickListener<MyLoanStatement>() {
            @Override
            public void onItemClick(View view, View rootView, MyLoanStatement myLoanStatement, int position) {
                //Toast.makeText(gContext, "click ", Toast.LENGTH_SHORT).show();
                /*Intent detail = new Intent(mContext, ViewNewOrderDetailActivity.class);
                startActivity(detail);*/

            }
        });
        rec_viewcredit_statement.setAdapter(myLoanStatementAdapter);

        for (int i = 0; i < 5; i++) {
            MyLoanStatement myLoanStatement = new MyLoanStatement();
            myLoanStatement.setTraderName("Customer Name");
            myLoanStatementList.add(myLoanStatement);
            myLoanStatementAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                Intent home = new Intent(mContext, HomeActivity.class);
                startActivity(home);
                finish();
                break;

            case R.id.tv_date:
                dateDialog();
                break;
        }

    }

    private void dateDialog() {

        MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
        pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
                monthYearStr = year + "-" + (month + 1) + "-" + i2;
                tv_date.setText(formatMonthYear(monthYearStr));
            }
        });
        pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
    }

    String formatMonthYear(String str) {
        Date date = null;
        try {
            date = input.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }
}
