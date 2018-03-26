package com.mercadolibre.android.ui.example.legacy.widgets.mlimageview;

import android.os.Bundle;

import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.legacy.widgets.image.MLImagePager;

public class MLImagePagerActivity extends BaseActivity {

    private static final String[] imagesURLs = {"http://resources.mlstatic.com/category/images/26eb0d37-778b-4347-8fe3-d67c9675ec86.png",
            "http://resources.mlstatic.com/category/images/d290aba2-b6bf-4274-bac8-0284b1cde8fa.png",
            "http://resources.mlstatic.com/category/images/20871ac9-0e53-4618-9431-e1344d2adea6.png",
            "http://resources.mlstatic.com/category/images/0abfa128-5fde-4ad3-8b20-47c324ebd1cb.png",
            "http://resources.mlstatic.com/category/images/bd9038fd-98e5-4097-b53d-0027c740c04a.png"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ml_image_pager);

        //Search item images
        loadItemPictures();
        setupListener();
    }

    private void setupListener() {
    }

    private void loadItemPictures() {
        MLImagePager imagePager = (MLImagePager) findViewById(R.id.item_gallery_image_pager);
        imagePager.setUpGallery(getSupportFragmentManager(), imagesURLs, 0);
    }

    @Override
    public void onBackPressed() {
        MLImagePager imagePager = (MLImagePager) findViewById(R.id.item_gallery_image_pager);
//        Intent intent = new Intent(getApplicationContext(), ItemGalleryActivity.class);
//        intent.putExtra(com.mercadolibre.activities.Intent.EXTRA_SELECTED_PICTURE, imagePager.getCurrentPicture());
//        imagePager.release();
//        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
