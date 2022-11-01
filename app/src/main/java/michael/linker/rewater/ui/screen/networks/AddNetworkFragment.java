package michael.linker.rewater.ui.screen.networks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import michael.linker.rewater.R;

public class AddNetworkFragment extends Fragment {

    private AddNetworkViewModel mViewModel;

    private AppBarConfiguration mAppBarConfiguration;

    public static AddNetworkFragment newInstance() {
        return new AddNetworkFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_network, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Navigation.findNavController(view).popBackStack();

        mViewModel = new ViewModelProvider(this).get(AddNetworkViewModel.class);
    }
}