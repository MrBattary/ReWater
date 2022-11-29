package michael.linker.rewater.ui.advanced.schedules.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.devices.model.DeviceIdNameUiModel;
import michael.linker.rewater.ui.advanced.schedules.view.part.UpdateScheduleDeviceCardView;
import michael.linker.rewater.ui.advanced.schedules.viewmodel.UpdateScheduleViewModel;

public class ScheduleAttachedDevicesItemAdapter extends
        RecyclerView.Adapter<ScheduleAttachedDevicesItemAdapter.ScheduleDevicesItemViewHolder> {
    private final Context mContext;
    private final UpdateScheduleViewModel mViewModel;
    private final List<DeviceIdNameUiModel> mDeviceUiModels;

    public ScheduleAttachedDevicesItemAdapter(
            final Context context,
            final UpdateScheduleViewModel viewModel,
            final List<DeviceIdNameUiModel> deviceUiModels) {
        mContext = context;
        mViewModel = viewModel;
        mDeviceUiModels = deviceUiModels;
    }

    @NonNull
    @Override
    public ScheduleDevicesItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
            int viewType) {
        final View adapterLayout = LayoutInflater.from(mContext)
                .inflate(R.layout.view_schedule_devices_card, parent, false);
        return new ScheduleAttachedDevicesItemAdapter.ScheduleDevicesItemViewHolder(adapterLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleDevicesItemViewHolder holder, int position) {
        final DeviceIdNameUiModel model = mDeviceUiModels.get(position);
        holder.mCardView.setUiModel(model);
        holder.mCardView.setOnClickListener(
                l -> mViewModel.detachDeviceFromSchedule(model.getId()));
    }

    @Override
    public int getItemCount() {
        return mDeviceUiModels.size();
    }

    static class ScheduleDevicesItemViewHolder extends RecyclerView.ViewHolder {
        private final UpdateScheduleDeviceCardView mCardView;

        public ScheduleDevicesItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            mCardView = new UpdateScheduleDeviceCardView(itemView);
        }
    }
}
