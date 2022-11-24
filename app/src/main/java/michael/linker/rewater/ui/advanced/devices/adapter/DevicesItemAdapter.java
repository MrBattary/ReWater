package michael.linker.rewater.ui.advanced.devices.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.devices.model.DeviceCardUiModel;
import michael.linker.rewater.ui.advanced.devices.view.part.DevicesCardView;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModel;
import michael.linker.rewater.ui.animation.transition.IOrderedTransition;

public class DevicesItemAdapter extends
        RecyclerView.Adapter<DevicesItemAdapter.DevicesItemViewHolder> {
    private final Context mContext;
    private final DevicesViewModel mParentViewModel;
    private final List<DeviceCardUiModel> mCompactDeviceModels;
    private final IOrderedTransition mTransition;

    public DevicesItemAdapter(
            final Context context,
            final DevicesViewModel parentViewModel,
            final List<DeviceCardUiModel> compactDeviceModels,
            final IOrderedTransition transition) {
        mContext = context;
        mParentViewModel = parentViewModel;
        mCompactDeviceModels = compactDeviceModels;
        mTransition = transition;
    }

    @NonNull
    @Override
    public DevicesItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View adapterLayout = LayoutInflater.from(mContext)
                .inflate(R.layout.view_devices_card, parent, false);
        return new DevicesItemViewHolder(mContext, adapterLayout, mParentViewModel, mTransition);

    }

    @Override
    public void onBindViewHolder(@NonNull DevicesItemViewHolder holder, int position) {
        holder.mDevicesCardView.setDataModel(mCompactDeviceModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mCompactDeviceModels.size();
    }

    static class DevicesItemViewHolder extends RecyclerView.ViewHolder {
        private final DevicesCardView mDevicesCardView;

        public DevicesItemViewHolder(
                final Context context,
                final View view,
                final DevicesViewModel parentViewModel,
                final IOrderedTransition transition) {
            super(view);
            mDevicesCardView = new DevicesCardView(context, view, parentViewModel, transition);
        }
    }
}
