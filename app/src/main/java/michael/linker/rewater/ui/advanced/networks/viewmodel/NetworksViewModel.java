package michael.linker.rewater.ui.advanced.networks.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import michael.linker.rewater.config.DataConfiguration;
import michael.linker.rewater.data.web.INetworksData;
import michael.linker.rewater.data.web.model.EditableNetworkModel;
import michael.linker.rewater.data.web.model.FullNetworkModel;
import michael.linker.rewater.data.web.model.NetworkListModel;

public class NetworksViewModel extends ViewModel {
    private final INetworksData mNetworksData;
    private final MutableLiveData<NetworkListModel> mNetworkList;
    private final MutableLiveData<FullNetworkModel> mEditableNetworkModel;

    public NetworksViewModel() {
        mNetworksData = DataConfiguration.getNetworksData();
        mNetworkList = new MutableLiveData<>();
        mEditableNetworkModel = new MutableLiveData<>();
        mNetworkList.setValue(mNetworksData.getNetworkList());
    }

    public LiveData<NetworkListModel> getNetworkList() {
        return mNetworkList;
    }

    public LiveData<FullNetworkModel> getEditableNetworkModel() {
        return mEditableNetworkModel;
    }

    public void setEditableNetworkId(final String id) {
        mEditableNetworkModel.setValue(mNetworksData.getNetworkById(id));
    }

    public void updateNetwork(final String id, final EditableNetworkModel model) {
        final FullNetworkModel fullModel = mNetworksData.getNetworkById(id);
        mNetworksData.updateNetwork(id, new FullNetworkModel(
                fullModel.getId(),
                model,
                fullModel.getStatus()
        ));
    }

    public void removeNetwork(final String id) {
        mNetworksData.removeNetwork(id);
    }

    public void addNetwork(final EditableNetworkModel model) {
        mNetworksData.addNetwork(model);
    }
}