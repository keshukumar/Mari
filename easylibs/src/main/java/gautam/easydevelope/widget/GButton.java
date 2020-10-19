package gautam.easydevelope.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import gautam.easydevelope.R;

/**
 * Created by gautam on 3/20/2017.
 */

public class GButton extends AppCompatTextView {
    private Context gContext;
    private Typeface typeface;


    public GButton(Context context) {
        super(context);
        init(context, null, 0);

    }

    public GButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public GButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context gContext, AttributeSet attrs, int defStyle) {
        this.gContext = gContext;
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GTextView, defStyle, 0);
        if (a.hasValue(R.styleable.GTextView_g_font)) {
            String fontName = a.getString(R.styleable.GTextView_g_font);
            typeface = Typeface.createFromAsset(gContext.getApplicationContext().getAssets(), String.format("fonts/%s", fontName));
            setTypeface(typeface);
        }
        a.recycle();
    }

}
