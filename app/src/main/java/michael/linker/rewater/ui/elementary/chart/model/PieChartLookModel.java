package michael.linker.rewater.ui.elementary.chart.model;

import michael.linker.rewater.data.res.StringsProvider;

public class PieChartLookModel {
    private final String mNoDataText;

    public PieChartLookModel(final int noDataTextStringId) {
        mNoDataText = StringsProvider.getString(noDataTextStringId);
    }

    public String getNoDataText() {
        return mNoDataText;
    }
}
