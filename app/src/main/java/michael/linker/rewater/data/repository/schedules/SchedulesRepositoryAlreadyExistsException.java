package michael.linker.rewater.data.repository.schedules;

public class SchedulesRepositoryAlreadyExistsException extends RuntimeException {
    public SchedulesRepositoryAlreadyExistsException(String message) {
        super(message);
    }
}
