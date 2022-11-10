package michael.linker.rewater.config;

import michael.linker.rewater.data.repository.networks.INetworksRepository;
import michael.linker.rewater.data.repository.networks.NetworksRepository;

public class RepositoryConfiguration {
    private static INetworksRepository sNetworksRepository;

    public static INetworksRepository getNetworksRepository() {
        if (sNetworksRepository == null) {
            sNetworksRepository = new NetworksRepository();
        }
        return sNetworksRepository;
    }
}
