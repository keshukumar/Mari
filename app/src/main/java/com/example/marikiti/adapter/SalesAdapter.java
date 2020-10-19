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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.marikiti.R;
import com.example.marikiti.activity.MyAccounts.Sale_Order_Model;
import com.example.marikiti.activity.MyAccounts.Sale_Order_View_Deails_Activity;
import com.example.marikiti.model.SalesStatement;

import org.w3c.dom.Text;

import java.util.List;

import gautam.easydevelope.utils.WindowUtils;
import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.CATEGORY_IMAGES;
import static com.example.marikiti.utilities.APP_URL.SUPERMARKET_IMAGE;


public class SalesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @LayoutRes
    Integer resId = R.layout.inflator_sales;

    private List<Sale_Order_Model> ts;
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

    public SalesAdapter(Context context, List<Sale_Order_Model> ts) {
        this.ts = ts;
        this.context = context;
        utils = WindowUtils.with(context);
    }

    @Override
    public int getItemCount() {
        //return ts.size();
        return ts.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resId, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        final Sale_Order_Model model=ts.get(position);
        viewHolder.tv_trader_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:0725872580"));
                context.startActivity(callIntent);
            }
        });
        viewHolder.tv_trader_name.setText(model.getFull_name());
        viewHolder.tv_trader_mobile.setText(model.getPhone_no());
        viewHolder.tv_price.setText(model.getPrice());
        viewHolder.tv_product_name.setText(model.getDescription());
        viewHolder.date.setText(model.getCart_date());
        viewHolder.tv_location.setText(model.getProduct_code());
        Glide.with(context).load(SUPERMARKET_IMAGE+model.getImage_name()).into(viewHolder.img_pImage);
        viewHolder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Sale_Order_View_Deails_Activity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra("cart_id",model.getCart_id()));
            }
        });

    }

    public void removeAt(int position) {
        ts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, ts.size());
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  {
        private int position;
        // declare view in holder
        private View rootView;
        private GCircularImageView img_pImage;
        private GTextView tv_trader_name, tv_trader_mobile, tv_product_name, tv_location, tv_price, tv_view,order_no;
        private RelativeLayout rl_mains;
        TextView date,viewDetails;



        public MyViewHolder(View view) {
            super(view);
            rootView = view;

            // init view here
            img_pImage = view.findViewById(R.id.img_sale_pImage);
            tv_trader_name = view.findViewById(R.id.tv_sale_trader_name);
            tv_trader_mobile = view.findViewById(R.id.tv_sale_trader_mobile);
            tv_product_name = view.findViewById(R.id.tv_sale_product_name);
            tv_location = view.findViewById(R.id.tv_sale_location);
            tv_price = view.findViewById(R.id.tv_sale_price);
            rl_mains = view.findViewById(R.id.rl_sale_mains);
            date=view.findViewById(R.id.date);
            order_no=view.findViewById(R.id.tv_order_no);
            viewDetails=view.findViewById(R.id.view);
        }


    }
}