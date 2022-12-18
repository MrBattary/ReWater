package michael.linker.rewater.ui.advanced.networks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.networks.model.NetworkHistoryUiModel;
import michael.linker.rewater.ui.elementary.history.HistoryCardView;
import michael.linker.rewater.ui.elementary.history.model.HistoryCardScheduleDateTimeModel;

public class NetworksHistoryItemAdapter extends
        RecyclerView.Adapter<NetworksHistoryItemAdapter.HomeHistoryItemViewHolder> {
    private final Context mContext;
    private final List<NetworkHistoryUiModel> mHistoryModels;

    public NetworksHistoryItemAdapter(
            Context context,
            List<NetworkHistoryUiModel> historyModels) {
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
                new HistoryCardScheduleDateTimeModel(mHistoryModels.get(position)));
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
