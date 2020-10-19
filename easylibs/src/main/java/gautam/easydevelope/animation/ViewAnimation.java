package gautam.easydevelope.animation;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import gautam.easydevelope.R;


/**
 * Created by gautam on 9/27/2016.
 */

public class ViewAnimation {

    public static Animation slideUp(Context mContext){
        Animation slide_up = AnimationUtils.loadAnimation(mContext,
                R.anim.slide_up);
        return slide_up;
    }

    public static Animation slideDown(Context mContext){
        Animation slide_up = AnimationUtils.loadAnimation(mContext,
                R.anim.slide_down);
        return slide_up;
    }

    public static Animation slideLeftToRight(Context mContext){
        Animation slide_up = AnimationUtils.loadAnimation(mContext,
                R.anim.slide_right);
        return slide_up;
    }

    public static Animation popUp(Context mContext){
        Animation slide_up = AnimationUtils.loadAnimation(mContext,
                R.anim.popup_enter);
        return slide_up;
    }


    public static void dialogAnimationBottomToTop(Context mContext, Dialog dialog, final View innerDialogView){
        final Context context = mContext;
        AnimationDialog.with(mContext, dialog).bottomToTop();

    }

}
