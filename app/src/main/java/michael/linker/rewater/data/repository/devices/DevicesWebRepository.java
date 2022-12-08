package michael.linker.rewater.data.repository.devices;

import java.util.ArrayList;
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
import michael.linker.rewater.data.web.gate.exceptions.group.ClientErrorException;
import michael.linker.rewater.data.web.gate.exceptions.status.BadRequestHttpException;
import michael.linker.rewater.data.web.gate.exceptions.status.NotFoundHttpException;

public class DevicesWebRepository implements IDevicesRepository {
    private final DevicesApi mApi;

    public DevicesWebRepository() {
        mApi = new DevicesApi();
    }

    @Override
    public List<DeviceRepositoryModel> getDeviceList() {
        try {
            return mApi.getAllDevices().stream()
                    .map(DeviceRepositoryModel::new)
                    .collect(Collectors.toList());
        } catch (ClientErrorException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<DeviceRepositoryModel> getDeviceAttachList() {
        try {
            return mApi.getAttachableDevices().stream()
                    .map(DeviceRepositoryModel::new)
                    .collect(Collectors.toList());
        } catch (ClientErrorException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public DeviceRepositoryModel getDeviceById(String deviceId)
            throws DevicesRepositoryNotFoundException {
        try {
            return new DeviceRepositoryModel(mApi.getDeviceById(deviceId));
        } catch (NotFoundHttpException e) {
            throw new DevicesRepositoryNotFoundException(
                    "Requested device with id: " + deviceId + " was not found!");
        }
    }

    @Override
    public DeviceRepositoryModel getDeviceByHardware(String hardwareId)
            throws DevicesRepositoryNotFoundException {
        try {
            return new DeviceRepositoryModel(mApi.getDeviceById(hardwareId));
        } catch (NotFoundHttpException e) {
            throw new DevicesRepositoryNotFoundException(
                    "Requested device with hardware id: " + hardwareId + " was not found!");
        }
    }

    @Override
    public void removeDevice(String deviceId) {
        try {
            mApi.deleteDevice(deviceId);
        } catch (ClientErrorException ignored) {
        }
    }

    @Override
    public void updateDevice(String deviceId, UpdateDeviceRepositoryModel model)
            throws DevicesRepositoryNotFoundException {
        try {
            mApi.updateDevice(deviceId, new UpdateDeviceRequest(model));
        } catch (NotFoundHttpException e) {
            throw new DevicesRepositoryNotFoundException(
                    "Requested device with id: " + deviceId + " was not found!");
        }
    }

    @Override
    public void createDevice(CreateDeviceRepositoryModel model)
            throws DevicesRepositoryAlreadyExistsException {
        try {
            mApi.createDevice(new CreateDeviceRequest(model));
        } catch (NotFoundHttpException e) {
            throw new DevicesRepositoryAlreadyExistsException(
                    "Device with name: " + model.getName() + " already exists!");
        }
    }

    @Override
    public void manualWatering(String deviceId, ManualWateringDeviceRepositoryModel model)
            throws DevicesRepositoryNotFoundException, DevicesRepositoryFailedException {
        try {
            mApi.manualWatering(deviceId, new ManualWateringDeviceRequest(model));
        } catch (NotFoundHttpException e) {
            throw new DevicesRepositoryNotFoundException(
                    "Requested device with id: " + deviceId + " was not found!");
        } catch (BadRequestHttpException e) {
            throw new DevicesRepositoryFailedException(
                    StringsProvider.getString(R.string.manual_watering_failure_overflow));
        }
    }
}
