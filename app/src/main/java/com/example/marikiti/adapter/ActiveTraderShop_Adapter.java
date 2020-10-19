package com.example.marikiti.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.marikiti.R;
import com.example.marikiti.model.AddShop_Model;

import java.util.List;

import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.ITEMS_IMAGES;

public class ActiveTraderShop_Adapter extends RecyclerView.Adapter<ActiveTraderShop_Adapter.ViewHolder> {

    List<AddShop_Model>addShop_models;
    private Context context;

    public ActiveTraderShop_Adapter(List<AddShop_Model> addShop_models, Context context) {
        this.addShop_models = addShop_models;
        this.context = context;
    }

    @NonNull
    @Override
    public ActiveTraderShop_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.active_shop_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveTraderShop_Adapter.ViewHolder viewHolder, int i) {
        AddShop_Model addShop_model = addShop_models.get(i);
        String url =ITEMS_IMAGES+addShop_model.getShop_image_name();

        Glide.with(context).load(url).into(viewHolder.shop_image);
        viewHolder.shop_name.setText(addShop_model.getShop_name());
    }

    @Override
    public int getItemCount() {
        return addShop_models.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        GCircularImageView shop_image;
        GTextView shop_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_image =(GCircularImageView)itemView.findViewById(R.id.img_pImage);
            shop_name  =(GTextView)itemView.findViewById(R.id.tv_shop_name);
        }
    }
}
