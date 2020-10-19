package com.example.marikiti.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.marikiti.R;
import com.example.marikiti.activity.MyAccounts.Trader_Shop_List_Activity;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.model.AddShop_Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.ADD_SHOP;
import static com.example.marikiti.utilities.APP_URL.ITEMS_IMAGES;

public class AddShopAdapter extends RecyclerView.Adapter<AddShopAdapter.ViewHolder> {

    List<AddShop_Model> addShop_models;
    Activity context;

    public AddShopAdapter(List<AddShop_Model> addShop_models, Activity context) {
        this.addShop_models = addShop_models;
        this.context = context;
    }

    @NonNull
    @Override
    public AddShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shops_list_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddShopAdapter.ViewHolder viewHolder, int i) {
        final AddShop_Model addShop_model = addShop_models.get(i);
        String url = ITEMS_IMAGES + addShop_model.getShop_image_name();

        Glide.with(context).load(url).into(viewHolder.shop_image);
        viewHolder.shop_name.setText(addShop_model.getShop_name());

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (addShop_model.getShop_name().equals("The Outlet")) {
                    context.startActivity(new Intent(context, Trader_Shop_List_Activity.class)
                            .putExtra("id", addShop_model.getShop_id())
                    .putExtra("name",addShop_model.getShop_name()));
                }else if (addShop_model.getShop_name().equals("Health and Beauty")) {
                    context.startActivity(new Intent(context, Trader_Shop_List_Activity.class)
                            .putExtra("id", addShop_model.getShop_id())
                            .putExtra("name",addShop_model.getShop_name()));
                }else if (addShop_model.getShop_name().equals("Ready Meals")) {
                    context.startActivity(new Intent(context, Trader_Shop_List_Activity.class)
                            .putExtra("id", addShop_model.getShop_id())
                            .putExtra("name",addShop_model.getShop_name()));
                }else if (addShop_model.getShop_name().equals("Supermarket")) {
                    context.startActivity(new Intent(context, Trader_Shop_List_Activity.class)
                            .putExtra("id", addShop_model.getShop_id())
                            .putExtra("name",addShop_model.getShop_name()));
                }else{

                    SharedPreferences sp = context.getSharedPreferences("trader_id_sp", Context.MODE_PRIVATE);
                    final String traders_id = sp.getString("traders_id", null);
                    final ProgressDialog dialog= ProgressDialog.show(context,"Loading","Please Wait..",false);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_SHOP, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response",traders_id+"\n"+response);
                            if (response.equals("1")) {
                                viewHolder.button.setText("Remove");
                            }else if (response.equals("2"))
                            {  viewHolder.button.setText("Add");
                            }

                            dialog.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("mtrader_id", traders_id);
                            params.put("mshop_id", addShop_model.getShop_id());
                            return params;
                        }
                    };
                    Marikiti.getInstance().addToRequestQueue(stringRequest);

                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return addShop_models.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        GCircularImageView shop_image;
        GTextView shop_name;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shop_image =(GCircularImageView)itemView.findViewById(R.id.img_pImage);
            shop_name  =(GTextView)itemView.findViewById(R.id.tv_shop_name);
            button =(Button)itemView.findViewById(R.id.tb_add_shop);



        }
    }





}
