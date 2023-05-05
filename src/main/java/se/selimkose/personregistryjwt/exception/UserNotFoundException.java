package se.selimkose.personregistryjwt.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Integer userId){
        super("The person with student id: '" + userId + " does not exist in our records");
    }
}
