package com.mercadolibre.android.ui.utils.facebook.fresco;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.os.OperationCanceledException;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Immutable utility class to load an image with a desired callback.
 *
 * Simple usage example;
 FrescoImageController.create()
 .load(uriToLoadImageFrom); //Supports uri/url/resId/file
 .listener(new FrescoImageController.Callback() {
 //Override the onSuccess and the onFailure and do what you want
 })
 //There are a lot more stuff, check it out
 .into(theView);
 *
 */
@SuppressWarnings({"PMD.GodClass", "PMD.UnusedFormalParameter"})
public class FrescoImageController {

    private final @NonNull WeakReference<? extends DraweeView> view;

    private final @NonNull FrescoControllerListener frescoCallback;

    private final @NonNull Uri uri;
    private final @Nullable Priority priority;

    private final @Nullable ResizeOptions resizeOptions;
    private final @Nullable RotationOptions rotationOptions;
    private final @Nullable ImageDecodeOptions decodeOptions;
    private final @Nullable Postprocessor postprocessor;

    private final boolean tapToRetry;
    private final boolean progressiveRendering;
    private final boolean localThumbnailPreview;
    private final boolean autoPlayAnimations;

    private final boolean noCache;
    private final boolean noDiskCache;
    private final boolean noMemoryCache;
    private final @Nullable ImageRequest.CacheChoice cacheChoice;

    /**
     * Package visibility constructor. Since its an immutable object, use builders.
     */
    FrescoImageController(@NonNull final Uri uri, @NonNull DraweeView view,
        @Nullable Callback deprecatedCallback, @Nullable FrescoImageListener callback,
        @Nullable ResizeOptions resizeOpt, @Nullable ImageDecodeOptions decodeOpt,
        @Nullable Postprocessor postprocessor,
        boolean ttr, boolean pr, boolean ltp,
        boolean noCache, boolean noDiskCache, boolean noMemoryCache,
        boolean autoPlayAnimations,
        @Nullable ImageRequest.CacheChoice cacheChoice, @Nullable Priority priority,
        @Nullable RotationOptions rotationOptions) {
        this.view = new WeakReference<>(view);
        this.uri = uri;
        this.priority = priority;

        this.resizeOptions = resizeOpt;
        this.rotationOptions = rotationOptions;
        this.decodeOptions = decodeOpt;
        this.postprocessor = postprocessor;

        this.tapToRetry = ttr;
        this.progressiveRendering = pr;
        this.localThumbnailPreview = ltp;
        this.autoPlayAnimations = autoPlayAnimations;

        this.noCache = noCache;
        this.noDiskCache = noDiskCache;
        this.noMemoryCache = noMemoryCache;
        this.cacheChoice = cacheChoice;

        if (callback == null) {
            frescoCallback = new FrescoControllerListener(deprecatedCallback);
        } else {
            frescoCallback = new FrescoControllerListener(callback);
        }

        ImageRequestBuilder request = ImageRequestBuilder.newBuilderWithSource(uri)
            .setLocalThumbnailPreviewsEnabled(localThumbnailPreview)
            .setProgressiveRenderingEnabled(progressiveRendering);

        if (noCache || noDiskCache) {
            request.disableDiskCache();
        }

        if (cacheChoice != null) {
            request.setCacheChoice(cacheChoice);
        }

        if (priority != null) {
            request.setRequestPriority(priority);
        }

        if (postprocessor != null) {
            request.setPostprocessor(postprocessor);
        }

        if (rotationOptions != null) {
            request.setRotationOptions(rotationOptions);
        }

        if (decodeOptions != null) {
            request.setImageDecodeOptions(decodeOptions);
        }

        if (resizeOptions != null) {
            request.setResizeOptions(resizeOptions);
        }

        DraweeController controller = Fresco.newDraweeControllerBuilder()
            .setUri(uri)
            .setImageRequest(request.build())
            .setTapToRetryEnabled(tapToRetry)
            .setOldController(view.getController())
            .setControllerListener(frescoCallback)
            .setAutoPlayAnimations(autoPlayAnimations)
            .build();

        view.setController(controller);
    }

    /**
     * Static method to create an empty builder. The same can be achieved by doing
     * new FrescoImageController.Builder();
     *
     * @return empty builder
     */
    public static @NonNull Builder create() {
        return new Builder();
    }

    /**
     * This method is no longer mantained, please use {@link #create()}
     *
     * @param context to use
     * @return a Builder
     *
     * @deprecated use {@link #create()}
     */
    @Deprecated
    public static @NonNull Builder create(@NonNull Context context) {
        return create();
    }

