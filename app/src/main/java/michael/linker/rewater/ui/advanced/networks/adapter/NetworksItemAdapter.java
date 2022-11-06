package michael.linker.rewater.ui.advanced.networks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import michael.linker.rewater.R;
import michael.linker.rewater.model.local.network.NetworksModel;
import michael.linker.rewater.ui.animation.transition.OrderedTransition;
import michael.linker.rewater.ui.advanced.networks.view.NetworksCardView;

public class NetworksItemAdapter extends
        RecyclerView.Adapter<NetworksItemAdapter.NetworksItemViewHolder> {
    private final Context mContext;
    private final NetworksModel mNetworksModel;
    private final OrderedTransition mTransition;

    public NetworksItemAdapter(final Context context,
            final NetworksModel networksModel,
            final OrderedTransition transition) {
        mContext = context;
        mNetworksModel = networksModel;
        mTransition = transition;
    }

    @NonNull
    @Override
    public NetworksItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View adapterLayout = LayoutInflater.from(mContext).inflate(R.layout.view_networks_card,
                parent,
                false);
        mTransition.addChangeBoundsTarget(adapterLayout);
        return new NetworksItemViewHolder(adapterLayout, mTransition);
    }

    @Override
    public void onBindViewHolder(@NonNull NetworksItemViewHolder holder, int position) {
        holder.mNetworksCardView.setData(mNetworksModel.getNetworkModelList().get(position));
    }

    @Override
    public int getItemCount() {
        return mNetworksModel.getNetworkModelList().size();
    }

    static class NetworksItemViewHolder extends RecyclerView.ViewHolder {
        private final NetworksCardView mNetworksCardView;

        private NetworksItemViewHolder(final View view, final OrderedTransition transition) {
            super(view);
            mNetworksCardView = new NetworksCardView(view, transition);
        }
    }
}
