package gautam.easydevelope.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.zip.Inflater;

import gautam.easydevelope.utils.ParamUtils;

public class GautamDialog {
    private final String TAG = getClass().getSimpleName();
    private Dialog mDialog;
    private String info;
    WindowManager.LayoutParams layoutParams;
    private GViewAdder gViewAdder;
    private LayoutInflater layoutInflater;
    public static GautamDialog instance;

    public interface GViewAdder{
        View defineView(LayoutInflater layoutInflater);
    }

    public GautamDialog(Context mContext) {
        this.mDialog = new Dialog(mContext);
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initDialog();
    }

    public GautamDialog addView(GViewAdder gViewAdder){
        this.gViewAdder = gViewAdder;
        if (gViewAdder != null)
            mDialog.setContentView(gViewAdder.defineView(layoutInflater));
        return instance;
    }

    public static GautamDialog with(Context gContext) {
        instance = new GautamDialog(gContext);
        return instance;
    }

    private void initDialog() {
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(mDialog.getWindow().getAttributes());
        layoutParams.height = ParamUtils.WRAP_CONTENT;
        layoutParams.width = ParamUtils.WRAP_CONTENT;
        mDialog.getWindow().setAttributes(layoutParams);
        setGravity(Gravity.CENTER);
    }

    public void setContentView(View contentView){
        mDialog.setContentView(contentView);
    }

    public GautamDialog setWidth(int width) {
        layoutParams.width = width;
        mDialog.getWindow().setAttributes(layoutParams);
        return instance;
    }

    public GautamDialog dialogNotify(){
        mDialog.notify();
        return instance;
    }

    public Dialog getDialog(){
        return mDialog;
    }

    public GautamDialog setHeight(int height) {
        layoutParams.height = height;
        mDialog.getWindow().setAttributes(layoutParams);
        return instance;
    }

    public GautamDialog setCancelable(boolean flag) {
        mDialog.setCancelable(flag);
        return instance;
    }


    public GautamDialog setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return instance;
    }

    public GautamDialog setGravity(int gravity) {
        mDialog.getWindow().setGravity(gravity);
        return instance;
    }

    public GautamDialog show() {
        if (!mDialog.isShowing())
            mDialog.show();
        return instance;
    }

    public GautamDialog dismiss() {
        if (mDialog.isShowing())
            mDialog.dismiss();
        instance = null;
        return instance;
    }

    public boolean isShowing(){
        return mDialog.isShowing();
    }


    // use this dialog using
     /*GautamDialog.with(this).addView(new GautamDialog.GViewAdder() {
        @Override
        public View defineView(LayoutInflater layoutInflater) {
            GTextView gTextView = new GTextView(getApplicationContext());
            gTextView.setText("Hello Gautam");

            return layoutInflater.inflate(R.layout.activity_splash, null);
        }
    }).setGravity(Gravity.BOTTOM).show();*/

}
