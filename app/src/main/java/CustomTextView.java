import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by costa on 16/08/2022.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fuentes/boxi_bold.otf");
        this.setTypeface(typeface);
    }
}