package com.example.marikiti.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


import com.example.marikiti.R;

import gautam.easydevelope.animation.AnimationDialog;


public class DialogWarning {

    public static class Holder {
        Button btn_ok;
        TextView title, description;
    }

    static Dialog dialog;

    public static void openDialog(Context mContext, String title, String description, String btnText, final OnOkClickListener onOkClickListener) {
        Holder holder = new Holder();
        dialog = new Dialog(mContext);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_warning, null);
//        view.setOnTouchListener(onTouchListener);
        holder.btn_ok = (Button) view.findViewById(R.id.ok);
        holder.title = (TextView) view.findViewById(R.id.title);
        holder.description = (TextView) view.findViewById(R.id.description);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        if (title != null) {
            holder.title.setText(Html.fromHtml(title));
        }
        if (description != null) {
            holder.description.setText(Html.fromHtml(description));
        }
        if (btnText != null) {
            holder.btn_ok.setText(Html.fromHtml(btnText));
        }
        holder.btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (onOkClickListener != null)
                    onOkClickListener.onOkClick();
            }
        });
        dialog.setCancelable(false);
        AnimationDialog.with(mContext, dialog).rightToRight();
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    public interface OnOkClickListener {
        public void onOkClick();
    }

}
