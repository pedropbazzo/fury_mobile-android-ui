package com.mercadolibre.android.ui.legacy.widgets.atableview.protocol;

import com.mercadolibre.android.ui.legacy.R;
import com.mercadolibre.android.ui.legacy.widgets.atableview.foundation.NSIndexPath;
import com.mercadolibre.android.ui.legacy.widgets.atableview.view.ATableView;
import com.mercadolibre.android.ui.legacy.widgets.atableview.view.ATableViewCell;

@Deprecated
public class ATableViewDelegate {
    public int heightForRowAtIndexPath(ATableView tableView, NSIndexPath indexPath) {
        return (int) tableView.getResources().getDimension(R.dimen.atv_cell_default_row_height);
    }

    public void willDisplayCellForRowAtIndexPath(ATableView tableView, ATableViewCell cell, NSIndexPath indexPath) {
        return;
    }

    public int heightForHeaderInSection(ATableView tableView, int section) {
        return ATableViewCell.LayoutParams.UNDEFINED;
    }

    public int heightForFooterInSection(ATableView tableView, int section) {
        return ATableViewCell.LayoutParams.UNDEFINED;
    }

    public void didSelectRowAtIndexPath(ATableView tableView, NSIndexPath indexPath) {
        return;
    }

    public void accessoryButtonTappedForRowWithIndexPath(ATableView tableView, NSIndexPath indexPath) {
        return;
    }
}
