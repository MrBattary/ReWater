package michael.linker.rewater.ui.elementary.chart;

import android.graphics.Paint;
import android.view.View;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.ColorsProvider;
import michael.linker.rewater.data.res.DimensionsProvider;
import michael.linker.rewater.ui.elementary.ICustomView;

public class CustomPieChart implements ICustomView {
    private static final int ADDITIONAL_MARGIN_MULTIPLIER = 4;
    private final PieChart mChart;

    public CustomPieChart(final View view, final PieChartLookModel lookModel) {
        mChart = (PieChart) view;
        final int edgeSize =
                (int) ((DimensionsProvider.getDisplayMetrics().widthPixels -
                        ((2 + ADDITIONAL_MARGIN_MULTIPLIER)
                                * DimensionsProvider.getDp(R.dimen.card_horizontal_spacing)))
                );
        mChart.setMinimumHeight(edgeSize);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(ColorsProvider.getColor(R.color.background_primary));

        mChart.setNoDataText(lookModel.getNoDataText());
        Paint p = mChart.getPaint(Chart.PAINT_INFO);
        p.setTextSize(DimensionsProvider.getSpExtracted(R.dimen.font_size_medium));
        p.setColor(ColorsProvider.getColor(R.color.text_primary));

        mChart.setTransparentCircleColor(ColorsProvider.getColor(R.color.background_primary));
        mChart.setTransparentCircleAlpha(110);

        mChart.setDrawCenterText(true);
        mChart.setCenterText(lookModel.getCenterText());
        mChart.setCenterTextSize(DimensionsProvider.getSpExtracted(R.dimen.font_size_medium));
        mChart.setCenterTextColor(ColorsProvider.getColor(R.color.text_primary));

        mChart.setUsePercentValues(false);
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(6f));
        entries.add(new PieEntry(3f));
        entries.add(new PieEntry(1f));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(
                ColorsProvider.getColor(R.color.background_positive),
                ColorsProvider.getColor(R.color.background_warning),
                ColorsProvider.getColor(R.color.background_negative)
        );

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "" + ((int) value);
            }
        });
        data.setValueTextSize(DimensionsProvider.getSpExtracted(R.dimen.font_size_large));
        data.setValueTextColor(ColorsProvider.getColor(R.color.text_primary));

        mChart.setData(data);

        mChart.getLegend().setEnabled(false);
        mChart.getDescription().setEnabled(false);

        mChart.highlightValues(null);
        mChart.invalidate();
    }

    @Override
    public View getViewInstance() {
        return mChart;
    }

    @Override
    public void setVisibility(int visibility) {
        mChart.setVisibility(visibility);
    }
}
