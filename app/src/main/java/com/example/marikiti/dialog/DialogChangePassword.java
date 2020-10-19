package com.example.marikiti.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.marikiti.R;
import com.example.marikiti.app.Marikiti;

import gautam.easydevelope.animation.ViewAnimation;
import gautam.easydevelope.data.AppPrefs;



/**
 * Created by Mehraj on 4/4/2017.
 */

public class DialogChangePassword {

    private final String TAG = getClass().getSimpleName();
    static Dialog dialog;
    static Context mContext;
    static AppPrefs prefs;

    public static class Holder {
        private static EditText et_old_password, et_new_password, et_confirm_new_password;
        private static Button btn_cancel, btn_ok;
        private static View v_line1, v_line2, v_line3;
    }

    public static void openDialog(final Context mContext) {
        DialogChangePassword.prefs = Marikiti.getInstance().getPrefs();
        DialogChangePassword.mContext = mContext;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dialog_change_pass, null);
        Holder.et_old_password = (EditText) view.findViewById(R.id.et_old_password);
        Holder.et_new_password = (EditText) view.findViewById(R.id.et_new_password);
        Holder.et_confirm_new_password = (EditText) view.findViewById(R.id.et_confirm_new_password);
        Holder.btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Holder.btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Holder.btn_ok.setOnClickListener(onClickListener);
        Holder.btn_cancel.setOnClickListener(onClickListener);
        Holder.v_line1 = view.findViewById(R.id.v_line1);
        Holder.v_line1.setAnimation(ViewAnimation.slideLeftToRight(mContext));
        Holder.v_line2 = view.findViewById(R.id.v_line2);
        Holder.v_line2.setAnimation(ViewAnimation.slideLeftToRight(mContext));
        Holder.v_line3 = view.findViewById(R.id.v_line3);
        Holder.v_line3.setAnimation(ViewAnimation.slideLeftToRight(mContext));
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    static View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btn_cancel:
                    hide();
                    break;

                case R.id.btn_ok:

                    if (Holder.et_old_password.getText().toString().length() == 0) {
                        Holder.et_old_password.setError(mContext.getText(R.string.mandatory));
                        Holder.et_old_password.requestFocus();
                    } else if (Holder.et_new_password.getText().toString().length() == 0) {
                        Holder.et_new_password.setError(mContext.getText(R.string.mandatory));
                        Holder.et_new_password.requestFocus();
                    } else if (Holder.et_confirm_new_password.getText().toString().length() == 0) {
                        Holder.et_confirm_new_password.setError(mContext.getText(R.string.mandatory));
                        Holder.et_confirm_new_password.requestFocus();
                    } else {
                        if (Holder.et_new_password.getText().toString().equals(Holder.et_confirm_new_password.getText().toString())) {
                            //changePassword();
                        } else {
                            Holder.et_confirm_new_password.setError(mContext.getString(R.string.password_did_not_match));
                        }

                    }
                    break;
            }
        }
    };

    public static void hide() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

/*
    private static void changePassword() {
        final String TAG = "DialogChangePassword";
        Map<String, String> map = new HashMap<>();
        map.put("login_id", AppPrefs.getString(App.Key.ID_LOGGED));
        map.put("old_pass", Holder.et_old_password.getText().toString());
        map.put("new_pass", Holder.et_new_password.getText().toString());

        Log.e(TAG, "checkProfile: " + map.toString());
        new Network(mContext, BASE_URL + "change_password.php", map, new Network.OnResponseListener() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e(TAG, "Responce: " + response);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        hide();
                        DialogWarning.openDialog(mContext,
                                "<font color='#0db6af'><b>Successfully Change</b></font>", // title
                                "Your password has been reset",// description
                                "Ok", // button text
                                new DialogWarning.OnOkClickListener() {
                                    @Override
                                    public void onOkClick() {
                                        //mContext.startActivity(new Intent(mContext, LoginActivity.class));
                                        hide();
                                    }
                                });


                    } else if (status.equals("2")) {
                        DialogWarning.openDialog(mContext,
                                "<font color='#0db6af'><b>Password not match!</b></font>", // title
                                "<font color='#525252'>Your old password did not match.. Please enter correct password</font>", // description
                                "OK", // button text
                                new DialogWarning.OnOkClickListener() {
                                    @Override
                                    public void onOkClick() {
                                        hide();
                                        DialogChangePassword.openDialog(mContext);
                                    }
                                });
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: ", e);
                    e.printStackTrace();
                }
            }
        }).getData(true);
    }
*/
}