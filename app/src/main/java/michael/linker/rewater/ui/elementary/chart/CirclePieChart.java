package michael.linker.rewater.ui.elementary.chart;

import android.graphics.Paint;
import android.view.View;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.ColorsProvider;
import michael.linker.rewater.data.res.DimensionsProvider;
import michael.linker.rewater.ui.elementary.ICustomView;

class CirclePieChart implements ICustomView {
    protected final PieChart mChart;

    private CirclePieChart() {
        mChart = null;
    }

    protected CirclePieChart(final View view) {
        mChart = (PieChart) view;

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(ColorsProvider.getColor(R.color.background_primary));

        Paint p = mChart.getPaint(Chart.PAINT_INFO);
        p.setTextSize(DimensionsProvider.getDimensionValue(R.dimen.font_size_small));
        p.setColor(ColorsProvider.getColor(R.color.text_tertiary));

        mChart.setTransparentCircleColor(ColorsProvider.getColor(R.color.background_primary));
        mChart.setTransparentCircleAlpha(110);

        mChart.setDrawCenterText(true);
        mChart.setCenterTextSize(DimensionsProvider.getSpExtracted(R.dimen.font_size_medium));
        mChart.setCenterTextColor(ColorsProvider.getColor(R.color.text_primary));

        mChart.setUsePercentValues(false);
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
