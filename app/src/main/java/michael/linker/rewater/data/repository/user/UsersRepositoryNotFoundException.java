package michael.linker.rewater.data.repository.user;

public class UsersRepositoryNotFoundException extends RuntimeException {
    public UsersRepositoryNotFoundException(String message) {
        super(message);
    }
}
