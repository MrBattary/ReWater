package michael.linker.rewater.ui.advanced.devices.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.devices.viewmodel.AddAlreadyPairedDeviceViewModel;

public class AddAlreadyPairedDeviceFragment extends Fragment {

    private AddAlreadyPairedDeviceViewModel mViewModel;

    public static AddAlreadyPairedDeviceFragment newInstance() {
        return new AddAlreadyPairedDeviceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_devices_add_already_paired, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddAlreadyPairedDeviceViewModel.class);
        // TODO: Use the ViewModel
    }

}