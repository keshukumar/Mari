package com.example.marikiti.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.marikiti.R;

import com.example.marikiti.activity.MyShops.Supermarket_Pharmacy_Cart_System;
import com.example.marikiti.model.Supermarket_model;
import com.example.marikiti.utilities.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gautam.easydevelope.widget.GTextView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.marikiti.utilities.APP_URL.MAIN_URL;
import static com.example.marikiti.utilities.APP_URL.SUPERMARKET_IMAGE;

public class Supermarket_Cart_Adapter extends RecyclerView.Adapter<Supermarket_Cart_Adapter.ViewHolder> {
    Activity activity;
    List<Supermarket_model> list;
    TextView cart_item_name,cart_item_sub_name,cart_price,item_code,tv_product_id;
    ImageView cart_image;
   GTextView title;
   Spinner cart_spinner;
   TextView credit_amount,total_amount;
   Button cart_addtocart;
    SharedPreferences sp;
    Constant c=new Constant();
   int credit=0,total=0;
    public Supermarket_Cart_Adapter(Activity activity, List<Supermarket_model> list) {
        this.activity = activity;
        this.list = list;
    }

    public Supermarket_Cart_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(activity).inflate(R.layout.inflate_cart_sub_item,viewGroup,false);
        Supermarket_Cart_Adapter.ViewHolder holder=new Supermarket_Cart_Adapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Supermarket_Cart_Adapter.ViewHolder viewHolder, int i) {
        final Supermarket_model model=list.get(i);
        Log.d("response",model.getProduct_code());
        viewHolder.textView.setText(model.getDescription());
        Glide.with(activity).load(SUPERMARKET_IMAGE+model.getImage_name()).into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText(model.getProduct_category());
                cart_item_name.setText(model.getProduct_name());
                cart_item_sub_name.setText(model.getDescription());
                cart_price.setText(model.getPrice());
                item_code.setText(model.getProduct_code());
                tv_product_id.setText(model.getId());
                Glide.with(activity).load(SUPERMARKET_IMAGE+model.getImage_name()).into(cart_image);
            }
        });

        cart_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                credit_amount.setText(model.getPrice().replace("Ksh.",""));
                int p_amount= Integer.parseInt(( model.getPrice().replace("Ksh.","")));
                total=p_amount+total;
                total_amount.setText(String.valueOf(total));
                addtocart();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected  class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.cart_sub_image);
            textView=itemView.findViewById(R.id.cart_sub_name);
            sp=activity.getSharedPreferences(c.USER_DETAILS,MODE_PRIVATE);
            cart_item_name=activity.findViewById(R.id.cart_item_name);
            cart_item_sub_name=activity.findViewById(R.id.cart_item_sub_name);
            cart_price=activity.findViewById(R.id.cart_price);
            cart_image=activity.findViewById(R.id.cart_image);
            item_code=activity.findViewById(R.id.cart_item_code);
            title= (GTextView) activity.findViewById(R.id.title);
            credit_amount=activity.findViewById(R.id.credit_amount);
            cart_spinner=activity.findViewById(R.id.cart_spinner);
            tv_product_id=activity.findViewById(R.id.product_id);
            total_amount=activity.findViewById(R.id.t2);
            cart_addtocart=activity.findViewById(R.id.cart_addtocart);


        }
    }

    public void addtocart()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("resposnse","response= "+response);
                if (response.equals("1"))
                {
                    Toast.makeText(activity,"added",Toast.LENGTH_LONG).show();
                    Intent refresh = new Intent(activity, Supermarket_Pharmacy_Cart_System.class);
                    activity.startActivity(refresh);
                    activity.finish(); //
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d("response","id="+sp.getString(c.USER_KEY,""));
                Map<String, String> params = new HashMap<>();
                params.put("type","add_cart" );
                params.put("muser_id",sp.getString(c.USER_KEY,""));
                params.put("mquantity",cart_spinner.getSelectedItem().toString());
                params.put("mproduct_id",tv_product_id.getText().toString());
                params.put("msize","medium");
                params.put("mrole","sm");

                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}
