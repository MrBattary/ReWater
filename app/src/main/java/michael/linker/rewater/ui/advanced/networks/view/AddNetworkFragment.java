package michael.linker.rewater.ui.advanced.networks.view;

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
import michael.linker.rewater.config.DataConfiguration;
import michael.linker.rewater.data.model.NewNetworkModel;
import michael.linker.rewater.data.network.INetworksData;
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.data.model.FullNetworkModel;
import michael.linker.rewater.ui.advanced.networks.viewmodel.AddNetworkViewModel;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;
import michael.linker.rewater.ui.elementary.input.text.ITextInputView;
import michael.linker.rewater.ui.elementary.input.text.TextInputView;

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
        List<String> alreadyTakenNetworksNames = mNetworksData.getNetworkList().getNetworkModelList()
                .stream()
                .map(FullNetworkModel::getHeading)
                .collect(Collectors.toList());
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
                mNetworksData.addNetwork(new NewNetworkModel(heading, description));
                Navigation.findNavController(view).navigateUp();
            } catch (InputNotAllowedException e) {
                Log.w(TAG, e.getMessage());
            }
        });

        mCancelButton.setOnClickListener(l -> Navigation.findNavController(view).navigateUp());
    }
}