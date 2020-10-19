package com.example.marikiti.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.marikiti.R;
import com.example.marikiti.activity.MyAccounts.Product_Unit;
import com.example.marikiti.app.Marikiti;
import com.example.marikiti.utilities.APP_URL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.marikiti.utilities.APP_URL.PRODUCT_ITEM_QUANTITY_ACTION;

public class ProductUnit_Adapter extends RecyclerView.Adapter<ProductUnit_Adapter.ViewHolder> {
    Context context;
    List<Product_Unit> list;
    APP_URL u=new APP_URL();
    Spinner quantity;
    String unit[]={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};
    public ProductUnit_Adapter(Context context, List<Product_Unit> list) {
        this.context = context;
        this.list = list;
    }
    public ProductUnit_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.inflate_product_unit,viewGroup,false);
        ProductUnit_Adapter.ViewHolder  holder=new ProductUnit_Adapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductUnit_Adapter.ViewHolder viewHolder, int i) {



        if (i<list.size())
        {
            final Product_Unit model=list.get(i);


        try{
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.spinner_item, unit);
            adapter.setDropDownViewResource(R.layout.spinner_item);
            quantity.setAdapter(adapter);
   int p=Integer.parseInt(model.getUnit_price());
   if (p<=30 )
   {
       int spinnerPosition = adapter.getPosition(model.getUnit_price());
       quantity.setSelection(spinnerPosition);
   }

        }catch (Exception e){}

        viewHolder.et_unit.setText(model.getProduct_price());
        int u=Integer.valueOf(model.getUnit_price());
        int a=Integer.valueOf(model.getProduct_price());
            final int totalamount;
        if (u>0)
        {
            totalamount=u*a;
        }else
        {
            totalamount=1*a;
        }
viewHolder.et_unit.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        int a=Integer.valueOf(String.valueOf(charSequence));
        int u=Integer.valueOf(quantity.getSelectedItem().toString());
        int t=a*u;
        viewHolder.tv_amount.setText("Ksh. "+String.valueOf(t));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
});
        viewHolder.tv_amount.setText("Ksh. "+String.valueOf(totalamount));
        if (model.getAction_status().equals("0"))
        {
            viewHolder.tb_status.setChecked(false);
        }else  if (model.getAction_status().equals("1"))
        {
            viewHolder.tb_status.setChecked(true);
        }
        viewHolder.tb_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    fetch_post("1",model.getProduct_size_id());
                }else
                {
                    fetch_post("0",model.getProduct_size_id());
                }
            }
        });
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_amount;
        EditText et_unit;
        ToggleButton tb_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_amount=itemView.findViewById(R.id.product_unit_amount);
            quantity=itemView.findViewById(R.id.addproduct_quantity);
            et_unit=itemView.findViewById(R.id.product_unit_price);
            tb_status=itemView.findViewById(R.id.product_unittoggle);

        }
    }

    public void fetch_post(final String status, final String item_id)
    {
        final String s;
        if (status.equals("1"))
        {
            s="Activate";
        }else
        {
            s="De-Activate";
        }
        final ProgressDialog load=ProgressDialog.show(context,"Loading..","Please Wait..!",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,PRODUCT_ITEM_QUANTITY_ACTION, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                Log.d("status","resp="+response);
                if (response.equals("1"))
                {
                    Toast.makeText(context,"Successful "+s ,Toast.LENGTH_LONG).show();

                }else
                {
                    Toast.makeText(context,"Fail "+s ,Toast.LENGTH_LONG).show();

                }
                load.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                Log.d("response_error",error.toString());
                load.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                Log.d("status",status+" id "+item_id);
                params.put("maction_type",status);
                params.put("mproduct_size_id",item_id);
                return params;
            }
        };

        Marikiti.getInstance().addToRequestQueue(stringRequest);
    }
}
