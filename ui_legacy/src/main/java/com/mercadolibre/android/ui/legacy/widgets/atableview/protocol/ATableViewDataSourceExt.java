package com.mercadolibre.android.ui.legacy.widgets.atableview.protocol;

import com.mercadolibre.android.ui.legacy.widgets.atableview.foundation.NSIndexPath;

@Deprecated
public abstract class ATableViewDataSourceExt extends ATableViewDataSource {
    public abstract int numberOfRowStyles();

    public abstract int styleForRowAtIndexPath(NSIndexPath indexPath);
}