    /**
     * @return attached view or null if its already gced by the os
     */
    public @Nullable DraweeView getView() {
        return view.get();
    }

    /**
     * @return resize options for the image, if existent
     */
    @Nullable
    public ResizeOptions getResizeOptions() {
        return resizeOptions;
    }

    /**
     * @return post processor used for the image, if existent
     */
    @Nullable
    public Postprocessor getPostprocessor() {
        return postprocessor;
    }

    /**
     * @return priority of the image for rendering/downloading
     */
    @Nullable
    public Priority getPriority() {
        return priority;
    }

    /**
     * @return cache choice used
     */
    @Nullable
    public ImageRequest.CacheChoice getCacheChoice() {
        return cacheChoice;
    }

    /**
     * @return rotation options if existent
     */
    @Nullable
    public RotationOptions getRotationOptions() {
        return rotationOptions;
    }

    /**
     * @return decode options if existent
     */
    @Nullable
    public ImageDecodeOptions getDecodeOptions() {
        return decodeOptions;
    }

    /**
     * @deprecated Please use {@link #getRotationOptions()} and validate the
     * desired rotation you like
     * @return if will rotate the image automatically
     */
    @Deprecated
    public boolean isAutoRotateEnabled() {
        return getRotationOptions() != null && getRotationOptions().rotationEnabled();
    }

    /**
     * @return uri used for rendering
     */
    @NonNull
    public Uri getUri() {
        return uri;
    }

    /**
     * @return if cache is enabled
     */
    public boolean isCacheEnabled() {
        return !noCache;
    }

    /**
     * If the uri provided is animatable (gif eg) it will be played immediatly
     *
     * @return if animations autoplay
     */
    public boolean isAutoPlayAnimations() {
        return autoPlayAnimations;
    }

    /**
     * @return if memory cache is enabled
     */
    public boolean isMemoryCacheEnabled() {
        return !noMemoryCache;
    }

    /**
     * @return if disk cache is enabled
     */
    public boolean isDiskCacheEnabled() {
        return !noDiskCache;
    }

    /**
     * @return if local thumbnail preview is enabled
     */
    public boolean isLocalThumbnailPreviewEnabled() {
        return localThumbnailPreview;
    }

    /**
     * @return if profressive rendering is enabled
     */
    public boolean isProgressiveRenderingEnabled() {
        return progressiveRendering;
    }

    /**
     * @return if tap to retry is enabled
     */
    public boolean isTapToRetryEnabled() {
        return tapToRetry;
    }

    /**
     * Perform an explicit success callback
     */
    public void success() {
        frescoCallback.success(null, null);
    }

    /**
     * Perform an explicit failure callback
     */
    public void failure() {
        frescoCallback.failure(new OperationCanceledException("Called failure explicitly from " + getClass().getSimpleName()));
    }

    /**
     * Create builder from state.
     *
     * Note this wont set the current view.
     *
     * @return new builder with current state
     */
    public @NonNull Builder newBuilder() {
        Builder builder = new Builder()
            .load(getUri())
            .tapToRetry(isTapToRetryEnabled())
            .autoPlayAnimations(isAutoPlayAnimations())
            .progressiveRendering(isProgressiveRenderingEnabled())
            .localThumbnailPreview(isLocalThumbnailPreviewEnabled());

        if (!isCacheEnabled()) {
            builder.noCache();
        }

        if (!isDiskCacheEnabled()) {
            builder.noDiskCache();
        }

        if (!isMemoryCacheEnabled()) {
            builder.noMemoryCache();
        }

        if (getRotationOptions() != null) {
            builder.rotationOptions(getRotationOptions());
        }

        if (getCacheChoice() != null) {
            builder.cacheChoice(getCacheChoice());
        }

        if (getPriority() != null) {
            builder.priority(getPriority());
        }

        if (getDecodeOptions() != null) {
            builder.decodeOptions(getDecodeOptions());
        }

        if (getResizeOptions() != null) {
            builder.resize(getResizeOptions().width, getResizeOptions().height);
        }

        if (getPostprocessor() != null) {
            builder.postprocessor(getPostprocessor());
        }

        if (frescoCallback.getFrescoListener() == null) {
            if (frescoCallback.getCallback() != null) {
                builder.listener(frescoCallback.getCallback());
            }
        } else {
            builder.listener(frescoCallback.getFrescoListener());
        }

        return builder;
    }

