package michael.linker.rewater.ui.advanced.networks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.networks.model.NetworkUiModel;
import michael.linker.rewater.ui.advanced.networks.view.part.NetworksCardView;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksDevicesLinkViewModel;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksViewModel;
import michael.linker.rewater.ui.animation.transition.IOrderedTransition;

public class NetworksItemAdapter extends
        RecyclerView.Adapter<NetworksItemAdapter.NetworksItemViewHolder> {
    private final Context mContext;
    private final NetworksViewModel mParentViewModel;
    private final NetworksDevicesLinkViewModel mLinkViewModel;
    private final List<NetworkUiModel> mNetworkRepositoryModels;
    private final IOrderedTransition mTransition;

    public NetworksItemAdapter(final Context context,
            final NetworksViewModel parentViewModel,
            final NetworksDevicesLinkViewModel linkViewModel,
            final List<NetworkUiModel> networkRepositoryModels,
            final IOrderedTransition transition) {
        mContext = context;
        mParentViewModel = parentViewModel;
        mLinkViewModel = linkViewModel;
        mNetworkRepositoryModels = networkRepositoryModels;
        mTransition = transition;
    }

    @NonNull
    @Override
    public NetworksItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View adapterLayout = LayoutInflater.from(mContext)
                .inflate(R.layout.view_networks_card, parent, false);
        return new NetworksItemViewHolder(mContext, adapterLayout, mParentViewModel, mLinkViewModel,
                mTransition);
    }

    @Override
    public void onBindViewHolder(@NonNull NetworksItemViewHolder holder, int position) {
        holder.mNetworksCardView.setDataModel(mNetworkRepositoryModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mNetworkRepositoryModels.size();
    }

    static class NetworksItemViewHolder extends RecyclerView.ViewHolder {
        private final NetworksCardView mNetworksCardView;

        private NetworksItemViewHolder(
                final Context context,
                final View view,
                final NetworksViewModel parentViewModel,
                final NetworksDevicesLinkViewModel linkViewModel,
                final IOrderedTransition transition) {
            super(view);
            mNetworksCardView = new NetworksCardView(context, view, parentViewModel, linkViewModel,
                    transition);
        }
    }
}
