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
import michael.linker.rewater.ui.advanced.devices.viewmodel.AddPairNewDeviceViewModel;

public class AddPairNewDeviceFragment extends Fragment {

    private AddPairNewDeviceViewModel mViewModel;

    public static AddPairNewDeviceFragment newInstance() {
        return new AddPairNewDeviceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_devices_add_pair_new, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddPairNewDeviceViewModel.class);
        // TODO: Use the ViewModel
    }

}