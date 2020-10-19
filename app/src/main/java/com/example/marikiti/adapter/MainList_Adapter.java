package com.example.marikiti.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.marikiti.R;
import com.example.marikiti.activity.Main_Other_List_Activity;
import com.example.marikiti.activity.Outlet_And_Health_Activity;
import com.example.marikiti.model.Main_Transfer;
import com.example.marikiti.model.Mainlist_model;

import java.util.List;

import static com.example.marikiti.utilities.APP_URL.ITEMS_IMAGES;
import static com.example.marikiti.utilities.Constant.LIST_STATUS;

public class MainList_Adapter extends RecyclerView.Adapter<MainList_Adapter.ViewHolder> {
    Context context;
    List<Mainlist_model> list;
    Main_Transfer transfer;
    SharedPreferences sp;
    SharedPreferences.Editor ed;

    public MainList_Adapter(Context context, List<Mainlist_model> list) {
        this.context = context;
        this.list = list;
    }

    public MainList_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.inflate_mainlist,viewGroup,false);
        MainList_Adapter.ViewHolder viewHolder=new MainList_Adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainList_Adapter.ViewHolder viewHolder, int i) {
        final Mainlist_model model=list.get(i);
        sp=context.getSharedPreferences(LIST_STATUS, Context.MODE_PRIVATE);
        ed=sp.edit();

        try { Glide.with(context).load(ITEMS_IMAGES+model.getImage_name()).into(viewHolder.imageView); }catch (Exception e){}

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((model.getShop_id().equals("10"))||(model.getShop_id().equals("11"))||(model.getShop_id().equals("9"))||(model.getShop_id().equals("12")))
                {
                    transfer.setShop_id(model.getShop_id());
                    transfer.setShop_name(model.getShop_name());
                    transfer.setMainCategoryId(model.getShop_id());
                    ed.putString(LIST_STATUS,"outlet");
                    ed.commit();
                    ed.apply();
                    context.startActivity(new Intent(context, Outlet_And_Health_Activity.class)
                    .putExtra("id",model.getShop_id()));

                } else if ((model.getShop_id().equals("1200000")))
                {
                    transfer.setShop_id(model.getShop_id());
                    transfer.setShop_name(model.getShop_name());
                    transfer.setMainCategoryId(model.getShop_id());
                    ed.putString(LIST_STATUS,"outlet");
                    ed.commit();
                    ed.apply();
                    context.startActivity(new Intent(context, Main_Other_List_Activity.class));

                }else
                {
                    ed.putString(LIST_STATUS,"main");
                    ed.commit();
                    ed.apply();
                    transfer.setShop_id(model.getShop_id());
                    transfer.setMainCategoryId(model.getShop_id());
                    transfer.setShop_name(model.getShop_name());
                    context.startActivity(new Intent(context, Main_Other_List_Activity.class));
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView=itemView.findViewById(R.id.main_image);
        }
    }
}
