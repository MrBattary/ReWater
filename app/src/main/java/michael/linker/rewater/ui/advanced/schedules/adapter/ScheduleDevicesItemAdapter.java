package michael.linker.rewater.ui.advanced.schedules.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.devices.model.DeviceWithoutParentsUiModel;
import michael.linker.rewater.ui.advanced.schedules.view.part.ScheduleDeviceCardView;

public class ScheduleDevicesItemAdapter extends
        RecyclerView.Adapter<ScheduleDevicesItemAdapter.ScheduleDevicesItemViewHolder> {
    private final Context mContext;
    private final List<DeviceWithoutParentsUiModel> mDeviceUiModels;

    public ScheduleDevicesItemAdapter(final Context context,
            final List<DeviceWithoutParentsUiModel> deviceUiModels) {
        mContext = context;
        mDeviceUiModels = deviceUiModels;
    }

    @NonNull
    @Override
    public ScheduleDevicesItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
            int viewType) {
        View adapterLayout = LayoutInflater.from(mContext)
                .inflate(R.layout.view_schedule_devices_card, parent, false);
        return new ScheduleDevicesItemViewHolder(adapterLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleDevicesItemViewHolder holder, int position) {
        holder.mCardView.setUiModel(mDeviceUiModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mDeviceUiModels.size();
    }

    static class ScheduleDevicesItemViewHolder extends RecyclerView.ViewHolder {
        private final ScheduleDeviceCardView mCardView;

        public ScheduleDevicesItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            mCardView = new ScheduleDeviceCardView(itemView);
        }
    }
}
