package michael.linker.rewater.ui.advanced.networks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.NetworkListModel;
import michael.linker.rewater.ui.advanced.networks.view.NetworksCardView;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksViewModel;
import michael.linker.rewater.ui.animation.transition.IOrderedTransition;

public class NetworksItemAdapter extends
        RecyclerView.Adapter<NetworksItemAdapter.NetworksItemViewHolder> {
    private final Context mContext;
    private final NetworksViewModel mParentViewModel;
    private final NetworkListModel mNetworkListModel;
    private final IOrderedTransition mTransition;

    public NetworksItemAdapter(final Context context,
            final NetworksViewModel parentViewModel,
            final NetworkListModel networkListModel,
            final IOrderedTransition transition) {
        mContext = context;
        mParentViewModel = parentViewModel;
        mNetworkListModel = networkListModel;
        mTransition = transition;
    }

    @NonNull
    @Override
    public NetworksItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View adapterLayout = LayoutInflater.from(mContext)
                .inflate(R.layout.view_networks_card, parent, false);
        return new NetworksItemViewHolder(adapterLayout, mParentViewModel, mTransition);
    }

    @Override
    public void onBindViewHolder(@NonNull NetworksItemViewHolder holder, int position) {
        holder.mNetworksCardView.setFullNetworkModel(
                mNetworkListModel.getNetworkModelList().get(position));
    }

    @Override
    public int getItemCount() {
        return mNetworkListModel.getNetworkModelList().size();
    }

    static class NetworksItemViewHolder extends RecyclerView.ViewHolder {
        private final NetworksCardView mNetworksCardView;

        private NetworksItemViewHolder(
                final View view,
                final NetworksViewModel parentViewModel,
                final IOrderedTransition transition) {
            super(view);
            mNetworksCardView = new NetworksCardView(view, parentViewModel, transition);
        }
    }
}
