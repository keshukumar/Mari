package com.example.marikiti.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.example.marikiti.R;
import com.example.marikiti.activity.MyAccounts.Transport_Tab_Adapter;
import com.example.marikiti.adapter.Main_Other_Adapter;
import com.example.marikiti.model.Main_Transfer;
import com.example.marikiti.model.Shop_Trader_List_model;
import com.example.marikiti.model.Transporter_model;
import com.example.marikiti.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.marikiti.utilities.APP_URL.MAIN_URL;

public class Transporter_Frag extends Fragment {
    Main_Transfer model;
    RecyclerView recyclerView;
    Transport_Tab_Adapter adapter;
    List<Transporter_model> list = new ArrayList<>();
    ProgressBar progressBar;
    TextView textView;
    Constant c=new Constant();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_transporter_, container, false);
        setupRecycler(root);
        return root;
    }

    public void setupRecycler(View v)
    {
        progressBar=v.findViewById(R.id.main_other_progressbar);
        recyclerView=v.findViewById(R.id.other_recycler);
        textView=v.findViewById(R.id.other_error);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager manager=new GridLayoutManager(getActivity(),1,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        fetchList();

    }

    private void fetchList()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MAIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response","shop_id="+model.getShop_id()+"\n"+response);
                if (!TextUtils.isEmpty(response))
                {
                    if (response.equals("1"))
                    {
                        textView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }else
                    {

                        textView.setVisibility(View.GONE);
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray =jsonObject.getJSONArray("server_response");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject o =jsonArray.getJSONObject(i);

                                Transporter_model models=new Transporter_model(o.getString("profile_pic"),o.getString("trader_id"),o.getString("tra_full_name"),o.getString("tra_phone_no"),o.getString("user_code"));
                                list.add(models);

                            }
                            adapter=new Transport_Tab_Adapter(getActivity(),list);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.INVISIBLE);
                        }catch(Exception e)
                        {
                            Log.d("response_error",e.toString());
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("type","transporter_list" );
                SharedPreferences sp=getActivity().getSharedPreferences(c.USER_DETAILS, Context.MODE_PRIVATE);
                params.put("mshop_id",sp.getString(c.TRADER_ID,""));
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}