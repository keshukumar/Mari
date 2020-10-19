package gautam.easydevelope.utils;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.IdRes;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by gautam on 1/13/2017.
 */

public class ViewUtils {

    View[] views;
    private static ViewUtils instance;

    public ViewUtils() {
    }

    public static void hideView(View view){
        if (view.getVisibility() == ImageView.VISIBLE) {
            view.setVisibility(TextView.GONE);
        }
    }
    public static void showView(View view){
        if (view.getVisibility() == ImageView.GONE) {
            view.setVisibility(TextView.VISIBLE);
        }
    }

    public static boolean isEditTextValid(EditText view, String errorMessage){
        String text = view.getText().toString();
        if (text.length() == 0) {
            view.setError(errorMessage);
            view.requestFocus();
            return false;
        }else {
            return true;
        }
    }

    public static boolean isMobileValid(EditText view, String errorMessage){
        String text = view.getText().toString();
        if (text.length()<10) {
            view.setError(errorMessage);
            view.requestFocus();
            return false;
        }else {
            return true;
        }
    }

    public static boolean isPassValid(EditText view, String errorMessage){
        String text = view.getText().toString();
        if (text.length()<6) {
            view.setError(errorMessage);
            view.requestFocus();
            return false;
        }else {
            return true;
        }
    }

    public static boolean isEmailEditTextValid(final EditText view, String errorMessage, String emailPatternMessage ) {
        final String emailPattern = "[a-zA-Z0-9.'_-]+@[a-z]+\\.+[a-z]+";
        String text = view.getText().toString();
        if (view.getText().toString().trim().matches(emailPattern) & isEditTextValid(view, errorMessage))
        {
            return true;
        }
        else
        {
            view.setError(errorMessage);
            view.requestFocus();
            return  false;
        }

    }

    public static boolean isStateValid(final EditText view,  String emailPatternMessage ) {
        final String emailPattern = "[a-zA-Z]+";
        if (view.getText().toString().trim().matches(emailPattern))
        {
            return true;
        }
        else
        {
            view.setError(emailPatternMessage);
            view.requestFocus();
            return  false;
        }


    }



        public static void refreshActivity(Activity activity){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            activity.recreate();
//        } else {
//            Intent intent = activity.getIntent();
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            activity.finish();
//            activity.overridePendingTransition(0, 0);
//            activity.startActivity(intent);
//            activity.overridePendingTransition(0, 0);
//        }
        Intent intent = activity.getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
//        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        activity.finish();
    }

    public ViewUtils findViewIdActivity(Activity activity, @IdRes Integer[] viewIds){
        views = new View[viewIds.length];
        for (int i = 0; i < viewIds.length; i++) {
            views[i] = activity.findViewById(viewIds[i]) ;
        }
        return instance;
    }

    public ViewUtils findViewIdFragment(View view, @IdRes Integer[] viewIds){
        views = new View[viewIds.length];
        for (int i = 0; i < viewIds.length; i++) {
            views[i] = view.findViewById(viewIds[i]) ;
        }
        return instance;
    }


    public void setOnClickListener(View.OnClickListener onClickListener){
        for ( View view : views) {
            view.setOnClickListener(onClickListener);
        }
    }

    public static ViewUtils getInstance() {
        instance = new ViewUtils();
        return instance;
    }
}
