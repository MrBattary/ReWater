package michael.linker.rewater.ui.elementary.chart.model;

import michael.linker.rewater.ui.advanced.home.model.HomeStatusChartDataUiModel;

public class PieChartStatusDataModel {
    private final String mStatusDataName;
    private final Integer mFineAmount, mWarningAmount, mFailureAmount, mUndefinedAmount;

    public PieChartStatusDataModel(
            String statusDataName,
            Integer fineAmount,
            Integer warningAmount,
            Integer failureAmount,
            Integer undefinedAmount) {
        mStatusDataName = statusDataName;
        mFineAmount = fineAmount;
        mWarningAmount = warningAmount;
        mFailureAmount = failureAmount;
        mUndefinedAmount = undefinedAmount;
    }

    public PieChartStatusDataModel(
            String statusDataName,
            HomeStatusChartDataUiModel chartDataUiModel) {
        mStatusDataName = statusDataName;
        mFineAmount = chartDataUiModel.getFineAmount();
        mWarningAmount = chartDataUiModel.getWarningAmount();
        mFailureAmount = chartDataUiModel.getFailureAmount();
        mUndefinedAmount = chartDataUiModel.getUndefinedAmount();
    }

    public String getStatusDataName() {
        return mStatusDataName;
    }

    public Integer getFineAmount() {
        return mFineAmount;
    }

    public Integer getWarningAmount() {
        return mWarningAmount;
    }

    public Integer getFailureAmount() {
        return mFailureAmount;
    }

    public Integer getUndefinedAmount() {
        return mUndefinedAmount;
    }
}
