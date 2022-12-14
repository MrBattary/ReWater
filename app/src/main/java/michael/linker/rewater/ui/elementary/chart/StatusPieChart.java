package michael.linker.rewater.ui.elementary.chart;

import android.view.View;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.res.ColorsProvider;
import michael.linker.rewater.data.res.DimensionsProvider;
import michael.linker.rewater.data.res.status.StatusColorsProvider;
import michael.linker.rewater.ui.elementary.chart.model.PieChartLookModel;
import michael.linker.rewater.ui.elementary.chart.model.PieChartStatusDataModel;

public class StatusPieChart extends CirclePieChart implements IChart<PieChartStatusDataModel> {
    private static final String
            DATA_SET_LABEL = "",
            DATA_VALUE_LABEL = "",
            ZERO_DATA_VALUE_LABEL = "";

    public StatusPieChart(View view, PieChartLookModel lookModel) {
        super(view);
        mChart.setNoDataText(lookModel.getNoDataText());
    }

    @Override
    public void setDataModel(PieChartStatusDataModel dataModel) {
        final int edgeSize =
                ((DimensionsProvider.getDisplayMetrics().widthPixels
                        - (2 * DimensionsProvider.getDimensionValue(R.dimen.card_horizontal_spacing))
                        - (2 * DimensionsProvider.getDimensionValue(R.dimen.chart_horizontal_margin)))
                );
        mChart.setMinimumHeight(edgeSize);

        mChart.setCenterText(dataModel.getStatusDataName());

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(dataModel.getFineAmount().floatValue()));
        entries.add(new PieEntry(dataModel.getWarningAmount().floatValue()));
        entries.add(new PieEntry(dataModel.getFailureAmount().floatValue()));
        entries.add(new PieEntry(dataModel.getUndefinedAmount().floatValue()));

        PieDataSet dataSet = new PieDataSet(entries, DATA_SET_LABEL);
        dataSet.setColors(
                StatusColorsProvider.getBackgroundColorForStatus(Status.OK),
                StatusColorsProvider.getBackgroundColorForStatus(Status.WARNING),
                StatusColorsProvider.getBackgroundColorForStatus(Status.DEFECT),
                StatusColorsProvider.getBackgroundColorForStatus(Status.UNDEFINED)
        );

        mChart.setData(decorateData(new PieData(dataSet)));
        mChart.invalidate();
    }

    private PieData decorateData(PieData data) {
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value == 0.0f) {
                    return ZERO_DATA_VALUE_LABEL;
                }
                return DATA_VALUE_LABEL + ((int) value);
            }
        });
        data.setValueTextSize(DimensionsProvider.getSpExtracted(R.dimen.font_size_large));
        data.setValueTextColor(ColorsProvider.getColor(R.color.text_primary));
        return data;
    }
}
