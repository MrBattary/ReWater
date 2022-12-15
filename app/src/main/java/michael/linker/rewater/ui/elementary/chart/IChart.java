package michael.linker.rewater.ui.elementary.chart;

import michael.linker.rewater.ui.elementary.ICustomView;

/**
 * Chart default interface.
 *
 * @param <T> Data model of the chart.
 */
public interface IChart<T> extends ICustomView {

    /**
     * Provide new data for the chart.
     *
     * @param dataModel Data model of the chart.
     */
    void setDataModel(T dataModel);
}
