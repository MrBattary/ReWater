package michael.linker.rewater.data.repository.networks;

public class NetworksRepositoryAlreadyExistsException extends RuntimeException {
    public NetworksRepositoryAlreadyExistsException(String message) {
        super(message);
    }
}
