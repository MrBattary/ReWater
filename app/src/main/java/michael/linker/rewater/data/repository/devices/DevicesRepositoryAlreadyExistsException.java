package michael.linker.rewater.data.repository.devices;

public class DevicesRepositoryAlreadyExistsException extends RuntimeException {
    public DevicesRepositoryAlreadyExistsException(String message) {
        super(message);
    }
}
