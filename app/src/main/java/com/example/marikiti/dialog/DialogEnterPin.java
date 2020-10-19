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

import gautam.easydevelope.animation.AnimationDialog;
import gautam.easydevelope.utils.ViewUtils;


public class DialogEnterPin {
    private final String TAG = getClass().getSimpleName();
    static Dialog dialog;
    static Context mContext;


    public static class Holder {
        private static EditText et_enter_pin;
        private static Button btn_cancel, btn_ok;
    }

    public static void openDialog(final Context mContext) {
        DialogEnterPin.mContext = mContext;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dialog_enter_pin, null);
        Holder.et_enter_pin = (EditText) view.findViewById(R.id.et_enter_pin);
        //Holder.et_enter_pin.setText(AppPrefs.getString(App.Key.USER_EMAIL));
        Holder.btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Holder.btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Holder.btn_ok.setOnClickListener(onClickListener);
        Holder.btn_cancel.setOnClickListener(onClickListener);
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
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        AnimationDialog.with(mContext, dialog).bottomToTop();


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
                    if (ViewUtils.isEditTextValid(Holder.et_enter_pin, "Required")) {
                        //forgotPass();
                        hide();
                    }

                    break;
            }


        }
    };

    public static void hide() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private static void forgotPass() {

/*
        if (ViewUtils.isEmailEditTextValid(Holder.et_email, "Required", "Enter correct email")) {
            Map<String, String> map = new HashMap<>();
            map.put("email", Holder.et_email.getText().toString());
            new Network(mContext, BASE_URL + "forgot-pass.php", map, new Network.OnResponseListener() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            DialogInfo.init(mContext);
                            DialogInfo.setTitle("<font color='#ffffff'>Successfully Sent</font>");
                            DialogInfo.setDesc("An email has been sent to your reigstered email address " + "<font color='#3E7EBC'><b>" + Holder.et_email.getText().toString() + "</b></font> , please update password!");
                            DialogInfo.setOkButton("ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DialogInfo.hide();
                                    Intent intent = new Intent(mContext, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    mContext.startActivity(intent);

                                }
                            });
                            DialogInfo.getGautamDialog().setCanceledOnTouchOutside(false);
                            DialogInfo.show();
                        } else {
                            DialogInfo.init(mContext);
                            DialogInfo.setTitle("<font color='#131313'><b>Email Not Registered!</b></font>");
                            DialogInfo.setDesc("<font color='#525252'>Your email" + Holder.et_email.getText().toString() +  "is not registered, Please Register now</font>");
                            DialogInfo.setOkButton("ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DialogInfo.hide();
                                    Intent intent = new Intent(mContext, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    mContext.startActivity(intent);
                                }
                            });
                            DialogInfo.getGautamDialog().setCanceledOnTouchOutside(false);
                            DialogInfo.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).getData(true);

        }
*/
    }
}
