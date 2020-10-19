package com.example.marikiti.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.marikiti.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    ArrayList<Uri> arrayList;
    Bitmap bitmap;

    public MyAdapter(Context context, ArrayList<Uri> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        EditText name;
        Button btn;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            btn=itemView.findViewById(R.id.submit);
            name=itemView.findViewById(R.id.imagename);
        }
    }

    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.list_items,viewGroup,false);
        MyAdapter.ViewHolder viewHolder=new MyAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.ViewHolder viewHolder, final int i)
    {
        Log.d("response","list="+arrayList.get(i));
        Glide.with(context).load(arrayList.get(i)).into(viewHolder.imageView);
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), arrayList.get(i));
                    if (viewHolder.name.getText().toString().trim().length()>0)
                    { loadConsiSpinnerData(i,viewHolder.name.getText().toString().trim());
                    }else
                    { Toast.makeText(context,"Please enter name.",Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e)
                { e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }




    public String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) throws IOException
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        int h=image.getHeight();
        int w =image.getWidth();
        h=h/4;
        w=w/4;
        image =Bitmap.createScaledBitmap(image,w,h,true);
        // image = new Compressor(this).compressToBitmap(image);
        // image = SiliCompressor.with(getApplicationContext()).getCompressBitmap(path.toString());
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private void loadConsiSpinnerData(final int position,final String name)
    {
        final ProgressDialog load =ProgressDialog.show(context,"Loading.","Please Wait..",false,false);
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://marikiti.app/modal.php", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
            if (response.equals("1"))
            {
                Toast.makeText(context,"Success upload !",Toast.LENGTH_SHORT).show();
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,arrayList.size());
            }
                load.dismiss();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                load.dismiss();
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError
            {

                Map<String,String> params=new HashMap<>();
                try
                {
                    params.put("image",encodeToBase64(bitmap, Bitmap.CompressFormat.PNG, 90));
                    params.put("type","upload_supermarket");
                    params.put("mimage_name",name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


}
