package michael.linker.rewater.ui.screen.networks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import michael.linker.rewater.R;
import michael.linker.rewater.model.local.network.NetworkModel;
import michael.linker.rewater.model.local.network.NetworkModelBundle;

public class EditNetworkFragment extends Fragment {

    private EditNetworkViewModel mViewModel;

    public static EditNetworkFragment newInstance() {
        return new EditNetworkFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(EditNetworkViewModel.class);
        return inflater.inflate(R.layout.fragment_edit_network, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            NetworkModel networkModel = new NetworkModelBundle().unpack(getArguments());
            final String a = networkModel.getDescription();
        }
        // TODO: Use the ViewModel
    }
}