package michael.linker.rewater.data.repository.user;

public class UsersRepositoryAlreadyExistsException extends RuntimeException {
    public UsersRepositoryAlreadyExistsException(String message) {
        super(message);
    }
}