    @Override
    public String toString() {
        return "FrescoImageController{" +
            "view=" + view +
            ", frescoCallback=" + frescoCallback +
            ", uri=" + uri +
            ", priority=" + priority +
            ", resizeOptions=" + resizeOptions +
            ", rotationOptions=" + rotationOptions +
            ", decodeOptions=" + decodeOptions +
            ", postprocessor=" + postprocessor +
            ", tapToRetry=" + tapToRetry +
            ", progressiveRendering=" + progressiveRendering +
            ", localThumbnailPreview=" + localThumbnailPreview +
            ", autoPlayAnimations=" + autoPlayAnimations +
            ", noCache=" + noCache +
            ", noDiskCache=" + noDiskCache +
            ", noMemoryCache=" + noMemoryCache +
            ", cacheChoice=" + cacheChoice +
            '}';
    }

    /**
     * Builder class to create an immutable FrescoController
     */
    public static class Builder {

        private @Nullable Uri mUri;
        private @Nullable Callback deprecatedListener;
        private @Nullable FrescoImageListener listener;
        private @Nullable ResizeOptions resizeOptions;
        private @Nullable RotationOptions rotationOptions = RotationOptions.autoRotate();
        private @Nullable ImageRequest.CacheChoice cacheChoice;
        private @Nullable Priority priority;
        private boolean tapToRetry;
        private boolean progressiveRendering;
        private boolean localThumbnailPreview;
        private boolean noCache;
        private boolean noDiskCache;
        private boolean noMemoryCache;
        private boolean autoPlayAnimations;
        private @Nullable ImageDecodeOptions decodeOptions;
        private @Nullable Postprocessor postprocessor;

        /**
         * @deprecated Please use the no args constructor
         * @param context unused param
         */
        @Deprecated
        public Builder(@NonNull Context context) {
            // Deprecated
        }

        /**
         * Constructor
         */
        public Builder() {
            // Default constructor
        }

        /**
         * Set a resId from where the image will be loaded
         * @param resId with the id of the drawable to load
         * @return Builder
         */
        public @NonNull Builder load(int resId) {
            this.mUri = ImageRequestBuilder.newBuilderWithResourceId(resId).build().getSourceUri();
            return this;
        }

        /**
         * Set Uri from where the image will be loaded
         * @param uri with the address to download the image from
         * @return Builder
         */
        public @NonNull Builder load(@NonNull Uri uri) {
            this.mUri = uri;
            return this;
        }

        /**
         * Set Url from where the image will be loaded
         * @param url with the address to download the image from
         * @return Builder
         */
        public @NonNull Builder load(@NonNull String url) {
            this.mUri = Uri.parse(url);
            return this;
        }

        /**
         * Set file from where the image will be loaded
         * @param file with the image
         * @return Builder
         */
        public @NonNull Builder load(@NonNull File file) {
            this.mUri = Uri.fromFile(file);
            return this;
        }

        /**
         * This method is no longer mantained.
         *
         * In favor of Fresco 1.+, consider using the FrescoImageListener args
         *
         * @param listener from where callbacks will be observed
         * @return Builder
         *
         * @deprecated use the FrescoImageListener args
         */
        @Deprecated
        public @NonNull Builder listener(@NonNull Callback listener) {
            this.deprecatedListener = listener;
            return this;
        }

