package com.mercadolibre.android.ui.utils;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.os.OperationCanceledException;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.mercadolibre.android.testing.AbstractRobolectricTest;
import com.mercadolibre.android.ui.utils.facebook.fresco.FrescoImageController;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import java.io.File;

public class FrescoImageControllerTest extends AbstractRobolectricTest {

    @Before
    public void setUp() {
        Fresco.initialize(RuntimeEnvironment.application);
    }

    @Test
    public void testImageWithoutUriThrowsException() {
        try {
            FrescoImageController
                    .create()
                    .into(new DraweeView(RuntimeEnvironment.application));

            Assert.assertTrue(false);
        } catch (IllegalStateException e) {
            Assert.assertEquals("Creating controller for drawee with no address to retrieve image from. Forgot to call setUri/setUrl ??",
                                e.getMessage());
        }
    }

    @Test
    public void testImageResize() {
        final FrescoImageController controller = FrescoImageController
                .create()
                .load("")
                .resize(50, 50)
                .into(new DraweeView(RuntimeEnvironment.application));

        Assert.assertEquals(50, controller.getResizeOptions().width);
        Assert.assertEquals(50, controller.getResizeOptions().height);
    }

    @Test
    public void testDifferentUris() {
        final String withFile = FrescoImageController
                .create()
                .load(new File("uri"))
                .into(new SimpleDraweeView(RuntimeEnvironment.application))
                .getUri().toString();

        final String withString = FrescoImageController
                .create()
                .load("uri")
                .into(new SimpleDraweeView(RuntimeEnvironment.application))
                .getUri().toString();

        final String withUri = FrescoImageController
                .create()
                .load(Uri.parse("uri"))
                .into(new SimpleDraweeView(RuntimeEnvironment.application))
                .getUri().toString();

        Assert.assertEquals(withUri, withString);

        Assert.assertTrue(withFile.contains("file://"));
        Assert.assertTrue(withFile.contains("ui"));
    }

    @Test
    public void testNoCache() {
        final FrescoImageController controller = FrescoImageController
                .create()
                .load("")
                .noCache()
                .into(new DraweeView(RuntimeEnvironment.application));

        Assert.assertFalse(controller.isCacheEnabled());
    }

    @Test
    public void testImageFlags() {
        final FrescoImageController controller = FrescoImageController
                .create()
                .load("")
                .tapToRetry(true)
                .rotationOptions(RotationOptions.autoRotate())
                .localThumbnailPreview(true)
                .progressiveRendering(true)
                .into(new DraweeView(RuntimeEnvironment.application));

        Assert.assertTrue(controller.isTapToRetryEnabled());
        Assert.assertTrue(controller.isProgressiveRenderingEnabled());
        Assert.assertTrue(controller.isLocalThumbnailPreviewEnabled());
        Assert.assertTrue(controller.getRotationOptions().rotationEnabled());
    }

    @Test
    public void testImageCallbacks() throws Exception {
        final FrescoImageController.Callback callback = new FrescoImageController.Callback() {
            @Override
            public void onSuccess(final ImageInfo info) {
                //Shouldnt enter here
                Assert.assertTrue(false);
            }

            @Override
            public void onFailure(@NonNull final Throwable t) {
                //Should enter here
                Assert.assertTrue(true);
            }
        };

        final FrescoImageController controller = FrescoImageController
                .create()
                .load("www")
                .listener(callback)
                .into(new DraweeView(RuntimeEnvironment.application));

        controller.failure();
    }

    @Test
    public void testImageUri() {
        final FrescoImageController controller = FrescoImageController
                .create()
                .load("some.uri")
                .into(new DraweeView(RuntimeEnvironment.application));

        Assert.assertEquals("some.uri", controller.getUri().toString());
    }

    @Test
    public void testBuilderFromAlreadyCreatedControllerBuildsTheSame() {
        final FrescoImageController controller = FrescoImageController
                .create()
                .tapToRetry(true)
                .rotationOptions(RotationOptions.autoRotate())
                .resize(200, 200)
                .load("some.uri")
                .into(new DraweeView(RuntimeEnvironment.application))
                //Create new builder from the controller created
                .newBuilder().into(new DraweeView(RuntimeEnvironment.application));

        Assert.assertTrue(controller.isTapToRetryEnabled());
        Assert.assertTrue(controller.getRotationOptions().rotationEnabled());
        Assert.assertEquals(200, controller.getResizeOptions().width);
        Assert.assertEquals(200, controller.getResizeOptions().height);
        Assert.assertEquals("some.uri", controller.getUri().toString());
    }

    @Test
    public void testFailureCallbackExplicitlyThrowsACancelOperationException() {
        FrescoImageController.Callback callback = new FrescoImageController.Callback() {
            @Override
            public void onSuccess(ImageInfo info) {
                //Shouldn't enter here
                Assert.assertTrue(false);
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                //Should enter here
                Assert.assertTrue(t instanceof OperationCanceledException);
            }
        };

        final FrescoImageController controller = FrescoImageController
                .create()
                .load("some.uri")
                .listener(callback)
                .into(new DraweeView(RuntimeEnvironment.application));

        controller.failure();
    }

}
