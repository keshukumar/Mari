package com.example.marikiti.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.marikiti.R;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.model.shop_list_model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.ADD_SHOP;
import static com.example.marikiti.utilities.APP_URL.ITEMS_IMAGES;

public class Add_Sub_Shop_Adapter extends RecyclerView.Adapter<Add_Sub_Shop_Adapter.ViewHolder> {


    List<shop_list_model> addShop_models;
    Context context;

    public Add_Sub_Shop_Adapter(List<shop_list_model> addShop_models, Context context) {
        this.addShop_models = addShop_models;
        this.context = context;
    }

    public Add_Sub_Shop_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shops_list_layout, viewGroup, false);
        return new  Add_Sub_Shop_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Add_Sub_Shop_Adapter.ViewHolder viewHolder, int i) {
        final shop_list_model addShop_model = addShop_models.get(i);
        String url = ITEMS_IMAGES + addShop_model.getOther_shop_image();

        Glide.with(context).load(url).into(viewHolder.shop_image);
        viewHolder.shop_name.setText(addShop_model.getOther_shop_name());
        viewHolder.Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog load=ProgressDialog.show(context,"Loading","Please Wait..",false,false);
                SharedPreferences sp =context.getSharedPreferences("trader_id_sp", Context.MODE_PRIVATE);
                final String traders_id =sp.getString("traders_id",null);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_SHOP, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",traders_id+"\n"+response);
                        if (response.equals("1")) {
                            viewHolder.Button.setText("Remove");
                        }else if (response.equals("2"))
                        {  viewHolder.Button.setText("Add");
                        }

                        load.dismiss();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        load.dismiss();

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>params = new HashMap<>();
                        params.put("mtrader_id",traders_id);
                        Log.d("keshav",addShop_model.getOther_shop_id());
                        params.put("mshop_id",addShop_model.getOther_shop_id());
                        return params;
                    }
                };
                Marikiti.getInstance().addToRequestQueue(stringRequest);

            }
        });
    }

    @Override
    public int getItemCount() {
        return addShop_models.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        GCircularImageView shop_image;
        GTextView shop_name;
        android.widget.Button Button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shop_image =(GCircularImageView)itemView.findViewById(R.id.img_pImage);
            shop_name  =(GTextView)itemView.findViewById(R.id.tv_shop_name);
            Button =(android.widget.Button)itemView.findViewById(R.id.tb_add_shop);



        }
    }
}
