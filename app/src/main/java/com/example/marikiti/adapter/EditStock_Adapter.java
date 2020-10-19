package com.example.marikiti.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.marikiti.R;
import com.example.marikiti.activity.MyShops.EditStock_ProductListActivity;
import com.example.marikiti.model.EditStock_Model;
import com.example.marikiti.model.Stock;

import java.util.List;

import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.ITEMS_IMAGES;

public class EditStock_Adapter extends RecyclerView.Adapter<EditStock_Adapter.ViewHolder> {

    Context context;
List<EditStock_Model> list;

    Stock s=new Stock();
    public EditStock_Adapter(Context context, List<EditStock_Model> list) {
        this.context = context;
        this.list = list;
    }

    public EditStock_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.inflator_edit_stock,viewGroup,false);
        EditStock_Adapter.ViewHolder holder= new EditStock_Adapter.ViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull final EditStock_Adapter.ViewHolder holder, final int i) {
        final EditStock_Model model=list.get(i);
      holder.nameText.setText(model.getShop_name());
        try { Glide.with(context).load(ITEMS_IMAGES+model.getImage_name()).into(holder.imageView); }catch (Exception e){}


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s.setTrader_shop_id(model.getTrader_shop_id());
                s.setImage_name(model.getImage_name());
                s.setShop_name(model.getShop_name());
                context.startActivity(new Intent(context, EditStock_ProductListActivity.class));

            }
        });

        holder.nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s.setTrader_shop_id(model.getTrader_shop_id());
                s.setImage_name(model.getImage_name());
                s.setShop_name(model.getShop_name());
                context.startActivity(new Intent(context, EditStock_ProductListActivity.class)
                       );
            }
        });

        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked)
               {
                   Toast.makeText(context, "Activite", Toast.LENGTH_SHORT).show();
               }else
               {
                   Toast.makeText(context, "Deactivite", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        GTextView nameText;
        GCircularImageView imageView;
        ToggleButton toggleButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText=itemView.findViewById(R.id.editstock_tv_trader_name);
            imageView=itemView.findViewById(R.id.editstock_img_pImage);
            toggleButton=itemView.findViewById(R.id.editstock_tb_select_trader);

        }
    }
}
