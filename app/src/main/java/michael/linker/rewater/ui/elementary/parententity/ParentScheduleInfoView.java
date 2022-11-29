package michael.linker.rewater.ui.elementary.parententity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;
import michael.linker.rewater.data.model.unit.WateringPeriodModel;
import michael.linker.rewater.ui.elementary.ICustomView;

public class ParentScheduleInfoView implements ICustomView {
    private final ViewGroup mParentView;
    private final TextView mVolumeInfo, mPeriodInfo;

    public ParentScheduleInfoView(final ViewGroup parentView) {
        mParentView = parentView;
        mVolumeInfo = parentView.findViewById(R.id.device_info_parent_schedule_information_water);
        mPeriodInfo = parentView.findViewById(R.id.device_info_parent_schedule_information_period);
    }

    public void setWateringPeriodInfo(final WateringPeriodModel info) {
        mPeriodInfo.setText(info.formatToCompact());
    }

    public void setWaterVolumeInfo(final WaterVolumeMetricModel info) {
        mVolumeInfo.setText(info.formatToCompact());
    }

    @Override
    public View getViewInstance() {
        return mParentView;
    }

    @Override
    public void setVisibility(final int visibility) {
        mParentView.setVisibility(visibility);
    }
}
