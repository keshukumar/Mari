package com.example.marikiti.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.example.marikiti.model.cart_model;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gautam.easydevelope.widget.GTextView;

import static com.example.marikiti.utilities.APP_URL.CATEGORY_IMAGES;
import static com.example.marikiti.utilities.APP_URL.MAIN_URL;

public class Cart_item_adapter extends RecyclerView.Adapter<Cart_item_adapter.ViewHolder> {

    Activity context;
    List<cart_model> item_list;
    List<cart_model> sub_item_list=new ArrayList<>();
    String trader_id;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private ImageView item_image;
    private TextView tv_item_name,tv_sub_item_name,tv_price;
    private ProgressBar progressBar;
    LinearLayout layout;
    private String SHOP_NAME;
    GTextView title;
boolean UPDATE=false;
    public Cart_item_adapter(Activity context, List<cart_model> item_list,String trader_id,String shop_name)
    {
        this.context = context;
        this.item_list = item_list;
        this.trader_id=trader_id;
        this.SHOP_NAME=shop_name;

    }

    public Cart_item_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.inflate_cart_item,viewGroup,false);
        Cart_item_adapter.ViewHolder holder=new Cart_item_adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Cart_item_adapter.ViewHolder viewHolder, int i)
    {

         final cart_model model=item_list.get(i);
        viewHolder.item_name.setText(model.getShopname());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetch_item_sub_data(model.getId());
                title.setText(model.getShopname());
            }
        });

     //   Log.d("response",model.getShopname()+"=="+SHOP_NAME);
//     if (model.getId().equals(SHOP_NAME))
//        {
//            Log.d("response",model.getShopname()+"=="+SHOP_NAME);
//            fetch_item_sub_data(model.getId());
//        }



    }

    @Override
    public int getItemCount()
    {
        return item_list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_name;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            item_name=itemView.findViewById(R.id.cart_adapter_item_name);
            tv_item_name=context.findViewById(R.id.cart_item_name);
            tv_sub_item_name=context.findViewById(R.id.cart_item_sub_name);
            tv_price=context.findViewById(R.id.cart_price);
            item_image=context.findViewById(R.id.cart_image);
            recyclerView=context.findViewById(R.id.cart_item_sub_recycler);
            progressBar=context.findViewById(R.id.cart_item_sub_progressBar);

            recyclerView.setHasFixedSize(false);
            GridLayoutManager manager=new GridLayoutManager(context,2,0,false);
            recyclerView.setLayoutManager(manager);

            layout=context.findViewById(R.id.cart_layout);
            title = (GTextView)context.findViewById(R.id.title);

            update();
        }
    }

    public void fetch_item_sub_data(final String shop_id)
    {
        Log.d("response",shop_id+"submit................................................................................");
        progressBar.setVisibility(View.VISIBLE);
        sub_item_list.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!TextUtils.isEmpty(response))
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject o =jsonArray.getJSONObject(i);
                          //  Log.d("response","sub_name="+o.getString("item_name")+" product="+o.getString("product_category")+" image="+o.getString("image_name"));
                            tv_item_name.setText(o.getString("item_name"));
                            if (o.getString("mdescription").equals("null"))
                            {
                                tv_sub_item_name.setText("Description is not available.");
                            }else
                            {
                                tv_sub_item_name.setText(o.getString("mdescription"));
                            }
                            Glide.with(context).load(CATEGORY_IMAGES+o.getString("image_name")).into(item_image);

                        }

                        JSONArray jsonArray1 =jsonObject.getJSONArray("server_response1");
                        sub_item_list.clear();
                        for(int i=0;i<jsonArray1.length();i++)
                        {
                            JSONObject o =jsonArray1.getJSONObject(i);
                          // Log.d("response","sub_name response1="+o.getString("item_name")+o.getString("product_category")+o.getString("image_name"));
                            cart_model cart_model=new cart_model(o.getString("image_name"),o.getString("item_name"),o.getString("product_category"),o.getString("mdescription"));
                            sub_item_list.add(cart_model);

                        }
                        layout.setVisibility(View.VISIBLE);
                        adapter=new Cart_sub_adapter(context,sub_item_list);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }catch(Exception e)
                    {
                        Log.d("response_error",e.toString());
                    }
                }else
                {
                    layout.setVisibility(View.GONE);
                }

                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressBar.setVisibility(View.GONE);
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("type","fetch_sub_item" );
                params.put("mtrader_id",trader_id);
                params.put("mshop_id",shop_id);
                params.put("types","subcategory");
                params.put("types","shop");

                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

    public void update()
    {

        for (int i=0;i<item_list.size();i++)
        {
            cart_model model=item_list.get(i);

            if (model.getId().equals(SHOP_NAME))
            {
                fetch_item_sub_data(model.getId());
            }

        }

    }
}
