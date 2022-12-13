package michael.linker.rewater.ui.elementary.chart;

import michael.linker.rewater.data.res.StringsProvider;

public class PieChartLookModel {
    private final String mNoDataText;
    private final String mCenterText;

    public PieChartLookModel(final int noDataTextStringId, final int centerTextStringId) {
        mNoDataText = StringsProvider.getString(noDataTextStringId);
        mCenterText = StringsProvider.getString(centerTextStringId);
    }

    public String getNoDataText() {
        return mNoDataText;
    }

    public String getCenterText() {
        return mCenterText;
    }
}
