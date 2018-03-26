package com.mercadolibre.android.ui.legacy.widgets.atableview.internal;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import com.mercadolibre.android.ui.legacy.widgets.atableview.protocol.ATableViewDelegate;
import com.mercadolibre.android.ui.legacy.widgets.atableview.view.ATableView;

@Deprecated
public class ATableViewCellClickListener implements OnItemClickListener {
    private ATableView mTableView;

    public ATableViewCellClickListener(ATableView tableView) {
        mTableView = tableView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
        ATableViewAdapter internalAdapter = mTableView.getInternalAdapter();

        // closes #14, adding a header to the table causes an offset on rows position.
        ListAdapter rawAdapter = mTableView.getAdapter();
        if (rawAdapter instanceof HeaderViewListAdapter &&
                ((HeaderViewListAdapter) rawAdapter).getHeadersCount() > 0) {
            pos--;
        }

        // do not send callbacks for header rows.
        if (!internalAdapter.isHeaderRow(pos) && !internalAdapter.isFooterRow(pos)) {
            ATableViewDelegate delegate = mTableView.getDelegate();
            delegate.didSelectRowAtIndexPath(mTableView, internalAdapter.getIndexPath(pos));
        }
    }
}