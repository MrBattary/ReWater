package michael.linker.rewater.ui.advanced.networks.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import michael.linker.rewater.config.DataConfiguration;
import michael.linker.rewater.data.model.FullNetworkModel;
import michael.linker.rewater.data.model.NetworkListModel;

public class NetworksViewModel extends ViewModel {
    private final MutableLiveData<NetworkListModel> mNetworkList;
    private final MutableLiveData<FullNetworkModel> mEditableNetworkModel;

    public NetworksViewModel() {
        mNetworkList = new MutableLiveData<>();
        mEditableNetworkModel = new MutableLiveData<>();
        mNetworkList.setValue(
                DataConfiguration.getNetworksData().getNetworkList());
    }

    public LiveData<NetworkListModel> getNetworkList() {
        return mNetworkList;
    }

    public LiveData<FullNetworkModel> getModelOfEditableNetwork() {
        return mEditableNetworkModel;
    }

    public void setEditableNetworkModel(final FullNetworkModel model) {
        mEditableNetworkModel.setValue(model);
    }
}