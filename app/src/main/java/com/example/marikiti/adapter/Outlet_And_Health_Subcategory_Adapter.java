package com.example.marikiti.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.marikiti.R;
import com.example.marikiti.activity.Cart_Activitty;
import com.example.marikiti.activity.MyShops.Supermarket_Pharmacy_Cart_System;
import com.example.marikiti.model.Main_Transfer;
import com.example.marikiti.model.Other_Shop_List_Model;

import java.util.List;

import static com.example.marikiti.utilities.APP_URL.ITEMS_IMAGES;

public class Outlet_And_Health_Subcategory_Adapter extends RecyclerView.Adapter<Outlet_And_Health_Subcategory_Adapter.ViewHolder>
{
    Context context;
    List<Other_Shop_List_Model> list;

    public Outlet_And_Health_Subcategory_Adapter(Context context, List<Other_Shop_List_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Outlet_And_Health_Subcategory_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.inflate_mainlist,viewGroup,false);
        Outlet_And_Health_Subcategory_Adapter.ViewHolder viewHolder=new Outlet_And_Health_Subcategory_Adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Outlet_And_Health_Subcategory_Adapter.ViewHolder viewHolder, int i) {
        final Other_Shop_List_Model model=list.get(i);
        Log.d("response","details id= "+model.getItem_id());
        Glide.with(context).load(ITEMS_IMAGES+model.getItem_image()).into(viewHolder.imageView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if (model.getItem_id().equals("12"))
                {
                    Log.d("response","id="+model.getItem_id());
                    Main_Transfer transfer=new Main_Transfer();
                    transfer.setItem_id(model.getItem_id());
                    transfer.setShop_name(model.getItem_name());
                    transfer.setItem_name(model.getItem_name());
                    transfer.setItem_image(model.getItem_image());
                    context.startActivity(new Intent(context, Supermarket_Pharmacy_Cart_System.class));

                }else
                {
                    Main_Transfer transfer=new Main_Transfer();
                    transfer.setItem_id(model.getItem_id());
                    transfer.setShop_name(model.getItem_name());
                    transfer.setItem_name(model.getItem_name());
                    transfer.setItem_image(model.getItem_image());
                    context.startActivity(new Intent(context, Cart_Activitty.class));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder
    {   ImageView imageView;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView=itemView.findViewById(R.id.main_image);


        }
    }
}
