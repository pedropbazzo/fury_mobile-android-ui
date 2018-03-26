package com.mercadolibre.android.ui.example.legacy.widgets.atableview;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.legacy.widgets.atableview.foundation.NSIndexPath;
import com.mercadolibre.android.ui.legacy.widgets.atableview.internal.ATableViewCellAccessoryView;
import com.mercadolibre.android.ui.legacy.widgets.atableview.protocol.ATableViewDataSource;
import com.mercadolibre.android.ui.legacy.widgets.atableview.protocol.ATableViewDelegate;
import com.mercadolibre.android.ui.legacy.widgets.atableview.view.ATableView;
import com.mercadolibre.android.ui.legacy.widgets.atableview.view.ATableViewCell;
import com.mercadolibre.android.ui.legacy.widgets.atableview.view.ATableViewCell.ATableViewCellStyle;

public class ATableViewActivity extends BaseActivity {

    private ViewGroup container;
    private ATableView aTableView;
    private ATableViewDelegate aTableViewDelegate;
    private ATableViewDataSource aTableViewDataSource;

    protected static final int FIRST_SECTION = 0;
    protected static final int SECOND_SECTION = 1;
    protected static final int THIRD_SECTION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atable_view);
        container = (ViewGroup) findViewById(R.id.atableview_container);
        aTableView = new ATableView(this);
        aTableViewDelegate = new ATableViewExampleDataDelegate();
        aTableViewDataSource = new ATableViewExampleDataSource();

        initATableView();
    }


    private void initATableView() {
        aTableView = new ATableView(ATableView.ATableViewStyle.Grouped, this);
        aTableView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.activity_atable_view_header, null));
        aTableView.addFooterView(LayoutInflater.from(this).inflate(R.layout.activity_atable_view_footer, null));
        aTableView.setDataSource(aTableViewDataSource);
        aTableView.setDelegate(aTableViewDelegate);

        container.addView(aTableView);

    }


    private class ATableViewExampleDataDelegate extends ATableViewDelegate {
        @Override
        public void didSelectRowAtIndexPath(ATableView tableView, NSIndexPath indexPath) {
            switch (indexPath.getSection()) {
                case FIRST_SECTION:
                    //do something when selecting second section
                    break;
                case SECOND_SECTION:
                    Toast.makeText(ATableViewActivity.this, "Section 2 row " + indexPath.getRow() + " clicked!", Toast.LENGTH_SHORT).show();
                    //do something when selecting second section
                    break;

            }
        }


        @Override
        public int heightForRowAtIndexPath(ATableView tableView, NSIndexPath indexPath) {
            if (isTitleRow(indexPath)) {
                return ListView.LayoutParams.WRAP_CONTENT;
            }
            return super.heightForRowAtIndexPath(tableView, indexPath);
        }

        private boolean isTitleRow(NSIndexPath indexPath) {
            return indexPath.getRow() == 0;
        }
    }


    protected class ATableViewExampleDataSource extends ATableViewDataSource {

        protected String SECTION_CELL_IDENTIFIER = "SECTION_CELL_IDENTIFIER";
        protected String SECTION_TWO_ROW_IDENTIFIER = "SECTION_TWO_ROW_IDENTIFIER";

        protected ATableViewCell cellForRowAtIndexPathSectionOne(ATableView tableView, NSIndexPath indexPath) {
            if (indexPath.getRow() == 0) {
                return getTitleCell("Section 1 title");
            }

            String rowName = SECTION_CELL_IDENTIFIER + String.valueOf(indexPath);
            String detailTextLabel;
            String textLabel = "";
            detailTextLabel = "";
            switch (indexPath.getRow()) {
                case 1: {
                    textLabel = "Row 1 label";
                    detailTextLabel = "row 1 data";
                    break;
                }
                case 2: {
                    textLabel = "Row 2 label";
                    detailTextLabel = "row 2 data";
                    break;
                }
                case 3: {
                    textLabel = "Row 3 label";
                    detailTextLabel = "row 3 data";
                    break;
                }
                case 4: {
                    textLabel = "Row 4 label";
                    detailTextLabel = "row 4 data";
                    break;
                }
            }

            ATableViewCell cell = new ATableViewCell(ATableViewCell.ATableViewCellStyle.Subtitle, rowName,
                    ATableViewActivity.this);
            cell.setSelectionStyle(ATableViewCell.ATableViewCellSelectionStyle.None);

            cell.getTextLabel().setSingleLine(false);
            cell.getTextLabel().setText(textLabel);
            cell.getTextLabel().setTypeface(null, Typeface.BOLD);

            cell.getDetailTextLabel().setSingleLine(false);
            cell.getDetailTextLabel().setText(detailTextLabel);
            cell.setClickable(false);

            setupLayout(cell, indexPath);

            return cell;
        }

        protected ATableViewCell cellForRowAtIndexPathSectionTwo(ATableView tableView, NSIndexPath indexPath) {
            int row = indexPath.getRow();
            if (row == 0) {
                return getTitleCell("Section 2 title");
            }
            String rowName = SECTION_TWO_ROW_IDENTIFIER + " row " + row;
            ATableViewCell cell = dequeueReusableCellWithIdentifier(rowName);
            if (cell == null) {
                cell = new ATableViewCell(ATableViewCellStyle.Subtitle, rowName, ATableViewActivity.this);
            }

            cell.setAccessoryType(ATableViewCellAccessoryView.ATableViewCellAccessoryType.DisclosureIndicator);
            cell.getTextLabel().setSingleLine(false); // this could be double line.
            cell.getTextLabel().setText("Section 2 row " + row + " label");

            cell.getDetailTextLabel().setSingleLine(false); // this could be double line.
            cell.getDetailTextLabel().setText("Section 2 row " + row + " information");

            setupLayout(cell, indexPath);

            return cell;
        }

        private ATableViewCell getTitleCell(String title) {
            ATableViewCell cell;

            cell = new ATableViewCell(ATableViewCell.ATableViewCellStyle.Default, null, ATableViewActivity.this);

            cell.setSelectionStyle(ATableViewCell.ATableViewCellSelectionStyle.None);
            cell.getTextLabel().setTypeface(null, Typeface.BOLD);
            cell.getTextLabel().setClickable(false);
            cell.getTextLabel().setText(title);


            Resources res = getResources();
            int minHeight = (int) (res.getDimension(R.dimen.atv_cell_default_row_height));
            // minHeight.
            View contentView = cell.getContentView();
            contentView.setMinimumHeight(minHeight);

            return cell;
        }

        private void setupLayout(ATableViewCell cell, NSIndexPath indexPath) {
            ATableViewDelegate delegate = aTableView.getDelegate();
            int padding = 0, minHeight = 0;

            // add multiline to textView apart from WRAP_CONTENT to make height dynamic.
            int height = delegate.heightForRowAtIndexPath(aTableView, indexPath);
            if (height == ListView.LayoutParams.WRAP_CONTENT) {
                Resources res = getResources();
                float density = res.getDisplayMetrics().density;
                padding = (int) (6 * density);
                minHeight = (int) (res.getDimension(R.dimen.atv_cell_default_row_height) * density);
            }

            // padding.
            View containerView = cell.getInternalContainerView();
            containerView.setPadding(0, padding, 0, padding);

            // minHeight.
            View contentView = cell.getContentView();
            contentView.setMinimumHeight(minHeight);
        }


        @Override
        public ATableViewCell cellForRowAtIndexPath(ATableView tableView, NSIndexPath indexPath) {
            switch (indexPath.getSection()) {
                case FIRST_SECTION:
                    return cellForRowAtIndexPathSectionOne(tableView, indexPath);
                case SECOND_SECTION:
                    return cellForRowAtIndexPathSectionTwo(tableView, indexPath);
                default:
                    return null;
            }
        }

        @Override
        public int numberOfSectionsInTableView(ATableView tableView) {
            return 2;
        }

        @Override
        public int numberOfRowsInSection(ATableView tableView, int section) {
            switch (section) {
                case FIRST_SECTION:
                    return 5;
                case SECOND_SECTION:
                    return 4;
                default:
                    return 0;
            }
        }

    }


}
