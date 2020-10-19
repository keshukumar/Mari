package gautam.easydevelope.animation;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.animation.Animation;

import gautam.easydevelope.R;

/**
 * Created by gautam on 2/16/2017.
 */

public class AnimationDialog {
    private Context mContext;
    private Dialog dialog;

    public AnimationDialog(Context mContext, Dialog dialog) {
        this.mContext = mContext;
        this.dialog = dialog;
    }

    public static AnimationDialog with(Context context, Dialog dialog){
        return new AnimationDialog(context, dialog);
    }

    public AnimationDialog bottomToTop(){
        animateDialog(R.style.animations_slide_bottom_to_top);
        return this;
    }

    public AnimationDialog topToBottom(){
        animateDialog(R.style.animations_slide_top_to_bottom);
        return this;
    }

    public AnimationDialog leftToRight(){
        animateDialog(R.style.animations_slide_left_to_right);
        return this;
    }

    public AnimationDialog rightToLeft(){
        animateDialog(R.style.animations_slide_right_to_left);
        return this;
    }

    public AnimationDialog rightToRight(){
        animateDialog(R.style.animations_slide_right_to_right);
        return this;
    }

    public AnimationDialog leftToLeft(){
        animateDialog(R.style.animations_slide_left_to_left);
        return this;
    }

    public AnimationDialog animateInnerView(final View dialogInnerView, final Animation onShow, final Animation onDismiss){
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog_i) {
                if (dialogInnerView != null)
                {
                    dialogInnerView.startAnimation(onShow);
                }

            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (dialogInnerView != null)
                {
                    dialogInnerView.startAnimation(onDismiss);
                }
            }
        });
        return this;
    }

    private void animateDialog(Integer resId){
        dialog.getWindow().setWindowAnimations(resId);
    }
}
