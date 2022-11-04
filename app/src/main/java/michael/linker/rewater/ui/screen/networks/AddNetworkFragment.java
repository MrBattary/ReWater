package michael.linker.rewater.ui.screen.networks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.R;
import michael.linker.rewater.assist.App;
import michael.linker.rewater.config.DataConfiguration;
import michael.linker.rewater.data.network.INetworksData;
import michael.linker.rewater.model.local.network.NetworkModel;
import michael.linker.rewater.ui.view.primitive.input.ITextInputView;
import michael.linker.rewater.ui.view.primitive.input.InputNotAllowedException;
import michael.linker.rewater.ui.view.primitive.input.TextInputView;

public class AddNetworkFragment extends Fragment {
    private static final String TAG = "AddNetworkFragment";
    private ITextInputView mHeadingInput, mDescriptionInput;
    private MaterialButton mCreateButton, mCancelButton;
    private INetworksData mNetworksData;
    private AddNetworkViewModel mViewModel;

    public static AddNetworkFragment newInstance() {
        return new AddNetworkFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AddNetworkViewModel.class);

        return inflater.inflate(R.layout.fragment_add_network, container, false);
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
        mNetworksData = DataConfiguration.getNetworksData();
    }

    private void initInputs() {
        List<String> alreadyTakenNetworksNames = mNetworksData.getNetworks().getNetworkModelList()
                .stream()
                .map(NetworkModel::getHeading)
                .collect(Collectors.toList());
        mHeadingInput.setBlacklist(alreadyTakenNetworksNames,
                App.getRes().getString(R.string.input_error_heading_taken));
        mHeadingInput.setMaxLimit(App.getRes().getInteger(R.integer.input_max_limit_header),
                App.getRes().getString(R.string.input_error_heading_overflow));
        mHeadingInput.setMinLimit(App.getRes().getInteger(R.integer.input_min_limit_header),
                App.getRes().getString(R.string.input_error_heading_lack));
        mDescriptionInput.setMaxLimit(
                App.getRes().getInteger(R.integer.input_max_limit_description),
                App.getRes().getString(R.string.input_error_description_overflow));
    }

    private void initButtons(@NonNull final View view) {
        final int actionUpId = R.id.navigation_action_networks_add_to_networks;

        mCreateButton.setOnClickListener(l -> {
            try {
                final String heading = mHeadingInput.getText();
                final String description = mDescriptionInput.getText();
                mNetworksData.addNetwork(new NetworkModel(heading, description));
                Navigation.findNavController(view).navigate(actionUpId);
            } catch (InputNotAllowedException e) {
                Log.w(TAG, e.getMessage());
            }
        });

        mCancelButton.setOnClickListener(l -> Navigation.findNavController(view).navigate(
                actionUpId));
    }
}