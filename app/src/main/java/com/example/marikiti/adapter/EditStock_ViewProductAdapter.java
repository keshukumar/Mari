package com.example.marikiti.adapter;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.marikiti.R;
import com.example.marikiti.activity.MyShops.AddProduct.Bakery_Add_Product_Activity;
import com.example.marikiti.activity.MyShops.EditStock_AddProductActivity;
import com.example.marikiti.activity.MyShops.View_Product_Model;

import java.util.List;

import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.CATEGORY_IMAGES;

public class EditStock_ViewProductAdapter extends RecyclerView.Adapter<EditStock_ViewProductAdapter.ViewHolder> {

    Activity context;
    List<View_Product_Model> list;

    public EditStock_ViewProductAdapter(Activity context, List<View_Product_Model> list) {
        this.context = context;
        this.list = list;
    }

    public EditStock_ViewProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.inflator_edit_stock,viewGroup,false);
        EditStock_ViewProductAdapter.ViewHolder holder= new EditStock_ViewProductAdapter.ViewHolder(view);

        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull final EditStock_ViewProductAdapter.ViewHolder holder, final int i) {
        final View_Product_Model model=list.get(i);
        Log.d("productname",model.getProduct_category()+" product image"+model.getImage_name());
        holder.nameText.setText(model.getMdescription());
        try { Glide.with(context).load(CATEGORY_IMAGES+model.getImage_name()) .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(holder.imageView); }catch (Exception e){}


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                context.startActivity(new Intent(context, Bakery_Add_Product_Activity.class)
                .putExtra("type","view")
                .putExtra("id",model.getTrader_shop_item_category_id()));
                context.finish();
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
