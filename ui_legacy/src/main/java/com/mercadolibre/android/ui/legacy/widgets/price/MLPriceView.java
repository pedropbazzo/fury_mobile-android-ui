package com.mercadolibre.android.ui.legacy.widgets.price;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mercadolibre.android.ui.legacy.R;
import com.mercadolibre.android.ui.legacy.utils.DimensionUtils;

@Deprecated
public class MLPriceView extends RelativeLayout {

    /**
     * AndroidStudio warns you when trying to set a value < 12.
     */
    private static final float MINIMUM_TEXT_SIZE = 12;

    /**
     * The multiplier used to compute fraction digits size.
     */
    private static final double DECIMAL_PART_MULTIPLIER = .4;

    final int ENTIRE = "entire".hashCode();
    final int DECIMALS = "decimals".hashCode();

    private TextView entirePartTextView;
    private TextView decimalsPartTextView;

    /**
     * Indicates whether or not to display the decimal part of the price.
     * <p>
     * By default it's {@code true}.
     */
    private boolean showDecimals = true;

    /**
     * It's the currency on which this price is displayed.
     * <p>
     * <strong>Important: </strong> If this attribute is not set before calling {@link #setPrice(java.lang.String, java.lang.String)} then no currency symbol wil be displayed.
     */
    private String currency;

    public MLPriceView(Context context) {
        this(context, null);
    }

    public MLPriceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MLPriceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.view_mlprice, this);

        if (!isInEditMode()) {
            entirePartTextView = findViewById(R.id.mlprice_entire_part);
            decimalsPartTextView = findViewById(R.id.mlprice_decimal_part);

            if (attrs != null) {
                applyCustomStyles(context, attrs);
            }
        }
    }

    /**
     * Apply the set of styles given in {@code attrs}.
     *
     * @param context The context to use.
     * @param attrs   The set of styles defined in the XML layout.
     */
    private void applyCustomStyles(Context context, AttributeSet attrs) {
        final String namespace = "http://schemas.android.com/apk/res/android";

        int userCustomId = attrs.getAttributeValue(namespace, "id").hashCode();
        entirePartTextView.setId(userCustomId + ENTIRE);
        decimalsPartTextView.setId(userCustomId + DECIMALS);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MLPriceView, 0, 0);
        try {
            int userCustomTextSize = (int) DimensionUtils.px2sp(context, typedArray.getDimensionPixelSize(R.styleable.MLPriceView_textSize, 0));
            setTextSize(userCustomTextSize);

            int color = typedArray.getColor(R.styleable.MLPriceView_textColor, getResources().getColor(R.color.black));
            entirePartTextView.setTextColor(color);
            decimalsPartTextView.setTextColor(color);
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * Update the corresponding {@link android.view.View}s to display the given {@code price}. It also updates {@link #decimalsPartTextView} visibility when corresponding.
     *
     * @param price            The new price to display to the user.
     * @param decimalSeparator used to properly display the widget
     */
    public void setPrice(String price, String decimalSeparator) {
        if (null == decimalSeparator) {
            entirePartTextView.setText((currency != null ? currency : "") + price);
            return;
        }

        String[] parts = splitPrice(price, decimalSeparator);
        String entirePart;
        if (parts.length != 0) {
            entirePart = parts[0];
        } else {
            entirePart = price;
        }
        entirePartTextView.setText((currency != null ? currency : "") + entirePart);
        if (parts.length > 1 && showDecimals) {
            decimalsPartTextView.setText(parts[1]);
            decimalsPartTextView.setVisibility(VISIBLE);
        }
    }

    /**
     * It splits the price in two: the entire and the decimal part. If the separating string is "."
     * the "\\" string should be added so the regex works
     *
     * @param price                    the price to split
     * @param originalDecimalSeparator the separating string
     * @return string array with the prices splitted
     */
    public static String[] splitPrice(String price, String originalDecimalSeparator) {
        String decimalSeparator;

        if (".".equals(originalDecimalSeparator)) {
            decimalSeparator = "\\.";
        } else {
            decimalSeparator = originalDecimalSeparator;
        }
        return price.split(decimalSeparator);
    }

    public TextView getEntirePart() {
        return entirePartTextView;
    }

    public TextView getDecimalsPart() {
        return decimalsPartTextView;
    }

    /**
     * @param userCustomTextSize The size to apply in {@link android.util.TypedValue#COMPLEX_UNIT_SP} unit.
     */
    public void setTextSize(float userCustomTextSize) {
        float decimalsPartTextSize = (float) (userCustomTextSize * DECIMAL_PART_MULTIPLIER);

        entirePartTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, userCustomTextSize);
        decimalsPartTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, decimalsPartTextSize >= MINIMUM_TEXT_SIZE ? decimalsPartTextSize : MINIMUM_TEXT_SIZE);
    }

    /**
     * Setter for {@link #showDecimals}.
     *
     * @param showDecimals {@code true} to display it. Otherwise {@code false}.
     */
    public void setShowDecimals(boolean showDecimals) {
        this.showDecimals = showDecimals;
        decimalsPartTextView.setVisibility(GONE);
    }

    /**
     * Setter for {@link #currency}.
     *
     * @param currency The currency that will be used to display this price.
     */
    public void setCurrency(String currency) {
        this.currency = currency != null ? currency + " " : null;
    }
}
