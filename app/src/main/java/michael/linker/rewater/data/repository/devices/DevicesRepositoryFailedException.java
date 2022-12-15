package michael.linker.rewater.data.repository.devices;

public class DevicesRepositoryFailedException extends RuntimeException {
    public DevicesRepositoryFailedException() {
    }

    public DevicesRepositoryFailedException(String message) {
        super(message);
    }
}