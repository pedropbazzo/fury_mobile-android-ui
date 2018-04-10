package com.mercadolibre.android.ui.widgets.contextual_menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.mercadolibre.android.ui.R;
import com.mercadolibre.android.ui.font.Font;
import com.mercadolibre.android.ui.font.TypefaceHelper;
import com.mercadolibre.android.ui.utils.UIUtil;

/**
 * A menu offering different actions to perform depending the context clicked
 */
@SuppressWarnings({ "ViewConstructor", "PMD.GodClass", "checkstyle:magicnumber" })
public final class ContextualMenu extends ViewGroup {

    /**
     * Default From of the angle of the container of the ContextualMenu
     **/
    private static final float DEFAULT_FROM_DEGREES = 270.0f;

    /**
     * Default To of the angle of the container of the ContextualMenu
     **/
    private static final float DEFAULT_TO_DEGREES = 360.0f;

    /**
     * Duration of the menu showing up
     **/
    /* default */ static final int ANIMATION_DURATION = 200;

    /**
     * Default radius when icons are 2
     **/
    private static final int DEFAULT_RADIUS = 70;

    /**
     * Tooltip Fade delay
     */
    /* default */ static final int TOOLTIP_FADE_DURATION = 15;

    /**
     * Hovered option in the ContextualMenu, default 0
     */
    private int hoveredOptionIndex;

    private ContextualMenuInfo contextualMenuInfo;
    private ContextualMenuListener listener;

    /**
     * Distance between menu icons
     **/
    private int angleBetweenItems = 40;
    private float fromDegrees = DEFAULT_FROM_DEGREES;
    private float toDegrees = DEFAULT_TO_DEGREES;

    /**
     * Default child size
     */
    private int childSize;

    /**
     * Default radius of the {@link ContextualMenu}
     */
    private int radius;

    /**
     * To render correctly the {@link ContextualMenuOption}s, we check if we have to invert the order
     * when displaying it
     */
    private boolean invertOrder;

    /**
     * Checks if the {@link ContextualMenu} is opened or not
     */
    private boolean expanded;

    /**
     * The X/Y position of the ContextualMenu centered circle
     */
    /* default */ PointF center;

    /**
     * The tooltip of the {@link ContextualMenuOption}
     */
    /* default */ Paint tooltipBkgPaint;
    private Paint tooltipTextPaint;
    private final Path path = new Path();
    private final Rect bounds = new Rect();
    private float tooltipUpperPadding;
    private float tooltipLowerPadding;
    private float tooltipIconMargin;
    private float tooltipHorizontalPadding;
    /* default */ float currentTooltipAlpha;
    /* default */ final Runnable fadeInTooltipRunner = new Runnable() {
        @Override
        @SuppressWarnings("checkstyle:magicnumber")
        public void run() {
            if (currentTooltipAlpha <= 1) {
                tooltipBkgPaint.setAlpha((int) (currentTooltipAlpha * 255));
                currentTooltipAlpha += 0.1f;
                invalidate();
                postDelayed(fadeInTooltipRunner, TOOLTIP_FADE_DURATION);
            }
        }
    };

    /**
     * Default constructor
     *
     * @param context            the context
     * @param contextualMenuInfo Information that will help you implementing this feature
     * @param listener           to handle the {@link ContextualMenu} events
     */
    public ContextualMenu(final Context context, @NonNull final ContextualMenuInfo contextualMenuInfo, final ContextualMenuListener listener) {
        super(context);
        this.contextualMenuInfo = contextualMenuInfo;
        this.listener = listener;
        init();
    }

    private static Rect computeChildFrame(final float centerX, final float centerY, final int radius, final float degrees, final int size) {
        final int childCenterX = (int) (centerX + radius * Math.cos(Math.toRadians(degrees)));
        final int childCenterY = (int) (centerY + radius * Math.sin(Math.toRadians(degrees)));

        return new Rect(childCenterX - size / 2, (childCenterY - size / 2), (childCenterX + size / 2), (childCenterY + size / 2));
    }

