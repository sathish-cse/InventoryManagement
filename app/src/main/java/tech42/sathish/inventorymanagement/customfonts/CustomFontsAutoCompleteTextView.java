package tech42.sathish.inventorymanagement.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class CustomFontsAutoCompleteTextView extends AutoCompleteTextView {

    public CustomFontsAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomFontsAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomFontsAutoCompleteTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Light.ttf");
            setTypeface(tf);
        }
    }

}