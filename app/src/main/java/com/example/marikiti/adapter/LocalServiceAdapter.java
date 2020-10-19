package com.example.marikiti.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.marikiti.R;
import com.example.marikiti.activity.Main_Other_List_Activity;
import com.example.marikiti.model.Main_Transfer;
import com.example.marikiti.model.Other_Shop_List_Model;

import java.util.List;

import static com.example.marikiti.utilities.APP_URL.ITEMS_IMAGES;
import static com.example.marikiti.utilities.Constant.LIST_STATUS;

public class LocalServiceAdapter extends RecyclerView.Adapter<LocalServiceAdapter.ViewHolder> {
    Activity context;
    List<Other_Shop_List_Model> list;
    String shop_id;

    public LocalServiceAdapter(Activity context, List<Other_Shop_List_Model> list,String shop_id) {
        this.context = context;
        this.list = list;
        this.shop_id=shop_id;
    }

    public LocalServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.inflate_mainlist,viewGroup,false);
        LocalServiceAdapter.ViewHolder viewHolder=new LocalServiceAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocalServiceAdapter.ViewHolder viewHolder, int i) {
        final Other_Shop_List_Model model=list.get(i);
        Log.d("response","details id= "+model.getOther_shop_id());
        Glide.with(context).load(ITEMS_IMAGES+model.getOther_shop_image()).into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp=context.getSharedPreferences(LIST_STATUS, Context.MODE_PRIVATE);
                SharedPreferences.Editor ed=sp.edit();
                ed.putString(LIST_STATUS,"main");
                ed.commit();
                ed.apply();
                Main_Transfer transfer=new Main_Transfer();
                transfer.setShop_id(model.getOther_shop_id());
                transfer.setShop_name(model.getOther_shop_name());
               transfer.setMainCategoryId(shop_id);
                context.startActivity(new Intent(context, Main_Other_List_Activity.class)
                        .putExtra("shop_id",shop_id)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.main_image);
        }
    }
}
