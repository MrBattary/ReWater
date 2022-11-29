package michael.linker.rewater.ui.advanced.networks.view;

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

import java.util.List;
import java.util.Objects;

import michael.linker.rewater.R;
import michael.linker.rewater.data.repository.networks.model.CreateOrUpdateNetworkRepositoryModel;
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksViewModel;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksViewModelFailedException;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;
import michael.linker.rewater.ui.elementary.input.text.ITextInputView;
import michael.linker.rewater.ui.elementary.input.text.TextInputView;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;

public class AddNetworkFragment extends Fragment {
    private ITextInputView mHeadingInput, mDescriptionInput;
    private MaterialButton mCreateButton, mCancelButton;
    private NetworksViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_networks);
        mViewModel = new ViewModelProvider(viewModelStoreOwner).get(NetworksViewModel.class);

        return inflater.inflate(R.layout.fragment_networks_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initFields(view);
        this.initInputs();
        this.initButtons(view);
    }

    private void initFields(@NonNull final View view) {
        mHeadingInput = new TextInputView(view.findViewById(R.id.add_network_heading),
                view.findViewById(R.id.add_network_heading_input));
        mDescriptionInput = new TextInputView(view.findViewById(R.id.add_network_description),
                view.findViewById(R.id.add_network_description_input));
        mCreateButton = view.findViewById(R.id.add_network_create_button);
        mCancelButton = view.findViewById(R.id.add_network_cancel_button);
    }

    private void initInputs() {
        List<String> alreadyTakenNetworksNames = Objects.requireNonNull(
                mViewModel.getAlreadyTakenNetworkNames().getValue());
        mHeadingInput.setBlacklist(alreadyTakenNetworksNames,
                StringsProvider.getString(R.string.input_error_heading_taken));
        mHeadingInput.setMaxLimit(IntegersProvider.getInteger(R.integer.input_max_limit_header),
                StringsProvider.getString(R.string.input_error_heading_overflow));
        mHeadingInput.setMinLimit(IntegersProvider.getInteger(R.integer.input_min_limit_header),
                StringsProvider.getString(R.string.input_error_heading_lack));
        mDescriptionInput.setMaxLimit(
                IntegersProvider.getInteger(R.integer.input_max_limit_description),
                StringsProvider.getString(R.string.input_error_description_overflow));
    }

    private void initButtons(@NonNull final View view) {
        mCreateButton.setOnClickListener(l -> {
            try {
                final String heading = mHeadingInput.getText();
                final String description = mDescriptionInput.getText();
                mViewModel.addNetwork(new CreateOrUpdateNetworkRepositoryModel(heading, description));
                Navigation.findNavController(view).navigateUp();
            } catch (NetworksViewModelFailedException e) {
                ToastProvider.showShort(requireContext(), e.getMessage());
            } catch (InputNotAllowedException ignored) {
            }
        });

        mCancelButton.setOnClickListener(l -> Navigation.findNavController(view).navigateUp());
    }
}