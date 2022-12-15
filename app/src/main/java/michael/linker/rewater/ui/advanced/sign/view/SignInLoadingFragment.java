package michael.linker.rewater.ui.advanced.sign.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import michael.linker.rewater.R;
import michael.linker.rewater.activity.ActivityGate;
import michael.linker.rewater.config.BuildConfiguration;
import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.sign.viewmodel.SignLoadingViewModel;
import michael.linker.rewater.ui.elementary.text.status.IStatusStyledTextView;
import michael.linker.rewater.ui.elementary.text.status.StatusStyledColoredTextView;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;

public class SignInLoadingFragment extends Fragment {
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

        return inflater.inflate(R.layout.fragment_sign_loading, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initFields(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.loadUserData();
    }

    private void initFields(final View view) {
        mStageTextView = new StatusStyledColoredTextView(
                view.findViewById(R.id.sign_loading_stage));
        mViewModel.getStageMessage().observe(getViewLifecycleOwner(),
                m -> mStageTextView.setText(m, Status.OK));
        mViewModel.getErrorStageMessage().observe(getViewLifecycleOwner(),
                m -> mStageTextView.setText(m, Status.DEFECT));
        mViewModel.setInitStageMessage(
                StringsProvider.getString(R.string.loading_stage_repository_installation));

        mVersionTextView = view.findViewById(R.id.sign_loading_app_version);
        mVersionTextView.setText(BuildConfiguration.getVersionName());

        mRetryButton = view.findViewById(R.id.sign_loading_retry_button);
        mRetryButton.setOnClickListener(l -> this.loadUserData());
    }

    private void loadUserData() {
        mViewModel.initRepositories()
                .flatMap(ir -> mViewModel.initData())
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
                        ActivityGate.moveToMainActivity(requireActivity());
                    }

                    @Override
                    public void onError(
                            @io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        ToastProvider.showLong(requireActivity(), e.getMessage());
                        mRetryButton.setVisibility(View.VISIBLE);
                    }
                });
    }
}