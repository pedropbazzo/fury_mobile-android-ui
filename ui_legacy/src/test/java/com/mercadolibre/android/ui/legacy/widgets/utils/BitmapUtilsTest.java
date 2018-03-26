package com.mercadolibre.android.ui.legacy.widgets.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.Display;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;

import com.mercadolibre.android.testing.AbstractRobolectricTest;
import com.mercadolibre.android.ui.legacy.R;
import com.mercadolibre.android.ui.legacy.utils.BitmapUtils;

import org.junit.Test;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@Config(sdk = 21)
public class BitmapUtilsTest extends AbstractRobolectricTest {

    @Test
    public void testGetTintedResource(){
        final Drawable d = BitmapUtils.getTintedResource(RuntimeEnvironment.application, R.drawable.ic_info_small, R.color.icons_blue);
        final PorterDuffColorFilter colorFilter =
            new PorterDuffColorFilter((RuntimeEnvironment.application).getResources().getColor(R.color.icons_blue), PorterDuff.Mode.SRC_IN);
        assertEquals(d.getColorFilter(), colorFilter);
    }

    @Test
    public void testGetTintedDrawable(){
        final Drawable d = BitmapUtils.getTintedDrawable(RuntimeEnvironment.application,
            (RuntimeEnvironment.application).getResources().getDrawable(R.drawable.ic_info_small), R.color.icons_blue);

        final PorterDuffColorFilter colorFilter =
            new PorterDuffColorFilter((RuntimeEnvironment.application).getResources().getColor(R.color.icons_blue), PorterDuff.Mode.SRC_IN);

        assertEquals(d.getColorFilter(), colorFilter);
    }

    @Test
    public void testMenuIconAsGrey(){
        final MenuItem menuItem = new TestMenuItem();
        BitmapUtils.tintMenuIconAsGrey(RuntimeEnvironment.application, menuItem);

        final PorterDuffColorFilter colorFilter =
            new PorterDuffColorFilter((RuntimeEnvironment.application).getResources().getColor(R.color.icons_grey), PorterDuff.Mode.SRC_IN);

        assertEquals(menuItem.getIcon().getColorFilter(), colorFilter);
    }

    @Test
    public void testMenuIcon(){
        final MenuItem menuItem = new TestMenuItem();
        BitmapUtils.tintMenuIcon(RuntimeEnvironment.application, menuItem, R.color.red_dark);

        final PorterDuffColorFilter colorFilter =
            new PorterDuffColorFilter((RuntimeEnvironment.application).getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_IN);

        assertEquals(menuItem.getIcon().getColorFilter(), colorFilter);
    }

    @Test
    public void testDisplaySize(){
        final WindowManager wm = (WindowManager) (RuntimeEnvironment.application).getSystemService(Context.WINDOW_SERVICE);
        final Display display = wm.getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);

        final Point retrievedSize = BitmapUtils.getDisplaySize(RuntimeEnvironment.application);

        assertEquals(size.x, retrievedSize.x);
        assertEquals(size.y, retrievedSize.y);

    }

    private class TestMenuItem implements MenuItem {
        private Drawable d = (RuntimeEnvironment.application).getResources().getDrawable(R.drawable.ic_info_big);

        @Override
        public int getItemId() {
            return 0;
        }

        @Override
        public int getGroupId() {
            return 0;
        }

        @Override
        public int getOrder() {
            return 0;
        }

        @Override
        public MenuItem setTitle(final CharSequence title) {
            return null;
        }

        @Override
        public MenuItem setTitle(final int title) {
            return null;
        }

        @Override
        public CharSequence getTitle() {
            return null;
        }

        @Override
        public MenuItem setTitleCondensed(final CharSequence title) {
            return null;
        }

        @Override
        public CharSequence getTitleCondensed() {
            return null;
        }

        @Override
        public MenuItem setIcon(final Drawable icon) {
            this.d = icon;
            return this;
        }

        @Override
        public MenuItem setIcon(final int iconRes) {
            this.d = (RuntimeEnvironment.application).getResources().getDrawable(iconRes);
            return this;
        }

        @Override
        public Drawable getIcon() {
            return d;
        }

        @Override
        public MenuItem setIntent(final Intent intent) {
            return null;
        }

        @Override
        public Intent getIntent() {
            return null;
        }

        @Override
        public MenuItem setShortcut(final char numericChar, final char alphaChar) {
            return null;
        }

        @Override
        public MenuItem setNumericShortcut(final char numericChar) {
            return null;
        }

        @Override
        public char getNumericShortcut() {
            return 0;
        }

        @Override
        public MenuItem setAlphabeticShortcut(final char alphaChar) {
            return null;
        }

        @Override
        public char getAlphabeticShortcut() {
            return 0;
        }

        @Override
        public MenuItem setCheckable(final boolean checkable) {
            return null;
        }

        @Override
        public boolean isCheckable() {
            return false;
        }

        @Override
        public MenuItem setChecked(final boolean checked) {
            return null;
        }

        @Override
        public boolean isChecked() {
            return false;
        }

        @Override
        public MenuItem setVisible(final boolean visible) {
            return null;
        }

        @Override
        public boolean isVisible() {
            return false;
        }

        @Override
        public MenuItem setEnabled(final boolean enabled) {
            return null;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }

        @Override
        public boolean hasSubMenu() {
            return false;
        }

        @Override
        public SubMenu getSubMenu() {
            return null;
        }

        @Override
        public MenuItem setOnMenuItemClickListener(final OnMenuItemClickListener menuItemClickListener) {
            return null;
        }

        @Override
        public ContextMenu.ContextMenuInfo getMenuInfo() {
            return null;
        }

        @Override
        public void setShowAsAction(final int actionEnum) {

        }

        @Override
        public MenuItem setShowAsActionFlags(final int actionEnum) {
            return null;
        }

        @Override
        public MenuItem setActionView(final View view) {
            return null;
        }

        @Override
        public MenuItem setActionView(final int resId) {
            return null;
        }

        @Override
        public View getActionView() {
            return null;
        }

        @Override
        public MenuItem setActionProvider(final ActionProvider actionProvider) {
            return null;
        }

        @Override
        public ActionProvider getActionProvider() {
            return null;
        }

        @Override
        public boolean expandActionView() {
            return false;
        }

        @Override
        public boolean collapseActionView() {
            return false;
        }

        @Override
        public boolean isActionViewExpanded() {
            return false;
        }

        @Override
        public MenuItem setOnActionExpandListener(final OnActionExpandListener listener) {
            return null;
        }
    }

}
