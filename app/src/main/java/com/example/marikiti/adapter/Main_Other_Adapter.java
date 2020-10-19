package com.example.marikiti.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.marikiti.R;
import com.example.marikiti.activity.Cart_Activitty;
import com.example.marikiti.activity.MyShops.Supermarket_Pharmacy_Cart_System;
import com.example.marikiti.activity.Outlet_and_Health_Subcategory_Activity;
import com.example.marikiti.model.Main_Transfer;
import com.example.marikiti.model.Shop_Trader_List_model;

import java.util.ArrayList;
import java.util.List;

import gautam.easydevelope.widget.GCircularImageView;

import static com.example.marikiti.utilities.APP_URL.PROFILE_IMAGES;
import static com.example.marikiti.utilities.Constant.LIST_STATUS;

public class Main_Other_Adapter extends RecyclerView.Adapter<Main_Other_Adapter.ViewHolder> implements Filterable {
    Context context;
    List<Shop_Trader_List_model> list;
    List<Shop_Trader_List_model> searchlist;
    private SharedPreferences sp;
    private SharedPreferences.Editor ed;
    private String STATUS;
    private String shop_id;
    public Main_Other_Adapter(Context context, List<Shop_Trader_List_model> list,String shop_id) {
        this.context = context;
        this.list = list;
        searchlist=new ArrayList<>(list);
        this.shop_id=shop_id;
    }
    public Main_Other_Adapter(Context context, List<Shop_Trader_List_model> list) {
        this.context = context;
        this.list = list;
        searchlist=new ArrayList<>(list);
    }
    public Main_Other_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(context).inflate(R.layout.inflator_select_trader,viewGroup,false);
        Main_Other_Adapter.ViewHolder holder=new Main_Other_Adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {

        final Shop_Trader_List_model model=list.get(i);
        sp=context.getSharedPreferences(LIST_STATUS,Context.MODE_PRIVATE);
        ed=sp.edit();
        STATUS=sp.getString(LIST_STATUS,"");
        viewHolder.tv_trader_code.setText(model.getUser_code());
        viewHolder.tv_mobile.setText(model.getH_name());
        viewHolder.tv_wardname.setText(model.getWard());
        viewHolder.tv_trader_name.setText(model.getTra_full_name());
        Log.d("response","https://marikiti.app/profile_images/"+model.getProfile_pic());

        Glide.with(context)
                    .load(PROFILE_IMAGES+model.getProfile_pic())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
        .into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
               if (STATUS.equals("outlet"))
                {
                    Main_Transfer transfer=new Main_Transfer();
                    transfer.setTrader_id(model.getTrader_id());
                    Log.d("response","actviity outlet");
                    context.startActivity(new Intent(context, Outlet_and_Health_Subcategory_Activity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

                }else if (STATUS.equals("main"))
                {

                    if (shop_id.equals("12"))
                    {

                        context.startActivity(new Intent(context, Supermarket_Pharmacy_Cart_System.class));

                    }else
                        {

                        Main_Transfer transfer=new Main_Transfer();
                        transfer.setTrader_id(model.getTrader_id());
                        Log.d("response","actviity main");
                        context.startActivity(new Intent(context, Cart_Activitty.class)
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    }




                }

            }
        });

//        viewHolder.tv_mobile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent callIntent = new Intent(Intent.ACTION_DIAL);
//                callIntent.setData(Uri.parse("tel:"+model.getTra_phone_no()));
//                callIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                context.startActivity(callIntent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_trader_name,tv_mobile,tv_trader_code,tv_wardname;
        GCircularImageView imageView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_trader_name=itemView.findViewById(R.id.tv_trader_name);
            tv_mobile=itemView.findViewById(R.id.tv_mobile);
            tv_trader_code=itemView.findViewById(R.id.tv_trader_code);
            imageView=itemView.findViewById(R.id.image);
            tv_wardname=itemView.findViewById(R.id.tv_wardname);
        }

    }


    public Filter getFilter() {
        return  exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Shop_Trader_List_model> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(searchlist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Shop_Trader_List_model item : searchlist) {
                    if (item.getUser_code().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
