package michael.linker.rewater.ui.advanced.sign.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;

import michael.linker.rewater.R;
import michael.linker.rewater.activity.ActivityGate;
import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.ui.advanced.sign.viewmodel.SignLoadingViewModel;
import michael.linker.rewater.ui.advanced.sign.viewmodel.SignLoadingViewModelFailedException;
import michael.linker.rewater.ui.advanced.sign.viewmodel.SignViewModelFailedException;
import michael.linker.rewater.ui.elementary.text.status.IStatusStyledTextView;
import michael.linker.rewater.ui.elementary.text.status.StatusStyledColoredTextView;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;

public class SignFirstLoadingFragment extends Fragment {
    private IStatusStyledTextView mStageTextView;
    private MaterialButton mExitButton;

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

    private void initFields(final View view) {
        mStageTextView = new StatusStyledColoredTextView(
                view.findViewById(R.id.sign_loading_stage));
        mViewModel.getStageMessage().observe(getViewLifecycleOwner(),
                m -> mStageTextView.setText(m, Status.OK));
        mViewModel.getErrorStageMessage().observe(getViewLifecycleOwner(),
                m -> mStageTextView.setText(m, Status.DEFECT));

        mExitButton = view.findViewById(R.id.sign_loading_exit_button);
        mExitButton.setOnClickListener(l -> ActivityGate.finishApplication(requireActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        this.firstLoading();
    }

    private void firstLoading() {
        NavController navController = NavHostFragment.findNavController(this);
        try {
            mViewModel.checkInternetConnection();
            mViewModel.checkServerConnection();
            mViewModel.loadDatabase();
            mViewModel.autoSignIn();
            navController.navigate(R.id.navigation_action_sign_first_loading_to_sign_in_loading);
        } catch (SignViewModelFailedException e) {
            ToastProvider.showShort(requireActivity(), e.getMessage());
            navController.navigate(R.id.navigation_action_sign_first_loading_to_sign_in);
        } catch (SignLoadingViewModelFailedException ignored) {
            mExitButton.setVisibility(View.VISIBLE);
        }
    }
}