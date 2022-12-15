package michael.linker.rewater.config;

import michael.linker.rewater.data.repository.devices.DevicesLocalRepository;
import michael.linker.rewater.data.repository.devices.DevicesWebRepository;
import michael.linker.rewater.data.repository.devices.IDevicesRepository;
import michael.linker.rewater.data.repository.history.HistoryLocalRepository;
import michael.linker.rewater.data.repository.history.HistoryWebRepository;
import michael.linker.rewater.data.repository.history.IHistoryRepository;
import michael.linker.rewater.data.repository.networks.INetworksRepository;
import michael.linker.rewater.data.repository.networks.NetworksLocalRepository;
import michael.linker.rewater.data.repository.networks.NetworksWebRepository;
import michael.linker.rewater.data.repository.schedules.ISchedulesRepository;
import michael.linker.rewater.data.repository.schedules.SchedulesLocalRepository;
import michael.linker.rewater.data.repository.schedules.SchedulesWebRepository;
import michael.linker.rewater.data.repository.user.IUsersRepository;
import michael.linker.rewater.data.repository.user.UsersLocalRepository;

public class RepositoryConfiguration {
    private static final BuildConfiguration.Server SERVER_MODE = BuildConfiguration.getServerMode();
    private static final BuildConfiguration.Server MODE_LOCAL = BuildConfiguration.Server.LOCAL;
    private static final BuildConfiguration.Server MODE_GLOBAL = BuildConfiguration.Server.GLOBAL;

    private static INetworksRepository sNetworksRepository;
    private static IDevicesRepository sDevicesRepository;
    private static ISchedulesRepository sSchedulesRepository;
    private static IUsersRepository sUserRepository;
    private static IHistoryRepository sHistoryRepository;

    public static INetworksRepository getNetworksRepository() {
        if (sNetworksRepository == null) {
            if (SERVER_MODE == MODE_LOCAL) {
                sNetworksRepository = new NetworksLocalRepository();
            }
            if (SERVER_MODE == MODE_GLOBAL) {
                sNetworksRepository = new NetworksWebRepository();
            }
        }
        return sNetworksRepository;
    }

    public static IDevicesRepository getDevicesRepository() {
        if (sDevicesRepository == null) {
            if (SERVER_MODE == MODE_LOCAL) {
                sDevicesRepository = new DevicesLocalRepository();
            }
            if (SERVER_MODE == MODE_GLOBAL) {
                sDevicesRepository = new DevicesWebRepository();
            }
        }
        return sDevicesRepository;
    }

    public static ISchedulesRepository getSchedulesRepository() {
        if (sSchedulesRepository == null) {
            if (SERVER_MODE == MODE_LOCAL) {
                sSchedulesRepository = new SchedulesLocalRepository();
            }
            if (SERVER_MODE == MODE_GLOBAL) {
                sSchedulesRepository = new SchedulesWebRepository();
            }
        }
        return sSchedulesRepository;
    }

    public static IUsersRepository getUsersRepository() {
        if (sUserRepository == null) {
            // TODO (ML): Remove stub
            if (SERVER_MODE == MODE_LOCAL) {
                sUserRepository = new UsersLocalRepository();
            }
            if (SERVER_MODE == MODE_GLOBAL) {
                sUserRepository = new UsersLocalRepository();
            }
        }
        return sUserRepository;
    }

    public static IHistoryRepository getHistoryRepository() {
        if (sHistoryRepository == null) {
            if (SERVER_MODE == MODE_LOCAL) {
                sHistoryRepository = new HistoryLocalRepository(
                        new HistoryLocalRepository.GenerationConfig(
                                12, 20
                        ));
            }
            if (SERVER_MODE == MODE_GLOBAL) {
                sHistoryRepository = new HistoryWebRepository();
            }
        }
        return sHistoryRepository;
    }
}
