package michael.linker.rewater.data.repository.devices;

import java.util.List;
import java.util.stream.Collectors;

import michael.linker.rewater.R;
import michael.linker.rewater.data.repository.devices.model.CreateDeviceRepositoryModel;
import michael.linker.rewater.data.repository.devices.model.DeviceRepositoryModel;
import michael.linker.rewater.data.repository.devices.model.ManualWateringDeviceRepositoryModel;
import michael.linker.rewater.data.repository.devices.model.UpdateDeviceRepositoryModel;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.data.web.api.devices.DevicesApi;
import michael.linker.rewater.data.web.api.devices.request.CreateDeviceRequest;
import michael.linker.rewater.data.web.api.devices.request.ManualWateringDeviceRequest;
import michael.linker.rewater.data.web.api.devices.request.UpdateDeviceRequest;
import michael.linker.rewater.data.web.gate.exceptions.status.BadRequestHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.NotFoundHttpException;

public class DevicesWebRepository implements IDevicesRepository {
    private final DevicesApi mApi;

    public DevicesWebRepository() {
        mApi = new DevicesApi();
    }

    @Override
    public List<DeviceRepositoryModel> getDeviceList() {
        return mApi.getAllDevices().stream()
                .map(DeviceRepositoryModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeviceRepositoryModel> getDeviceAttachList() {
        return mApi.getAttachableDevices().stream()
                .map(DeviceRepositoryModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public DeviceRepositoryModel getDeviceById(String deviceId)
            throws DevicesRepositoryNotFoundException {
        try {
            return new DeviceRepositoryModel(mApi.getDeviceById(deviceId));
        } catch (NotFoundHttpException e) {
            throw new DevicesRepositoryNotFoundException(String.format(
                    StringsProvider.getString(R.string.internal_repository_device_not_found),
                    deviceId));
        }
    }

    @Override
    public DeviceRepositoryModel getDeviceByHardwareId(String hardwareId)
            throws DevicesRepositoryNotFoundException {
        try {
            return new DeviceRepositoryModel(mApi.getDeviceByHardwareId(hardwareId));
        } catch (NotFoundHttpException e) {
            throw new DevicesRepositoryNotFoundException(String.format(
                    StringsProvider.getString(R.string.internal_repository_device_not_found),
                    hardwareId));
        }
    }

    @Override
    public void removeDevice(String deviceId) {
        mApi.deleteDevice(deviceId);
    }

    @Override
    public void updateDevice(String deviceId, UpdateDeviceRepositoryModel model)
            throws DevicesRepositoryNotFoundException {
        try {
            mApi.updateDevice(deviceId, new UpdateDeviceRequest(model));
        } catch (NotFoundHttpException e) {
            throw new DevicesRepositoryNotFoundException(String.format(
                    StringsProvider.getString(R.string.internal_repository_device_not_found),
                    deviceId));
        }
    }

    @Override
    public void createDevice(CreateDeviceRepositoryModel model)
            throws DevicesRepositoryAlreadyExistsException {
        try {
            mApi.createDevice(new CreateDeviceRequest(model));
        } catch (BadRequestHttpException e) {
            throw new DevicesRepositoryAlreadyExistsException(String.format(
                    StringsProvider.getString(R.string.internal_repository_device_already_exists),
                    model.getName()));
        }
    }

    @Override
    public void manualWatering(String deviceId, ManualWateringDeviceRepositoryModel model)
            throws DevicesRepositoryNotFoundException, DevicesRepositoryFailedException {
        try {
            mApi.manualWatering(deviceId, new ManualWateringDeviceRequest(model));
        } catch (NotFoundHttpException e) {
            throw new DevicesRepositoryNotFoundException(String.format(
                    StringsProvider.getString(R.string.internal_repository_device_not_found),
                    deviceId));
        } catch (BadRequestHttpException e) {
            throw new DevicesRepositoryFailedException(
                    StringsProvider.getString(R.string.manual_watering_failure_overflow));
        }
    }
}
