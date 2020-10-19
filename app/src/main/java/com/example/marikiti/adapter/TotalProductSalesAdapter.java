package com.example.marikiti.adapter;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.marikiti.R;
import com.example.marikiti.model.TotalSales;

import java.util.List;

import gautam.easydevelope.utils.WindowUtils;
import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GTextView;


public class TotalProductSalesAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @LayoutRes
    Integer resId = R.layout.inflator_total_productsales;

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

    public TotalProductSalesAdapter(Context context, List<T> ts) {
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
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.bindWith(position);
        if (position == 0)
        {
            ((MyViewHolder) holder).tv_product_name.setText("Apples");
            ((MyViewHolder) holder).tv_price.setText("250.00");
            ((MyViewHolder) holder).date.setText("15 Fri");
        }else   if (position == 1)
        {
            ((MyViewHolder) holder).tv_product_name.setText("Mangos");
            ((MyViewHolder) holder).tv_price.setText("700.00");
            ((MyViewHolder) holder).date.setText("16 Sat");
        }else   if (position == 2)
        {
            ((MyViewHolder) holder).tv_product_name.setText("Oranges");
            ((MyViewHolder) holder).tv_price.setText("500.00");
            ((MyViewHolder) holder).date.setText("17 Sun");

        }else   if (position == 3)
        {
            ((MyViewHolder) holder).tv_product_name.setText("Apples");
            ((MyViewHolder) holder).tv_price.setText("700.00");
            ((MyViewHolder) holder).date.setText("18 Mon");
        }else   if (position == 4)
        {
            ((MyViewHolder) holder).tv_product_name.setText("Mangos");
            ((MyViewHolder) holder).tv_price.setText("800.00");
            ((MyViewHolder) holder).date.setText("19 Tus");
        }else   if (position == 5)
        { ((MyViewHolder) holder).tv_product_name.setText("Orages");
            ((MyViewHolder) holder).tv_price.setText("700.00");
            ((MyViewHolder) holder).date.setText("20 Wed");

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
        private GTextView tv_product_name,  tv_price;
        private RelativeLayout rl_mains;
        private TextView date;

        public MyViewHolder(View view) {
            super(view);
            rootView = view;
            view.setOnClickListener(this);
            // init view here
            img_pImage = view.findViewById(R.id.img_pImage);
            tv_product_name = view.findViewById(R.id.tv_product_name);
            tv_price = view.findViewById(R.id.tv_price);
            rl_mains = view.findViewById(R.id.rl_mains);
            date=view.findViewById(R.id.sale_date);
        }

        private void bindWith(int position) {
            this.position = position;
            // set views data here
            if (ts.get(position) instanceof TotalSales) {
                TotalSales totalSales = (TotalSales) ts.get(position);
            }
        }

        @Override
        public void onClick(View v) {
            if (onRecyclerListItemClickListener != null)
                onRecyclerListItemClickListener.onItemClick(v, rootView, ts.get(position), position);

            TotalSales request = (TotalSales) ts.get(position);
            switch (v.getId()) {

            }
        }
    }

}