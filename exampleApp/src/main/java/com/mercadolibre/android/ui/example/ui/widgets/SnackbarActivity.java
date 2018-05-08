package com.mercadolibre.android.ui.example.ui.widgets;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.widgets.MeliSnackbar;

public class SnackbarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View parentView = LayoutInflater.from(this).inflate(R.layout.activity_snackbar, null);
        setContentView(parentView);
    }

    public void showGreenSnackbar(View view) {
        MeliSnackbar.make(view, "Feedback positivo sin salida", Snackbar.LENGTH_SHORT, MeliSnackbar.SnackbarType.SUCCESS)
                .show();
    }

    public void showGreenSnackbarWithAction(final View view) {
        MeliSnackbar.make(view, "Feedback positivo con salida y mucho texto para ver como se ven las dos lineas", Snackbar.LENGTH_INDEFINITE, MeliSnackbar.SnackbarType.SUCCESS)
                .setAction("Salida", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "Dismiss!", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    public void showRedSnackbar(View view) {
        MeliSnackbar.make(view, "Feedback de error", Snackbar.LENGTH_INDEFINITE, MeliSnackbar.SnackbarType.ERROR)
                .setAction("Salida", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "Dismiss!", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    public void showOrangeSnackbar(View view) {
        MeliSnackbar.make(view, "Feedback de warning", Snackbar.LENGTH_LONG, MeliSnackbar.SnackbarType.WARNING)
                .setAction("Salida", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "Dismiss!", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    public void showBlackSnackbar(View view) {
        MeliSnackbar.make(view, "Mensaje genérico multilínea sin salida donde hay mucho texto", Snackbar.LENGTH_LONG)
                .show();
    }
}
