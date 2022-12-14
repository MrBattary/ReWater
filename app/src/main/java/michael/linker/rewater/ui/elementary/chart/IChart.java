package michael.linker.rewater.ui.elementary.chart;

/**
 * Chart default interface.
 *
 * @param <T> Data model of the chart.
 */
public interface IChart<T> {

    /**
     * Provide new data for the chart.
     *
     * @param dataModel Data model of the chart.
     */
    void setDataModel(T dataModel);
}
