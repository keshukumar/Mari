package com.example.marikiti.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.marikiti.R;
import com.example.marikiti.model.cart_model;

import java.util.List;

import static com.example.marikiti.utilities.APP_URL.CATEGORY_IMAGES;

public class Cart_sub_adapter extends RecyclerView.Adapter<Cart_sub_adapter.ViewHolder> {
    Activity context;
    List<cart_model> list;
    private TextView tv_item_name,tv_sub_item_name,tv_price;
    private ImageView item_image;
    public Cart_sub_adapter(Activity activity, List<cart_model> list) {
        this.context = activity;
        this.list = list;
    }

    public Cart_sub_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(context).inflate(R.layout.inflate_cart_sub_item,viewGroup,false);
        Cart_sub_adapter.ViewHolder holder=new Cart_sub_adapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Cart_sub_adapter.ViewHolder viewHolder, int i) {
        final cart_model model=list.get(i);
        if (model.getMdescription().equals("null"))
        {  viewHolder.textView.setText("Description is not available.");
        }else
        { viewHolder.textView.setText(model.getMdescription());
        }
        Glide.with(context).load(CATEGORY_IMAGES+model.getImage_name()).into(viewHolder.imageView);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_item_name.setText(model.getItem_name());
                if (model.getMdescription().equals("null"))
                {
                    tv_sub_item_name.setText("Description is not available.");
                }else
                {
                    tv_sub_item_name.setText(model.getMdescription());
                }
                Glide.with(context).load(CATEGORY_IMAGES+model.getImage_name()).into(item_image);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.cart_sub_image);
            textView=itemView.findViewById(R.id.cart_sub_name);

            tv_item_name=context.findViewById(R.id.cart_item_name);
            tv_sub_item_name=context.findViewById(R.id.cart_item_sub_name);
            tv_price=context.findViewById(R.id.cart_price);
            item_image=context.findViewById(R.id.cart_image);

        }
    }
}
