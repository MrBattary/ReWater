package michael.linker.rewater.ui.advanced.home.model;

public class HomeStatusChartDataUiModel {
    private Integer mFineAmount, mWarningAmount, mFailureAmount, mUndefinedAmount;

    public HomeStatusChartDataUiModel() {
        mFineAmount = 0;
        mWarningAmount = 0;
        mFailureAmount = 0;
        mUndefinedAmount = 0;
    }

    public void incrementFineAmount() {
        mFineAmount += 1;
    }

    public void incrementWarningAmount() {
        mWarningAmount += 1;
    }

    public void incrementFailureAmount() {
        mFailureAmount += 1;
    }

    public void incrementUndefinedAmount() {
        mUndefinedAmount += 1;
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

    public Integer getDataSize() {
        return mFineAmount + mWarningAmount + mFailureAmount + mUndefinedAmount;
    }
}
