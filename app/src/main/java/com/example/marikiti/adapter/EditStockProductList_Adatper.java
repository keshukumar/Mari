package com.example.marikiti.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.marikiti.R;
import com.example.marikiti.activity.MyShops.AddProduct.Bakery_Add_Product_Activity;
import com.example.marikiti.activity.MyShops.EditStock_AddProductActivity;
import com.example.marikiti.activity.MyShops.EditStock_Supermarket_Activity;
import com.example.marikiti.activity.MyShops.EditStock_ViewProduct;
import com.example.marikiti.model.ProductList_Model;
import com.example.marikiti.model.Stock;
import com.example.marikiti.utilities.Constant;

import java.util.List;

import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.ITEMS_IMAGES;

public class EditStockProductList_Adatper extends RecyclerView.Adapter<EditStockProductList_Adatper.ViewHolder> {
    List<ProductList_Model> list;
   Activity context;
    SharedPreferences sp;
    Constant c=new Constant();
    Stock s=new Stock();
    String shop_id;
    public EditStockProductList_Adatper(List<ProductList_Model> list, Activity context,String shop_id) {
        this.list = list;
        this.context = context;
        this.shop_id=shop_id;
    }

    public EditStockProductList_Adatper.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.inflate_editstock_productlist,viewGroup,false);
        EditStockProductList_Adatper.ViewHolder holder= new EditStockProductList_Adatper.ViewHolder(view);
        return holder;
    }

    public void onBindViewHolder(@NonNull final EditStockProductList_Adatper.ViewHolder holder, final int i) {
        sp=context.getSharedPreferences(c.USER_DETAILS,Context.MODE_PRIVATE);
        final ProductList_Model model=list.get(i);
        holder.nameText.setText(model.getItem_name());

                Glide.with(context).load(ITEMS_IMAGES+model.getItem_image())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.startActivity(new Intent(context, EditStock_AddProductActivity.class)
//                        .putExtra("name",namelist[i])
//                        .putExtra("image",imagelist[i]));
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

        holder.cv_viewproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, EditStock_ViewProduct.class)
                        .putExtra("mproduct_id",model.getTrader_shop_item_list_id())
                        .putExtra("mtrader_id",sp.getString(c.TRADER_ID,""))
                        .putExtra("mshop_id",s.getTrader_shop_id()));

            }
        });
        Log.d("response","id"+model.getTrader_shop_item_list_id());
        holder.cv_addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shop_id.equals("1"))
                {
                    s.setItem_image(model.getItem_image());
                    s.setItem_name(model.getItem_name());
                    s.setTrader_shop_item_list_id(model.getTrader_shop_item_list_id());
                    context.startActivity(new Intent(context, Bakery_Add_Product_Activity.class));
                }else if (shop_id.equals("12"))
                {
                    s.setItem_image(model.getItem_image());
                    s.setItem_name(model.getItem_name());
                    s.setTrader_shop_item_list_id(model.getTrader_shop_item_list_id());
                    context.startActivity(new Intent(context, EditStock_Supermarket_Activity.class));
                }else
                {
                    s.setItem_image(model.getItem_image());
                    s.setItem_name(model.getItem_name());
                    s.setTrader_shop_item_list_id(model.getTrader_shop_item_list_id());
                    context.startActivity(new Intent(context, EditStock_AddProductActivity.class));

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
        CardView cv_viewproduct,cv_addproduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText=itemView.findViewById(R.id.editstock_tv_trader_name);
            imageView=itemView.findViewById(R.id.editstock_img_pImage);
            toggleButton=itemView.findViewById(R.id.editstock_tb_select_trader);
            cv_addproduct=itemView.findViewById(R.id.editstock_addproduct);
            cv_viewproduct=itemView.findViewById(R.id.editstock_viewproduct);

        }
    }
}


