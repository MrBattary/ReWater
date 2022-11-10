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
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import michael.linker.rewater.R;
import michael.linker.rewater.data.web.model.EditableNetworkModel;
import michael.linker.rewater.data.web.model.FullNetworkModel;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksViewModel;
import michael.linker.rewater.ui.elementary.dialog.IDialog;
import michael.linker.rewater.ui.elementary.dialog.TwoChoicesWarningDialog;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;
import michael.linker.rewater.ui.elementary.input.text.ITextInputView;
import michael.linker.rewater.ui.elementary.input.text.TextInputView;
import michael.linker.rewater.ui.model.TwoChoicesWarningDialogModel;

public class EditNetworkFragment extends Fragment {
    private static final String TAG = "AddNetworkFragment";
    private ITextInputView mHeadingInput, mDescriptionInput;
    private MaterialButton mDeleteButton, mSaveButton, mCancelButton;
    private IDialog mOnDeleteDialog;
    private NetworksViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_networks);
        mViewModel = new ViewModelProvider(viewModelStoreOwner).get(NetworksViewModel.class);
        return inflater.inflate(R.layout.fragment_edit_network, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFields(view);
        mViewModel.getEditableNetworkModel().observe(getViewLifecycleOwner(), fullNetworkModel -> {
            initInputs(fullNetworkModel);
            initDialogs(view, fullNetworkModel.getId());
            initButtons(view, fullNetworkModel.getId());
        });
    }

    private void initFields(@NonNull final View view) {
        mHeadingInput = new TextInputView(view.findViewById(R.id.edit_network_heading),
                view.findViewById(R.id.edit_network_heading_input));
        mDescriptionInput = new TextInputView(view.findViewById(R.id.edit_network_description),
                view.findViewById(R.id.edit_network_description_input));
        mDeleteButton = view.findViewById(R.id.edit_network_delete_button);
        mSaveButton = view.findViewById(R.id.edit_network_save_button);
        mCancelButton = view.findViewById(R.id.edit_network_cancel_button);
    }

    private void initInputs(final FullNetworkModel fullNetworkModel) {
        List<String> alreadyTakenNetworksNames =
                Objects.requireNonNull(mViewModel.getNetworkList().getValue())
                        .getNetworkModelList()
                        .stream()
                        .map(FullNetworkModel::getHeading)
                        .filter(heading -> !Objects.equals(heading, fullNetworkModel.getHeading()))
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
        mHeadingInput.setText(fullNetworkModel.getHeading());
        mDescriptionInput.setText(fullNetworkModel.getDescription());
    }

    private void initButtons(@NonNull final View view, final String networkId) {
        mDeleteButton.setOnClickListener(l -> mOnDeleteDialog.show());

        mSaveButton.setOnClickListener(l -> {
            try {
                final String heading = mHeadingInput.getText();
                final String description = mDescriptionInput.getText();
                mViewModel.updateNetwork(
                        networkId,
                        new EditableNetworkModel(heading, description)
                );
                Navigation.findNavController(view).navigateUp();
            } catch (InputNotAllowedException e) {
                Log.w(TAG, e.getMessage());
            }
        });

        mCancelButton.setOnClickListener(l -> Navigation.findNavController(view).navigateUp());
    }

    private void initDialogs(@NonNull final View view, final String networkId) {
        mOnDeleteDialog = new TwoChoicesWarningDialog(requireContext(),
                new TwoChoicesWarningDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_warning),
                        StringsProvider.getString(R.string.title_warning),
                        StringsProvider.getString(R.string.dialog_delete_network_part_1),
                        StringsProvider.getString(R.string.button_delete),
                        StringsProvider.getString(R.string.button_cancel)
                ),
                (dialogInterface, i) -> {
                    mViewModel.removeNetwork(networkId);
                    dialogInterface.cancel();
                    Navigation.findNavController(view).navigateUp();
                },
                (dialogInterface, i) -> dialogInterface.cancel()
        );
    }
}