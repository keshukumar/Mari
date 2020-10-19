package gautam.easydevelope.animation;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import gautam.easydevelope.R;

/**
 * Created by gautam on 2/16/2017.
 */

public class AnimationSlide {
    Context mContext;

    public AnimationSlide(Context mContext) {
        this.mContext = mContext;
    }

    public static AnimationSlide with(Context context){
        return new AnimationSlide(context);
    }

    public Animation up(){
        Animation slide_up = AnimationUtils.loadAnimation(mContext, R.anim.slide_up);
        return slide_up;
    }

    public Animation down(){
        Animation slide_down = AnimationUtils.loadAnimation(mContext, R.anim.slide_down);
        return slide_down;
    }

    public Animation right(){
        Animation slide_right = AnimationUtils.loadAnimation(mContext, R.anim.slide_right);
        return slide_right;
    }

    public Animation right_back(){
        Animation slide_right = AnimationUtils.loadAnimation(mContext, R.anim.slide_left);
        return slide_right;
    }

    public Animation left(){
        Animation slide_left = AnimationUtils.loadAnimation(mContext, R.anim.slide_right);
        return slide_left;
    }
}
