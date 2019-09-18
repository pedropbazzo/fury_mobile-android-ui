package com.mercadolibre.android.ui.widgets;

import android.graphics.Color;
import android.os.Build;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class MeliButtonTest {

    private static final String buttonText = "bntTEXT";

    private MeliButton button;

    @Before
    public void setUp() {
        button = new MeliButton(RuntimeEnvironment.application);
    }

    @Test
    public void testDefaultInitialization() {
        button.setText(buttonText);
        Assert.assertEquals(MeliButton.Type.ACTION_PRIMARY, button.getType());
        Assert.assertEquals(MeliButton.State.NORMAL, button.getState());
        Assert.assertEquals(buttonText, button.getText());
    }

    @Test
    public void testStatusChange() {
        button.setText(buttonText);
        Assert.assertEquals(MeliButton.Type.ACTION_PRIMARY, button.getType());
        Assert.assertEquals(MeliButton.State.NORMAL, button.getState());
        button.setState(MeliButton.State.DISABLED);
        Assert.assertFalse(button.isEnabled());
        button.setState(MeliButton.State.NORMAL);
        Assert.assertTrue(button.isEnabled());
    }

    @Test
    public void testTypeChange() {
        button.setText(buttonText);
        Assert.assertEquals(MeliButton.Type.ACTION_PRIMARY, button.getType());
        Assert.assertEquals(MeliButton.State.NORMAL, button.getState());
        final int textColor = Color.parseColor("#FFFFFF");
        final int defTextColor = Color.parseColor("#FFFFFF");
        final int[] states = {android.R.attr.state_enabled};
        Assert.assertEquals(textColor, button.getTextColors().
                getColorForState(states, defTextColor));

        button.setType(MeliButton.Type.ACTION_SECONDARY);
        Assert.assertNotSame(textColor, button.getTextColors().
                getColorForState(states, defTextColor));
    }

    @Test
    public void testTypeSecondaryAction() {
        button.setType(MeliButton.Type.ACTION_SECONDARY);
        final int textColor = Color.parseColor("#3483FA");
        final int defTextColor = Color.parseColor("#332681FF");
        final int[] states = {android.R.attr.state_enabled};
        Assert.assertEquals(textColor, button.getTextColors().
                getColorForState(states, defTextColor));
        Assert.assertEquals(MeliButton.Type.ACTION_SECONDARY, button.getType());
    }

    @Test
    public void testTypePrymaryActionByDefault() {
        Assert.assertEquals(MeliButton.Type.ACTION_PRIMARY, button.getType());
        Assert.assertEquals(MeliButton.State.NORMAL, button.getState());
        final int textColor = Color.parseColor("#FFFFFF");
        final int defTextColor = Color.parseColor("#FFFFFF");
        final int[] states = {android.R.attr.state_enabled};
        Assert.assertEquals(textColor, button.getTextColors().
                getColorForState(states, defTextColor));
    }

    @Test
    public void testTypePrimaryOption() {
        button.setType(MeliButton.Type.OPTION_PRIMARY);
        final int textColor = Color.parseColor("#3483FA");
        final int defTextColor = Color.parseColor("#332681FF");
        final int[] states = {android.R.attr.state_enabled};
        Assert.assertEquals(textColor, button.getTextColors().
                getColorForState(states, defTextColor));
        Assert.assertEquals(MeliButton.Type.OPTION_PRIMARY, button.getType());
    }

    @Test
    public void testCorrectValuesInButtonWithSizeLarge() {
        button.setSize(MeliButton.Size.LARGE);
        final float textSize = 18F;
        Assert.assertEquals(textSize, button.getTextSize(), 0);
        Assert.assertEquals(MeliButton.Size.LARGE, button.getSize());
    }

    @Test
    public void testCorrectValuesInButtonWithSizeSmall() {
        button.setSize(MeliButton.Size.SMALL);
        final float textSize = 14F;
        Assert.assertEquals(textSize, button.getTextSize(), 0);
        Assert.assertEquals(MeliButton.Size.SMALL, button.getSize());
    }
}

