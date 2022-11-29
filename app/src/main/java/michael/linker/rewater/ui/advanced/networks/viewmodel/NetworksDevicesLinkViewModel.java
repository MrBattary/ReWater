package michael.linker.rewater.ui.advanced.networks.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import michael.linker.rewater.data.model.IdNameModel;

public class NetworksDevicesLinkViewModel extends ViewModel {
    private final MutableLiveData<IdNameModel> mParentNetworkIdName;

    public NetworksDevicesLinkViewModel() {
        mParentNetworkIdName = new MutableLiveData<>();
    }

    public LiveData<IdNameModel> getParentNetworkIdName() {
        return mParentNetworkIdName;
    }

    public void setParentNetworkIdName(final IdNameModel networkId) {
        mParentNetworkIdName.setValue(networkId);
    }
}
