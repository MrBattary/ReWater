package michael.linker.rewater.data.web.gate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HttpGateStatusObserver {
    private final MutableLiveData<Boolean> isInternetAccessible, isServerAccessible;

    public HttpGateStatusObserver() {
        isInternetAccessible = new MutableLiveData<>();
        isServerAccessible = new MutableLiveData<>();
    }

    public LiveData<Boolean> isInternetAccessible() {
        return isInternetAccessible;
    }

    public void notifyInternetAccessible() {
        isInternetAccessible.postValue(true);
    }

    public void notifyInternetNotAccessible() {
        isInternetAccessible.postValue(false);
    }

    public LiveData<Boolean> isServerAccessible() {
        return isServerAccessible;
    }

    public void notifyServerAccessible() {
        isServerAccessible.postValue(true);
    }

    public void notifyServerNotAccessible() {
        isServerAccessible.postValue(false);
    }
}
