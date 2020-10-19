package com.example.marikiti.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.marikiti.R;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.model.AddShop_Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.ITEMS_IMAGES;
import static com.example.marikiti.utilities.APP_URL.SHOP_STATUS_CHANGE;

public class TraderShopAdapter extends RecyclerView.Adapter<TraderShopAdapter.ViewHolder> {


    List<AddShop_Model> addShop_models;
    private Context context;

    public TraderShopAdapter(List<AddShop_Model> addShop_models, Context context) {
        this.addShop_models = addShop_models;
        this.context = context;
    }

    @NonNull
    @Override
    public TraderShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View v =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trader_shop_list_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TraderShopAdapter.ViewHolder viewHolder, int i) {
        AddShop_Model addShop_model = addShop_models.get(i);
        String url =ITEMS_IMAGES+addShop_model.getShop_image_name();

        Glide.with(context).load(url).into(viewHolder.shop_image);
        viewHolder.shop_name.setText(addShop_model.getShop_name());
    }

    @Override
    public int getItemCount() {
        return addShop_models.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        GCircularImageView shop_image;
        GTextView shop_name;
        ToggleButton toggleButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shop_image =(GCircularImageView)itemView.findViewById(R.id.img_pImage);
            shop_name  =(GTextView)itemView.findViewById(R.id.tv_shop_name);
            toggleButton =(ToggleButton)itemView.findViewById(R.id.tb_add_shop);

            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    int i =getAdapterPosition();
                    final AddShop_Model addShop_model =addShop_models.get(i);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, SHOP_STATUS_CHANGE, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equals("1"))
                            {
                                toggleButton.setChecked(true);
                                Toast.makeText(view.getContext(),"Shop Active",Toast.LENGTH_LONG).show();
                            }else   if(response.equals("2"))
                            {
                                toggleButton.setChecked(false);
                                Toast.makeText(view.getContext(),"Shop De-Active",Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String>params = new HashMap<>();
                            params.put("mtrader_shop_id",addShop_model.getShop_id());
                            return params;
                        }
                    };
                    Marikiti.getInstance().addToRequestQueue(stringRequest);
                }
            });
        }
    }
}
