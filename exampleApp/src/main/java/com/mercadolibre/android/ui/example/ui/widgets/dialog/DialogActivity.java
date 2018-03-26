package com.mercadolibre.android.ui.example.ui.widgets.dialog;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.example.ui.widgets.dialog.DummyFeedBackDialogWithTitle.DummyInterface;
import com.mercadolibre.android.ui.widgets.MeliDialog;

public class DialogActivity extends BaseActivity implements DummyInterface {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }

    public void showDialogWithSmallView(final View view) {

        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        final MeliDialog frag = new DummyFeedbackDialog();
        frag.show(ft, TAG);
    }

    public void showDialogWithBigView(final View view) {

        final  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        final DummyFeedBackDialogWithTitle frag = new DummyFeedBackDialogWithTitle();
        frag.setListener(this);
        frag.show(ft, TAG);
    }

    public void showDialogWithBigViewNoTitle(final View view) {

        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        final MeliDialog frag = new DummyBigErrorDialog();
        frag.show(ft, TAG);
    }

    public void showDialogWithList(final View view) {

        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        final MeliDialog frag = new DummyListDialogWithTitle();
        frag.show(ft, TAG);
    }

    public void showDialogWithListNoTitle(final View view) {

        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        final MeliDialog frag = new DummyListDialog();
        frag.show(ft, TAG);
    }

    @Override
    public void dismissed() {
        finish();
    }
}

