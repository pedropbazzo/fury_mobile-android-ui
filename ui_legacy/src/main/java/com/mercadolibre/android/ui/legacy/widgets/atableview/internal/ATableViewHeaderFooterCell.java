package com.mercadolibre.android.ui.legacy.widgets.atableview.internal;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mercadolibre.android.ui.legacy.widgets.atableview.view.ATableView;
import com.mercadolibre.android.ui.legacy.widgets.atableview.view.ATableView.ATableViewStyle;

@Deprecated
public class ATableViewHeaderFooterCell extends FrameLayout {
    private TextView mTextLabel;

    public ATableViewHeaderFooterCell(ATableViewHeaderFooterCellType type, ATableView tableView) {
        super(tableView.getContext());
        LayoutInflater.from(getContext()).inflate(getLayout(type, tableView), this, true);

        mTextLabel = (TextView) findViewById(com.mercadolibre.android.ui.legacy.R.id.textLabel);
    }

    public ATableViewHeaderFooterCell(Context context) {
        super(context);
    }

    protected static int getLayout(ATableViewHeaderFooterCellType type, ATableView tableView) {
        ATableViewStyle style = tableView.getStyle();
        if (ATableViewStyle.Grouped == style) {
            if (ATableViewHeaderFooterCellType.Header == type) {
                return com.mercadolibre.android.ui.legacy.R.layout.atv_grouped_header;
            }

            return com.mercadolibre.android.ui.legacy.R.layout.atv_grouped_footer;
        }

        return com.mercadolibre.android.ui.legacy.R.layout.atv_plain_header_footer;
    }

    public TextView getTextLabel() {
        return mTextLabel;
    }

    public enum ATableViewHeaderFooterCellType {Header, Footer}
}
