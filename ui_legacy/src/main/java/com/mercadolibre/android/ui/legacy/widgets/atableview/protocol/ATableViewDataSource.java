package com.mercadolibre.android.ui.legacy.widgets.atableview.protocol;

import android.graphics.Typeface;

import com.mercadolibre.android.ui.legacy.widgets.atableview.foundation.NSIndexPath;
import com.mercadolibre.android.ui.legacy.widgets.atableview.view.ATableView;
import com.mercadolibre.android.ui.legacy.widgets.atableview.view.ATableViewCell;

@Deprecated
public abstract class ATableViewDataSource {
    private ATableViewCell mReusableCell;

    public abstract ATableViewCell cellForRowAtIndexPath(ATableView tableView, NSIndexPath indexPath);

    public abstract int numberOfRowsInSection(ATableView tableView, int section);

    public int numberOfSectionsInTableView(ATableView tableView) {
        return 1;
    }

    public String titleForHeaderInSection(ATableView tableView, int section) {
        return null;
    }

    public String titleForFooterInSection(ATableView tableView, int section) {
        return null;
    }

    public Typeface typefaceForHeader() {
        return null;
    }

    public Typeface typefaceForFooter() {
        return null;
    }

    public ATableViewCell dequeueReusableCellWithIdentifier(String cellIdentifier) {
        if (cellIdentifier != null && mReusableCell != null &&
                cellIdentifier.equals(mReusableCell.getReuseIdentifier())) {
            return mReusableCell;
        }

        return null;
    }

    public void setReusableCell(ATableViewCell reusableCell) {
        mReusableCell = reusableCell;
    }
}
