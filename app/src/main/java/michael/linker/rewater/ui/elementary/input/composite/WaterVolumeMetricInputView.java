package michael.linker.rewater.ui.elementary.input.composite;

import android.view.View;
import android.view.ViewGroup;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.elementary.ICustomView;
import michael.linker.rewater.ui.elementary.IFocusable;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;
import michael.linker.rewater.ui.elementary.input.number.integer.FocusableIntegerNumberInput;
import michael.linker.rewater.ui.elementary.input.number.integer.IFocusableIntegerNumberInput;

public class WaterVolumeMetricInputView implements ICustomView, IFocusable {
    private static final Integer LITRES_MIN = 0;
    private static final Integer LITRES_MAX = 99;
    private static final Integer MILLILITRES_MIN = 0;
    private static final Integer MILLILITRES_MAX = 999;
    private final ViewGroup mParentView;
    private final IFocusableIntegerNumberInput mLitresInput, mMillilitresInput;

    public WaterVolumeMetricInputView(final ViewGroup parentView) {
        mParentView = parentView;
        mLitresInput = new FocusableIntegerNumberInput(
                parentView.findViewById(R.id.input_metric_water_volume_greater),
                parentView.findViewById(R.id.input_metric_water_volume_greater_input));
        mMillilitresInput = new FocusableIntegerNumberInput(
                parentView.findViewById(R.id.input_metric_water_volume_lesser),
                parentView.findViewById(R.id.input_metric_water_volume_lesser_input));

        this.initRestrictions();
        setWaterVolume(new WaterVolumeMetricModel(0, 0));
    }

    public void setWaterVolume(final WaterVolumeMetricModel metricModel) {
        mLitresInput.setNumber(metricModel.getLitres());
        mMillilitresInput.setNumber(metricModel.getMillilitres());
    }

    public WaterVolumeMetricModel getWaterVolume() throws InputNotAllowedException {
        return new WaterVolumeMetricModel(mLitresInput.getNumber(),
                mMillilitresInput.getNumber());
    }

    public WaterVolumeMetricModel getWaterVolumeForce() {
        return new WaterVolumeMetricModel(mLitresInput.getNumberForce(),
                mMillilitresInput.getNumberForce());
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
        mLitresInput.setOnFocusChangeListener(listener);
        mMillilitresInput.setOnFocusChangeListener(listener);
    }

    private void initRestrictions() {
        mLitresInput.setMinLimit(LITRES_MIN,
                StringsProvider.getString(R.string.input_error_litres));
        mLitresInput.setMaxLimit(LITRES_MAX,
                StringsProvider.getString(R.string.input_error_litres));
        mMillilitresInput.setMinLimit(MILLILITRES_MIN,
                StringsProvider.getString(R.string.input_error_millilitres));
        mMillilitresInput.setMaxLimit(MILLILITRES_MAX,
                StringsProvider.getString(R.string.input_error_millilitres));
    }
}
