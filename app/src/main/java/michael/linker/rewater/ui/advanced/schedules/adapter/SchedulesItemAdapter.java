package michael.linker.rewater.ui.advanced.schedules.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.schedules.model.ScheduleUiModel;
import michael.linker.rewater.ui.advanced.schedules.view.part.ScheduleCardView;
import michael.linker.rewater.ui.advanced.schedules.viewmodel.SchedulesViewModel;
import michael.linker.rewater.ui.animation.transition.IOrderedTransition;

public class SchedulesItemAdapter extends
        RecyclerView.Adapter<SchedulesItemAdapter.SchedulesItemViewHolder> {
    private final Context mContext;
    private final SchedulesViewModel mParentViewModel;
    private final List<ScheduleUiModel> mScheduleUiModels;
    private final IOrderedTransition mTransition;

    public SchedulesItemAdapter(
            final Context context,
            final SchedulesViewModel parentViewModel,
            final List<ScheduleUiModel> scheduleUiModels,
            final IOrderedTransition transition) {
        mContext = context;
        mScheduleUiModels = scheduleUiModels;
        mParentViewModel = parentViewModel;
        mTransition = transition;
    }

    @NonNull
    @Override
    public SchedulesItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
            int viewType) {
        View adapterLayout = LayoutInflater.from(mContext)
                .inflate(R.layout.view_schedules_card, parent, false);
        return new SchedulesItemViewHolder(mContext, adapterLayout, mParentViewModel, mTransition);
    }

    @Override
    public void onBindViewHolder(@NonNull SchedulesItemViewHolder holder, int position) {
        holder.mCardView.setUiModel(mScheduleUiModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mScheduleUiModels.size();
    }

    static class SchedulesItemViewHolder extends RecyclerView.ViewHolder {
        private final ScheduleCardView mCardView;

        public SchedulesItemViewHolder(
                final Context context,
                @NonNull final View itemView,
                final SchedulesViewModel parentViewModel,
                final IOrderedTransition transition) {
            super(itemView);
            mCardView = new ScheduleCardView(context, itemView, parentViewModel, transition);
        }
    }
}
