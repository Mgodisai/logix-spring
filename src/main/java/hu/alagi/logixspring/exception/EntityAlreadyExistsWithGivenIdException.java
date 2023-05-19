package hu.alagi.logixspring.exception;

public class EntityAlreadyExistsWithGivenIdException extends RuntimeException {

    public EntityAlreadyExistsWithGivenIdException(long id, Class<?> clazz) {
        super(clazz.getSimpleName()+ " is already exists with id: "+id);
    }
}
