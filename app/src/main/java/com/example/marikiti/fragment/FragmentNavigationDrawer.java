package com.example.marikiti.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.example.marikiti.R;

import gautam.easydevelope.widget.GCircularImageView;


public class FragmentNavigationDrawer extends Fragment implements View.OnClickListener {
    private Context mContext;
    Activity mActivity;
    final String TAG = this.getClass().getSimpleName();
    private OnDrawerItemInitListener onDrawerItemInitListener;
    private GCircularImageView civ_profile;
    private LinearLayout ll_nav_my_profile,ll_nav_my_account,ll_nav_my_shop,ll_nav_view_balance,
            ll_nav_request_credit, ll_nav_apply_loan,ll_nav_buy_bundles, ll_nav_view_trader,ll_nav_add_trader, ll_nav_add_staff,
            ll_nav_setting_privacy,ll_nav_help_center,ll_nav_logout;

    private SharedPreferences sp;
    private SharedPreferences.Editor ed;

    public interface OnDrawerItemInitListener {
        public void onNavigationInit(View v);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mActivity = getActivity();
    }

    public void setOnDrawerItemInitListener(OnDrawerItemInitListener onDrawerItemInitListener) {
        this.onDrawerItemInitListener = onDrawerItemInitListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_drawer, container, false);
        sp=getContext().getSharedPreferences("user_detail",Context.MODE_PRIVATE);
        ed=sp.edit();
        initView(view);
        try
        {
            String role =sp.getString("role","");
            if (role.equals("GU"))
            {
                User();
            }else if (role.equals("FA"))
            {
                Agent();
            }else
            {
                User();
            }
        }catch (Exception e){}

        return view;
    }

    private void initView(View view) {
        onDrawerItemInitListener.onNavigationInit(view);
        ll_nav_my_profile = view.findViewById(R.id.ll_nav_my_profile);
        ll_nav_my_account = view.findViewById(R.id.ll_nav_my_account);
        ll_nav_my_shop = view.findViewById(R.id.ll_nav_my_shop);
        ll_nav_view_balance = view.findViewById(R.id.ll_nav_view_balance);
        ll_nav_request_credit = view.findViewById(R.id.ll_nav_request_credit);
        ll_nav_apply_loan = view.findViewById(R.id.ll_nav_apply_loan);
        ll_nav_buy_bundles = view.findViewById(R.id.ll_nav_buy_bundles);
        ll_nav_view_trader = view.findViewById(R.id.ll_nav_view_trader);
        ll_nav_add_trader = view.findViewById(R.id.ll_nav_add_trader);
        ll_nav_add_staff = view.findViewById(R.id.ll_nav_add_staff);
        ll_nav_setting_privacy = view.findViewById(R.id.ll_nav_setting_privacy);
        ll_nav_help_center = view.findViewById(R.id.ll_nav_help_center);
        ll_nav_logout = view.findViewById(R.id.ll_nav_logout);

        ll_nav_my_profile.setOnClickListener(this);
        ll_nav_my_account.setOnClickListener(this);
        ll_nav_my_shop.setOnClickListener(this);
        ll_nav_view_balance.setOnClickListener(this);
        ll_nav_request_credit.setOnClickListener(this);
        ll_nav_apply_loan.setOnClickListener(this);
        ll_nav_buy_bundles.setOnClickListener(this);
        ll_nav_view_trader.setOnClickListener(this);
        ll_nav_add_trader.setOnClickListener(this);
        ll_nav_add_staff.setOnClickListener(this);
        ll_nav_setting_privacy.setOnClickListener(this);
        ll_nav_help_center.setOnClickListener(this);
        ll_nav_logout.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //loadProfile();
    }


    @Override
    public void onClick(View v) {
        onDrawerItemInitListener.onNavigationInit(v);
        switch (v.getId()) {
        }
    }

    public void User()
    {
ll_nav_my_profile.setVisibility(View.VISIBLE);
        ll_nav_my_account.setVisibility(View.VISIBLE);
     //   ll_nav_request_credit.setVisibility(View.VISIBLE);
       // ll_nav_apply_loan.setVisibility(View.VISIBLE);
     //   ll_nav_buy_bundles.setVisibility(View.VISIBLE);
        ll_nav_setting_privacy.setVisibility(View.VISIBLE);
        ll_nav_help_center.setVisibility(View.VISIBLE);
        ll_nav_logout.setVisibility(View.VISIBLE);
     //   ll_nav_add_trader.setVisibility(View.VISIBLE);
        ll_nav_add_staff.setVisibility(View.VISIBLE);
    }
    public void Agent()
    {
        ll_nav_my_profile.setVisibility(View.VISIBLE);
        ll_nav_my_account.setVisibility(View.VISIBLE);
        ll_nav_request_credit.setVisibility(View.VISIBLE);
        ll_nav_apply_loan.setVisibility(View.VISIBLE);
        ll_nav_buy_bundles.setVisibility(View.VISIBLE);
        ll_nav_setting_privacy.setVisibility(View.VISIBLE);
        ll_nav_help_center.setVisibility(View.VISIBLE);
        ll_nav_logout.setVisibility(View.VISIBLE);
        ll_nav_add_staff.setVisibility(View.VISIBLE);
        ll_nav_add_staff.setVisibility(View.VISIBLE);

    }

}