    /**
     * Initializes the {@link ContextualMenu}
     */
    private void init() {
        final Resources resources = getContext().getResources();
        createCenterView(getContext());
        showCenteredAt(contextualMenuInfo.getTouch());
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.ui_contextual_menu_background_color));

        radius = resources.getDimensionPixelSize(R.dimen.ui_contextual_menu_radius);
        childSize = resources.getDimensionPixelSize(R.dimen.ui_contextual_menu_child_size);

        tooltipBkgPaint = new Paint();
        tooltipBkgPaint.setColor(ContextCompat.getColor(getContext(), R.color.ui_meli_black));
        tooltipBkgPaint.setAntiAlias(true);
        tooltipBkgPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        tooltipTextPaint = new Paint();
        tooltipTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.ui_meli_white));
        tooltipTextPaint.setTextSize(resources.getDimensionPixelSize(R.dimen.ui_contextual_menu_text_size));
        TypefaceHelper.setTypeface(getContext(), tooltipTextPaint, Font.REGULAR);

        tooltipUpperPadding = resources.getDimension(R.dimen.ui_contextual_menu_tooltip_upper_text_padding);
        tooltipLowerPadding = resources.getDimension(R.dimen.ui_contextual_menu_tooltip_lower_text_padding);
        tooltipIconMargin = resources.getDimension(R.dimen.ui_contextual_menu_tooltip_margin);
        tooltipHorizontalPadding = resources.getDimension(R.dimen.ui_contextual_menu_tooltip_horizontal_padding);
    }

    /**
     * Creates and adds the circle centered of the ContextualMenu
     *
     * @param context the context
     */
    private void createCenterView(final Context context) {
        final View centerView = new View(context);
        final Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ui_contextual_menu_center);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            centerView.setBackground(drawable);
        } else {
            centerView.setBackgroundDrawable(drawable);
        }
        addView(centerView);
    }

    /**
     * Make the ContextualMenu visible with the circle centered at the specified coordinates
     *
     * @param position in screen
     */
    public void showCenteredAt(final PointF position) {
        center = position;
        setStartAndEndAnglesFromPressPoint(center);
        switchState(true);
    }

    /**
     * Calculates the angle that the MenuContextual options will be displayed
     *
     * @param center the touch X/Y screen position
     */
    private void setStartAndEndAnglesFromPressPoint(final PointF center) {
        /* This is the X position of the clicked position regarding the screen.
         Where 0 represents the leftmost side of the screen, whilst 1 is the rightmost. */
        float ratioX = center.x / UIUtil.getScreenSize(getContext()).x;
        /* This is the Y position of the clicked position regarding the screen.
         Where 0 represents the top of the screen, whilst 1 the bottom. */
        final float ratioY = center.y / UIUtil.getScreenSize(getContext()).y;
        // The calculated center of the arc where the icons will be contained
        final float arcCenter;
        // The length of the arc taking into account the angle between items
        final float arcLength;

        // This is used if we have to invert vertically the angle of the options

        invertOrder = ratioX >= 0.5f;

        /* We change the angle of the ContextualMenu taking into account the click position
        * in order to display well the options. */
        if (ratioX <= 0.50f) {
            if (ratioY >= 0.4f) {
                ratioX = 0.2f;
            } else {
                ratioX = -0.5f;
            }
        } else if (ratioX > 0.50f) {
            if (ratioY >= 0.4f) {
                ratioX = -0.2f;
            } else {
                ratioX = 0.5f;
            }
        }

        arcLength = (getChildCount() - 2) * angleBetweenItems;
        arcCenter = (270 - (ratioX) * 90);
        fromDegrees = (arcCenter - arcLength / 2);
        toDegrees = (arcCenter + arcLength / 2);

        requestLayout();
    }

    /**
     * Returns the angle to display correctly the {@link ContextualMenuOption}
     */
    private float getAngleFor(final int index) {
        if (index < 1) {
            return 0;
        }

        final int i = index - 1;

        if (invertOrder) {
            return toDegrees - angleBetweenItems * i;
        } else {
            return fromDegrees + angleBetweenItems * i;
        }
    }

    /**
     * Add options to the ContextualMenu
     *
     * @param icons the icons to add
     */
    public void addIcons(final ContextualMenuOption... icons) {
        for (final ContextualMenuOption icon : icons) {
            addView(icon);
        }

        radius = dp2px(getContext(), DEFAULT_RADIUS - (icons.length - 2) * 5);
        angleBetweenItems = angleBetweenItems - (icons.length - 2 * 10);
    }

    /**
     * This method handles the touch events of the {@link ContextualMenu} options
     *
     * @param event the event
     */
    public void handleTouchEvent(final MotionEvent event) {
        final int action = event.getAction();
        if (action == MotionEvent.ACTION_MOVE) {
            handleMoveAction(event.getRawX(), event.getRawY());
        } else if (action == MotionEvent.ACTION_UP) {
            handleUpAction(event.getRawX(), event.getRawY());
        }
    }

    private void handleMoveAction(final float rawX, final float rawY) {
        final PointF touch = new PointF(rawX, rawY);
        final int prevHovered = hoveredOptionIndex;
        // Only listen ACTION_MOVE when ContextualMenu is visible
        if (getVisibility() == VISIBLE) {
            boolean isOneChildContained = false;
            for (int i = 1; i < getChildCount(); i++) {
                final boolean contains = isTouchContainedInIcon(touch.x, touch.y, i);
                final ContextualMenuOption contextualMenuOption = (ContextualMenuOption) getChildAt(i);
                // If the touch position is contained in any Menu Option range, we set it as hovered
                // and change its color and scale
                if (contains) {
                    isOneChildContained = true;
                    hoveredOptionIndex = i;

                    // We hover the icon
                    contextualMenuOption.setFillColor(ContextCompat.getColor(getContext(), R.color.ui_meli_yellow));
                    final float ratio = Math.min(1, getDistanceFromCenter(touch.x, touch.y) / radius);
                    final float scale = ratio * 0.3f + 1;
                    final double angle = Math.toRadians(getAngleFor(i));
                    contextualMenuOption.setScaleX(scale);
                    contextualMenuOption.setScaleY(scale);
                    contextualMenuOption.setHovered(true);
                    contextualMenuOption.setTranslationX((float) (ratio * Math.cos(angle) * childSize / 3));
                    contextualMenuOption.setTranslationY((float) (ratio * Math.sin(angle) * childSize / 3));

                    /* Every other option that was hovered is reset */
                    for (int j = 1; j < getChildCount(); j++) {
                        if (j != i) {
                            final ContextualMenuOption option = (ContextualMenuOption) getChildAt(j);
                            resetContextualMenuOptionView(option);
                        }
                    }
                    break;
                } else {
                    // If the touch is not contained in the icon range,
                    resetContextualMenuOptionView(contextualMenuOption);
                }
            }
            if (isOneChildContained) {
                invalidate();
            } else {
                hoveredOptionIndex = 0;
            }
            if (hoveredOptionIndex != prevHovered) {
                if (hoveredOptionIndex != 0) {
                    currentTooltipAlpha = 0;
                    tooltipBkgPaint.setAlpha((int) (currentTooltipAlpha * 255));
                    post(fadeInTooltipRunner);
                }
                invalidate();
            }
        }
    }

    private void handleUpAction(final float rawX, final float rawY) {
        if (getVisibility() == VISIBLE) {
            for (int i = 1; i < getChildCount(); i++) {
                        /* For each option of the Contextual Menu, verifies if the touch is contained
                        in the touch range of any option. */
                final boolean contains = isTouchContainedInIcon(rawX, rawY, i);
                if (contains) {
                    contextualMenuInfo.setChildAt(i - 1);
                    listener.onMenuItemClick(contextualMenuInfo);
                    getChildAt(i).setScaleX(1);
                    getChildAt(i).setScaleY(1);
                }
            }
            switchState(true);
        }
    }

    /**
     * Reset the {@link ContextualMenuOption} from hover to the normal values
     *
     * @param option to reset
     */
    @VisibleForTesting
    /* default */ void resetContextualMenuOptionView(final ContextualMenuOption option) {
        option.setFillColor(ContextCompat.getColor(getContext(), R.color.ui_meli_white));
        option.setScaleX(1);
        option.setScaleY(1);
        option.setTranslationX(0);
        option.setTranslationY(0);
        option.setHovered(false);
    }

    /**
     * Used to start selecting an option without having to be above it.
     *
     * @param x   position in the screen
     * @param y   position in the screen
     * @param idx the index of the ContextualMenu option going to be clicked
     * @return {@code true} if contained, otherwise {@code false}
     */
    /* default */ boolean isTouchContainedInIcon(final float x, final float y, final int idx) {
        final int touchAngle = (int) getAngleForCoordinates(x, y);
        final int distance = (int) getDistanceFromCenter(x, y);
        return distance > radius / 2 && distance < radius * 2 && isAngleInRangeComparedTo(touchAngle, getAngleFor(idx), angleBetweenItems / 2);
    }

    /**
     * Gets angle regarding the position X/Y of the touch
     *
     * @param x the touch X position
     * @param y the touch Y position
     */
    private float getAngleForCoordinates(final float x, final float y) {
        final float diffX = x - center.x;
        final float diffY = y - center.y;
        final double tan = Math.atan2(diffY, diffX);
        return (float) Math.toDegrees(tan < 0 ? tan + Math.PI * 2 : tan);
    }

    /**
     * Calculates the distance from the touch position, to the center view of the {@link ContextualMenu}
     */
    private float getDistanceFromCenter(final float x, final float y) {
        return (float) Math.hypot(x - center.x, y - center.y);
    }

    private boolean isAngleInRangeComparedTo(final float firstAngle, final float secondAngle, final int range) {
        final float phi = Math.abs(firstAngle - secondAngle) % 360;
        final float dist = phi > 180 ? 360 - phi : phi;
        return dist < range;
    }

    /**
     * Handles the animation of the MenuContextual options when being displayed
     */
    private void animateExpandChildren() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int childCount = getChildCount();
                for (int i = 1; i < childCount; i++) {
                    final View view = getChildAt(i);
                    view.setTranslationX((center.x - view.getX()) / 1.5f);
                    view.setTranslationY((center.y - view.getY()) / 1.5f);
                    view.setScaleX(0.3f);
                    view.setScaleY(0.3f);
                    view.animate().setDuration(ANIMATION_DURATION).translationX(0).translationY(0).scaleX(1).scaleY(1).start();
                }
            }

        });
    }

    /**
     * To know if the ContextualMenu is opened or not
     *
     * @param showAnimation show we show the animation
     */
    public void switchState(final boolean showAnimation) {
        expanded = !expanded;
        final int childCount = getChildCount();
        if (showAnimation) {
            for (int i = 1; i < childCount; i++) {
                ((ContextualMenuOption) getChildAt(i)).setFillColor(ContextCompat.getColor(getContext(), R.color.ui_meli_white));
            }
            if (expanded) {
                animateExpandChildren();
            }
        }

        if (listener != null) {
            if (expanded) {
                listener.onShowPinMenu();
            } else {
                listener.onHidePinMenu();
            }
        }
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        setMeasuredDimension(getContext().getResources().getDisplayMetrics().widthPixels,
                             getContext().getResources().getDisplayMetrics().heightPixels);

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(childSize, MeasureSpec.EXACTLY),
                                  MeasureSpec.makeMeasureSpec(childSize, MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onLayout(final boolean changed, final int l, final int t, final int r, final int b) {
        final int cX = center.x == 0 ? getWidth() / 2 : (int) center.x;
        final int cY = center.y == 0 ? getHeight() / 2 : (int) center.y - childSize / 2;

        final int childCount = getChildCount();
        float degrees = invertOrder ? toDegrees : fromDegrees;

        //add centerView
        final Rect centerRect = computeChildFrame(cX, cY, 0, angleBetweenItems, childSize);
        getChildAt(0).layout(centerRect.left, centerRect.top, centerRect.right, centerRect.bottom);

        //add other views
        for (int i = 1; i < childCount; i++) {
            final Rect frame = computeChildFrame(cX, cY, radius, degrees, childSize);
            degrees += angleBetweenItems * (invertOrder ? -1 : 1);
            final ContextualMenuOption imageView = (ContextualMenuOption) getChildAt(i);
            imageView.layout(frame.left, frame.top, frame.right, frame.bottom);
        }
    }

    @Override
    protected void dispatchDraw(final Canvas canvas) {
        // We override dispatchDraw instead of onDraw to make sure the tooltip is drawn above the views
        super.dispatchDraw(canvas);
        if (hoveredOptionIndex > 0) {
            final ContextualMenuOption view = (ContextualMenuOption) getChildAt(hoveredOptionIndex);

            tooltipTextPaint.getTextBounds(view.getTooltip(), 0, view.getTooltip().length(), bounds);
            final float textHeight = bounds.height();
            final float textWidth = bounds.width();

            //Floats to round the corners. See SliderView for more info
            final float curveLength = tooltipHorizontalPadding / 2 + tooltipUpperPadding + tooltipLowerPadding;
            final float cornerShort = curveLength / 4;
            final float cornerLong = cornerShort * 3;

            final float totalX = textWidth + tooltipHorizontalPadding * 2 + curveLength * 2;

            final float startingX = view.getX() + childSize / 2 - totalX / 2;
            final float startingY = view.getY() - curveLength * 2L - tooltipIconMargin - textHeight + (1L - currentTooltipAlpha) * tooltipIconMargin;

            //Draw the tooltip contour
            path.reset();
            path.moveTo(startingX + tooltipHorizontalPadding + curveLength, startingY);
            path.rLineTo(textWidth, 0);
            path.rLineTo(tooltipHorizontalPadding, 0);
            path.rQuadTo(cornerLong, cornerShort, curveLength, curveLength);
            path.rLineTo(0, textHeight / 2);
            path.rQuadTo(-cornerShort, cornerLong, -curveLength, curveLength);
            path.rLineTo(-tooltipHorizontalPadding, 0);
            path.rLineTo(-textWidth - tooltipHorizontalPadding, 0);
            path.rQuadTo(-cornerLong, -cornerShort, -curveLength, -curveLength);
            path.rLineTo(0, -textHeight / 2);
            path.rQuadTo(cornerShort, -cornerLong, curveLength, -curveLength);

            path.close();

            canvas.drawPath(path, tooltipBkgPaint);

            canvas.drawText(view.getTooltip(), startingX + tooltipHorizontalPadding + curveLength,
                            startingY + tooltipUpperPadding + curveLength + textHeight / 2,
                            tooltipTextPaint);
        }
    }

    /**
     * Getter method of the {@link ContextualMenuInfo}
     *
     * @return the contextual menu info
     */
    public ContextualMenuInfo getContextualMenuInfo() {
        return contextualMenuInfo;
    }

    /**
     * Setter method of the {@link ContextualMenuInfo}
     *
     * @param clickContext the clicked intem info
     */
    public void setContextualMenuInfo(final ContextualMenuInfo clickContext) {
        this.contextualMenuInfo = clickContext;
    }

    /**
     * Getter method of the {@link ContextualMenuListener}
     *
     * @return the contextual menu listener
     */
    public ContextualMenuListener getContextualMenuClickListener() {
        return listener;
    }

    /**
     * Sets the ContextualMenu listener
     *
     * @param contextualMenuListener callback
     */
    public void setContextualMenuClickListener(final ContextualMenuListener contextualMenuListener) {
        this.listener = contextualMenuListener;
    }

    /**
     * Returns the center X/Y position of the {@link ContextualMenu}
     *
     * @return the center
     */
    public PointF getCenter() {
        return center;
    }

    private int dp2px(Context context, int dp) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        display.getMetrics(displaymetrics);

        return (int) (dp * displaymetrics.density + 0.5f);
    }

    @Override
    public String toString() {
        return "ContextualMenu{"
                + "hoveredOptionIndex=" + hoveredOptionIndex
                + ", contextualMenuInfo=" + contextualMenuInfo
                + ", listener=" + listener
                + ", angleBetweenItems=" + angleBetweenItems
                + ", fromDegrees=" + fromDegrees
                + ", toDegrees=" + toDegrees
                + ", childSize=" + childSize
                + ", radius=" + radius
                + ", invertOrder=" + invertOrder
                + ", expanded=" + expanded
                + ", center=" + center
                + ", tooltipBkgPaint=" + tooltipBkgPaint
                + ", tooltipTextPaint=" + tooltipTextPaint
                + ", path=" + path
                + ", bounds=" + bounds
                + ", tooltipUpperPadding=" + tooltipUpperPadding
                + ", tooltipLowerPadding=" + tooltipLowerPadding
                + ", tooltipIconMargin=" + tooltipIconMargin
                + ", tooltipHorizontalPadding=" + tooltipHorizontalPadding
                + ", currentTooltipAlpha=" + currentTooltipAlpha
                + ", fadeInTooltipRunner=" + fadeInTooltipRunner
                + '}';
    }
}