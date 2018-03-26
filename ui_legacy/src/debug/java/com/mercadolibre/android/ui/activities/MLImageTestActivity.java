package com.mercadolibre.android.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.mercadolibre.android.ui.legacy.R;
import com.mercadolibre.android.ui.legacy.widgets.image.MLImageFragment;

public class MLImageTestActivity extends FragmentActivity {
	private static final String URL_IMAGE = "http://image.com";
	private static final String MATRIX = "MATRIX";
	private static final String CENTER_CROP = "CENTER_CROP";

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_mlimage_fragment);

		final Button centerCropScaleTypeButton = (Button) findViewById(R.id.center_crop_scale_type);
		final Button matrixScaleTypeButton = (Button) findViewById(R.id.matrix_scale_type);
		final Button noScaleTypeButton = (Button) findViewById(R.id.no_scale_type);

		centerCropScaleTypeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.main_container,
							MLImageFragment.newInstance(URL_IMAGE, CENTER_CROP, false))
					.commit();
			}
		});

		matrixScaleTypeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.main_container,
								MLImageFragment.newInstance(URL_IMAGE, MATRIX, false))
						.commit();
			}
		});

		noScaleTypeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.main_container,
								MLImageFragment.newInstance(URL_IMAGE, false))
						.commit();
			}
		});

	}
}
