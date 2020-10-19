package com.example.marikiti.dialog;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import com.example.marikiti.R;
import gautam.easydevelope.utils.ViewUtils;
import gautam.easydevelope.widget.GButton;
import gautam.easydevelope.widget.GTextView;
import gautam.easydevelope.widget.GautamDialog;


public class DialogInfo {
    static Context mContext;
    static GautamDialog gautamDialog;
    static GTextView tv_title, tv_desc, tv_desc2;
    private static GButton btn_cancel, btn_ok;
    private static ProgressBar progressBar;

    private static View.OnClickListener clickListener;


    public static void show() {
        if (gautamDialog != null)
            gautamDialog.dismiss();
        gautamDialog.show();
    }

    public static void init(Context context) {
        DialogInfo.mContext = context;
        if (mContext != null) {
            if (gautamDialog != null && gautamDialog.getDialog().isShowing())
                gautamDialog.dismiss();
            gautamDialog = GautamDialog.with(mContext).addView(new GautamDialog.GViewAdder() {
                @Override
                public View defineView(LayoutInflater layoutInflater) {
                    return setUpWithView(layoutInflater);
                }
            }).setGravity(Gravity.CENTER);
        }
    }

    public static void hide() {
        if (gautamDialog != null)
            gautamDialog.dismiss();
    }

    private static View setUpWithView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_info, null);
        tv_title = view.findViewById(R.id.tv_title);
        tv_desc = view.findViewById(R.id.tv_desc);
        tv_desc2 = view.findViewById(R.id.tv_desc2);
        progressBar = view.findViewById(R.id.progressBar);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_ok = view.findViewById(R.id.btn_ok);

        ViewUtils.hideView(btn_cancel);
        ViewUtils.hideView(btn_ok);
        ViewUtils.hideView(progressBar);
        ViewUtils.hideView(tv_title);
        ViewUtils.hideView(tv_desc);
        ViewUtils.hideView(tv_desc2);
        return view;
    }

    public static void setTitle(String title) {
        if (tv_title != null) {
            tv_title.setText(Html.fromHtml(title));
            ViewUtils.showView(tv_title);
        } else {
            new Exception("First of all initialize your dialog").printStackTrace();
        }
    }

    public static void setDesc(String desc) {
        if (tv_desc != null) {
            tv_desc.setText(Html.fromHtml(desc));
            ViewUtils.showView(tv_desc);
        } else {
            new Exception("First of all initialize your dialog").printStackTrace();
        }

    }

    public static void setDesc2(String desc) {
        if (tv_desc2 != null) {
            tv_desc2.setText(Html.fromHtml(desc));
            ViewUtils.showView(tv_desc2);
        } else {
            new Exception("First of all initialize your dialog").printStackTrace();
        }

    }

    public static void setOkButton(String text, View.OnClickListener onClickListener) {
        if (btn_ok != null) {
            btn_ok.setText(Html.fromHtml(text));
            btn_ok.setOnClickListener(onClickListener);
            ViewUtils.showView(btn_ok);
        }
    }

    public static void setCancelButton(String text, View.OnClickListener onClickListener) {
        if (btn_cancel != null) {
            btn_cancel.setText(Html.fromHtml(text));
            btn_cancel.setOnClickListener(onClickListener);
            ViewUtils.showView(btn_cancel);
        }
    }


    public static GautamDialog getGautamDialog() {
        return gautamDialog;
    }
}
