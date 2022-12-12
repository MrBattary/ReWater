package michael.linker.rewater.ui.advanced.home.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.repository.history.IHistoryRepository;
import michael.linker.rewater.data.repository.user.IUsersRepository;
import michael.linker.rewater.data.web.api.common.request.PageSizeCommonRequest;
import michael.linker.rewater.ui.advanced.home.model.HomeHistoryUiModel;
import michael.linker.rewater.ui.advanced.profile.model.ProfileUiModel;

public class HomeViewModel extends ViewModel {
    private final IUsersRepository mUsersRepository;
    private final IHistoryRepository mHistoryRepository;

    private final MutableLiveData<ProfileUiModel> mProfileModel;
    private final MutableLiveData<List<HomeHistoryUiModel>> mHistoryList;

    public HomeViewModel() {
        mUsersRepository = RepositoryConfiguration.getUsersRepository();
        mHistoryRepository = RepositoryConfiguration.getHistoryRepository();

        mProfileModel = new MutableLiveData<>();
        mHistoryList = new MutableLiveData<>();
    }

    public void requireProfileUpdate() {
        mProfileModel.setValue(new ProfileUiModel(mUsersRepository.getActiveUserData()));
    }

    public LiveData<ProfileUiModel> getProfileModel() {
        return mProfileModel;
    }

    public MutableLiveData<List<HomeHistoryUiModel>> getHistoryList() {
        return mHistoryList;
    }

    public void requireHistoryUpdate(PageSizeCommonRequest request) {
        Single.fromCallable(() -> mHistoryRepository.getAllHistory(request))
                .doOnSuccess(historyEventList ->
                        mHistoryList.postValue(
                                historyEventList.stream()
                                        .map(HomeHistoryUiModel::new)
                                        .collect(Collectors.toList())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}