package com.example.marikiti.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.example.marikiti.R;
import com.example.marikiti.model.SearchTrader;
import com.example.marikiti.model.SelectTrader;

import java.util.List;

import gautam.easydevelope.utils.WindowUtils;
import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GTextView;


public class SearchTraderAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @LayoutRes
    Integer resId = R.layout.inflator_search_trader;

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

    public SearchTraderAdapter(Context context, List<T> ts) {
        this.ts = ts;
        this.context = context;
        utils = WindowUtils.with(context);
    }

    @Override
    public int getItemCount() {
        //return ts.size();
        return 20;
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
        private GTextView tv_trader_name, tv_mobile, tv_trader_code;
        private ToggleButton tb_select_trader;
        private RelativeLayout rl_mains;


        public MyViewHolder(View view) {
            super(view);
            rootView = view;
            view.setOnClickListener(this);
            // init view here
            img_pImage = view.findViewById(R.id.img_pImage);
            tv_trader_name = view.findViewById(R.id.tv_trader_name);
            tv_mobile = view.findViewById(R.id.tv_mobile);
            tv_mobile = view.findViewById(R.id.tv_mobile);
            tv_trader_code = view.findViewById(R.id.tv_trader_code);
            tb_select_trader = view.findViewById(R.id.tb_select_trader);
            rl_mains = view.findViewById(R.id.rl_mains);


        }

        private void bindWith(int position) {
            this.position = position;
            // set views data here
            if (ts.get(position) instanceof SelectTrader) {
                SearchTrader searchTrader = (SearchTrader) ts.get(position);
                tv_trader_name.setText(searchTrader.getName());
            }
        }

        @Override
        public void onClick(View v) {
            if (onRecyclerListItemClickListener != null)
                onRecyclerListItemClickListener.onItemClick(v, rootView, ts.get(position), position);

            SearchTrader request = (SearchTrader) ts.get(position);
            switch (v.getId()) {
                case R.id.tv_mobile:
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:0725872580"));
                    context.startActivity(callIntent);
                    break;
            }
        }
    }

}