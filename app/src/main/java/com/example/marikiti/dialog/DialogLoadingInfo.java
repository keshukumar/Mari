package com.example.marikiti.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.example.marikiti.R;

import gautam.easydevelope.widget.GautamDialog;


public class DialogLoadingInfo {
    static Context mContext;
    static GautamDialog gautamDialog;
    static String loadingInfo;
    static TextView tv_info;


    public static void show(Context context, String loadingInfo) {
        DialogLoadingInfo.mContext = context;
        if (mContext != null){
            DialogLoadingInfo.loadingInfo = loadingInfo;
            if (gautamDialog != null && gautamDialog.getDialog().isShowing())
                gautamDialog.dismiss();

            gautamDialog = GautamDialog.with(mContext).addView(new GautamDialog.GViewAdder() {
                @Override
                public View defineView(LayoutInflater layoutInflater) {

                    return setUpWithView(layoutInflater);
                }
            }).setGravity(Gravity.CENTER);
//        AnimationDialog.with(context, gautamDialog.getDialog());
            if (gautamDialog != null)
                gautamDialog.dismiss();
            gautamDialog.show();
        }
    }

    public static void hide() {
        if (gautamDialog != null)
            gautamDialog.dismiss();
    }

    public static void refreshContent(String loadingInfo) {
        tv_info.setText(loadingInfo);
    }

    private static View setUpWithView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_loading_info, null);
        tv_info = (TextView) view.findViewById(R.id.inflator_dialog_network_info_tv_info);
        tv_info.setText(loadingInfo);

        return view;
    }


}
