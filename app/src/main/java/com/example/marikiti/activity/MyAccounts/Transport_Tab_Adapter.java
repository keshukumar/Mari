package com.example.marikiti.activity.MyAccounts;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.marikiti.R;

import com.example.marikiti.model.Transporter_model;

import java.util.ArrayList;
import java.util.List;

import gautam.easydevelope.widget.GCircularImageView;

import static com.example.marikiti.utilities.APP_URL.PROFILE_IMAGES;


public class Transport_Tab_Adapter extends RecyclerView.Adapter<Transport_Tab_Adapter.ViewHolder> implements Filterable {
    Activity activity;
    List<Transporter_model> list;
    List<Transporter_model> searchlist;
    public Transport_Tab_Adapter(Activity activity, List<Transporter_model> list) {
        this.activity = activity;
        this.list = list;
        searchlist=new ArrayList<>(list);
    }

    public Transport_Tab_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.inflator_select_trader,parent,false);
        Transport_Tab_Adapter.ViewHolder holder=new Transport_Tab_Adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Transport_Tab_Adapter.ViewHolder  viewHolder, int position) {
        final Transporter_model model=list.get(position);

        viewHolder.tv_trader_code.setText(model.getUser_code());
        viewHolder.tv_mobile.setText(model.getTra_phone_no());
        viewHolder.tv_trader_name.setText(model.getTra_full_name());
        Log.d("response","https://marikiti.app/profile_images/"+model.getProfile_pic());

        Glide.with(activity)
                .load(PROFILE_IMAGES+model.getProfile_pic())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public Filter getFilter() {
        return  exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Transporter_model> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(searchlist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Transporter_model item : searchlist) {
                    if (item.getUser_code().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_trader_name,tv_mobile,tv_trader_code,tv_wardname;
        GCircularImageView imageView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_trader_name=itemView.findViewById(R.id.tv_trader_name);
            tv_mobile=itemView.findViewById(R.id.tv_mobile);
            tv_trader_code=itemView.findViewById(R.id.tv_trader_code);
            imageView=itemView.findViewById(R.id.image);
            tv_wardname=itemView.findViewById(R.id.tv_wardname);
        }

    }


}
