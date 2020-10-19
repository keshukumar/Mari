package com.example.marikiti.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.marikiti.R;
import com.example.marikiti.model.SalesStatement;

import java.util.List;

import gautam.easydevelope.utils.WindowUtils;
import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GTextView;

public class SaleStatementAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
        @LayoutRes
        Integer resId = R.layout.inflator_sales_statement;

        private List<T> ts;
        private Context context;
        private final String TAG = getClass().getSimpleName();
        private OnRecyclerListItemClickListener onRecyclerListItemClickListener = null;
        private WindowUtils utils;


        public OnRecyclerListItemClickListener getOnRecyclerListItemClickListener() {
            return onRecyclerListItemClickListener;
        }

        public void setOnRecyclerListItemClickListener(OnRecyclerListItemClickListener onRecyclerListItemClickListener) {
            this.onRecyclerListItemClickListener = onRecyclerListItemClickListener;
        }

        public SaleStatementAdapter(Context context, List<T> ts) {
            this.ts = ts;
            this.context = context;
            utils = WindowUtils.with(context);
        }

        @Override
        public int getItemCount() {
            //return ts.size();
            return 5;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(resId, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SaleStatementAdapter.MyViewHolder viewHolder = (SaleStatementAdapter.MyViewHolder) holder;
            viewHolder.bindWith(position);

            if (position==0)
            {
                ((MyViewHolder) holder).tv_trader_name.setText("John Paul");
                ((MyViewHolder) holder).tv_trader_mobile.setText("MRK712383908");
                ((MyViewHolder) holder).tv_product_name.setText("Meat Deluxe");
                ((MyViewHolder) holder).tv_location.setText("Order No. MRK00234");
                ((MyViewHolder) holder).tv_price.setText("250.00");
                ((MyViewHolder) holder).date.setText("15"+" Fri");

            }else if (position==1){
                ((MyViewHolder) holder).tv_trader_name.setText("Mary John");
                ((MyViewHolder) holder).tv_trader_mobile.setText("MRK712383908");
                ((MyViewHolder) holder).tv_product_name.setText("Vegetarian");
                ((MyViewHolder) holder).tv_location.setText("Vegetarian");
                ((MyViewHolder) holder).tv_price.setText("500.00");
                ((MyViewHolder) holder).date.setText("16"+" Sat");
            }else if  (position==2)
            {
                ((MyViewHolder) holder).tv_trader_name.setText("Samuel Paul");
                ((MyViewHolder) holder).tv_trader_mobile.setText("MRK712383908");
                ((MyViewHolder) holder).tv_product_name.setText("Hawaiian Pizza");
                ((MyViewHolder) holder).tv_location.setText("Order No. MRK00234");
                ((MyViewHolder) holder).tv_price.setText("750.00");
                ((MyViewHolder) holder).date.setText("17"+" Sun");
            }else if  (position==3)
            {
                ((MyViewHolder) holder).tv_trader_name.setText("Mary Paul");
                ((MyViewHolder) holder).tv_trader_mobile.setText("MRK712383908");
                ((MyViewHolder) holder).tv_product_name.setText("Hawaiian Pizza");
                ((MyViewHolder) holder).tv_location.setText("Order No. MRK00234");
                ((MyViewHolder) holder).tv_price.setText("500.00");
                ((MyViewHolder) holder).date.setText("18"+" Mon ");
            }
        }

        public void removeAt(int position) {
            ts.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, ts.size());
        }


public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private int position;
    // declare view in holder
    private View rootView;
    private GCircularImageView img_pImage;
    private GTextView tv_trader_name, tv_trader_mobile, tv_product_name, tv_location, tv_price, tv_view;
    private LinearLayout rl_mains;
    private TextView date;


    public MyViewHolder(View view) {
        super(view);
        rootView = view;
        view.setOnClickListener(this);
        // init view here
        img_pImage = view.findViewById(R.id.img_pImage);
        tv_trader_name = view.findViewById(R.id.tv_trader_name);
        date=view.findViewById(R.id.sale_date);
        tv_trader_mobile = view.findViewById(R.id.tv_trader_mobile);
        tv_product_name = view.findViewById(R.id.tv_product_name);
        tv_location = view.findViewById(R.id.tv_location);
        tv_price = view.findViewById(R.id.tv_price);
        rl_mains = view.findViewById(R.id.rl_mains);
    }

    private void bindWith(int position) {
        this.position = position;
        // set views data here
        if (ts.get(position) instanceof SalesStatement) {
            SalesStatement salesStatement = (SalesStatement) ts.get(position);
            tv_trader_name.setText(salesStatement.getTraderName());
        }
    }

    @Override
    public void onClick(View v) {
        if (onRecyclerListItemClickListener != null)
            onRecyclerListItemClickListener.onItemClick(v, rootView, ts.get(position), position);

        SalesStatement request = (SalesStatement) ts.get(position);
        switch (v.getId()) {
            case R.id.tv_trader_mobile:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:0725872580"));
                context.startActivity(callIntent);
                break;
        }
    }
}

}