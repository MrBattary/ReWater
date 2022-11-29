package michael.linker.rewater.ui.elementary.status;

import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.elementary.ICustomView;
import michael.linker.rewater.ui.elementary.text.status.StatusStyledColoredTextView;

public class DetailDescribedStatusView implements ICustomView {
    private static final String UNDEFINED_WATER_STATUS = StringsProvider.getString(
            R.string.status_undefined);
    private static final String UNDEFINED_BATTERY_STATUS = StringsProvider.getString(
            R.string.status_undefined);
    private final ViewGroup mParentView;
    private final DescribedStatusView mWaterView, mBatteryView;
    private final Map<Status, String> mWaterStatusTextMap, mBatteryStatusTextMap;

    public DetailDescribedStatusView(final ViewGroup parentView) {
        mParentView = parentView;
        mWaterView = new DescribedStatusView(
                parentView.findViewById(R.id.status_described_water),
                parentView.findViewById(R.id.status_described_water_image),
                new StatusStyledColoredTextView(
                        parentView.findViewById(R.id.status_described_water_text)));
        mBatteryView = new DescribedStatusView(
                parentView.findViewById(R.id.status_described_battery),
                parentView.findViewById(R.id.status_described_battery_image),
                new StatusStyledColoredTextView(
                        parentView.findViewById(R.id.status_described_battery_text)));

        mWaterStatusTextMap = new HashMap<>();
        mBatteryStatusTextMap = new HashMap<>();
        this.initMaps();
    }

    /**
     * Style water status view according to the transmitted status,
     * if the text for the status is not found,
     * an undefined text will be shown
     *
     * @param status the status on the basis of which the style will be set
     */
    public void showWater(final Status status) {
        mWaterView.setText(mWaterStatusTextMap.getOrDefault(status, UNDEFINED_WATER_STATUS),
                status);
    }

    /**
     * Style battery status view according to the transmitted status,
     * if the text for the status is not found,
     * an undefined text will be shown
     *
     * @param status the status on the basis of which the style will be set
     */
    public void showBattery(final Status status) {
        mBatteryView.setText(mBatteryStatusTextMap.getOrDefault(status, UNDEFINED_BATTERY_STATUS),
                status);
    }

    @Override
    public View getViewInstance() {
        return mParentView;
    }

    @Override
    public void setVisibility(final int visibility) {
        mParentView.setVisibility(visibility);
    }

    private void initMaps() {
        mBatteryStatusTextMap.put(Status.OK,
                StringsProvider.getString(R.string.status_battery_fine));
        mBatteryStatusTextMap.put(Status.WARNING,
                StringsProvider.getString(R.string.status_battery_warning));
        mBatteryStatusTextMap.put(Status.DEFECT,
                StringsProvider.getString(R.string.status_battery_defect));
        mWaterStatusTextMap.put(Status.OK,
                StringsProvider.getString(R.string.status_water_fine));
        mWaterStatusTextMap.put(Status.WARNING,
                StringsProvider.getString(R.string.status_water_warning));
        mWaterStatusTextMap.put(Status.DEFECT,
                StringsProvider.getString(R.string.status_water_defect));
    }
}
