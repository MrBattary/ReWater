package michael.linker.rewater.ui.advanced.devices.view;

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
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModel;

public class AddDeviceOptionsFragment extends Fragment {
    private DevicesViewModel mViewModel;
    private MaterialButton mPairNewButton, mAlreadyPairedButton, mCancelButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_devices);
        mViewModel = new ViewModelProvider(viewModelStoreOwner).get(DevicesViewModel.class);

        return inflater.inflate(R.layout.fragment_devices_add_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initFields(view);
        this.initButtons(view);
    }

    private void initFields(final View view) {
        mPairNewButton = view.findViewById(R.id.devices_add_options_pair_new_button);
        mAlreadyPairedButton = view.findViewById(R.id.devices_add_options_already_paired_button);
        mCancelButton = view.findViewById(R.id.devices_add_options_cancel_button);
    }

    private void initButtons(final View view) {
        NavController navController = Navigation.findNavController(view);
        mPairNewButton.setOnClickListener(l -> navController.navigate(
                R.id.navigation_action_devices_add_options_to_devices_add_pair_new));
        mAlreadyPairedButton.setOnClickListener(l -> navController.navigate(
                R.id.navigation_action_devices_add_options_to_devices_add_already_paired));
        mCancelButton.setOnClickListener(
                l -> navController.popBackStack(R.id.navigation_devices_add_options, true));
    }
}