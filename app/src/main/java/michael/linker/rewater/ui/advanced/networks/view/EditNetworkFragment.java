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

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import michael.linker.rewater.R;
import michael.linker.rewater.data.repository.networks.model.NetworkModel;
import michael.linker.rewater.data.repository.networks.model.UpdateNetworkModel;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksViewModel;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksViewModelFailedException;
import michael.linker.rewater.ui.elementary.dialog.IDialog;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesWarningDialog;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;
import michael.linker.rewater.ui.elementary.input.text.ITextInputView;
import michael.linker.rewater.ui.elementary.input.text.TextInputView;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesWarningDialogModel;

public class EditNetworkFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_networks_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFields(view);
        mViewModel.getEditableNetworkModel().observe(getViewLifecycleOwner(),
                this::initInputs);
        mViewModel.getEditableNetworkId().observe(getViewLifecycleOwner(), id -> {
            initDialogs(view, id);
            initButtons(view, id);
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

    private void initInputs(final NetworkModel compactModel) {
        List<String> alreadyTakenNetworksNames =
                mViewModel.getAlreadyTakenNetworkNames().getValue();
        List<String> alreadyTakenNetworkNamesExceptThis = Collections.emptyList();
        if (alreadyTakenNetworksNames != null) {
            alreadyTakenNetworkNamesExceptThis = alreadyTakenNetworksNames
                    .stream()
                    .filter(heading -> !Objects.equals(heading, compactModel.getHeading()))
                    .collect(Collectors.toList());
        }

        mHeadingInput.setBlacklist(alreadyTakenNetworkNamesExceptThis,
                StringsProvider.getString(R.string.input_error_heading_taken));
        mHeadingInput.setMaxLimit(IntegersProvider.getInteger(R.integer.input_max_limit_header),
                StringsProvider.getString(R.string.input_error_heading_overflow));
        mHeadingInput.setMinLimit(IntegersProvider.getInteger(R.integer.input_min_limit_header),
                StringsProvider.getString(R.string.input_error_heading_lack));
        mDescriptionInput.setMaxLimit(
                IntegersProvider.getInteger(R.integer.input_max_limit_description),
                StringsProvider.getString(R.string.input_error_description_overflow));
        mHeadingInput.setText(compactModel.getHeading());
        mDescriptionInput.setText(compactModel.getDescription());
    }

    private void initButtons(@NonNull final View view, final String networkId) {
        mDeleteButton.setOnClickListener(l -> mOnDeleteDialog.show());

        mSaveButton.setOnClickListener(l -> {
            try {
                final String heading = mHeadingInput.getText();
                final String description = mDescriptionInput.getText();
                mViewModel.updateNetwork(
                        networkId,
                        new UpdateNetworkModel(heading, description)
                );
                Navigation.findNavController(view).navigateUp();
            } catch (NetworksViewModelFailedException e) {
                ToastProvider.showShort(requireContext(), e.getMessage());
            } catch (InputNotAllowedException ignored) {
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