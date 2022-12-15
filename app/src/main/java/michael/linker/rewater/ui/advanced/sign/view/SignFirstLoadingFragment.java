package michael.linker.rewater.ui.advanced.sign.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import michael.linker.rewater.R;
import michael.linker.rewater.activity.intent.SignOutIntent;
import michael.linker.rewater.config.BuildConfiguration;
import michael.linker.rewater.core.permission.PermissionManager;
import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.sign.viewmodel.SignLoadingViewModel;
import michael.linker.rewater.ui.advanced.sign.viewmodel.SignLoadingViewModelBlockedException;
import michael.linker.rewater.ui.advanced.sign.viewmodel.SignLoadingViewModelFailedException;
import michael.linker.rewater.ui.advanced.sign.viewmodel.SignViewModelFailedException;
import michael.linker.rewater.ui.elementary.text.status.IStatusStyledTextView;
import michael.linker.rewater.ui.elementary.text.status.StatusStyledColoredTextView;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;

public class SignFirstLoadingFragment extends Fragment {
    private boolean isNavigatedOut;
    private IStatusStyledTextView mStageTextView;
    private MaterialButton mRetryButton;
    private TextView mVersionTextView;

    private SignLoadingViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.navigation_sign);
        mViewModel = new ViewModelProvider(viewModelStoreOwner).get(SignLoadingViewModel.class);

        this.checkAndRequestAwaitedPermissions();

        return inflater.inflate(R.layout.fragment_sign_loading, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initFields(view);
    }

    private void initFields(final View view) {
        mStageTextView = new StatusStyledColoredTextView(
                view.findViewById(R.id.sign_loading_stage));
        mViewModel.getStageMessage().observe(getViewLifecycleOwner(),
                m -> mStageTextView.setText(m, Status.OK));
        mViewModel.getErrorStageMessage().observe(getViewLifecycleOwner(),
                m -> mStageTextView.setText(m, Status.DEFECT));

        mVersionTextView = view.findViewById(R.id.sign_loading_app_version);
        mVersionTextView.setText(BuildConfiguration.getVersionName());

        mRetryButton = view.findViewById(R.id.sign_loading_retry_button);
        mRetryButton.setOnClickListener(l -> this.firstLoading());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (SignOutIntent.INSTANCE.unpack(
                requireActivity().getIntent()).getExpectedSignOutValue()) {
            NavHostFragment.findNavController(this).navigate(
                    R.id.navigation_action_sign_first_loading_to_sign_in);
            isNavigatedOut = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.firstLoading();
    }

    private void firstLoading() {
        if (!isNavigatedOut) {
            NavController navController = NavHostFragment.findNavController(this);
            mViewModel.setInitStageMessage(
                    StringsProvider.getString(R.string.loading_stage_permissions));
            mViewModel.checkPermissions()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<>() {
                        @Override
                        public void onSubscribe(
                                @io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        }

                        @Override
                        public void onSuccess(
                                @io.reactivex.rxjava3.annotations.NonNull Boolean aBoolean) {
                            mViewModel.checkInternetConnection()
                                    .flatMap(ic -> mViewModel.checkServerConnection())
                                    .flatMap(sc -> mViewModel.loadDatabase())
                                    .flatMap(d -> mViewModel.autoSignIn())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new SingleObserver<>() {
                                        @Override
                                        public void onSubscribe(
                                                @io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                        }

                                        @Override
                                        public void onSuccess(
                                                @io.reactivex.rxjava3.annotations.NonNull Boolean aBoolean) {
                                            if (!isNavigatedOut) {
                                                navController.navigate(
                                                        R.id.navigation_action_sign_first_loading_to_sign_in_loading);
                                                isNavigatedOut = true;
                                            }
                                        }

                                        @Override
                                        public void onError(
                                                @io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                            if (e instanceof SignViewModelFailedException) {
                                                if (!isNavigatedOut) {
                                                    navController.navigate(
                                                            R.id.navigation_action_sign_first_loading_to_sign_in);
                                                    isNavigatedOut = true;
                                                }
                                                return;
                                            }
                                            if (e instanceof SignLoadingViewModelFailedException) {
                                                mRetryButton.setVisibility(View.VISIBLE);
                                                return;
                                            }
                                            if (!(e instanceof SignLoadingViewModelBlockedException)) {
                                                ToastProvider.showShort(requireActivity(),
                                                        e.getMessage());
                                                mViewModel.postErrorStageMessage(
                                                        StringsProvider.getString(
                                                                R.string.loading_stage_failure));
                                            }
                                        }
                                    });
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        }
                    });
        }
    }

    private void checkAndRequestAwaitedPermissions() {
        final List<String> awaitedPermissions = PermissionManager.getAwaitedPermissions();
        if (awaitedPermissions.size() > 0) {
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                    isGrantedMap -> {
                        if (!isGrantedMap.containsValue(false)) {
                            this.firstLoading();
                        }
                    }).launch(awaitedPermissions.toArray(new String[0]));
        }

    }
}