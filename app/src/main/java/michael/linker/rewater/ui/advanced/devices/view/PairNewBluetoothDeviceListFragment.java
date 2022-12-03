package michael.linker.rewater.ui.advanced.devices.view;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;

import me.aflak.bluetooth.constants.DiscoveryError;
import me.aflak.bluetooth.interfaces.BluetoothCallback;
import me.aflak.bluetooth.interfaces.DeviceCallback;
import me.aflak.bluetooth.interfaces.DiscoveryCallback;
import michael.linker.rewater.R;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.devices.model.BluetoothDeviceUiModel;
import michael.linker.rewater.ui.advanced.devices.viewmodel.PairNewDeviceBluetoothViewModel;
import michael.linker.rewater.ui.elementary.dialog.IDialog;
import michael.linker.rewater.ui.elementary.dialog.none.NoneChoiceDialogModel;
import michael.linker.rewater.ui.elementary.dialog.none.NoneChoiceInfoDialog;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;

public class PairNewBluetoothDeviceListFragment extends Fragment {
    private PairNewDeviceBluetoothViewModel mBluetoothViewModel;

    private List<String> mFoundDeviceMacList;
    private ArrayAdapter<BluetoothDeviceListItemView> mAdapter;
    private NavController mViewNavController;

    private ListView mListView;
    private MenuItem mDoneMenuItem;
    private IDialog mDeviceConnectionDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_devices);
        mBluetoothViewModel = new ViewModelProvider(viewModelStoreOwner).get(
                PairNewDeviceBluetoothViewModel.class);

        return inflater.inflate(R.layout.fragment_devices_add_pair_new_ble_device, container,
                false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewNavController = Navigation.findNavController(view);

        this.initViews(view);
        this.initCallbacks();
        this.initDialogs();
        this.addMenuProvider();
    }

    private void initViews(final View view) {
        mListView = view.findViewById(R.id.add_device_pair_new_ble_list);
        mAdapter = new ArrayAdapter<>(requireContext(),
                R.layout.view_custom_list_item_multiple_choise,
                new ArrayList<>());
        mListView.setAdapter(mAdapter);
        mFoundDeviceMacList = new ArrayList<>();
    }

    private void initCallbacks() {
        mBluetoothViewModel.setBluetoothCallback(mBluetoothCallback);
        mBluetoothViewModel.setDiscoveryCallback(mDiscoveryCallback);
        mBluetoothViewModel.setDeviceCallback(mConnectDeviceCallback);
    }

    private void initDialogs() {
        mDeviceConnectionDialog = new NoneChoiceInfoDialog(requireContext(),
                new NoneChoiceDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_info),
                        StringsProvider.getString(R.string.title_loading),
                        StringsProvider.getString(
                                R.string.dialog_bluetooth_connecting_in_progress)));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBluetoothViewModel.isConnectedToDevice()) {
            mBluetoothViewModel.disconnectFromDevice();
        }
        mBluetoothViewModel.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBluetoothViewModel.isEnabled()) {
            mBluetoothViewModel.stopScanning();
            mBluetoothViewModel.startScanning();
        } else {
            showErrorToastAndFinishFragment(R.string.bluetooth_failure_turned_off);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mBluetoothViewModel.stopScanning();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDeviceConnectionDialog.dismiss();
        mBluetoothViewModel.stopScanning();
        mBluetoothViewModel.removeCallbacks();
        mBluetoothViewModel.onStop();
    }

    private void addMenuProvider() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.list_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.list_done_button) {
                    mDoneMenuItem = menuItem;
                    mDoneMenuItem.setEnabled(false);
                    mBluetoothViewModel.stopScanning();
                    for (int i = 0; i < mListView.getCount(); i++) {
                        if (mListView.isItemChecked(i)) {
                            final PairNewBluetoothDeviceListFragment.BluetoothDeviceListItemView
                                    chosenDevice =
                                    (PairNewBluetoothDeviceListFragment.BluetoothDeviceListItemView)
                                            mListView.getItemAtPosition(i);
                            mDeviceConnectionDialog.show();
                            mBluetoothViewModel.connectToDevice(chosenDevice.mBluetoothDevice);
                            mListView.setEnabled(false);
                            return true;
                        }
                    }
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private static class BluetoothDeviceListItemView {
        private final BluetoothDevice mBluetoothDevice;
        private final boolean mIsPaired;

        public BluetoothDeviceListItemView(BluetoothDevice bluetoothDevice) {
            mBluetoothDevice = bluetoothDevice;
            mIsPaired = false;
        }

        public BluetoothDeviceListItemView(BluetoothDevice bluetoothDevice, boolean isPaired) {
            mBluetoothDevice = bluetoothDevice;
            mIsPaired = isPaired;
        }

        @SuppressLint("MissingPermission")
        @NonNull
        @Override
        public String toString() {
            return (mBluetoothDevice.getName() != null
                    ? mBluetoothDevice.getName()
                    : mBluetoothDevice.getAddress());
        }
    }

    private final BluetoothCallback mBluetoothCallback = new BluetoothCallback() {
        @Override
        public void onBluetoothTurningOn() {
        }

        @Override
        public void onBluetoothOn() {
        }

        @Override
        public void onBluetoothTurningOff() {
            showErrorToastAndFinishFragment(R.string.bluetooth_failure_turned_off);
        }

        @Override
        public void onBluetoothOff() {
            showErrorToastAndFinishFragment(R.string.bluetooth_failure_turned_off);
        }

        @Override
        public void onUserDeniedActivation() {
            showErrorToastAndFinishFragment(R.string.bluetooth_failure_permission_not_found);
        }
    };

    private final DiscoveryCallback mDiscoveryCallback = new DiscoveryCallback() {
        @Override
        public void onDiscoveryStarted() {
            /*
            If already paired devices required

            mBDAdapter.addAll(
                    mBluetooth.getPairedDevices().stream()
                            .map(bluetoothDevice ->
                                    new BluetoothDeviceListItemView(bluetoothDevice, true))
                            .collect(Collectors.toList()));*/
        }

        @Override
        public void onDiscoveryFinished() {
            mBluetoothViewModel.startScanning();
        }

        @SuppressLint("MissingPermission")
        @Override
        public void onDeviceFound(BluetoothDevice device) {
            if (!mFoundDeviceMacList.contains(device.getAddress())) {
                mFoundDeviceMacList.add(device.getAddress());
                mAdapter.add(new BluetoothDeviceListItemView(device));
            }
        }

        @Override
        public void onDevicePaired(BluetoothDevice device) {
        }

        @Override
        public void onDeviceUnpaired(BluetoothDevice device) {
        }

        @Override
        public void onError(int errorCode) {
            if (errorCode == DiscoveryError.BLUETOOTH_DISABLED) {
                showErrorToastAndFinishFragment(R.string.bluetooth_failure_turned_off);
            } else {
                showErrorToastAndFinishFragment(R.string.bluetooth_failure_scan);
            }
        }
    };

    private final DeviceCallback mConnectDeviceCallback = new DeviceCallback() {
        @SuppressLint("MissingPermission")
        @Override
        public void onDeviceConnected(BluetoothDevice device) {
            mBluetoothViewModel.setConnectedDeviceData(
                    new BluetoothDeviceUiModel(
                            device.getName(),
                            device.getAddress()));
            mViewNavController.navigateUp();
        }

        @Override
        public void onDeviceDisconnected(BluetoothDevice device, String message) {
            mBluetoothViewModel.setConnectedDeviceData(null);
        }

        @Override
        public void onMessage(byte[] message) {

        }

        @Override
        public void onError(int errorCode) {

        }

        @Override
        public void onConnectError(BluetoothDevice device, String message) {
            ToastProvider.showShort(requireContext(),
                    StringsProvider.getString(R.string.bluetooth_failure_connect));
            mListView.setEnabled(true);
            mBluetoothViewModel.startScanning();
            mDoneMenuItem.setEnabled(true);
            mDeviceConnectionDialog.dismiss();
        }
    };

    private void showErrorToastAndFinishFragment(final int errorMsgId) {
        ToastProvider.showLong(requireContext(), StringsProvider.getString(errorMsgId));
        mViewNavController.navigateUp();
    }
}
