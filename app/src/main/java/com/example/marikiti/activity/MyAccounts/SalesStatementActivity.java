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
import com.example.marikiti.adapter.OnRecyclerListItemClickListener;
import com.example.marikiti.adapter.SaleStatementAdapter;
import com.example.marikiti.app.BaseActivity;
import com.example.marikiti.fragment.MonthYearPickerDialog;
import com.example.marikiti.model.SalesStatement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gautam.easydevelope.data.AppPrefs;
import gautam.easydevelope.widget.GTextView;

public class SalesStatementActivity extends BaseActivity implements View.OnClickListener {
    final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private AppPrefs prefs;
    private Toolbar mToolbar;
    public static GTextView title;
    public static ImageView home;

    private RecyclerView rec_sales_statement;
    private TextView tv_no_trader_found;
    private SaleStatementAdapter<SalesStatement> salesStatementAdapter;
    private List<SalesStatement> salesStatementList;

    private TextView tv_date;
    String monthYearStr;
    SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_sales_statement);
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
        rec_sales_statement = findViewById(R.id.rec_sales_statement);
        rec_sales_statement.setHasFixedSize(true);
        rec_sales_statement.setLayoutManager(new LinearLayoutManager(mContext));

    }

    private void initList() {
        salesStatementList = new ArrayList<>();
        salesStatementAdapter = new SaleStatementAdapter<>(mContext, salesStatementList);
        salesStatementAdapter.setOnRecyclerListItemClickListener(new OnRecyclerListItemClickListener<SalesStatement>() {
            @Override
            public void onItemClick(View view, View rootView, SalesStatement salesStatement, int position) {
                //Toast.makeText(gContext, "click ", Toast.LENGTH_SHORT).show();
                /*Intent detail = new Intent(mContext, TraderDetailActivity.class);
                startActivity(detail);*/

            }
        });
        rec_sales_statement.setAdapter(salesStatementAdapter);

        for (int i = 0; i < 5; i++) {
            SalesStatement salesStatement = new SalesStatement();
            salesStatement.setTraderName("Trader Name");
            salesStatementList.add(salesStatement);
            salesStatementAdapter.notifyDataSetChanged();
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
