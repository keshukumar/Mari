package com.example.marikiti.adapter;

import android.view.View;


public interface OnRecyclerListItemClickListener<T> {
    void onItemClick(View view, View rootView, T t, int position);
}
