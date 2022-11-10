package michael.linker.rewater.config;

import michael.linker.rewater.data.repository.devices.DevicesRepository;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;
import michael.linker.rewater.data.repository.networks.INetworksRepository;
import michael.linker.rewater.data.repository.networks.NetworksRepository;

public class RepositoryConfiguration {
    private static INetworksRepository sNetworksRepository;
    private static IDevicesRepository sDevicesRepository;

    public static INetworksRepository getNetworksRepository() {
        if (sNetworksRepository == null) {
            sNetworksRepository = new NetworksRepository();
        }
        return sNetworksRepository;
    }

    public static IDevicesRepository getDevicesRepository() {
        if (sDevicesRepository == null) {
            sDevicesRepository = new DevicesRepository();
        }
        return sDevicesRepository;
    }
}
