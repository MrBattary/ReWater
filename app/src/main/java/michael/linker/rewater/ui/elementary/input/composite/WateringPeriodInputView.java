package michael.linker.rewater.ui.elementary.input.composite;

import android.view.View;
import android.view.ViewGroup;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.unit.WateringPeriodModel;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.elementary.ICustomView;
import michael.linker.rewater.ui.elementary.IFocusable;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;
import michael.linker.rewater.ui.elementary.input.number.integer.FocusableIntegerNumberInput;
import michael.linker.rewater.ui.elementary.input.number.integer.IFocusableIntegerNumberInput;

public class WateringPeriodInputView implements ICustomView, IFocusable {
    private static final Integer DAYS_MIN = 0;
    private static final Integer DAYS_MAX = 99;
    private static final Integer HOURS_MIN = 0;
    private static final Integer HOURS_MAX = 23;
    private static final Integer MINUTES_MIN = 0;
    private static final Integer MINUTES_MAX = 59;
    private final ViewGroup mParentView;
    private final IFocusableIntegerNumberInput mDaysInput, mHoursInput, mMinutesInput;

    public WateringPeriodInputView(final ViewGroup parentView) {
        mParentView = parentView;

        mDaysInput = new FocusableIntegerNumberInput(
                parentView.findViewById(R.id.input_water_period_days),
                parentView.findViewById(R.id.input_water_period_days_input));
        mHoursInput = new FocusableIntegerNumberInput(
                parentView.findViewById(R.id.input_water_period_hours),
                parentView.findViewById(R.id.input_water_period_hours_input));
        mMinutesInput = new FocusableIntegerNumberInput(
                parentView.findViewById(R.id.input_water_period_minutes),
                parentView.findViewById(R.id.input_water_period_minutes_input));

        this.initRestrictions();
    }

    public void setWateringPeriod(final WateringPeriodModel periodModel) {
        mDaysInput.setNumber(periodModel.getDays());
        mHoursInput.setNumber(periodModel.getHours());
        mMinutesInput.setNumber(periodModel.getMinutes());
    }

    public Integer getDaysPeriod() throws InputNotAllowedException {
        return mDaysInput.getNumber();
    }

    public Integer getHoursPeriod() throws InputNotAllowedException {
        return mHoursInput.getNumber();
    }

    public Integer getMinutesPeriod() throws InputNotAllowedException {
        return mMinutesInput.getNumber();
    }

    public void setDaysInputOnFocusChangeListener(final View.OnFocusChangeListener listener) {
        mDaysInput.setOnFocusChangeListener(listener);
    }

    public void setHoursInputOnFocusChangeListener(final View.OnFocusChangeListener listener) {
        mHoursInput.setOnFocusChangeListener(listener);
    }

    public void setMinutesInputOnFocusChangeListener(final View.OnFocusChangeListener listener) {
        mMinutesInput.setOnFocusChangeListener(listener);
    }


    public WateringPeriodModel getWateringPeriod() throws InputNotAllowedException {
        Integer days = null, hours = null, minutes = null;
        try {
            days = mDaysInput.getNumber();
        } catch (InputNotAllowedException ignored) {
        }
        try {
            hours = mHoursInput.getNumber();
        } catch (InputNotAllowedException ignored) {
        }
        try {
            minutes = mMinutesInput.getNumber();
        } catch (InputNotAllowedException ignored) {
        }
        if (days != null && hours != null && minutes != null) {
            return new WateringPeriodModel(days, hours, minutes);
        }
        throw new InputNotAllowedException(
                "Could not get the entered values from the water volume input!");
    }

    @Override
    public View getViewInstance() {
        return mParentView;
    }

    @Override
    public void setVisibility(final int visibility) {
        mParentView.setVisibility(visibility);
    }

    @Override
    public void setOnFocusChangeListener(final View.OnFocusChangeListener listener) {
        mDaysInput.setOnFocusChangeListener(listener);
        mHoursInput.setOnFocusChangeListener(listener);
        mMinutesInput.setOnFocusChangeListener(listener);
    }

    private void initRestrictions() {
        mDaysInput.setMinLimit(DAYS_MIN,
                StringsProvider.getString(R.string.input_error_days));
        mDaysInput.setMaxLimit(DAYS_MAX,
                StringsProvider.getString(R.string.input_error_days));
        mHoursInput.setMinLimit(HOURS_MIN,
                StringsProvider.getString(R.string.input_error_hours));
        mHoursInput.setMaxLimit(HOURS_MAX,
                StringsProvider.getString(R.string.input_error_hours));
        mMinutesInput.setMinLimit(MINUTES_MIN,
                StringsProvider.getString(R.string.input_error_minutes));
        mMinutesInput.setMaxLimit(MINUTES_MAX,
                StringsProvider.getString(R.string.input_error_minutes));
    }
}
