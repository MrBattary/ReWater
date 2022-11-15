package michael.linker.rewater.ui.elementary.status;

import android.view.View;
import android.view.ViewGroup;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.Status;
import michael.linker.rewater.ui.elementary.ICustomView;

public class DetailDescribedStatusView implements ICustomView {
    private final ViewGroup mParentView;
    private final DescribedStatusView mWaterView, mBatteryView;

    public DetailDescribedStatusView(final ViewGroup parentView) {
        mParentView = parentView;
        mWaterView = new DescribedStatusView(
                parentView.findViewById(R.id.status_described_water),
                parentView.findViewById(R.id.status_described_water_image),
                parentView.findViewById(R.id.status_described_water_text)
        );
        mBatteryView = new DescribedStatusView(
                parentView.findViewById(R.id.status_described_battery),
                parentView.findViewById(R.id.status_described_battery_image),
                parentView.findViewById(R.id.status_described_battery_text)
        );
    }

    public void setWater(final String text, final Status status) {
        mWaterView.setText(text, status);
    }

    public void setBattery(final String text, final Status status) {
        mBatteryView.setText(text, status);
    }

    @Override
    public View getViewInstance() {
        return null;
    }

    @Override
    public void setVisibility(int visibility) {

    }
}
