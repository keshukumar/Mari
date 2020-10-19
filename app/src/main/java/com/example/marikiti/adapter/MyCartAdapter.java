package com.example.marikiti.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.example.marikiti.activity.MyCartActivity;
import com.example.marikiti.model.MyCart;
import com.example.marikiti.model.Supermarket_model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gautam.easydevelope.utils.WindowUtils;
import gautam.easydevelope.widget.GCircularImageView;
import gautam.easydevelope.widget.GTextView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.marikiti.utilities.APP_URL.MAIN_URL;
import static com.example.marikiti.utilities.APP_URL.SUPERMARKET_IMAGE;


public class MyCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @LayoutRes
    Integer resId = R.layout.inflator_my_cart;
    private List<MyCart> myCartList;
    private boolean mSpinnerInitialized;
    private Activity context;
    private final String TAG = getClass().getSimpleName();
    private OnRecyclerListItemClickListener onRecyclerListItemClickListener = null;
    private WindowUtils utils;
    int total_amount=0,currentItem;


    public OnRecyclerListItemClickListener getOnRecyclerListItemClickListener() {
        return onRecyclerListItemClickListener;
    }

    public void setOnRecyclerListItemClickListener(OnRecyclerListItemClickListener onRecyclerListItemClickListener) {
        this.onRecyclerListItemClickListener = onRecyclerListItemClickListener;
    }

    public MyCartAdapter(Activity context, List<MyCart> ts) {
        this.myCartList = ts;
        this.context = context;
        utils = WindowUtils.with(context);
    }

    @Override
    public int getItemCount() {
        //return ts.size();
        return myCartList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resId, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyCart myCart=myCartList.get(position);
        final MyViewHolder myViewHolder=(MyViewHolder) holder;
        myViewHolder.tv_product_name.setText(myCart.getProduct_name());
        myViewHolder.tv_product_size.setText(myCart.getSize());
        myViewHolder.tv_price.setText(myCart.getPrice());
        myViewHolder.tv_product_code.setText(myCart.getProduct_code());
        Glide.with(context).load(SUPERMARKET_IMAGE+myCart.getImage_name()).into(myViewHolder.img_pImage);
        myViewHolder.sp_quantity.setSelection(Integer.valueOf(myCart.getQuantity())-1);


        int amount=Integer.valueOf(myCart.getPrice().replace("Ksh.", ""));
        total_amount=total_amount+(amount*Integer.valueOf(myCart.getQuantity()));
        myViewHolder.tv_total_purchase.setText("Ksh."+total_amount+".0");

//
//        //spinner change lister


        myViewHolder.sp_quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              parent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                  @Override
                  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                      fetch_item_data(myCart.getCart_id(),myViewHolder.sp_quantity.getSelectedItem().toString());
                  }

                  @Override
                  public void onNothingSelected(AdapterView<?> parent) {

                  }
              });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
delete(myCart.getCart_id(),position);
            }
        });


    }

    public void removeAt(int position) {
        myCartList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, myCartList.size());
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private int position;
        // declare view in holder
        private View rootView;
        private GCircularImageView img_pImage;
        GTextView tv_product_name,tv_product_size,tv_product_code,tv_price,tv_total_purchase;
        private RelativeLayout rl_mains;
        private Spinner sp_quantity;
        ImageView delete;


        public MyViewHolder(View view) {
            super(view);
            rootView = view;

            // init view here
            img_pImage = view.findViewById(R.id.img_pImage);
            tv_product_name=view.findViewById(R.id.tv_product_name);
            tv_product_size=view.findViewById(R.id.tv_product_size);
            tv_product_code=view.findViewById(R.id.tv_product_code);
            tv_price=view.findViewById(R.id.tv_price);
            sp_quantity=view.findViewById(R.id.sp_quantity);
            tv_total_purchase=context.findViewById(R.id.tv_total_purchase);
            delete=view.findViewById(R.id.delete);
        }




    }


    public void fetch_item_data(final String id, final String qt)
    {

        final ProgressDialog loading=ProgressDialog.show(context,"Loading...","Please Wait..",false);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response","item= "+response);
                Supermarket_model supermarket_model;
                if (!TextUtils.isEmpty(response))
                {

//                    notifyDataSetChanged();


                }else
                {


                }
                loading.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                loading.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("type","update_quantity_cart" );
                params.put("mcart_id",id);
                params.put("mquantity",qt);


                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);


    }

    public void delete(final String cart_id,final int position)
    {
        final ProgressDialog loading=ProgressDialog.show(context,"Loading...","Please Wait..",false);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                removeAt(position);
                loading.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                loading.dismiss();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("type","remove_item_cart" );
                params.put("mcart_id",cart_id);



                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

}