        /**
         * Set if you want to receive callbacks from the loading.
         *
         * @param listener from where callbacks will be observed
         * @return Builder
         */
        public @NonNull Builder listener(@NonNull FrescoImageListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * Resize the image before showing it.
         * Of course resize != scale.
         * For scaling just use the layout_width / layout_height of the view. If you need a particular mode
         * you can use the hierarchy.
         *
         * Note: Currently fresco doesnt support png resizing. So take into account that if you will
         * implement this in a place with possible .png images (like local user files) you wont have
         * all images resized. This can lead to problems, eg if you have a jpg and a png image of
         * 16:9 and you resize to 1:1, you will only resize the squared one.
         * A possible solution for this, but please use it carefully is to use a postprocessor that
         * resizes the bitmap if its Uri is a .png, but be careful it can make the cache useless
         *
         * @param width dest to resize
         * @param height dest to resize
         * @return Builder
         */
        public @NonNull Builder resize(int width, int height) {
            this.resizeOptions = new ResizeOptions(width, height);
            return this;
        }

        /**
         * Dont cache the image.
         * By default all images are cached
         *
         * @return Builder
         */
        public @NonNull Builder noCache() {
            this.noCache = true;
            return this;
        }

        /**
         * Dont cache the image in disk.
         * By default all images are cached
         *
         * @return Builder
         */
        public @NonNull Builder noDiskCache() {
            this.noDiskCache = true;
            return this;
        }

        /**
         * Dont cache the image in memory
         * By default all images are cached
         *
         * @return Builder
         */
        public @NonNull Builder noMemoryCache() {
            this.noMemoryCache = true;
            return this;
        }

        /**
         * Set the choice of cache. By default Fresco can store images in a
         * lowres cache and a default. If you know your image is tiny enough, you can
         * set the tiny cache to make fresco more performant.
         *
         * @param cacheChoice cache choice
         * @return Builder
         */
        public @NonNull Builder cacheChoice(@NonNull ImageRequest.CacheChoice cacheChoice) {
            this.cacheChoice = cacheChoice;
            return this;
        }

        /**
         * Priority for the rendering of the image (for higher priorities, it will be picked faster from the
         * queue for rendering)
         *
         * @param priority priority
         * @return Builder
         */
        public @NonNull Builder priority(@NonNull Priority priority) {
            this.priority = priority;
            return this;
        }

        /**
         * Tap on the image to retry loading it
         * Default: False
         *
         * @param should enable tap to retry
         * @return Builder
         */
        public @NonNull Builder tapToRetry(boolean should) {
            this.tapToRetry = should;
            return this;
        }

        /**
         * Start automatically the animations.
         *
         * This is the same as attaching a listener, checking nullability of animatable and doing
         * animatable.start();
         *
         * @param autoPlayAnimations auto play the animations
         * @return Builder
         */
        public @NonNull Builder autoPlayAnimations(boolean autoPlayAnimations) {
            this.autoPlayAnimations = autoPlayAnimations;
            return this;
        }

        /**
         * Load the image while its rendering, this is useful if you want to show previews while its
         * rendering
         * Default: false
         *
         * @param should be enabled
         * @return Builder
         */
        public @NonNull Builder progressiveRendering(boolean should) {
            this.progressiveRendering = should;
            return this;
        }

        /**
         * Show local thumbnail if present in the exif data
         * Default: false
         *
         * Fresco limitation:
         * This option is supported only for local URIs, and only for images in the JPEG format.
         *
         * @param should show it
         * @return builder
         */
        public @NonNull Builder localThumbnailPreview(boolean should) {
            this.localThumbnailPreview = should;
            return this;
        }

        /**
         * Use a custom decode options. Create it with ImageDecodeOptionsBuilder.
         * Beware since this handles internal state information. Use at your own risk if needed.
         *
         * @param options for image decoding
         * @return Builder
         */
        public @NonNull Builder decodeOptions(@NonNull ImageDecodeOptions options) {
            this.decodeOptions = options;
            return this;
        }

        /**
         * Helper that autorotates the image depending on the exif value
         * Default: True
         *
         * Note: Autorotate happens inside the DraweeView. Meaning that the bitmap wont be rotated
         * and only when rendering it will be for showing. So if using the pipeline or getting the bitmap
         * by your own means, be careful it will probably wont be rotated. Use an ExifInterface for getting
         * the rotation.
         *
         * @param should auto rotate
         * @return Builder
         * @deprecated By default it already uses autorotation. Please consider using
         * {@link #rotationOptions(RotationOptions)} if you want to set a customized rotation mode
         */
        @Deprecated
        public Builder autoRotate(final boolean should) {
              return rotationOptions(RotationOptions.autoRotate());
        }

        /**
         * Describes how the image should be rotated, this happens pre decoding!
         *
         * Works for JPEG only.
         *
         * @param rotationOptions rotations options
         * @return Builder
         */
        public @NonNull Builder rotationOptions(@NonNull RotationOptions rotationOptions) {
            this.rotationOptions = rotationOptions;
            return this;
        }

        /**
         * Since there are more than one postprocessor and processing methods (see
         * BasePostprocessor and BaseRepeatedPostprocessor) and there are three different
         * processing methods, you should feed the builder with the postprocessor instance already created
         * (instead of us defining a particular method and class for you to process the data)
         *
         * Note: DO NOT override more than one of the bitmap processing methods, this WILL lead to
         * undesired behaviours and is prone to errors
         *
         * Note: Fresco may (in a future, but currently it doesnt) support postprocessing
         * on animations.
         *
         * @param postprocessor instance for images
         * @return Builder
         */
        public @NonNull Builder postprocessor(@NonNull Postprocessor postprocessor) {
            this.postprocessor = postprocessor;
            return this;
        }

        /**
         * Attach to a view.
         *
         * Note this will handle the loading of the uri. There MUST be (Mandatory) an existent Uri
         * from where to load the image.
         *
         * You can save the returned instance to retreive the data you have used or to explicitly
         * call the callbacks
         *
         * @param view to attach the desired args
         * @return Controller
         */
        public @NonNull FrescoImageController into(@NonNull DraweeView view) {
            if (mUri == null) {
                throw new IllegalStateException(
                    "Creating controller for drawee with no address to retrieve image from. Forgot to call setUri/setUrl ??");
            }

            return new FrescoImageController(mUri, view,
                deprecatedListener, listener,
                resizeOptions, decodeOptions,
                postprocessor,
                tapToRetry, progressiveRendering, localThumbnailPreview,
                noCache, noDiskCache, noMemoryCache,
                autoPlayAnimations,
                cacheChoice, priority,
                rotationOptions);
        }

        @Override
        public String toString() {
            return "Builder{" +
                "mUri=" + mUri +
                ", deprecatedListener=" + deprecatedListener +
                ", listener=" + listener +
                ", resizeOptions=" + resizeOptions +
                ", rotationOptions=" + rotationOptions +
                ", cacheChoice=" + cacheChoice +
                ", priority=" + priority +
                ", tapToRetry=" + tapToRetry +
                ", progressiveRendering=" + progressiveRendering +
                ", localThumbnailPreview=" + localThumbnailPreview +
                ", noCache=" + noCache +
                ", noDiskCache=" + noDiskCache +
                ", noMemoryCache=" + noMemoryCache +
                ", autoPlayAnimations=" + autoPlayAnimations +
                ", decodeOptions=" + decodeOptions +
                ", postprocessor=" + postprocessor +
                '}';
        }
    }

