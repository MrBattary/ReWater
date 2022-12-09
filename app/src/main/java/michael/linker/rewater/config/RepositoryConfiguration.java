package michael.linker.rewater.config;

import michael.linker.rewater.data.repository.devices.DevicesWebRepository;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;
import michael.linker.rewater.data.repository.networks.INetworksRepository;
import michael.linker.rewater.data.repository.networks.NetworksWebRepository;
import michael.linker.rewater.data.repository.schedules.ISchedulesRepository;
import michael.linker.rewater.data.repository.schedules.SchedulesWebRepository;
import michael.linker.rewater.data.repository.user.IUsersRepository;
import michael.linker.rewater.data.repository.user.UsersLocalRepository;

public class RepositoryConfiguration {
    private static INetworksRepository sNetworksRepository;
    private static IDevicesRepository sDevicesRepository;
    private static ISchedulesRepository sSchedulesRepository;
    private static IUsersRepository sUserRepository;

    public static INetworksRepository getNetworksRepository() {
        if (sNetworksRepository == null) {
            // TODO (ML): Remove stub
            //sNetworksRepository = new NetworksLocalRepository();
            sNetworksRepository = new NetworksWebRepository();
        }
        return sNetworksRepository;
    }

    public static IDevicesRepository getDevicesRepository() {
        if (sDevicesRepository == null) {
            // TODO (ML): Remove stub
            //sDevicesRepository = new DevicesLocalRepository();
            sDevicesRepository = new DevicesWebRepository();
        }
        return sDevicesRepository;
    }

    public static ISchedulesRepository getSchedulesRepository() {
        if (sSchedulesRepository == null) {
            // TODO (ML): Remove stub
            //sSchedulesRepository = new SchedulesLocalRepository();
            sSchedulesRepository = new SchedulesWebRepository();
        }
        return sSchedulesRepository;
    }

    public static IUsersRepository getUsersRepository() {
        if (sUserRepository == null) {
            // TODO (ML): Remove stub
            sUserRepository = new UsersLocalRepository();
        }
        return sUserRepository;
    }
}
