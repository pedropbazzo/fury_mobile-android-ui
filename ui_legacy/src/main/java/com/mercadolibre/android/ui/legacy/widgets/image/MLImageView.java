package com.mercadolibre.android.ui.legacy.widgets.image;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

import android.util.DisplayMetrics;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.mercadolibre.android.ui.legacy.R;
import com.mercadolibre.android.ui.legacy.utils.BitmapUtils;

import android.util.Pair;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;

/**
 * ImageView with methods to load images from network using Fresco library.
 */
@Deprecated
class MLImageView extends SimpleDraweeView {

    //image url if any.
    private String url;
    private boolean forceDimension = false;
    private ColorStateList tint;

    public MLImageView(Context context) {
        this(context, null);
    }

    public MLImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MLImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, com.mercadolibre.android.ui.legacy.R.styleable.MLImage);
        forceDimension = a.getBoolean(com.mercadolibre.android.ui.legacy.R.styleable.MLImage_forceDimension, false);
        tint = a.getColorStateList(R.styleable.MLImage_colorFilter);
        updateTintColor();
        a.recycle();

        // If the attributes contained scaling type in the xml, dispatch it to Fresco
        final ScaleType type = getScaleType();
        if (type != null) {
            setImageScaleType(type);
        }
    }

    public final void setImageScaleType(ScaleType scaleType) {
        ScalingUtils.ScaleType type;
        switch (scaleType) {
            case CENTER:
                type = ScalingUtils.ScaleType.CENTER;
                break;
            case CENTER_CROP:
                type = ScalingUtils.ScaleType.CENTER_CROP;
                break;
            case CENTER_INSIDE:
                type = ScalingUtils.ScaleType.CENTER_INSIDE;
                break;
            case FIT_CENTER:
                type = ScalingUtils.ScaleType.FIT_CENTER;
                break;
            case FIT_END:
                type = ScalingUtils.ScaleType.FIT_END;
                break;
            case FIT_START:
                type = ScalingUtils.ScaleType.FIT_START;
                break;
            case FIT_XY:
                type = ScalingUtils.ScaleType.FIT_XY;
                break;
            default:
                type = null;
        }

        if (type == null) {
            setScaleType(scaleType);
        } else {
            try  {
                //Cant use a if (hierarchy != null) because getHierarchy throws NPE if null.
                getHierarchy().setActualImageScaleType(type);
            } catch (NullPointerException e) {
                final GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                    .setActualImageScaleType(type).build();
                setHierarchy(hierarchy);
            }
        }
    }

    public void loadImage(String url, Context context) {
        if (context == null)
            return;

        FrescoImageController.Builder controller = FrescoImageController.create(context);

        if (!TextUtils.isEmpty(url)) {
            if ("http".equals(Uri.parse(url).getScheme())) {
                controller.load(url);
            } else {
                setImageScaleType(ScaleType.CENTER_INSIDE);

                controller.load(new File(url));
            }
        } else {
            controller.load(com.mercadolibre.android.ui.legacy.R.drawable.no_pic_p);
        }

        Pair<Integer, Integer> resizeBounds = getResizeBounds();
        controller.resize(resizeBounds.first, resizeBounds.second)
            .listener(getFrescoCallback())
            .into(this);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (tint != null)
            updateTintColor();
    }

    public void setTintColorStateResource(Integer colorStateListResId) {
        XmlResourceParser parser = getResources().getXml(colorStateListResId);
        ColorStateList colors = null;
        try {
            colors = ColorStateList.createFromXml(getResources(), parser);
            setTint(colors);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTint(ColorStateList tint) {
        this.tint = tint;
        updateTintColor();
    }

    public void setTintColor(int colorInt) {
        this.tint = ColorStateList.valueOf(colorInt);
        updateTintColor();
    }

    private void updateTintColor() {
        if (tint != null) {
            int color = tint.getColorForState(getDrawableState(), 0);
            setColorFilter(color, Mode.SRC_IN);
        }
    }

    protected Pair<Integer, Integer> getResizeBounds() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int min = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
        return new Pair<>(min, min);
    }

    public String getUrl() {
        return url;
    }

    protected FrescoImageController.Callback getFrescoCallback() {
        return new FrescoImageController.Callback() {
            @Override
            public void onSuccess(@Nullable final ImageInfo imageInfo) {

            }

            @Override
            public void onFailure(@NonNull final Throwable t) {

            }
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (forceDimension) {
            Point windowsSize = BitmapUtils.getDisplaySize(getContext());
            int width = windowsSize.x;
            int height = windowsSize.y;
            if (height > width) {
                height = (int) (width / (4f / 3f));
            } else {
                height = (int) (height * 0.5f);
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}