    /**
     * Interface from where callbacks will be dispatched
     */
    public interface FrescoImageListener {
        /**
         * Success method, called when the image is loaded correctly
         * @param imageInfo info of the loaded image
         * @param animatable info of the animatable
         */
        void onSuccess(@Nullable ImageInfo imageInfo, @Nullable Animatable animatable);

        /**
         * Failure method, called when the image couldnt load correctly
         * @param t throwable thrown
         */
        void onFailure(@NonNull Throwable t);
    }

    /**
     * In favor of Fresco 1.+, please consider using {@link FrescoImageListener}
     * since this class is no longer mantained
     *
     * @deprecated use {@link FrescoControllerListener}
     */
    @Deprecated
    public interface Callback {
        /**
         * Success method, called when the image is loaded correctly
         * @param imageInfo info of the loaded image
         */
        void onSuccess(@Nullable ImageInfo imageInfo);

        /**
         * Failure method, called when the image couldnt load correctly
         * @param t throwable thrown
         */
        void onFailure(@NonNull Throwable t);
    }

    private class FrescoControllerListener extends BaseControllerListener<ImageInfo> {

        private @Nullable Callback deprecatedCallback;
        private @Nullable FrescoImageListener callback;

        public FrescoControllerListener(@Nullable Callback callback) {
            this.deprecatedCallback = callback;
        }

        public FrescoControllerListener(@Nullable FrescoImageListener callback) {
            this.callback = callback;
        }

        /**
         * In favor of the new callback, getFrescoListener() should be used.
         * This method is no longer mantained
         * @return deprecated callback
         * @deprecated use {@link #getFrescoListener()}
         */
        @Deprecated
        @Nullable
        public Callback getCallback() {
            return deprecatedCallback;
        }

        @Nullable
        public FrescoImageListener getFrescoListener() {
            return callback;
        }

        public void success(@Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
            if (callback != null) {
                callback.onSuccess(imageInfo, animatable);
            }

            if (deprecatedCallback != null) {
                deprecatedCallback.onSuccess(imageInfo);
            }

            if (!isCacheEnabled() || !isMemoryCacheEnabled()) {
                Fresco.getImagePipeline().evictFromCache(getUri());
            }
        }

        public void failure(Throwable throwable) {
            if (callback != null) {
                callback.onFailure(throwable);
            }

            if (deprecatedCallback != null) {
                deprecatedCallback.onFailure(throwable);
            }
        }

        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);
            success(imageInfo, animatable);
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            super.onFailure(id, throwable);
            failure(throwable);
        }
    }

}
