package michael.linker.rewater.ui.advanced.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.home.model.HomeHistoryUiModel;
import michael.linker.rewater.ui.elementary.history.HistoryCardView;
import michael.linker.rewater.ui.elementary.history.model.HistoryCardNetworkScheduleDateTimeModel;

public class HomeHistoryItemAdapter extends
        RecyclerView.Adapter<HomeHistoryItemAdapter.HomeHistoryItemViewHolder> {
    private final Context mContext;
    private final List<HomeHistoryUiModel> mHistoryModels;

    public HomeHistoryItemAdapter(
            Context context,
            List<HomeHistoryUiModel> historyModels) {
        mContext = context;
        mHistoryModels = historyModels;
    }

    @NonNull
    @Override
    public HomeHistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View adapterLayout = LayoutInflater.from(mContext)
                .inflate(R.layout.view_history_card, parent, false);
        return new HomeHistoryItemViewHolder(adapterLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHistoryItemViewHolder holder, int position) {
        holder.mHistoryCardView.setCardContent(
                new HistoryCardNetworkScheduleDateTimeModel(mHistoryModels.get(position)));
    }

    @Override
    public int getItemCount() {
        return mHistoryModels.size();
    }

    static class HomeHistoryItemViewHolder extends RecyclerView.ViewHolder {
        private final HistoryCardView mHistoryCardView;

        public HomeHistoryItemViewHolder(final View view) {
            super(view);
            mHistoryCardView = new HistoryCardView(view);
        }
    }
}
